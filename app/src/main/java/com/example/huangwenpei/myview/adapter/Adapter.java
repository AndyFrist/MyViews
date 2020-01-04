package com.example.huangwenpei.myview.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huangwenpei.myview.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mStrings;
    LayoutInflater inflater;

    public Adapter(Context mContext, List<String> strings) {
        this.mContext = mContext;
        this.mStrings = strings;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TViewHolder(inflater.inflate(R.layout.adapter_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String string = mStrings.get(position);
        TViewHolder viewHolder = (TViewHolder) holder;
        viewHolder.title.setText(string);
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, position+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStrings==null? 0 :mStrings.size();
    }

    class TViewHolder extends RecyclerView.ViewHolder{

        private Image iconImg;
        private TextView title;
        View mView;

        public TViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            mView = itemView.findViewById(R.id.new_menu_item);
        }
    }
}
