package com.example.huangwenpei.myview.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.huangwenpei.myview.R;

import static android.view.animation.Animation.*;

/**
 * Created by xuxiaopeng
 * on 2020/4/18.
 * Description：
 */
public class MusicView extends View {
    private Context context;
    private Paint paint;

    private int width;
    private int height;

    private int floatHeight;
    private int floatHeight2;
    private int floatHeight3;

    public MusicView(Context context) {
        this(context, null);
    }

    public MusicView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.red));
        paint.setStrokeWidth(20);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(context.getResources().getColor(R.color.colorAccent));
        canvas.drawCircle(height/2,height/2,height/2,paint);
        paint.setColor(context.getResources().getColor(R.color.white));
        canvas.drawLine(width / 4, height /2 + Math.abs(floatHeight), width / 4, height /2 - Math.abs(floatHeight), paint);
        canvas.drawLine(width * 2 / 4, height /2 + Math.abs(floatHeight2), width * 2 / 4, height /2 - Math.abs(floatHeight2), paint);
        canvas.drawLine(width * 3 / 4, height /2 + Math.abs(floatHeight3), width * 3 / 4, height /2 - Math.abs(floatHeight3), paint);


    }

    ValueAnimator valueAnimator;

    public void startAnimate() {

        valueAnimator = ValueAnimator.ofInt(0, 360);
        valueAnimator.setDuration(1000 * 3);
        valueAnimator.setRepeatMode(RESTART);
        valueAnimator.setRepeatCount(INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int aa = (int) animation.getAnimatedValue();


                floatHeight = (int) (Math.sin(Math.PI*(aa + 0)/180) * height / 4);
                floatHeight2 = (int) (Math.sin(Math.PI*(aa + 45)/180) * height / 4);
                floatHeight3 = (int) (Math.sin(Math.PI*(aa + 90)/180) * height / 4);

                invalidate();
            }
        });

        valueAnimator.start();
    }


    public void stop(){
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.end();
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getWidth();
        height = getHeight();

        Log.d("musicView", "width" + width + " height" + height);
    }
}
