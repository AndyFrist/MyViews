package com.example.huangwenpei.myview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;//上下文
    private List<String> list;//数据源

    public ViewPagerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    //ViewPager总共有几个页面
    @Override
    public int getCount() {
        return list.size();
    }
    //判断一个页面(View)是否与instantiateItem方法返回的Object一致
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    //添加视图
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //加载vp的布局
//        View inflate = View.inflate(context, R.layout.vp_item, null);
        //给布局控件赋值
//        RecyclerView recyclerView = new RecyclerView(context);
//        BaseQuickAdapter baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_test_find, list){
//
//            @Override
//            protected void convert(BaseViewHolder helper, String item) {
//                helper.setText(R.id.name, item);
//            }
//        };
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.setAdapter(baseQuickAdapter);
//        //添加一个布局（不添加无效果）
//        container.addView(recyclerView);

        TextView textView = new TextView(context);
        textView.setText(list.get(position));
        container.addView(textView);
        return textView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        //移除视图
        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
