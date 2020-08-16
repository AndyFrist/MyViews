package com.example.huangwenpei.myview.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.huangwenpei.myview.R;

import java.util.ArrayList;

public class FloorActivity extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor);
    }
}
