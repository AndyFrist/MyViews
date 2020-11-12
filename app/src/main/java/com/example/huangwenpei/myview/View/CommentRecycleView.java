package com.example.huangwenpei.myview.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CommentRecycleView extends RecyclerView {
    public CommentRecycleView(Context context) {
        super(context);
    }

    public CommentRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // 拦截事件；


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return true;
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        // 拦截触摸事件；
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // 消费事件；
        return true;
    }
}
