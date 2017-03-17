package com.example.trungnguyen.newsapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trungnguyen.newsapp.News;
import com.example.trungnguyen.newsapp.R;
import com.example.trungnguyen.newsapp.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trung Nguyen on 2/21/2017.
 */
public class FragmentCongNghe extends Fragment {
    private List<News> newsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ragment_congnghe, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        mAdapter = new NewsAdapter(newsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        setNewsData();
        return rootView;
    }
    private void setNewsData(){
        News news = new News("Ahihi","fuck you","fuck");
        newsList.add(news);
        mAdapter.notifyDataSetChanged();
    }
}
