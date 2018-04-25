package com.example.huangwenpei.myview.Activity;

import android.os.Bundle;
import android.view.View;

import com.example.huangwenpei.myview.R;

public class ChartLineActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_line,R.layout.titles_layout);

        back_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        label_title.setText(getLocalClassName());
    }
}
