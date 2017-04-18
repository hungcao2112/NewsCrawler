package com.example.legen.readnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.legen.readnews.adapter.NewsAdapter;
import com.example.legen.readnews.library.News;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Suggestions extends AppCompatActivity {

    private static List<News> newsList = new ArrayList<>();
    private RecyclerView recyclerView;
    public static NewsAdapter mAdapter;
    public static String link, title, image,type;
    public static WebSocketClient client;
    public int max;
    public static String Type_max;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new NewsAdapter(newsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Suggestions.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        connectWebSocket();
    }

    private void connectWebSocket(){
        URI uri;
        try{
            uri = new URI("ws://10.45.210.147:8887");
        }catch(URISyntaxException e){
            e.printStackTrace();
            return;
        }

        client = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.d("Socket","Open");
                Suggestions.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Suggestions.this,"Websocket Opened",Toast.LENGTH_SHORT).show();
                    }
                });

                onGetSuggest("hungcao");
            }

            @Override
            public void onMessage(String message) {
                Log.d("Recieve",message);
                try {
                    JSONObject obj = new JSONObject(message);
                    String topic = obj.getString("Topic");
                    String rcode = obj.getString("Rcode");
                    if(topic.equals("RGETSUGGEST")){
                        if(rcode.equals("200")){
                            JSONArray array = obj.getJSONArray("Data");
                            for(int i=0;i<array.length();i++){
                                JSONObject data = array.getJSONObject(i);
                                JSONArray arr = data.getJSONArray("History");
                                for(int j=0;j<arr.length();j++){
                                    JSONObject object = arr.getJSONObject(j);
                                    Log.d("count",object.getInt("Count")+"");
                                    if(j==0){
                                        max = object.getInt("Count");
                                    }
                                    else{
                                        if(max < object.getInt("Count")){
                                            max = object.getInt("Count");
                                        }
                                    }
                                }
                                Log.d("max",max + "");
                                for(int j=0;j<arr.length();j++){
                                    JSONObject object = arr.getJSONObject(j);
                                    if(object.getInt("Count")==max){
                                        Type_max = object.getString("Type");
                                    }
                                }
                                onGetLink(Type_max);
                            }
                        }
                    }
                    else if(topic.equals("RGETLINK")){
                        if(rcode.equals("200")){
                            Log.d("connect","success");
                            JSONArray array = obj.getJSONArray("RLinks");
                            for(int i=60;i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                title = object.getString("Title");
                                link = object.getString("Link");
                                image = object.getString("Images");
                                type = object.getString("Type");
                                if(!image.isEmpty()){
                                    newsList.add(new News(i+1,title,link,type,image));
                                    Log.d("image",image);
                                }
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                        else{
                            Suggestions.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Suggestions.this,"Get Data Failed",Toast.LENGTH_SHORT).show();
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
                ex.printStackTrace();
            }
        };
        client.connect();
    }
    public void onGetSuggest(String name){
        JSONObject obj = new JSONObject();
        try {
            obj.put("Topic","GETSUGGEST");
            obj.put("UserId",name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        client.send(obj.toString());

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
    public static void History(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("Topic","HISTORY");
            obj.put("Type",Type_max);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        client.send(obj.toString());
    }
}
