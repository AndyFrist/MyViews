package com.example.huangwenpei.myview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
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
    //整个屏幕区域
    private RectF re = new RectF(0, 0, AndroidUtil.getScreenWidth(MyApp.getContext()), AndroidUtil.getScreenHeight(MyApp.getContext()));
    //一个dp
    private float dimens;
    //直径
    private float diameter = AndroidUtil.getScreenWidth(MyApp.getContext()) / 3 * 2;
    //半径
    private float radius = diameter / 2;
    //圆心点
    private Point point = new Point();
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
        height = 100 * dimens;
        point.x = AndroidUtil.getScreenWidth(MyApp.getContext()) / 2;
        point.y = (int) diameter;
        re1 = new RectF(point.x - radius + 10 * dimens, 0 + radius + 10 * dimens, (diameter + point.x - radius - 10 * dimens), (diameter + radius - 10 * dimens));
    }

    private void init() {
        paint.reset();
        paint1.reset();
        paint.setAntiAlias(true);
        paint1.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        init();

        //画透明圆
        paint.setColor(0xff0000ff);
        canvas.drawCircle(point.x, point.y, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

        //画半透明背景（整个屏幕）
        paint.setColor(0x99000000);
        canvas.drawRect(re, paint);

        //画蓝色的圆环
        paint1.setColor(0xff1389ff);
        paint1.setStyle(Paint.Style.STROKE);//设置为空心
        paint1.setStrokeWidth(6 * dimens);
        canvas.drawCircle(point.x, point.y, radius, paint1);

        //画4个扇形
        paint1.reset();
        paint1.setAntiAlias(true);
        paint1.setStrokeWidth(2 * dimens);
        paint1.setStyle(Paint.Style.STROKE);//设置为空心
        paint1.setColor(0xffffffff);
        for (int i = 0; i < 4; i++) {
            canvas.drawArc(re1, i * 90 - 87, 85, false, paint1);
        }

        //画扫动线
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Shader mShader = new LinearGradient(0, bar_Y + radius, 0, bar_Y + radius + height, new int[]{0x00ffffff, 0xff1389ff}, new float[]{0.1f, 0.8f}, Shader.TileMode.CLAMP);
        paint.setShader(mShader);
        Path path = new Path();
        path.addCircle(point.x, point.y, radius - 11 * dimens, Path.Direction.CW);
        canvas.clipPath(path);
        canvas.drawRect(point.x - radius, bar_Y + radius, point.x + radius, bar_Y + radius + height, paint);

    }

    private boolean isRun = false;
    private float bar_Y = 0;
    //渐变高度
    private float height = 0;

    @Override
    public void run() {
        while (isRun) {
            if (bar_Y > diameter) {
                bar_Y = -height;
            }
            bar_Y = bar_Y + 2 * dimens;
            try {
                Thread.sleep(10);
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

