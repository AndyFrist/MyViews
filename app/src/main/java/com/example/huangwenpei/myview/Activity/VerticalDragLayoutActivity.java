package com.example.huangwenpei.myview.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.View.behavior.AnchorBottomSheetBehavior;
import com.example.huangwenpei.myview.adapter.MyAdapter;

import java.util.ArrayList;

public class VerticalDragLayoutActivity extends Activity implements View.OnClickListener{
    private ArrayList<String> list;

    private RecyclerView bus_segment_list;
    private MyAdapter myAdapter;
    private TextView more;

    private NestedScrollView mNestScrollView;
    private AnchorBottomSheetBehavior mBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_drag_layout);
        bus_segment_list = findViewById(R.id.bus_segment_list);
        mNestScrollView = findViewById(R.id.bus_paths_bottom_sheet);
        more = findViewById(R.id.more);
        more.setOnClickListener(this);
        mBehavior = AnchorBottomSheetBehavior.from(mNestScrollView);
        mBehavior.addBottomSheetCallback(new AnchorBottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case AnchorBottomSheetBehavior.STATE_COLLAPSED:
                        Log.d("bottomsheet-", "STATE_COLLAPSED");
//                        mCurrentOverlay.zoomToBusSpan(getSheetPadding());
                        break;
                    case AnchorBottomSheetBehavior.STATE_DRAGGING:
                        Log.d("bottomsheet-", "STATE_DRAGGING");
                        break;
                    case AnchorBottomSheetBehavior.STATE_EXPANDED:
                        Log.d("bottomsheet-", "STATE_EXPANDED");
                        break;
                    case AnchorBottomSheetBehavior.STATE_ANCHOR_POINT:
                        Log.d("bottomsheet-", "STATE_ANCHOR_POINT");
//                        mCurrentOverlay.zoomToBusSpan(getSheetPadding());
                        break;
                    case AnchorBottomSheetBehavior.STATE_HIDDEN:
                        Log.d("bottomsheet-", "STATE_HIDDEN");
                        break;
                    default:
                        Log.d("bottomsheet-", "STATE_SETTLING");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                Log.d("bottomsheet-", "slideOffset:"+slideOffset);
//                mOffset = slideOffset;
            }
        });
        mBehavior.setState(AnchorBottomSheetBehavior.STATE_COLLAPSED);



        //构造数据源
        list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("数据源" + i);
        }

        myAdapter = new MyAdapter(this, list);
        bus_segment_list.setAdapter(myAdapter);
        bus_segment_list.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        if (more == view) {
            mBehavior.setState(AnchorBottomSheetBehavior.STATE_EXPANDED);
        }
    }
}
