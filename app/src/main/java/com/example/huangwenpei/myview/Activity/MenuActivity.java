package com.example.huangwenpei.myview.Activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SeekBar;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.adapter.Adapter;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //设置列表可以水平滑动，两行
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
//        mRecyclerView = new CustomeRecyclerView(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        final List<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("name" + i);
        }
        Adapter adapter = new Adapter(this, strings);
        mRecyclerView.setAdapter(adapter);
        seekBar = findViewById(R.id.slide_indicator_point);
        seekBar.setPadding(0, 0, 0, 0);
        seekBar.setThumbOffset(0);
//        GradientDrawable gradientDrawable =(GradientDrawable) seekBar.getThumb();
//        gradientDrawable.setSize(1440/(strings.size()/2),5);
//        final GradientDrawable mGroupDrawable = (GradientDrawable) seekBar.getThumb();
//        mGroupDrawable.setSize(1000,5);
        //显示区域的高度。
        int extent = mRecyclerView.computeHorizontalScrollExtent();
        //整体的高度，注意是整体，包括在显示区域之外的。
        int range = mRecyclerView.computeHorizontalScrollRange();
        //已经向下滚动的距离，为0时表示已处于顶部。
        int offset = mRecyclerView.computeHorizontalScrollOffset();
//        int screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
//        int tt =screenWidth/2;
//        seekBar.setMax(tt+100);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //显示区域的高度。
                int extent = mRecyclerView.computeHorizontalScrollExtent();
                //整体的高度，注意是整体，包括在显示区域之外的。
                int range = mRecyclerView.computeHorizontalScrollRange();
                //已经向下滚动的距离，为0时表示已处于顶部。
                int offset = mRecyclerView.computeHorizontalScrollOffset();
                Log.i("dx------", range + "****" + extent + "****" + offset);
                //此处获取seekbar的getThumb，就是可以滑动的小的滚动游标
                GradientDrawable gradientDrawable = (GradientDrawable) seekBar.getThumb();
                //根据列表的个数，动态设置游标的大小，设置游标的时候，progress进度的颜色设置为和seekbar的颜色设置的一样的，所以就不显示进度了。
                gradientDrawable.setSize(seekBar.getWidth()/2, 5);
                //设置可滚动区域
                seekBar.setMax((int) (range - extent));
                if (dx == 0) {
                    seekBar.setProgress(0);
                } else if (dx > 0) {
//                    int ss = (int)(tt/2.3f);
                    Log.i("dx------", "右滑");
                    seekBar.setProgress(offset);
                } else if (dx < 0) {
                    Log.i("dx------", "左滑");
                    seekBar.setProgress(offset);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

        });
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }
}
