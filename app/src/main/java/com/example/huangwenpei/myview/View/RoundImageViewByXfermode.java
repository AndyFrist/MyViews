package com.example.huangwenpei.myview.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


import com.example.huangwenpei.myview.R;

import java.lang.ref.WeakReference;

/**
 * 圆形图片
 */
public class RoundImageViewByXfermode extends ImageView {

    private Paint mPaint;
    private Xfermode mXfermode = new PorterDuffXfermode(Mode.DST_IN);
    private Bitmap mMaskBitmap;
    private WeakReference<Bitmap> mWeakBitmap;

    public RoundImageViewByXfermode(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
//        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = mWeakBitmap == null ? null : mWeakBitmap.get();
        if (null == bitmap || bitmap.isRecycled()) {
            Drawable drawable = getDrawable();// 拿到drawable
            if (drawable == null) {
                drawable = getContext().getResources().getDrawable(R.mipmap.ic_launcher);
            }

            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();

            if (null != drawable) {
                // 创建bitmap
                bitmap = Bitmap.createBitmap(getWidth() + 1, getHeight() + 1, Bitmap.Config.ARGB_8888);
                float scale = 1.0f;

                // 创建canvas
                Canvas drawCanvas = new Canvas(bitmap);
                scale = getWidth() * 1.0F / Math.min(width, height);
                drawable.setBounds(0, 0, (int) (scale * width), (int) (scale * height));
                drawable.draw(drawCanvas);
                if (null == mMaskBitmap || mMaskBitmap.isRecycled()) {
                    mMaskBitmap = getCircleBitmap();
                }
                mPaint.reset();
                mPaint.setFilterBitmap(false);
                mPaint.setXfermode(mXfermode);
                // 绘制形状
                drawCanvas.drawBitmap(mMaskBitmap, 0, 0, mPaint);
                mPaint.setXfermode(null);
                // 将准备好的bitmap绘制出来
                canvas.drawBitmap(bitmap, 0, 0, null);
                // bitmap缓存起来，避免每次调用onDraw，分配内存
                mWeakBitmap = new WeakReference<Bitmap>(bitmap);

            }

        }
        // 如果bitmap还存在，则直接绘制即可
        if (bitmap != null) {
            mPaint.setXfermode(null);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, mPaint);
            return;
        }

    }

    /**
     * 创建圆形图片
     */
    public Bitmap getCircleBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth() + 1, getHeight() + 1, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, paint);
        return bitmap;
    }

    @Override
    public void invalidate() {
        mWeakBitmap = null;
        if (mMaskBitmap != null) {
            mMaskBitmap.recycle();
            mMaskBitmap = null;
        }
        super.invalidate();
    }
}
