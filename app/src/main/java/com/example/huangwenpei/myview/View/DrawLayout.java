package com.example.huangwenpei.myview.View;

import android.content.Context;
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
 * Created by Administrator
 * on 2018/1/12 0012.
 */

public class DrawLayout extends FrameLayout {
    private static final String TAG = "DrawLayout";
    private ViewDragHelper.Callback cb;
    private ViewDragHelper viewDragHelper;
    private ViewGroup leftMenu;
    private ViewGroup mainMenu;

    public DrawLayout(@NonNull Context context) {
        this(context, null);
    }

    public DrawLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        cb = new ViewDragHelper.Callback() {
            //2重写事件
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //根据返回的值决定是否可以拖拽
                return true; //允许
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                //capturedChild被捕获时调用
                super.onViewCaptured(capturedChild, activePointerId);
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                //返回拖拽范围，不对拖拽进行真正的限制，仅仅决定了动画的速度
                return super.getViewHorizontalDragRange(child);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //根据建议值修改移动的位置
                //left = child.getLeft + dx
                if (child == mainMenu) {
                    return fixLeft(left);
                }
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                //根据建议值修改移动的位置
                //top = child.gettop + dx
                return super.clampViewPositionVertical(child, top, dy);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                //当位置改变时候，处理要做的事（更新状态，伴随动画，重绘界面）
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                int newleft = left;
                if (changedView == leftMenu) {
                    newleft = mainMenu.getLeft() + dx;
                    LogUtil.d("DrawQQLayout", "newleft" + newleft + "dx" + dx + "mainMenu.getLeft()" + mainMenu.getLeft());

                    newleft = fixLeft(newleft);
                    leftMenu.layout(0, 0, mWidth, mHeight);
                    mainMenu.layout(0 + newleft, 0, mWidth + newleft, mHeight);
                }
                //更新状态执行动画
                dispatchDrawEvent(newleft);
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
                if (xvel == 0 && mainMenu.getLeft() > range / 2.0) {
                    open();
                } else if (xvel > 0) {
                    open();
                } else {
                    close();
                }
            }

            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
            }
        };
        //1初始化 viewDragHelper
        viewDragHelper = ViewDragHelper.create(this, cb);
    }

    private void dispatchDrawEvent(int newleft) {
        float peesent = newleft * 1.0f / range;
        LogUtil.d(TAG, "百分比" + peesent);
        //伴随动画
        // 1、左面板：缩放动画，平移动画，透明度变化
        //缩放动画 0.5 》》0.5 + 0.5 * peesent
        leftMenu.setScaleX(0.5f + 0.5f * peesent);
        leftMenu.setScaleY(0.5f + 0.5f * peesent);
        //平移动画 -mWidth >>> 0
        leftMenu.setTranslationX(evaluate(peesent, -mWidth / 2.0f, 0));
        //透明度0.5》》1.0
        leftMenu.setAlpha(0.5f + 0.5f * peesent);

        // 2、主面板：缩放动画
        mainMenu.setScaleX(evaluate(peesent, 1.0f, 0.8f));
        mainMenu.setScaleY(evaluate(peesent, 1.0f, 0.8f));
        // 3、背景动画：亮度变化（颜色变化）
//        getBackground().setColorFilter(getCurrentColor(peesent, Color.BLACK, Color.YELLOW), PorterDuff.Mode.SRC_OVER);
    }

    private float evaluate(float peesent, float startValue, float endValue) {
        return startValue + (endValue - startValue) * peesent;
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

    public void close() {
        close(true);
    }

    public void close(boolean smooth) {
        int finalLeft = 0;
        if (smooth) {
            //a触发一个平滑动画
            //返回true表示还没有移动到指定位置，需要刷新界面
            if (viewDragHelper.smoothSlideViewTo(mainMenu, finalLeft, 0)) {
                //参数传this（child所在的parent）
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            mainMenu.layout(finalLeft, 0, finalLeft + mWidth, 0 + mHeight);
        }
    }

    public void open() {
        open(true);
    }

    public void open(boolean smooth) {
        int finalLeft = range;
        if (smooth) {
            //a触发一个平滑动画
            //返回true表示还没有移动到指定位置，需要刷新界面
            if (viewDragHelper.smoothSlideViewTo(mainMenu, finalLeft, 0)) {
                //参数传this（child所在的parent）
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            mainMenu.layout(finalLeft, 0, finalLeft + mWidth, 0 + mHeight);
        }
    }

    /**
     * 设置范围
     *
     * @param left
     * @return
     */
    private int fixLeft(int left) {
        if (left < 0) {
            return 0;
        } else if (left > range) {
            return range;
        }
        return left;
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
        if (!(getChildAt(0) instanceof ViewGroup && getChildAt(1) instanceof ViewGroup)) {
            throw new IllegalStateException("两个孩子view不是容器");
        }
        leftMenu = (ViewGroup) getChildAt(0);
        mainMenu = (ViewGroup) getChildAt(1);
    }

    int range;
    int mHeight;
    int mWidth;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        range = (int) (mWidth * 0.6);
    }
}
