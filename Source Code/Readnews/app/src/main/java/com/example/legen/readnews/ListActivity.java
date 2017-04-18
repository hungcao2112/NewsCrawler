package com.example.legen.readnews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.legen.readnews.adapter.NewsAdapter;
import com.example.legen.readnews.library.News;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity  {

    private List<News> newsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;
    News news;
    Intent intent = getIntent();
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        getSupportActionBar().hide();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new NewsAdapter(newsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        //newsList = (List<News>) getIntent().getSerializableExtra("saveznews");


        //setdata();

    }
//    private void setdata(){
//        newsList.clear();
//        newsList.add(news);
//        newsList.add(new News("gy2", "title2", "link","imgae"));
//        newsList.add(new News("gy3", "title3", "link","imgae"));
//        newsList.add(new News("gy4", "title4", "link","imgae"));
//        newsList.add(new News("gy5", "title5", "link","imgae"));
//    }
}
