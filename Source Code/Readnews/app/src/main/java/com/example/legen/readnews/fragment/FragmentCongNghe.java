package com.example.legen.readnews.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.legen.readnews.library.News;
import com.example.legen.readnews.R;
import com.example.legen.readnews.adapter.NewsAdapter;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by legen on 3/23/2017.
 */

public class FragmentCongNghe extends Fragment {
    private static List<News> newsList = new ArrayList<>();
    private RecyclerView recyclerView;
    public static NewsAdapter mAdapter;
    public static String link, title, image,type;
    public static WebSocketClient client;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_item, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        mAdapter = new NewsAdapter(newsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        connectWebSocket();
        return rootView;
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"Websocket Opened",Toast.LENGTH_SHORT).show();
                    }
                });

                onGetLink("Cong Nghe");
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
                            Log.d("connect","success");
                            JSONArray array = obj.getJSONArray("RLinks");
                            for(int i=0;i<array.length();i++){
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
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(),"Get Data Failed",Toast.LENGTH_SHORT).show();
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
    public static void History(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("Topic","HISTORY");
            obj.put("Type","Cong Nghe");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        client.send(obj.toString());
    }
//    private void setNewsData(){
//        newsList.clear();
//        newsList.add(new News(1, "title1", "link","imgae"));
//        newsList.add(new News(2, "title2", "link","imgae"));
//        newsList.add(new News(3, "title3", "link","imgae"));
//        newsList.add(new News(4, "title4", "link","imgae"));
//        newsList.add(new News(5, "title5", "link","imgae"));
//        mAdapter.notifyDataSetChanged();
//    }

}