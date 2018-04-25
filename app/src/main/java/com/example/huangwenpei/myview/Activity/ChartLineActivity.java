package com.example.huangwenpei.myview.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.huangwenpei.myview.Bean.LineChartData;
import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.View.LineChart;
import com.example.huangwenpei.myview.View.LineChartView;

import java.util.ArrayList;
import java.util.List;

public class ChartLineActivity extends BaseActivity {



    private LineChart mWeekLineChart;
    private LineChart mLineChart;
    private TextView tvDay;
    private TextView tvWeek;
    private TextView tvMonth;

    private String[] mDayItems = new String[]{"31日", "1日", "2日", "3日", "4日", "5日", "6日"};
    private int[] mDayPoints = new int[]{0, 2, 7, 4, 0, 1, -1};
    private String[] mWeekItems = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    private int[] mWeekPoints = new int[]{7, 2, 1, 4, 0, 1, -1};
    private String[] mMonthItems = new String[]{"5月", "6月", "7月", "8月", "9月", "10月", "11月"};
    private int[] mMonthPoints = new int[]{0, 2, 1, 0, 0, 0, 8};
    private List<LineChartData> dataList1 = new ArrayList<>();
    private List<LineChartData> dataList2 = new ArrayList<>();

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

        mWeekLineChart = (LineChart) findViewById(R.id.line_chart_week);
        mLineChart = (LineChart) findViewById(R.id.line_chart);
        tvDay = (TextView) findViewById(R.id.tv_day);
        tvWeek = (TextView) findViewById(R.id.tv_week);
        tvMonth = (TextView) findViewById(R.id.tv_month);

        for (int i = 0; i < mWeekItems.length; i++) {
            LineChartData data = new LineChartData();
            data.setItem(mWeekItems[i]);
            data.setPoint(mWeekPoints[i]);
            dataList1.add(data);
        }
        mWeekLineChart.setData(dataList1);

        resetTextColor();
        tvDay.setTextColor(getResources().getColor(R.color.colorPrimary));
        for (int i = 0; i < mDayItems.length; i++) {
            LineChartData data = new LineChartData();
            data.setItem(mDayItems[i]);
            data.setPoint(mDayPoints[i]);
            dataList2.add(data);
        }
        mLineChart.setData(dataList2);

        LineChartView li = (LineChartView) findViewById(R.id.l);
        li.setParams("09/24","10/21",new float[]{1.0f, 3.0f, 1.0f, 3.0f, 1.0f, 3.0f, 1.0f});
    }

    public void dayClick(View view){
        resetTextColor();
        tvDay.setTextColor(getResources().getColor(R.color.colorPrimary));
        dataList2.clear();
        for (int i = 0; i < mDayItems.length; i++) {
            LineChartData data = new LineChartData();
            data.setItem(mDayItems[i]);
            data.setPoint(mDayPoints[i]);
            dataList2.add(data);
        }
        mLineChart.setData(dataList2);
    }

    public void weekClick(View view){

        resetTextColor();
        tvWeek.setTextColor(getResources().getColor(R.color.colorPrimary));
        dataList2.clear();
        for (int i = 0; i < mWeekItems.length; i++) {
            LineChartData data = new LineChartData();
            data.setItem(mWeekItems[i]);
            data.setPoint(mWeekPoints[i]);
            dataList2.add(data);
        }
        mLineChart.setData(dataList2);
    }

    public void monthClick(View view){
        resetTextColor();
        tvMonth.setTextColor(getResources().getColor(R.color.colorPrimary));
        dataList2.clear();
        for (int i = 0; i < mMonthItems.length; i++) {
            LineChartData data = new LineChartData();
            data.setItem(mMonthItems[i]);
            data.setPoint(mMonthPoints[i]);
            dataList2.add(data);
        }
        mLineChart.setData(dataList2);
    }

    public void resetTextColor(){
        tvDay.setTextColor(getResources().getColor(R.color.text_title));
        tvWeek.setTextColor(getResources().getColor(R.color.text_title));
        tvMonth.setTextColor(getResources().getColor(R.color.text_title));
    }
}
