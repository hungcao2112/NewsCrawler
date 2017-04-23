package com.example.legen.readnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.legen.readnews.adapter.NewsAdapter;

import org.java_websocket.client.WebSocketClient;

public class SearchActivity extends AppCompatActivity {
    ImageButton btn_back, btn_search;
    EditText edt;
    RecyclerView recyclerView;
    public static NewsAdapter mAdapter;
    public static String link, title, image,type;
    public static WebSocketClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        btn_back = (ImageButton) findViewById(R.id.search_back);
        btn_search = (ImageButton) findViewById(R.id.search_s);
        edt = (EditText) findViewById(R.id.search_et);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_search);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
