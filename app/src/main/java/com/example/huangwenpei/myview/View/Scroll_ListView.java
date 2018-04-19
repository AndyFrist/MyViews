package com.example.huangwenpei.myview.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by
 * huangwenpei on 2018/4/19.
 */

public class Scroll_ListView extends ListView {
    private static final String TAG = "Scroll_ListView";
    private int mOriginalHeight;
    private int drawableHeight;
    private ImageView mImage;

    public Scroll_ListView(Context context) {
        super(context);
    }

    public Scroll_ListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Scroll_ListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setParallaxImage(ImageView mImage) {
        this.mImage = mImage;
        mOriginalHeight = mImage.getHeight(); // 160
        drawableHeight = mImage.getDrawable().getIntrinsicHeight(); // 488

        Log.d(TAG, "height: " + mOriginalHeight + " drawableHeight: " + drawableHeight);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        // deltaY : 竖直方向的瞬时偏移量 / 变化量 dx   顶部到头下拉为-, 底部到头上拉为+
        // scrollY : 竖直方向的偏移量 / 变化量
        // scrollRangeY : 竖直方向滑动的范围
        // maxOverScrollY : 竖直方向最大滑动范围
        // isTouchEvent : 是否是手指触摸滑动, true为手指, false为惯性

        Log.d(TAG, "deltaY: " + deltaY + " scrollY: " + scrollY + " scrollRangeY: " + scrollRangeY + " maxOverScrollY: " + maxOverScrollY + " isTouchEvent: " + isTouchEvent);


        // 手指拉动 并且 是下拉
        if (isTouchEvent && deltaY < 0) {
            // 把拉动的瞬时变化量的绝对值交给Header, 就可以实现放大效果
            if (mImage.getHeight() <= drawableHeight) {
                int newHeight = (int) (mImage.getHeight() + Math.abs(deltaY / 3.0f));

                // 高度不超出图片最大高度时,才让其生效
                mImage.getLayoutParams().height = newHeight;
                mImage.requestLayout();
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                // 执行回弹动画, 方式一: 属性动画\值动画
                // 从当前高度mImage.getHeight(), 执行动画到原始高度mOriginalHeight
                final int startHeight = mImage.getHeight();
                final int endHeight = mOriginalHeight;

                //				valueAnimator(startHeight, endHeight);

                // 执行回弹动画, 方式二: 自定义Animation
                ResetAnimation animation = new ResetAnimation(mImage, startHeight, endHeight);
                startAnimation(animation);
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void valueAnimator(final int startHeight, final int endHeight) {
        ValueAnimator mValueAnim = ValueAnimator.ofInt(1);
        mValueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator mAnim) {
                float fraction = mAnim.getAnimatedFraction();
                // percent 0.0 -> 1.0
                Log.d(TAG, "fraction: " +fraction);
                Integer newHeight = evaluate(fraction, startHeight, endHeight);

                mImage.getLayoutParams().height = newHeight;
                mImage.requestLayout();
            }
        });

        mValueAnim.setInterpolator(new OvershootInterpolator(3));//差之器，弹性力度
        mValueAnim.setDuration(500);
        mValueAnim.start();
    }
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int)(startInt + fraction * (endValue - startInt));
    }
}
