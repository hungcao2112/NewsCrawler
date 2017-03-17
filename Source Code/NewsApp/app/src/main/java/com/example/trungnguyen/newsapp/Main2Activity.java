package com.example.trungnguyen.newsapp;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.trungnguyen.newsapp.adapter.NewsAdapter;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private List<News> newsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;
    private WebSocketClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mAdapter = new NewsAdapter(newsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        connectWebSocket();

    }
    private void connectWebSocket(){
        URI uri;
        try{
            uri = new URI("ws://172.30.62.56:8887");
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
                        Toast.makeText(Main2Activity.this,"Websocket Opened",Toast.LENGTH_SHORT).show();
                    }
                });

                onGetLink("Bong Da");
            }

            @Override
            public void onMessage(String message) {
                Log.d("Recieve",message);
                try {
                    JSONObject obj = new JSONObject(message);
                    String topic = obj.getString("Topic");
                    String rcode = obj.getString("Rcode");
                    if(topic.equals("RGETLINK")){
                        if(rcode.equals("200")){
                            JSONArray array = obj.getJSONArray("Links");
                            for(int i=0;i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                String title = object.getString("Title");
                                String link = object.getString("Link");
                                String image = object.getString("Images");
                                Log.d("image",image);
                                newsList.add(new News(title,link,image));
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                        else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Main2Activity.this,"Get Data Failed",Toast.LENGTH_SHORT).show();
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
    public void onGetLink(String type){
        JSONObject obj = new JSONObject();
        try {
            obj.put("Topic","GETLINK");
            obj.put("Type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        client.send(obj.toString());
    }
}
