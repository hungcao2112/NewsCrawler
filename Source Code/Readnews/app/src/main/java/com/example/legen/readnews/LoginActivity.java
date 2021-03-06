package com.example.legen.readnews;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class LoginActivity extends AppCompatActivity {
    private EditText edt_user, edt_password;
    private ImageButton btn_login, btn_register;
    public static String userid, password;
    private WebSocketClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        edt_user = (EditText) findViewById(R.id.email);
        edt_password = (EditText) findViewById(R.id.password);
        btn_login = (ImageButton) findViewById(R.id.login_btn);
        btn_register = (ImageButton) findViewById(R.id.register_btn);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogin();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegister();
            }
        });

        connectWebSocket();
    }
    private void connectWebSocket(){
        URI uri;
        try{
            uri = new URI("ws://192.168.1.106:8887");
        }catch(URISyntaxException e){
            e.printStackTrace();
            return;
        }

        client = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.d("Socket","Open");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this,"Websocket Opened",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onMessage(String message) {
                Log.d("Recieve",message);
                try {
                    JSONObject obj = new JSONObject(message);
                    String topic = obj.getString("Topic");
                    String rcode = obj.getString("Rcode");
                    if(topic.equals("RLOGIN")){
                        if(rcode.equals("200")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this,"Login Success",Toast.LENGTH_SHORT).show();
                                }
                            });
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else if(rcode.equals("201")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "UserId not exists",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    if(topic.equals("RREGISTER")){
                        if(rcode.equals("200")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Register Success !", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        else if(rcode.equals("202")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this,"UserId already exists !", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this,"Register Failed",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.i("Websocket","Closed" + reason);
            }

            @Override
            public void onError(Exception ex) {
                Log.i("Websocket", "Error" + ex.getMessage());
            }
        };
        client.connect();
    }

    private void onLogin(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userid = edt_user.getText().toString();
                password = edt_password.getText().toString();
                JSONObject obj = new JSONObject();
                try {
                    obj.put("Topic","LOGIN");
                    obj.put("UserId",userid);
                    obj.put("Pass",password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                client.send(obj.toString());
            }
        });
    }
    private void onRegister(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String id,pass;
                id = edt_user.getText().toString();
                pass = edt_password.getText().toString();
                JSONObject obj = new JSONObject();
                try {
                    obj.put("Topic","REGISTER");
                    obj.put("UserId",id);
                    obj.put("Pass",pass);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                client.send(obj.toString());
            }
        });
    }
}
