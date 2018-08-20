package com.example.huangwenpei.myview.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.Util.sliding.IntentUtils;
import com.example.huangwenpei.myview.zxing.activity.CaptureActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button sildemenu, chart, overscrollby, scrollview_pull, mswipebtn, qr_code, circle, circle_image, drag_gridview;
private Button viewDragHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        sildemenu = findViewById(R.id.sildemenu);
        sildemenu.setOnClickListener(this);
        chart = findViewById(R.id.chart);
        chart.setOnClickListener(this);
        overscrollby = findViewById(R.id.overscrollby);
        overscrollby.setOnClickListener(this);
        scrollview_pull = findViewById(R.id.scrollview_pull);
        scrollview_pull.setOnClickListener(this);
        mswipebtn = findViewById(R.id.mswipebtn);
        mswipebtn.setOnClickListener(this);
        qr_code = findViewById(R.id.qr_code);
        qr_code.setOnClickListener(this);
        circle = findViewById(R.id.circle);
        circle.setOnClickListener(this);
        circle_image = findViewById(R.id.circle_image);
        circle_image.setOnClickListener(this);
        drag_gridview = findViewById(R.id.drag_gridview);
        drag_gridview.setOnClickListener(this);
        viewDragHelper = findViewById(R.id.viewDragHelper);
        viewDragHelper.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.sildemenu:
                intent.setClass(this, SlidemenuActivity.class);
                break;
            case R.id.chart:
                intent.setClass(this, ChartLineActivity.class);
                break;
            case R.id.overscrollby:
                intent.setClass(this, OverScrollyActivity.class);
                break;
            case R.id.scrollview_pull:
                intent.setClass(this, PullableScrollviewActivity.class);
                break;
            case R.id.mswipebtn:
                intent.setClass(this, SwipeDeleteActivity.class);
                break;
            case R.id.qr_code:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                    return;
                } else {
                    intent.setClass(this, CaptureActivity.class);
                }
                break;
            case R.id.circle:
                intent.setClass(this, CircleActivity.class);
                break;
            case R.id.circle_image:
                intent.setClass(this, CircleBitmapActivity.class);
                break;
            case R.id.drag_gridview:
                intent.setClass(this, DragItemActivity.class);
                break;

            case R.id.viewDragHelper:
                intent.setClass(this, VerticalDragLayoutActivity.class);
                break;
            default:
                break;
        }
        IntentUtils.getInstance().startActivity(this, intent);
    }
}
