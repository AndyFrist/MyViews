package com.example.huangwenpei.myview.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.huangwenpei.myview.Bean.SkillBean;
import com.example.huangwenpei.myview.R;

import java.util.ArrayList;

public class SkillView extends View {
    private int comWidth; //控件宽度
    private int comHeight;//控件高度
    private float centerX;
    private float centerY;
    private float radius;  //半径长度

    private float angle = 0;//每个值的跨度
    private float size = 0;//有多少个值
    private ArrayList<SkillBean> list = new ArrayList<>();

    private Paint paintLine;
    private Paint mShapePaint;
    private Paint paintValue;
    private Paint paintText;
    Context context;

    private int mMaskColor;

    public SkillView(Context context) {
        this(context, null);
    }

    public SkillView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkillView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        mMaskColor = getResources().getColor(R.color.blue);

        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.CYAN);

        mShapePaint = new Paint();
        mShapePaint.setAntiAlias(true);

        paintValue = new Paint();
        paintValue.setAntiAlias(true);

        paintText = new Paint();
        paintText.setAntiAlias(true);

        this.list.add(new SkillBean(0, 15, 1, 13.8f, "得分"));
        this.list.add(new SkillBean(0, 10, 1, 4.8f, "篮板"));
        this.list.add(new SkillBean(0, 10, 1, 7.8f, "助攻"));
        this.list.add(new SkillBean(0, 5, 1, 4.8f, "盖帽"));
        this.list.add(new SkillBean(0, 5, 1, 2.8f, "抢断"));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        comWidth = w;
        comHeight = h;
        centerX = comWidth / 2f;
        centerY = comHeight / 2f;
        radius = comHeight / 3f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (list != null && !list.isEmpty()) {
            drawNet(canvas);
            drawValue(canvas);
            drawText(canvas);
        }
    }

    private void drawNet(Canvas canvas) {
        //画线
        for (int i = 0; i < size; i++) {
            canvas.drawLine(centerX, centerY, (float) (centerX - radius * Math.sin(angle * i)), (float) (centerY - radius * Math.cos(angle * i)), paintLine);
        }

        //画环形
        int step = 5; //环的个数
        for (int i = 1; i <= step; i++) {
            //i代表层数
            Path path = new Path();
            path.moveTo((float) (centerX - radius * i / step * Math.sin(0)), (float) (centerY - radius * i / step * Math.cos(0)));
            for (int j = 0; j < size; j++) {
                //j代表：第J线的第层的点
                path.lineTo((float) (centerX - radius * i / step * Math.sin(angle * j)), (float) (centerY - radius * i / step * Math.cos(angle * j)));
            }
            path.close();


            int argb = Color.argb(255 / i, Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor));
            mShapePaint.setColor(argb);

            canvas.drawPath(path, mShapePaint);
        }
    }


    private void drawValue(Canvas canvas) {

        Path path = new Path();
        SkillBean bean = list.get(0);
        path.moveTo((float) (centerX - radius * (bean.getValue() / (bean.getMax() - bean.getMin())) * Math.sin(0)* mAnimatedValue) ,
                (float) (centerY - radius * (bean.getValue() / (bean.getMax() - bean.getMin())) * Math.cos(0)* mAnimatedValue));


        for (int i = 0; i < size; i++) {
            SkillBean skillBean = list.get(i);
            path.lineTo((float) (centerX - radius * (skillBean.getValue() / (skillBean.getMax() - skillBean.getMin())) * Math.sin(angle * i)* mAnimatedValue),
                    (float) (centerY - radius * (skillBean.getValue() / (skillBean.getMax() - skillBean.getMin())) * Math.cos(angle * i)* mAnimatedValue));

            paintValue.setColor(Color.WHITE);

        }
        path.close();
        int argb = Color.argb(255 / 5, Color.red(255), Color.green(255), Color.blue(255));
        paintValue.setColor(argb);
        canvas.drawPath(path, paintValue);
    }

    private void drawText(Canvas canvas){

        for (int i = 0; i < size; i++) {

            String name = list.get(i).getName();
            float value = list.get(i).getValue();

            paintText.setTextAlign(Paint.Align.CENTER);
            paintText.setTextSize(28);

            Paint.FontMetrics fontMetrics = paintText.getFontMetrics();
            float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
            float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

            float baseLineY =  bottom/2-top/2;//基线中间点的y轴计算公式


            canvas.drawText(name +":"+ value, (float) (centerX - radius * 4/ 3 * Math.sin(angle * i)), (float)(centerY - radius * 4 / 3 * Math.cos(angle * i)) + baseLineY,paintText);
//            canvas.drawCircle((float) (centerX - radius * 4/ 3 * Math.sin(angle * i)), (float)(centerY - radius * 4 / 3 * Math.cos(angle * i)),5,paintText);
        }

    }

    public void setData(ArrayList<SkillBean> list) {
        this.list = list;

        if (this.list != null && this.list.size() > 0) {
            size = this.list.size();
            angle = (float) (360 * Math.PI / size / 180);
        }
        startViewAnim(0f, 1f, 1000);
    }

    public ValueAnimator valueAnimator;

    private ValueAnimator startViewAnim(float startF, final float endF, long time) {
        valueAnimator = ValueAnimator.ofFloat(startF, endF);
        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new LinearInterpolator());

//        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);

//        valueAnimator.setRepeatMode(ValueAnimator.RESTART);

        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                OnAnimationUpdate(valueAnimator);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }
        });
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
        }

        return valueAnimator;
    }

    private float mAnimatedValue = 0f;

    protected void OnAnimationUpdate(ValueAnimator valueAnimator) {
        mAnimatedValue = (float) valueAnimator.getAnimatedValue();
        invalidate();
    }
}
