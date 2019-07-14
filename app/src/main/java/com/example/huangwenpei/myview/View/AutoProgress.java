package com.example.huangwenpei.myview.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;


/**
 * create by xuxiaopeng
 * on 2018/9/4
 * 描述：
 */
public class AutoProgress extends RelativeLayout {
    private int comWidth; //控件宽度
    private int comHeight;//控件高度
    private int progressMax = 100;    //最大值
    private int progressValue = 60;  //值
    private float animRate = 1; //动画速度   以每1毫秒计
    private float size = 0; //进度条的尺寸
    private LayoutParams layoutParams;

    private View background;
    private View foreground;


    public AutoProgress(Context context) {
        this(context, null);
    }

    public AutoProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void run() {
        if (anim) {
            if (orientation == Orientation.Vertical) {
                layoutParams.height = growth * comHeight / progressMax;
                layoutParams.width = comWidth;
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                foreground.setLayoutParams(layoutParams);
            } else if (orientation == Orientation.Horizontal) {
                layoutParams.height = comHeight;
                layoutParams.width = growth * comWidth / progressMax;
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                foreground.setLayoutParams(layoutParams);
            }
        } else {
            if (orientation == Orientation.Vertical) {
                layoutParams.height = (progressValue * comHeight) / progressMax;
                layoutParams.width = comWidth;
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                foreground.setLayoutParams(layoutParams);
            } else if (orientation == Orientation.Horizontal) {
                layoutParams.height = comHeight;
                layoutParams.width = (progressValue * comWidth) / progressMax;
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                foreground.setLayoutParams(layoutParams);
            }
        }

        requestLayout();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //初始化控件和进度的条相关参数
        comWidth = w;
        comHeight = h;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //容错性检查
        if (getChildCount() != 2) {
            throw new IllegalStateException("必须有两个孩子view");
        }

        background = getChildAt(0);
        foreground = getChildAt(1);
        layoutParams = (LayoutParams) foreground.getLayoutParams();
    }

    //默认有动画
    public void setProgressValue(int progressValue, Orientation orientation) {
        this.progressValue = progressValue;
        this.orientation = orientation;
        this.size = 0;
        animRate = progressValue / 10f;
        if (orientation == Orientation.Vertical) {
            layoutParams.height = 0;
        } else {
            layoutParams.width = 0;
        }
        startAnimater(progressValue);
    }

    int growth;


    public void setProgressValue(int progressValue, Orientation orientation, boolean anim) {
        this.anim = anim;
        setProgressValue(progressValue, orientation);
    }

    boolean anim = true;

    private Orientation orientation = Orientation.Horizontal;

    public enum Orientation {
        Horizontal, Vertical
    }

    private void startAnimater(int progressValue) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, progressValue);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                growth = (int) animation.getAnimatedValue();
                run();
            }
        });
        valueAnimator.start();
    }
}
