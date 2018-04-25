package com.example.huangwenpei.myview.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.Util.sliding.IntentUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button sildemenu, chart, overscrollby, scrollview_pull, mvpbtn;

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
        mvpbtn = findViewById(R.id.mvpbtn);
        mvpbtn.setOnClickListener(this);

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
            case R.id.mvpbtn:
                intent.setClass(this, MVPActivity.class);
                break;
        }
        IntentUtils.getInstance().startActivity(this, intent);
    }
}
