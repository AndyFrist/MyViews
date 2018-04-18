package com.example.huangwenpei.myview.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.Util.sliding.IntentUtils;

public class SlidemenuActivity extends BaseActivity implements View.OnClickListener {

    private TextView main_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidemenu);
        main_tv = findViewById(R.id.main_tv);
        main_tv.setOnClickListener(this);
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
