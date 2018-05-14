package com.example.huangwenpei.myview.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.View.MyView2;

public class CircleActivity extends BaseActivity {
    private MyView2 myView2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);

        myView2 = findViewById(R.id.myView2);
        myView2.start();
    }
}
