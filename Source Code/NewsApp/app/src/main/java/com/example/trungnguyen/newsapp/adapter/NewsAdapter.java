package com.example.trungnguyen.newsapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trungnguyen.newsapp.News;
import com.example.trungnguyen.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by caonguyen on 3/17/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private List<News> newsList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, link;
        public ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            link = (TextView)itemView.findViewById(R.id.link);
            image = (ImageView)itemView.findViewById(R.id.imageView);
        }
    }

    public NewsAdapter(List<News> newsList){
        this.newsList = newsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row_list, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.link.setText(news.getLink());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}

