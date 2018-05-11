package com.example.huangwenpei.myview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.example.huangwenpei.myview.MyApp;
import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.Util.AndroidUtil;

/**
 * Created by ex-xuxiaopeng001
 * on 2018/5/11.
 */

public class BaseFundChartView extends View implements Runnable {

    private Paint paint = new Paint();
    private RectF re = new RectF(0, 0, AndroidUtil.getScreenWidth(MyApp.getContext()), AndroidUtil.getScreenHeight(MyApp.getContext()));

    private float dimens;
    //直径
    private float diameter = AndroidUtil.getScreenWidth(MyApp.getContext()) / 2;
    //半径
    private float radius = diameter / 2;

    private Paint paint1 = new Paint();
    //4个扇形所在区域
    private RectF re1;

    public BaseFundChartView(Context context) {
        this(context, null);
    }

    public BaseFundChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseFundChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dimens = context.getResources().getDimension(R.dimen.width_01);
        init();
    }

    private void init() {
        paint.setAntiAlias(true);
        paint1.setAntiAlias(true);
        re1 = new RectF(0 + radius + 10 * dimens, 0 + radius + 10 * dimens, (diameter + radius - 10 * dimens), (diameter + radius - 10 * dimens));

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画透明圆
        paint.setColor(0x990000ff);
        canvas.drawCircle(diameter, diameter, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

        //画半透明背景（整个屏幕）
        paint.setColor(0x99000000);
        canvas.drawRect(re, paint);


        //画蓝色的圆环
        paint1.setColor(0xff0000ff);
        paint1.setStyle(Paint.Style.STROKE);//设置为空心
        paint1.setStrokeWidth(6 * dimens);
        canvas.drawCircle(diameter, diameter, radius, paint1);

        //画扫动线
        drawLines(canvas);

        //画4个扇形
        paint1.reset();
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.STROKE);//设置为空心
        paint1.setColor(0xffffffff);
        paint1.setStrokeWidth(2 * dimens);
        for (int i = 0; i < 4; i++) {
            canvas.drawArc(re1, i * 90 - 90, 85, false, paint1);
        }


    }

    private void drawLines(Canvas canvas) {

        Shader mShader = new LinearGradient(0, 0, 0, 20 * dimens, new int[]{Color.TRANSPARENT, Color.BLUE}, null, Shader.TileMode.MIRROR);
        paint1.setShader(mShader);
//        canvas.drawRect(diameter - bar_X,  bar_Y + radius, diameter + bar_X, bar_Y + radius, paint1);
        canvas.drawLine(diameter - bar_X, bar_Y + radius, diameter + bar_X, bar_Y + radius, paint1);

    }

    private boolean isRun = false;
    private float bar_Y = 0;

    private float bar_X = 0;

    @Override
    public void run() {
        while (isRun) {
            if (bar_Y > diameter) {
                bar_Y = 0;
            }
            bar_Y = bar_Y + 2 * dimens;

            bar_X = (float) Math.sqrt(radius * radius - (radius - bar_Y) * (radius - bar_Y));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            postInvalidate();
        }
    }

    public void start() {
        isRun = true;
        new Thread(this).start();
    }

    public void stop() {
        isRun = false;
    }
}

