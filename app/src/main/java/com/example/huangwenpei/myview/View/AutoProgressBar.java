package com.example.huangwenpei.myview.View;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;


/**
 * create by xuxiaopeng
 * on 2018/9/4
 * 描述：
 */
public class AutoProgressBar extends RelativeLayout implements Runnable {
    private Context context;
    private int comWidth; //控件宽度
    private int comHeight;//控件高度
    private Handler handler = new Handler();
    private int progressMax = 100;    //最大值
    private int progressValue = 60;  //值
    private float animRate = 1; //动画速度   以每1毫秒计
    private float size = 0; //进度条的尺寸
    private Paint paint;
    private RelativeLayout.LayoutParams layoutParams;

    private View background;
    private View foreground;


    public AutoProgressBar(Context context) {
        this(context, null);
    }

    public AutoProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        paint = new Paint();

    }

    @Override
    public void run() {
        if (anim) {
            if (orientation == Orientation.Vertical) {
                if (size < (progressValue * comHeight) / progressMax) {
                    size += animRate;
//            layoutParams = new RelativeLayout.LayoutParams(comWidth, size);
                layoutParams.height = (int)(size+0.5);
                layoutParams.width = comWidth;
                    if (layoutParams.height < layoutParams.width) {
                        layoutParams.width = layoutParams.height;
                    }
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                foreground.setLayoutParams(layoutParams);

                    handler.postDelayed(this, 1);
                } else {
                    handler.removeCallbacks(this);
                }
            } else if (orientation == Orientation.Horizontal) {
                if (size < (progressValue * comWidth) / progressMax) {
                    size += animRate;
//            layoutParams = new RelativeLayout.LayoutParams(comWidth, size);
                layoutParams.height = comHeight;
                layoutParams.width = (int)(size+0.5);
                    if (layoutParams.height > layoutParams.width) {
                        layoutParams.height = layoutParams.width;
                    }
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                foreground.setLayoutParams(layoutParams);

                    handler.postDelayed(this, 1);
                } else {
                    handler.removeCallbacks(this);
                }
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
        layoutParams = (RelativeLayout.LayoutParams) foreground.getLayoutParams();
    }

    public void setProgressValue(int progressValue, Orientation orientation) {
        this.progressValue = progressValue;
        this.orientation = orientation;
        this.size = 0;
        animRate = progressValue / 10f;
        handler.postDelayed(this, 100);
        if (orientation == Orientation.Vertical) {
            layoutParams.height = 0;
        } else {
            layoutParams.width = 0;
        }
    }

    public void setProgressValue(int progressValue, Orientation orientation, boolean anim) {
        this.anim = anim;
        setProgressValue(progressValue, orientation);
    }
    boolean anim = true;

    private Orientation orientation = Orientation.Horizontal;

    public enum Orientation {Horizontal, Vertical}


}
