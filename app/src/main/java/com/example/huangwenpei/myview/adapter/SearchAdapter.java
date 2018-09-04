package com.example.huangwenpei.myview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huangwenpei.myview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * create by xuxiaopeng
 * on 2018/8/31
 * 描述：
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<String> data = new ArrayList<>();

    public SearchAdapter( Context context, List<String> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recycler, null);
        MyHolder myHolder = new MyHolder(view);


        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        String text = data.get(position);
        holder.name_tv.setText(text);

        if (!TextUtils.isEmpty(key)) {
            int i = text.indexOf(key);
            int i1 = text.lastIndexOf(key);

            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.blue_light));
            builder.setSpan(redSpan, i, i+key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.name_tv.setText(builder);

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private String key;
    public void setKey(String key) {
        this.key = key;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView name_tv;
        public MyHolder(View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.name_tv);
        }
    }
}
