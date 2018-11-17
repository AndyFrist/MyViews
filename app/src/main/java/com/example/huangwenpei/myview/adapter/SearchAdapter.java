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
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<String> data = new ArrayList<>();

    public SearchAdapter( Context context, List<String> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER_VIEW) {
            /*这里返回的是FooterView*/
            final View footerView = inflater.inflate(R.layout.item_recycler_footer,parent, false);
            return new FooterViewHolder(footerView);
        } else {
            /*这里返回的是普通的View*/
            View view = inflater.inflate(R.layout.item_recycler, parent,false);
            MyHolder myHolder = new MyHolder(view);
            return myHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyHolder) {

            String text = data.get(position);
            ((MyHolder)holder).name_tv.setText(text);

            if (!TextUtils.isEmpty(key)) {
                int i = text.indexOf(key);
                int i1 = text.lastIndexOf(key);

                SpannableStringBuilder builder = new SpannableStringBuilder(text);
                ForegroundColorSpan redSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.blue_light));
                builder.setSpan(redSpan, i, i+key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ((MyHolder)holder).name_tv.setText(builder);

            }
        } else if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder)holder).pull_up.setText("上拉加载更多");
            pullMoreListener.onLoadMore();
        }
    }

    @Override
    public int getItemCount() {
        return data.size() +1;
    }

    @Override
    public int getItemViewType(int position) {
        /*当position是最后一个的时候，也就是比list的数量多一个的时候，则表示FooterView*/
        if (position + 1 == data.size() + 1) {
            return TYPE_FOOTER_VIEW;
        }

        return super.getItemViewType(position);
    }
    public static final int TYPE_FOOTER_VIEW = 1;

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


    class FooterViewHolder extends RecyclerView.ViewHolder {
        /**一个很简单的ViewHolder，小伙伴可以根据自己的需求自定义*/
        TextView pull_up ;
        public FooterViewHolder(View itemView) {
            super(itemView);
            pull_up = itemView.findViewById(R.id.pull_up);
        }
    }

    public interface PullMoreListener{
        void onLoadMore();
        void onRefresh();
    }
    private PullMoreListener pullMoreListener;

    public void setPullMoreListener(PullMoreListener pullMoreListener) {
        this.pullMoreListener = pullMoreListener;
    }
}
