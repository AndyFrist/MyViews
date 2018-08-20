package com.example.huangwenpei.myview.Activity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.View.MyPullListener;
import com.example.huangwenpei.myview.View.PullToRefreshLayout;
import com.example.huangwenpei.myview.View.RatingBar;

public class PullableScrollviewActivity extends BaseActivity {
    private RatingBar mRatingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pullable_scrollview);
        ((PullToRefreshLayout) findViewById(R.id.refresh_view))
                .setOnPullListener(new MyPullListener());
        initView();
        initData();
    }
    private void initView(){
        mRatingBar =  findViewById(R.id.star);
    }
    private void initData(){
        mRatingBar.setClickable(true);
        mRatingBar.setStar(3f);
        float star = mRatingBar.getStar();
        Toast.makeText(this,"a"+star,3000).show();
    }
}
