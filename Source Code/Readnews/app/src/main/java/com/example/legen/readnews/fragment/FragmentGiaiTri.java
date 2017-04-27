package com.example.legen.readnews.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.legen.readnews.R;
import com.example.legen.readnews.SearchActivity;
import com.example.legen.readnews.adapter.NewsAdapter;
import com.example.legen.readnews.adapter.NewsAdapter2;
import com.example.legen.readnews.library.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by legen on 3/23/2017.
 */

public class FragmentGiaiTri extends Fragment {
    private List<News> newsList0 = new ArrayList<>();
    private List<News> newsList1 = new ArrayList<>();
    private List<News> newsList2 = new ArrayList<>();
    private RecyclerView recycler0, recycler1, recycler2;
    private NewsAdapter mAdapter0;
    private NewsAdapter2 mAdapter1, mAdapter2;
    public static String link, title;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_item2, container, false);
        recycler0 = (RecyclerView) rootView.findViewById(R.id.recyclerView0);
        recycler1 = (RecyclerView) rootView.findViewById(R.id.recyclerView1);
        recycler2 = (RecyclerView) rootView.findViewById(R.id.recyclerView2);
        mAdapter0 = new NewsAdapter(newsList0);
        mAdapter1 = new NewsAdapter2(newsList1);
        mAdapter2 = new NewsAdapter2(newsList2);
        RecyclerView.LayoutManager mLayoutManager0 = new LinearLayoutManager(getActivity());
        recycler0.setLayoutManager(mLayoutManager0);
        recycler0.setItemAnimator(new DefaultItemAnimator());
        recycler0.setAdapter(mAdapter0);

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        recycler1.setLayoutManager(mLayoutManager1);
        recycler1.setItemAnimator(new DefaultItemAnimator());
        recycler1.setAdapter(mAdapter1);

        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity());
        recycler2.setLayoutManager(mLayoutManager2);
        recycler2.setItemAnimator(new DefaultItemAnimator());
        recycler2.setAdapter(mAdapter2);
        setdata();

        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putParcelableArrayListExtra("GiaiTri", (ArrayList<? extends Parcelable>) newsList1);
        return rootView;
    }

    private void setdata(){
        newsList0.clear();
        newsList0.add(new News("gy2", "Tỷ lệ ủng hộ Trump xuống mức thấp kỷ lục", "http://vnexpress.net/tin-tuc/the-gioi/ty-le-ung-ho-trump-xuong-muc-thap-ky-luc-3574623.html","type","http://img.f29.vnecdn.net/2017/04/23/170301141317-donald-trump-0228-6070-3918-1492956220.jpg"));
        newsList0.add(new News("gy2", "Tỷ lệ ủng hộ Trump xuống mức thấp kỷ lục", "http://vnexpress.net/tin-tuc/the-gioi/ty-le-ung-ho-trump-xuong-muc-thap-ky-luc-3574623.html","type","http://img.f29.vnecdn.net/2017/04/23/170301141317-donald-trump-0228-6070-3918-1492956220.jpg"));
        newsList0.add(new News("gy2", "Tỷ lệ ủng hộ Trump xuống mức thấp kỷ lục", "http://vnexpress.net/tin-tuc/the-gioi/ty-le-ung-ho-trump-xuong-muc-thap-ky-luc-3574623.html","type","http://img.f29.vnecdn.net/2017/04/23/170301141317-donald-trump-0228-6070-3918-1492956220.jpg"));
        newsList0.add(new News("gy2", "title4", "link","type","imgae"));
        newsList0.add(new News("gy2", "title5", "link","type","imgae"));
        newsList0.add(new News("gy2", "title6", "link","type","imgae"));
        newsList1.clear();
        newsList1.add(new News("gy2", "Tỷ lệ ủng hộ Trump xuống mức thấp kỷ lục", "http://vnexpress.net/tin-tuc/the-gioi/ty-le-ung-ho-trump-xuong-muc-thap-ky-luc-3574623.html","type","http://img.f29.vnecdn.net/2017/04/23/170301141317-donald-trump-0228-6070-3918-1492956220.jpg"));
        newsList1.add(new News("gy2", "Tỷ lệ ủng hộ Trump xuống mức thấp kỷ lục", "http://vnexpress.net/tin-tuc/the-gioi/ty-le-ung-ho-trump-xuong-muc-thap-ky-luc-3574623.html","type","http://img.f29.vnecdn.net/2017/04/23/170301141317-donald-trump-0228-6070-3918-1492956220.jpg"));
        newsList1.add(new News("gy2", "Tỷ lệ ủng hộ Trump xuống mức thấp kỷ lục", "http://vnexpress.net/tin-tuc/the-gioi/ty-le-ung-ho-trump-xuong-muc-thap-ky-luc-3574623.html","type","http://img.f29.vnecdn.net/2017/04/23/170301141317-donald-trump-0228-6070-3918-1492956220.jpg"));
        newsList1.add(new News("gy2", "title4", "link","type","imgae"));
        newsList1.add(new News("gy2", "title5", "link","type","imgae"));
        newsList1.add(new News("gy2", "title6", "link","type","imgae"));
        newsList2.clear();
        newsList2.add(new News("gy2", "Tỷ lệ ủng hộ Trump xuống mức thấp kỷ lục", "http://vnexpress.net/tin-tuc/the-gioi/ty-le-ung-ho-trump-xuong-muc-thap-ky-luc-3574623.html","type","http://img.f29.vnecdn.net/2017/04/23/170301141317-donald-trump-0228-6070-3918-1492956220.jpg"));
        newsList2.add(new News("gy2", "Tỷ lệ ủng hộ Trump xuống mức thấp kỷ lục", "http://vnexpress.net/tin-tuc/the-gioi/ty-le-ung-ho-trump-xuong-muc-thap-ky-luc-3574623.html","type","http://img.f29.vnecdn.net/2017/04/23/170301141317-donald-trump-0228-6070-3918-1492956220.jpg"));
        newsList2.add(new News("gy2", "Tỷ lệ ủng hộ Trump xuống mức thấp kỷ lục", "http://vnexpress.net/tin-tuc/the-gioi/ty-le-ung-ho-trump-xuong-muc-thap-ky-luc-3574623.html","type","http://img.f29.vnecdn.net/2017/04/23/170301141317-donald-trump-0228-6070-3918-1492956220.jpg"));
        newsList2.add(new News("gy2", "title4", "link","type","imgae"));
        newsList2.add(new News("gy2", "title5", "link","type","imgae"));
        newsList2.add(new News("gy2", "title6", "link","type","imgae"));

    }
}