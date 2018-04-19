package com.example.huangwenpei.myview.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.View.Scroll_ListView;

public class OverScrollyActivity extends BaseActivity {
private Scroll_ListView over_listview;
private String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_scrolly,true);
        initView();
    }

    private void initView() {
        data = new String[50];
        for (int i =0;i<50;i++) {
            data[i] = "第"  + i;
        }
        over_listview = findViewById(R.id.over_listview);
        final View head = View.inflate(OverScrollyActivity.this, R.layout.headview_layout, null);
        final ImageView mImage = head.findViewById(R.id.head_image);

        over_listview.addHeaderView(head);
        over_listview.setAdapter(new ArrayAdapter<String>(OverScrollyActivity.this,android.R.layout.simple_list_item_1,data));

        head.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 当布局填充结束之后, 此方法会被调用
                over_listview.setParallaxImage(mImage);
                head.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

    }



}
