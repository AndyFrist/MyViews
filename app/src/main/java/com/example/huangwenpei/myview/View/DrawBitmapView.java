package com.example.huangwenpei.myview.View;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.huangwenpei.myview.R;

public class DrawBitmapView extends View {
    private Resources mResources;
    private Paint mBitPaint;
    private Bitmap mBitmap;
    private Rect mSrcRect, mDestRect;

    // view 的宽高
    private int mBitWidth;
    private int mBitHeight;

    public DrawBitmapView(Context context) {
        this(context, null);
    }

    public DrawBitmapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mResources = getResources();
        mBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.star)).getBitmap();

        initPaint();
    }

    public DrawBitmapView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void initPaint() {
        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
    }

    private void initBitmap() {
//        mBitWidth = mBitmap.getWidth();
//        mBitHeight = mBitmap.getHeight();

        //方法一:选择绘画区域为下半图  选择绘画内容为下半图
        mSrcRect = new Rect(0, 0, mBitWidth, mBitHeight);
        mDestRect = new Rect(0, 0, mBitWidth, mBitHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mDestRect,mBitPaint);
        //方法一:绘制
        canvas.drawBitmap(mBitmap, mSrcRect, mDestRect, mBitPaint);

        //方法二:裁剪
//        canvas.clipRect(0, mBitHeight/2, mBitWidth, mBitHeight);
//        canvas.drawBitmap(mBitmap, 0, 0, mBitPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitWidth = w;
        mBitHeight = h;
        initBitmap();
    }
}