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

import com.example.legen.readnews.R;
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

/**
 * Created by legen on 3/23/2017.
 */

public class FragmentTheGioi extends Fragment {

    private List<News> newsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;
    public static String link, title;
    public WebSocketClient client;
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
        newsList.clear();
        connectWebSocket();

        return rootView;
    }
    private void connectWebSocket(){
        URI uri;
        try{
            uri = new URI("ws://10.0.131.223:8887");
        }catch(URISyntaxException e){
            e.printStackTrace();
            return;
        }

        client = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.d("Socket","Open THe Gioi");

                onGetLink("The Gioi");
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

            @Override
            public void onMessage(final String message) {
                Log.d("Recieve",message);
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
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
                                        String image = object.getString("Images");
                                        newsList.add(new News("tg"+i,title,link,"The Gioi",image));
                                        Log.d("image",image);
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
                });
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

}
