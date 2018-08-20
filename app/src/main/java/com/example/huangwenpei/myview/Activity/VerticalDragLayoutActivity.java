package com.example.huangwenpei.myview.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.huangwenpei.myview.R;

import java.util.ArrayList;

public class VerticalDragLayoutActivity extends Activity {
    private ArrayList<String> list;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_drag_layout);
        //构造数据源
        list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("数据源" + i);
        }
        //为适配器添加数据源
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        //为listView的容器添加适配器
    }
}
