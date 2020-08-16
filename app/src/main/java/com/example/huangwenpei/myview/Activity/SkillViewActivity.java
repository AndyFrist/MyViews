package com.example.huangwenpei.myview.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.huangwenpei.myview.Bean.SkillBean;
import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.View.SkillView;

import java.util.ArrayList;
import java.util.Collections;

public class SkillViewActivity extends BaseActivity {
    private ArrayList<SkillBean> list = new ArrayList<>();
    private ArrayList<SkillBean> list2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_view);

        this.list.add(new SkillBean(0, 15, 1, 13.8f, "得分"));
        this.list.add(new SkillBean(0, 10, 1, 4.8f, "篮板"));
        this.list.add(new SkillBean(0, 10, 1, 7.8f, "助攻"));
        this.list.add(new SkillBean(0, 5, 1, 4.8f, "盖帽"));
        this.list.add(new SkillBean(0, 5, 1, 2.8f, "抢断"));

        SkillView skillView = findViewById(R.id.skillView);
        skillView.setData(list);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.shuffle(list);
                skillView.setData(list);
            }
        });



        this.list2.add(new SkillBean(0, 150, 1, 138f, "语文"));
        this.list2.add(new SkillBean(0, 150, 1, 128f, "数学"));
        this.list2.add(new SkillBean(0, 150, 1, 118f, "英语"));
        this.list2.add(new SkillBean(0, 100, 1, 88f, "物理"));
        this.list2.add(new SkillBean(0, 100, 1, 75f, "化学"));
        this.list2.add(new SkillBean(0, 100, 1, 88f, "生物"));
        this.list2.add(new SkillBean(0, 100, 1, 95f, "历史"));
        this.list2.add(new SkillBean(0, 100, 1, 65f, "地理"));
        this.list2.add(new SkillBean(0, 100, 1, 75f, "政治"));
        this.list2.add(new SkillBean(0, 100, 1, 95f, "音乐"));



        SkillView skillView2 = findViewById(R.id.skillView2);
        skillView2.setData(list2);


        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.shuffle(list2);
                skillView2.setData(list2);
            }
        });
    }
}
