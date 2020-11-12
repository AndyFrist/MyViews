package com.example.huangwenpei.myview.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huangwenpei.myview.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<String> data;

    public CommentAdapter( Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_comment, parent, false);
        CommentAdapter.MyViewHolder holder = new CommentAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.MyViewHolder holder, int position) {
        holder.name_tv.setText( position % data.size() + data.get(position % data.size()));
        if (position % 2 == 0) {
            holder.icon_tv.setImageResource(R.mipmap.app_rwsp_ico);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : Integer.MAX_VALUE;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_tv;
        ImageView icon_tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.name);
            icon_tv = itemView.findViewById(R.id.icon);
        }
    }
}
