package com.example.huangwenpei.myview.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Author: ex-xuxiaopeng002
 * @CreateDate: 2019-07-05 15:45
 * @Description: 进度条
 */
public class ProgressBar extends View {

    private Context context;
    private Paint paint_background;
    private Paint paint_forground;
    private int comWidth; //控件宽度
    private int comHeight;//控件高度
    private int progressMax = 100;    //最大值
    private int progressValue = 60;  //值

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        paint_background = new Paint();
        LinearGradient ss = new LinearGradient(0, 0, 0, getWidth(), 0xffaaaaaa, 0xffaaaaaa, Shader.TileMode.REPEAT);
        paint_background.setShader(ss);
        paint_background.setAntiAlias(true);
        paint_background.setStrokeJoin(Paint.Join.ROUND);
        paint_background.setStrokeCap(Paint.Cap.ROUND);

        paint_forground = new Paint();
        LinearGradient s = new LinearGradient(0, 0, 0, getWidth(), 0xff00e7ff, 0xffffff7e, Shader.TileMode.REPEAT);
        paint_forground.setShader(s);
        paint_forground.setAntiAlias(true);
        paint_forground.setStrokeJoin(Paint.Join.ROUND);
        paint_forground.setStrokeCap(Paint.Cap.ROUND);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0 + StrokeWidth / 2, 0 + StrokeWidth / 2, comWidth - StrokeWidth / 2, 0 + StrokeWidth / 2, paint_background);
        if (progressValue > 0) {
            canvas.drawLine(0 + StrokeWidth / 2, 0 + StrokeWidth / 2, (comWidth - StrokeWidth / 2) * progressValue / progressMax, 0 + StrokeWidth / 2, paint_forground);
        }
    }


    private float StrokeWidth = 0;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        comWidth = w;
        comHeight = h;

        StrokeWidth = comHeight;
        paint_background.setStrokeWidth(StrokeWidth);
        paint_forground.setStrokeWidth(StrokeWidth);

    }


    public void setProgressValue(int value) {
        setProgressValue(value, true);
    }

    public void setProgressValue(int value, boolean anim) {
        if (!anim) {
            this.progressValue = value;
            invalidate();
            return;
        }

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, value);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progressValue = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }
}
