package com.example.huangwenpei.myview.View;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.huangwenpei.myview.R;

public class FloorView extends FrameLayout {
    Context context;
    ViewDragHelper viewDragHelper;
    ViewGroup topContent;
    ViewGroup mainContent;
    TextView stateTV;
    int mWidth;
    int mHeight;
    int range;


    public FloorView(@NonNull Context context) {
        this(context, null);
    }

    public FloorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        viewDragHelper = ViewDragHelper.create(this, callback);
    }

    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (child == mainContent) {
                top = fixTop(top);
            }
            return top;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);

            int newTop = top;
            if (changedView == topContent) {
                newTop = mainContent.getTop() + dy;
            }

            newTop = fixTop(newTop);
            if (changedView == topContent) {
//                topContent.layout(0, 0, mWidth, mHeight);
                mainContent.layout(0, newTop, mWidth, mHeight + newTop);
            }
            if (changedView == mainContent) {
                topContent.layout(0, newTop - mHeight, mWidth, newTop);
            }
            setState(mainContent.getTop());
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mainContent.getTop() < range / 2.0) {
                close();
            } else if (mainContent.getTop() >= range / 2.0  && mainContent.getTop() < range) {
                open();
            } else if (mainContent.getTop() >= range) {
                gotoFloor();
            }
        }
    };


    @Override
    public void computeScroll() {
        super.computeScroll();

        //aa持续平滑动画（高频调用）
        //如果返回true，动画还需要继续执行
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void close() {
        close(true);
    }

    public void close(boolean smooth) {
        int finalTop = 0;
        if (smooth) {
            //a触发一个平滑动画
            //返回true表示还没有移动到指定位置，需要刷新界面
            if (viewDragHelper.smoothSlideViewTo(mainContent, 0, finalTop)) {
                //参数传this（child所在的parent）
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            mainContent.layout(finalTop, 0, 0 + mWidth, finalTop + mHeight);
        }
    }

    public void open() {
        open(true);
    }

    public void open(boolean smooth) {
        int finalTop = range;
        if (smooth) {
            //a触发一个平滑动画
            //返回true表示还没有移动到指定位置，需要刷新界面
            if (viewDragHelper.smoothSlideViewTo(mainContent, 0, finalTop)) {
                //参数传this（child所在的parent）
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            mainContent.layout(finalTop, 0, 0 + mWidth, finalTop + mHeight);
        }
    }

    public void gotoFloor() {
        gotoFloor(true);
    }

    public void gotoFloor(boolean smooth) {
        int finalTop = mHeight;
        if (smooth) {
            //a触发一个平滑动画
            //返回true表示还没有移动到指定位置，需要刷新界面
            if (viewDragHelper.smoothSlideViewTo(mainContent, 0, finalTop)) {
                //参数传this（child所在的parent）
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            mainContent.layout(finalTop, 0, 0 + mWidth, finalTop + mHeight);
        }
    }

    private int fixTop(int top) {
        if (top < 0) {
            return 0;
        } else if (top > range) {
//            return range;
            return top;//不限
        }
        return top;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        range = getMeasuredHeight() / 4;
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
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() < 2) {
            throw new IllegalStateException("至少有两个孩子");
        }
        if (!(getChildAt(0) instanceof ViewGroup && getChildAt(1) instanceof ViewGroup)) {
            throw new IllegalStateException("子view必需是ViewGroup");
        }

        topContent = (ViewGroup) getChildAt(0);
        mainContent = (ViewGroup) getChildAt(1);
        stateTV = topContent.findViewById(R.id.stateTV);

    }

    //正常状态
    private static final int normal = 0;
    //下拉中。。。继续下拉可刷新（下拉刷新）
    private static final int goToReflsh = 1;
    //下拉中。。。达到释放可刷新（松手加载）
    private static final int release2flshing = 2;
    //释放后。。。正在刷新（加载中..）
    private static final int loding = 3;
    //下拉中。。。达到释放可到二楼（继续下拉到二楼）
    private static final int release2Floor = 4;
    //停留在二楼
    private static final int atFloored = 5;
    private static int currentState = normal;


    private void setState(int position) {

        if (position <= 0) {
            currentState = normal;
            stateTV.setText("下拉刷新");
        } else if (position < range / 2) {
            currentState = goToReflsh;
            stateTV.setText("下拉刷新");
        } else if (position > range / 2 && position < range) {
            currentState = release2flshing;
            stateTV.setText("松手加载");
            //获取系统震动服务
            Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);//震动70毫秒
            vib.vibrate(70);
        } else if (position == range) {
            currentState = loding;
            stateTV.setText("加载中...");
        } else if (position > range) {
            currentState = release2Floor;
            stateTV.setText("继续下拉到二楼");
        } else if (position >= mHeight) {
            currentState = atFloored;
            stateTV.setVisibility(GONE);
            stateTV.setText("下拉刷新");
        }
    }
}
