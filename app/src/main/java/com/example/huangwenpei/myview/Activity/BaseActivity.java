package com.example.huangwenpei.myview.Activity;

import android.os.Bundle;

import com.example.huangwenpei.myview.Util.StatusBarUtil;
import com.example.huangwenpei.myview.Util.sliding.SlidingActivity;


public class BaseActivity extends SlidingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.translucentStatusBar(this);
    }


}
