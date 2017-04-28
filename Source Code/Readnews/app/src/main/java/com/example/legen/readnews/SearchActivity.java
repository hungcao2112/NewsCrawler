package com.example.legen.readnews;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SearchActivity extends AppCompatActivity {
    private List<News> newsList = new ArrayList<>();
    private List<News> filterList = new ArrayList<>();
    ImageButton btn_back, btn_search;
    EditText edt;
    RecyclerView recyclerView;
    String listtype[] ={"Thể loại","Thế Giới","Thể Thao ","Công Nghệ","Giải Trí", "Giáo Dục", "Sức Khỏe", "Du Lịch" } ;
    Spinner spinner;
    public static NewsAdapter mAdapter;
    public static String link, title, image,type;
    public static WebSocketClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();

        Initialize();

        mAdapter = new NewsAdapter(filterList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listtype);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onSearch();
                filter(edt.getText().toString());
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    test();
                    connectWebSocket(removeAccent(spinner.getItemAtPosition(position).toString()));
                    //Intent i = new Intent(SearchActivity.this, ProgressActivity.class);
                    //startActivityForResult(i, 1);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    private void Initialize(){
        spinner = (Spinner) findViewById(R.id.spinner);
        btn_back = (ImageButton) findViewById(R.id.search_back);
        btn_search = (ImageButton) findViewById(R.id.search_s);
        edt = (EditText) findViewById(R.id.search_et);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_search);
    }
    public void filter(String key){
        filterList.clear();
        //int j = 0;
//        if(newsList.get(2).getTitle().toLowerCase().contains(key))
//            Toast.makeText(SearchActivity.this,
//                    newsList.get(2).getTitle(),
//                    Toast.LENGTH_SHORT).show();
        for(int i = newsList.size()-1; i>=0; i--){
            if(newsList.get(i).getTitle().toLowerCase().contains(key)){
                filterList.add(newsList.get(i));
                //j++;
            }
        }
        //if(filterList == null){
//        newsList.clear();
//            newsList.add(new News("id","Không có tin nào được tìm thấy với từ khóa : "+key,"","type", "image"));
        mAdapter.notifyDataSetChanged();
        //}
    }

    public void test(){
        newsList.clear();
        newsList.add(new News("gy2", "Tỷ lệ ủng hộ Trump xuống mức thấp kỷ lục", "http://vnexpress.net/tin-tuc/the-gioi/ty-le-ung-ho-trump-xuong-muc-thap-ky-luc-3574623.html","type","http://img.f29.vnecdn.net/2017/04/23/170301141317-donald-trump-0228-6070-3918-1492956220.jpg"));
        newsList.add(new News("gy2", "Tỷ lệ ủng hộ Trump xuống mức thấp kỷ lục", "http://vnexpress.net/tin-tuc/the-gioi/ty-le-ung-ho-trump-xuong-muc-thap-ky-luc-3574623.html","type","http://img.f29.vnecdn.net/2017/04/23/170301141317-donald-trump-0228-6070-3918-1492956220.jpg"));
        newsList.add(new News("gy2", "Tỷ lệ ủng hộ Trump xuống mức thấp kỷ lục", "http://vnexpress.net/tin-tuc/the-gioi/ty-le-ung-ho-trump-xuong-muc-thap-ky-luc-3574623.html","type","http://img.f29.vnecdn.net/2017/04/23/170301141317-donald-trump-0228-6070-3918-1492956220.jpg"));
        newsList.add(new News("gy2", "title4", "link","type","imgae"));
        newsList.add(new News("gy2", "title5", "link","type","imgae"));
        newsList.add(new News("gy2", "title6", "link","type","imgae"));
    }

    // ham chuyen tieng viet co dau thanh khong dau
    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    private void connectWebSocket(final String choice){
        URI uri;
        try{
            uri = new URI("ws://10.0.133.81:8887");
        }catch(URISyntaxException e){
            e.printStackTrace();
            return;
        }

        client = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.d("Socket","Open Search");

                onGetLink(choice);
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
                //newsList.clear();
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            JSONObject obj = new JSONObject(message);
                            String topic = obj.getString("Topic");
                            String rcode = obj.getString("Rcode");
                            if(topic.equals("RGETLINK")){
                                if(rcode.equals("200")){
                                    Log.d("connect search","success");
                                    JSONArray array = obj.getJSONArray("RLinks");
                                    for(int i=0;i<array.length();i++){
                                        JSONObject object = array.getJSONObject(i);
                                        title = object.getString("Title");
                                        link = object.getString("Link");
                                        image = object.getString("Images");
                                        newsList.add(new News("tg"+i,title,link,choice,image));
                                        Log.d("image",image);
                                    }
                                    mAdapter.notifyDataSetChanged();
                                }
                                else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(SearchActivity.this,"Get Data Failed",Toast.LENGTH_SHORT).show();
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