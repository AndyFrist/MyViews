package com.example.huangwenpei.myview.View;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;

import com.example.huangwenpei.myview.Bean.CircleBean;

/**
 * Created by xuxiaopeng
 * on 2019/5/23.
 * Description：
 */
public class CircleView extends View {
    private Context context;
    private Paint paint;


    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawCircle(width / 2, width / 2, radio, paint);
    }


    float radio;


    public void startAnimater() {

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new MyAnimator(), new CircleBean(50), new CircleBean(width / 3));

        valueAnimator.setInterpolator(new AnticipateOvershootInterpolator());

        valueAnimator.setDuration(2000);


        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {


                CircleBean circleBean = (CircleBean) valueAnimator.getAnimatedValue();

                radio = circleBean.getRadio();

                invalidate();



            }
        });

        valueAnimator.setRepeatCount(3);
        valueAnimator.start();


    }


    class MyAnimator implements TypeEvaluator<CircleBean> {

        @Override
        public CircleBean evaluate(float fraction, CircleBean startValue, CircleBean endValue) {

            float start = startValue.getRadio();
            float end = endValue.getRadio();
            float result = (start + fraction * (end - start));

            CircleBean point = new CircleBean(result);

            return point;
        }
    }

    int width;
    int height;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = getWidth();
        height = getHeight();


    }
}
