package com.example.huangwenpei.myview.View;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * create by xuxiaopeng
 * on 2018/8/17
 * 描述：
 */
public class VerticalDragLayout extends LinearLayout {
    private ViewDragHelper.Callback cb;
    private ViewDragHelper viewDragHelper;

    private LinearLayout dragView;
    private LinearLayout listView;
    private int dragHeight;


    public VerticalDragLayout(Context context) {
        this(context, null);
    }

    public VerticalDragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        cb = new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == dragView;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }
        };


        viewDragHelper = ViewDragHelper.create(this, cb);
    }

    //3传递触摸事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            viewDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //容错性检查
        if (getChildCount() < 2) {
            throw new IllegalStateException("至少有两个孩子view");
        }

        dragView = (LinearLayout) getChildAt(0);
        listView = (LinearLayout) getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        dragHeight = dragView.getMeasuredHeight();
    }
}
