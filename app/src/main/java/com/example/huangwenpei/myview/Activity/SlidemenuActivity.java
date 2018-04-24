package com.example.huangwenpei.myview.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.Util.LogUtil;
import com.example.huangwenpei.myview.Util.sliding.IntentUtils;
import com.example.huangwenpei.myview.View.MyScrollView;

public class SlidemenuActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "SlidemenuActivity";
    private TextView main_tv;
    private MyScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidemenu);
        main_tv = findViewById(R.id.main_tv);
        main_tv.setOnClickListener(this);
//        mScrollView = findViewById(R.id.mscrollview);
//
//        mScrollView.setScrollToBottomListener(new MyScrollView.OnScrollToBottomListener() {
//            @Override
//            public void onScrollToBottom() {
//                mScrollView.requestDisallowInterceptTouchEvent(false);
//            }
//
//            @Override
//            public void onNotScrollToBottom() {
//                mScrollView.requestDisallowInterceptTouchEvent(true);
//            }
//        });
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.main_tv:
                intent.setClass(this, ChartLineActivity.class);
                break;
        }
        IntentUtils.getInstance().startActivity(this, intent);
    }
}
