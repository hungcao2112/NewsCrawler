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

public class FragmentTheGioi extends Fragment {
    private List<News> newsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;
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
        setNewsData();

        return rootView;
    }
    private void setNewsData(){
        newsList.clear();
        newsList.add(new News(1, "title1", "https://www.google.com.vn","imgae"));
        newsList.add(new News(2, "title2", "link","imgae"));
        newsList.add(new News(3, "title3", "link","imgae"));
        newsList.add(new News(4, "title4", "link","imgae"));
        newsList.add(new News(5, "title5", "link","imgae"));
        newsList.add(new News(6, "title4", "link","imgae"));
        newsList.add(new News(7, "title5", "link","imgae"));
        newsList.add(new News(8, "title4", "link","imgae"));
        newsList.add(new News(9, "title5", "link","imgae"));
        newsList.add(new News(10, "title5", "link","imgae"));
        mAdapter.notifyDataSetChanged();
    }


}
