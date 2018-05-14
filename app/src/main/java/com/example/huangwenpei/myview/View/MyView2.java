package com.example.huangwenpei.myview.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.huangwenpei.myview.R;

/**
 * Created by ex-xuxiaopeng001
 * on 2018/5/11.
 */

public class MyView2 extends View implements Runnable {
    //一个dp
    private float dimens;

    public MyView2(Context context) {
        this(context, null);
    }

    public MyView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dimens = context.getResources().getDimension(R.dimen.width_01);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);//设置为空心
        paint.setStrokeWidth(3);
        canvas.drawCircle(40, 40, 30, paint);
        canvas.drawRect(10, 90, 70, 150, paint);
        canvas.drawRect(10, 170, 70, 200, paint);
        canvas.drawOval(new RectF(10, 220, 70, 250), paint);
        Path path = new Path();//三角形
        path.moveTo(10, 330);
        path.lineTo(70, 330);
        path.lineTo(40, 270);
        path.close();
        canvas.drawPath(path, paint);
        Path path1 = new Path();//梯形
        path1.moveTo(10, 410);//绘画基点
        path1.lineTo(70, 410);
        path1.lineTo(55, 350);
        path1.lineTo(25, 350);
        path1.close();//把开始的点和最后的点连接在一起，构成一个封闭图形
            /*
             * 最重要的就是movtTo和close,如果是Style.FILL的话，不设置close,也没有区别，可是如果是STROKE模式，
             * 如果不设置close,图形不封闭。
             *
             * 当然，你也可以不设置close，再添加一条线，效果一样。
             */
        canvas.drawPath(path1, paint);


        ///////////////////////////////////////第二列

        paint.setColor(Color.BLUE);

        paint.setStyle(Paint.Style.FILL);//设置实心

        canvas.drawCircle(120, 40, 30, paint);

        canvas.drawRect(90, 90, 150, 150, paint);

        canvas.drawRect(90, 170, 150, 200, paint);

        RectF re2 = new RectF(90, 220, 150, 250);

        canvas.drawOval(re2, paint);

        Path path2 = new Path();

        path2.moveTo(90, 330);

        path2.lineTo(150, 330);

        path2.lineTo(120, 270);

        path2.close();

        canvas.drawPath(path2, paint);

        Path path3 = new Path();

        path3.moveTo(90, 410);

        path3.lineTo(150, 410);

        path3.lineTo(135, 350);

        path3.lineTo(105, 350);

        path3.close();

        canvas.drawPath(path3, paint);


        ////////////////////////////////////////////////////第三列

            /*
             * LinearGradient shader = new LinearGradient(0, 0, endX, endY, new
             * int[]{startColor, midleColor, endColor},new float[]{0 , 0.5f,
             * 1.0f}, TileMode.MIRROR);
             * 参数一为渐变起初点坐标x位置，参数二为y轴位置，参数三和四分辨对应渐变终点
             * 其中参数new int[]{startColor, midleColor,endColor}是参与渐变效果的颜色集合，
             * 其中参数new float[]{0 , 0.5f, 1.0f}是定义每个颜色处于的渐变相对位置， 这个参数可以为null，如果为null表示所有的颜色按顺序均匀的分布
             */
        Shader mShader = new LinearGradient(0, 0, 100, 100,

                new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW},

                null, Shader.TileMode.REPEAT);

        // Shader.TileMode三种模式

        // REPEAT:沿着渐变方向循环重复

        // CLAMP:如果在预先定义的范围外画的话，就重复边界的颜色

        // MIRROR:与REPEAT一样都是循环重复，但这个会对称重复

        paint.setShader(mShader);// 用Shader中定义定义的颜色来话

        canvas.drawCircle(200, 40, 30, paint);

        canvas.drawRect(170, 90, 230, 150, paint);

        canvas.drawRect(170, 170, 230, 200, paint);

        RectF re3 = new RectF(170, 220, 230, 250);

        canvas.drawOval(re3, paint);

        Path path4 = new Path();

        path4.moveTo(170, 330);

        path4.lineTo(230, 330);

        path4.lineTo(200, 270);

        path4.close();

        canvas.drawPath(path4, paint);

        Path path5 = new Path();

        path5.moveTo(170, 410);

        path5.lineTo(230, 410);

        path5.lineTo(215, 350);

        path5.lineTo(185, 350);

        path5.close();

        canvas.drawPath(path5, paint);

        //////////////////////////////////第4列

        paint.setTextSize(24);

        canvas.drawText("圆形", 240, 50, paint);

        canvas.drawText("正方形", 240, 120, paint);

        canvas.drawText("长方形", 240, 190, paint);

        canvas.drawText("椭圆形", 240, 250, paint);

        canvas.drawText("三角形", 240, 320, paint);

        canvas.drawText("梯形", 240, 390, paint);


        //渐变矩形
        Shader mShader1 = new LinearGradient(0, 0, 0, getMeasuredHeight(), new int[]{0x99ffffff, 0x990000ff}, new float[]{0.1f, 0.8f}, Shader.TileMode.CLAMP);
//        paint.setShader(mShader1);

//        paint.reset();
//
//
//        paint.setColor(Color.RED);
//        canvas.drawRect(370, 790, 830, 1150, paint);
//
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//
//        paint.setColor(Color.BLUE);
//        canvas.drawRect(170, 490, 630, 950, paint);


        //画黄色的圆 满屏幕那种 bitmap
//        Bitmap bt = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
//        canvas.setBitmap(bt);
//        paint.setColor(Color.YELLOW);
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 200, paint);
//        //画蓝色的圆 满屏幕那种 bitmap
//        Bitmap bt2 = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
//        canvas.setBitmap(bt2);
//        paint.setColor(Color.BLUE);
//        canvas.drawCircle(getWidth() / 2 + 200, getHeight() / 2 + 200, 200, paint);


        paint.reset();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2 * dimens);
        paint.setColor(0xffff0000);
        paint.setStyle(Paint.Style.FILL);


        paint.setColor(0xff0000ff);
        canvas.drawRect(cX - bar_X, cY - radius + bar_Y, cX + bar_X, cY - radius + bar_Y + 40, paint);


        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

//        canvas.saveLayerAlpha(0, 0, getWidth(), getHeight(), 255, Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(cX, cY, radius, paint);
//        this.setLayerType(View.LAYER_TYPE_HARDWARE, null);

    }

    private boolean isRun = false;

    private float cX = 1080 / 2 + 200;
    private float cY = 1920 / 2 + 200;
    private float radius = 200;

    private float bar_Y = 0;
    private float bar_X = 0;

    @Override
    public void run() {

        while (isRun) {
            if (bar_Y > radius * 2) {
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