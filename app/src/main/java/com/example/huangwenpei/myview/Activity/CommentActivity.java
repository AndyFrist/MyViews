package com.example.huangwenpei.myview.Activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.huangwenpei.myview.Bean.GetAllDevicesBean;
import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.Util.MyLinearLayoutManager;
import com.example.huangwenpei.myview.View.CenterLayoutManager;
import com.example.huangwenpei.myview.View.CommentRecycleView;
import com.example.huangwenpei.myview.adapter.CommentAdapter;
import com.example.huangwenpei.myview.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CommentActivity extends AppCompatActivity {

    private CommentRecycleView  commentView ;
    private ArrayList<String> data = new ArrayList<>();
    private int index = 0;
    private RecyclerView tabView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        commentView = findViewById(R.id.commentView);
        tabView = findViewById(R.id.tabView);
        viewPager = findViewById(R.id.viewPager);

        data.add("美国");
        data.add("美国超");
        data.add("美国超500");
        data.add("美国超500家");
        data.add("美国超500家大企业");
        data.add("美国超500家大企业申请");
        data.add("美国超500家大企业申请破产");


        CommentAdapter adapter = new CommentAdapter(this, data);
        commentView.setLayoutManager(new MyLinearLayoutManager(this));
        commentView.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                index ++;
                commentView.smoothScrollToPosition(index);
                Log.e("TAG","没隔1秒执行一次操作" + index);
            }
        },1000,1000);

        tabMethod();
    }


        int currentPosition = 0;
    private void tabMethod() {
        CenterLayoutManager linearLayoutManager = new CenterLayoutManager(this);
        tabView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        BaseQuickAdapter<String, BaseViewHolder> tabadapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_tab, data) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
               helper.setText(R.id.name,item);
                TextView textView = helper.getView(R.id.name);
                if (currentPosition == helper.getAdapterPosition()) {
                    textView.setBackgroundColor(getResources().getColor(R.color.blue));
                }else{
                    textView.setBackgroundColor(getResources().getColor(R.color.gray));
                }
            }
        };
        tabadapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                currentPosition = position;
                viewPager.setCurrentItem(position);
                linearLayoutManager.smoothScrollToPosition(tabView, new RecyclerView.State(),position);
                adapter.notifyDataSetChanged();
            }
        });
        tabView.setAdapter(tabadapter);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(this, data);

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                linearLayoutManager.smoothScrollToPosition(tabView, new RecyclerView.State(),position);
                tabadapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
