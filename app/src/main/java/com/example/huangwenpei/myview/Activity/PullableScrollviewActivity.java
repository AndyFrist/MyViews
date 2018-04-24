package com.example.huangwenpei.myview.Activity;

import android.os.Bundle;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.View.MyPullListener;
import com.example.huangwenpei.myview.View.PullToRefreshLayout;

public class PullableScrollviewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pullable_scrollview);
        ((PullToRefreshLayout) findViewById(R.id.refresh_view))
                .setOnPullListener(new MyPullListener());
    }
}
