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

import com.example.legen.readnews.LoginActivity;
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

public class FragmentGoiY extends Fragment {

    private List<News> newsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;
    public static String link, title, image,type;
    public static WebSocketClient client;
    public int max;
    public static String Type_max;
    public List<String> most = new ArrayList<>();
    Context context;
    int max1 = Integer.MIN_VALUE;
    int max2 = Integer.MIN_VALUE;
    int max3 = Integer.MIN_VALUE;  //assuming integer elements in the array
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

                onGetSuggest(LoginActivity.userid);
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
                                    if (object.getInt("Count") > max1)
                                    {
                                        max3 = max2; max2 = max1; max1 = object.getInt("Count");
                                    }
                                    else if (object.getInt("Count") > max2)
                                    {
                                        max3 = max2; max2 = object.getInt("Count");
                                    }
                                    else if (object.getInt("Count") > max3)
                                    {
                                        max3 = object.getInt("Count");
                                    }
                                }
                                Log.d("max",max1 + ", " + max2 + ", " + max3 + ", ");
                                most.clear();
                                for(int j=0;j<arr.length();j++){
                                    JSONObject object = arr.getJSONObject(j);
                                    if(object.getInt("Count")!=0){
                                        if(object.getInt("Count")==max3){
                                            most.add(object.getString("Type"));
                                        }
                                        else if(object.getInt("Count")==max2){
                                            most.add(object.getString("Type"));
                                        }
                                        else if(object.getInt("Count")==max1){
                                            most.add(object.getString("Type"));
                                        }
                                    }
                                }

                            }
                            Log.d("most",most.toString());
                            for(int i=0;i<most.size();i++){
                                onGetLink(most.get(i));
                            }

                        }
                    }
                    else if(topic.equals("RGETLINK")){
                        if(rcode.equals("200")){
                            Log.d("connect","success");
                            JSONArray array = obj.getJSONArray("RLinks");
                            for(int i=0;i<10;i++){
                                JSONObject object = array.getJSONObject(i);
                                title = object.getString("Title");
                                link = object.getString("Link");
                                image = object.getString("Images");
                                type = object.getString("Type");
                                if(!image.isEmpty()){
                                    newsList.add(new News("tg" + i + 1,title,link,type,image));
                                    Log.d("image",image);
                                }
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.notifyDataSetChanged();
                                }
                            });

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
            obj.put("UserId",LoginActivity.userid);
            obj.put("Type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        client.send(obj.toString());
    }

}
