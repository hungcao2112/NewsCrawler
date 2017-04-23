package com.example.legen.readnews.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legen.readnews.NewsChildActivity;
import com.example.legen.readnews.R;
import com.example.legen.readnews.library.ItemClickListener;
import com.example.legen.readnews.library.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.legen.readnews.R.layout.item;

/**
 * Created by legen on 3/23/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private List<News> newsList;
    private List<News> savelist = new ArrayList<>();
    Context context;
    int count = 0;



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView title, link;
        public ImageView image;
        public LinearLayout linearLayout;
        public RelativeLayout item_layout;
        private ItemClickListener clickListener;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_title);
            link = (TextView) itemView.findViewById(R.id.item_link);
            image = (ImageView) itemView.findViewById(R.id.item_imageView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.item_explore);
            item_layout = (RelativeLayout) itemView.findViewById(R.id.group_layout);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onClick(v, getPosition(), true);
            return true;
        }
    }

    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(item, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.link.setText(news.getLink());
        Picasso.with(context).load(news.getImage()).into(holder.image);
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, final int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(context, "longclick clicked", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Xóa tin")
                            .setMessage("Bạn có muốn xóa tin này?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    holder.item_layout.setVisibility(View.GONE);
                                    newsList.remove(position);
                                }
                            });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.show();
                } else {
                    Intent intent_child = new Intent(context, NewsChildActivity.class);
                    intent_child.putExtra("link", news.getLink());
                    context.startActivity(intent_child);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

}