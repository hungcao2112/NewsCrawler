package com.example.legen.readnews.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.legen.readnews.R;
import com.example.legen.readnews.adapter.NewsAdapter;
import com.example.legen.readnews.library.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by legen on 3/23/2017.
 */

public class FragmentGiaiTri extends Fragment {
    private List<News> newsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;
    public static String link, title;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_item, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mAdapter = new NewsAdapter(newsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        setdata();

        return rootView;
    }

    private void setdata(){
        newsList.clear();
        newsList.add(new News("gy2", "title1", "https://www.youtube.com","type","imgae"));
        newsList.add(new News("gy2", "title2", "https://www.google.com","type","imgae"));
        newsList.add(new News("gy2", "title3", "link","https://www.facebook.com","imgae"));
        newsList.add(new News("gy2", "title4", "link","type","imgae"));
        newsList.add(new News("gy2", "title5", "link","type","imgae"));
        newsList.add(new News("gy2", "title6", "link","type","imgae"));

    }
}