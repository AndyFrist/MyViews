package com.example.huangwenpei.myview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.huangwenpei.myview.Util.LogUtil;

/**
 * Created by
 * ex-xuxiaopeng001 on 2018/4/16.
 */

public class SlideMenuView extends FrameLayout {
    private static final String TAG = "SlideMenuView";
    private ViewDragHelper.Callback cb;
    private ViewDragHelper viewDragHelper;
    private ViewGroup headView;
    private ViewGroup mainView;
    private int range;
    private int mHeight;
    private int mWidth;

    private int headY = 180;

    public SlideMenuView(@NonNull Context context) {
        this(context, null);
    }

    public SlideMenuView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideMenuView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {

        cb = new ViewDragHelper.Callback() {

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //根据返回的值决定是否可以拖拽
                return true; //允许
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                //根据建议值修改移动的位置
                //top = child.gettop + dx
                return top;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                //当位置改变时候，处理要做的事（更新状态，伴随动画，重绘界面）
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                int newTop = top;
                newTop = fixVealue(top);
                headView.layout(0, -mHeight + newTop, mWidth, 0 + newTop);
                mainView.layout(0, 0 + newTop, mWidth, mHeight + newTop);

                //为了兼容低版本2.3以下，每次修改值后重绘
                invalidate();
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                //当View被释放处理的事（执行动画）
                // releasedChild 被释放的view
                //xvel 水平方向的速度
                //yvel 垂直方向的速度
                super.onViewReleased(releasedChild, xvel, yvel);

                LogUtil.d(TAG,"top" + mainView.getTop());
                if (mainView.getTop() >= headY) {
                    open();
                } else {
                    close();
                }
            }
        };
        //1初始化 viewDragHelper
        viewDragHelper = ViewDragHelper.create(this, cb);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        //aa持续平滑动画（高频调用）
        //如果返回true，动画还需要继续执行
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void open() {
        int finalTop = headY;
        //a触发一个平滑动画
        //返回true表示还没有移动到指定位置，需要刷新界面
        if (viewDragHelper.smoothSlideViewTo(mainView, 0, finalTop)) {
            //参数传this（child所在的parent）
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void close() {
        int finalTop = 0;
        //a触发一个平滑动画
        //返回true表示还没有移动到指定位置，需要刷新界面
        if (viewDragHelper.smoothSlideViewTo(mainView, 0, finalTop)) {
            //参数传this（child所在的parent）
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private int fixVealue(int top) {
        if (top < 0) {
            return top;
        } else if (top > range) {
            return range;
        }
        return top;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() < 2) {
            throw new IllegalStateException("至少有两个孩子view");
        }
        if (!(getChildAt(0) instanceof ViewGroup && getChildAt(1) instanceof ViewGroup)) {
            throw new IllegalStateException("两个孩子view不是容器");
        }
        headView = (ViewGroup) getChildAt(0);
        mainView = (ViewGroup) getChildAt(1);

        LogUtil.d(TAG, "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.d(TAG, "onLayout");
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        range = (int) (0.6 * mHeight);
        headView.layout(0, -mHeight, mWidth, 0);
        mainView.layout(0, 0, mWidth, mHeight);
    }


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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        LogUtil.d(TAG, "onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onFinishInflate() {
        LogUtil.d(TAG, "onFinishInflate");
        super.onFinishInflate();
    }
}
