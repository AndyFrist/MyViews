package com.example.huangwenpei.myview.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.Util.AndroidUtils;
import com.example.huangwenpei.myview.Util.LogUtil;


/**
 * Created by Cecil on 2016/10/31.
 */

public class LineChartView extends View {
    private static final String TAG = "LineChartView";
    private static final int DEFAULT_PADDING = 10;
    private final float DP = getResources().getDisplayMetrics().density;
    private final int mDipPadding;
    private final int mFillColor;
    private final int mAxisColor;
    private final float mStrokeWidth;
    private final int mStrokeSpacing;
    private boolean mUseDips;
    private final int mBackgroundColor;
    private Paint mPaint = new Paint();
    private Bitmap mFullImage;
    private Canvas mCanvas;
    //提出来的变量
    private float mTextSizeOfAxis = 15;//坐标轴字体大小
    private float mXAxisUsedWidth = 400;//x轴使用宽度
    private String[] mXAxisContents = {"无", "无"};//x轴的坐标内容
    private float mYAxisPoints[];//数据y轴的坐标
    private float mMinYAxis;//y轴起点，根据数组最大值和最小值的差值换算
    private float mMaxYAxis;//y轴重点，根据数组最大值和最小值的差值换算
    private String mXAxisColor;//横线的颜色，默认是#E6E6E6
    private String mTextColor;//字体的颜色，默认是#999999
    private String mLineStartColor;//渐变折线的起始颜色（淡），默认是#0022ff
    private String mLineEndColor;//渐变折线的终止颜色（浓），默认是#FF4E00
    private String mPathStartColor;//填充色块的起始颜色（淡），默认是#11FFFFFF
    private String mPathEndColor;//填充色块的终止颜色（浓），默认是#aa3344dd
    private boolean bNeedClean = false;

    //使用此函数设置x轴起始坐标和终结坐标，利率（浮点型）数组。
    public void setParams(String xminDesc, String xmaxDesc, float values[]) {
        bNeedClean = true;
        postInvalidate();
        setmXAxisContents(new String[]{xminDesc, xmaxDesc});
        setmYAxisPoints(values);
        this.mYAxisPoints = values;
        float mMinValues = values[0];
        float mMaxValues = values[0];
        for (int i = 1; i < values.length; i++) {
            if (mMinValues > values[i]) {
                mMinValues = values[i];
            }
            if (values[i] > mMaxValues) {
                mMaxValues = values[i];
            }
        }
        float difference = mMaxValues - mMinValues;
        mMinYAxis = mMinValues - 0.5f * difference;
        mMaxYAxis = mMaxValues + 0.05f * difference;
        setmXAxisColor("#efeff4");
        setmTextColor("#9a9a9a");
        setmLineStartColor("#0022ff");
        setmLineEndColor("#FF4E00");
        setmPathStartColor("#11FFFFFF");
        setmPathEndColor("#aa3344dd");
        postInvalidate();
    }

    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDipPadding = getPixelForDip(DEFAULT_PADDING);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.DIYForCharts, 0, 0);
        mFillColor = a.getColor(R.styleable.DIYForCharts_lineStrokeColor, Color.BLACK);
        mAxisColor = a.getColor(R.styleable.DIYForCharts_lineAxisColor, Color.LTGRAY);
        mBackgroundColor = a.getColor(R.styleable.DIYForCharts_lineBackground, Color.TRANSPARENT);
        mStrokeWidth = a.getDimension(R.styleable.DIYForCharts_lineStrokeWidth, 2);
        mStrokeSpacing = a.getDimensionPixelSize(R.styleable.DIYForCharts_lineStrokeSpacing, 10);
        mUseDips = a.getBoolean(R.styleable.DIYForCharts_lineUseDip, false);
        a.recycle();
    }

    public float[] getmYAxisPoints() {
        return mYAxisPoints;
    }

    public void setmXAxisContents(String[] mXAxisContents) {
        this.mXAxisContents = mXAxisContents;
    }

    public void setmYAxisPoints(float[] mYAxisPoints) {
        this.mYAxisPoints = mYAxisPoints;
    }

    public void setmXAxisColor(String mXAxisColor) {
        this.mXAxisColor = mXAxisColor;
    }

    public void setmTextColor(String mTextColor) {
        this.mTextColor = mTextColor;
    }

    public void setmLineStartColor(String mLineStartColor) {
        this.mLineStartColor = mLineStartColor;
    }

    public void setmLineEndColor(String mLineEndColor) {
        this.mLineEndColor = mLineEndColor;
    }

    public void setmPathStartColor(String mPathStartColor) {
        this.mPathStartColor = mPathStartColor;
    }

    public void setmPathEndColor(String mPathEndColor) {
        this.mPathEndColor = mPathEndColor;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        //设置宽高
        setMeasuredDimension(width, height);
    }

    //根据xml的设定获取宽度
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST) {
            specSize = AndroidUtils.px2dip(getContext(), 280);
        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY) {
            if (specSize < 280) {
                specSize = AndroidUtils.px2dip(getContext(), 280);
            }
        }
        this.mXAxisUsedWidth = specSize * 9 / 10;
        return specSize;
    }

    //根据xml的设定获取高度
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST) {
            specSize = AndroidUtils.px2dip(getContext(), 150);
        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY) {
            if (specSize < 150) {
                specSize = AndroidUtils.px2dip(getContext(), 150);
            }
        }
        this.mTextSizeOfAxis = AndroidUtils.px2dip(getContext(), specSize / 60);
        return specSize;
    }

    //重置画笔和抗锯齿
    private void resetPaintWithAntiAlias(Paint paint, boolean antiAlias) {
        paint.reset();
        paint.setAntiAlias(antiAlias);
    }

    public void onDraw(Canvas canvas) {
        if (null == mFullImage) {
            mFullImage = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mFullImage);
        }

        if (bNeedClean) {
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            bNeedClean = false;
        }

        mCanvas.drawColor(mBackgroundColor);

        //没有数据时
        if (mYAxisPoints == null || mYAxisPoints.length == 0) {
            resetPaintWithAntiAlias(mPaint, true);
            mPaint.setColor(Color.parseColor("#000000"));
            mPaint.setStrokeWidth(DP);
            mPaint.setTextAlign(Paint.Align.LEFT);
//            mCanvas.drawText("暂无数据，请稍后重试", getWidth() / 3, getHeight() / 8, mPaint);
            return;
        }

//        mCanvas.drawText("", getWidth() / 3, getHeight() / 8, mPaint);

        //再画5条一样的线
        resetPaintWithAntiAlias(mPaint, true);
        mPaint.setColor(Color.parseColor(mXAxisColor));
        mPaint.setStrokeWidth(DP);


        for (int i = 1; i <= 5; i++) {
            mCanvas.drawLine(getWidth() - mXAxisUsedWidth, getHeight() / 8 + getHeight() * 3 / 16 * (i - 1), getWidth(), getHeight() / 8 + getHeight() * 3 / 16 * (i - 1), mPaint);
        }


        //写y轴的5个数字
        resetPaintWithAntiAlias(mPaint, true);
        mPaint.setColor(Color.parseColor(mTextColor));
        mPaint.setTextSize(mTextSizeOfAxis);
        mPaint.setStrokeWidth(DP);
        mPaint.setTextAlign(Paint.Align.RIGHT);
        float f = mMaxYAxis - mMinYAxis;

        mCanvas.drawText(AndroidUtils.omit4Float(mMaxYAxis) + " ", getWidth() - mXAxisUsedWidth, getHeight() / 8 + mTextSizeOfAxis / 3, mPaint);
        mCanvas.drawText(AndroidUtils.omit4Float(mMaxYAxis - f / 4f) + " ", getWidth() - mXAxisUsedWidth, getHeight() / 8 + getHeight() * 3 / 16 + mTextSizeOfAxis / 3, mPaint);
        mCanvas.drawText(AndroidUtils.omit4Float(mMaxYAxis - f / 2f) + " ", getWidth() - mXAxisUsedWidth, getHeight() / 8 + getHeight() * 3 / 16 * 2 + mTextSizeOfAxis / 3, mPaint);
        mCanvas.drawText(AndroidUtils.omit4Float(mMaxYAxis - 3f * f / 4f) + " ", getWidth() - mXAxisUsedWidth, getHeight() / 8 + getHeight() * 3 / 16 * 3 + mTextSizeOfAxis / 3, mPaint);
        mCanvas.drawText(AndroidUtils.omit4Float(mMaxYAxis - f) + " ", getWidth() - mXAxisUsedWidth, getHeight() / 8 + getHeight() * 3 / 16 * 4 + mTextSizeOfAxis / 3, mPaint);


        //x轴数字
        resetPaintWithAntiAlias(mPaint, true);
        mPaint.setColor(Color.parseColor(mTextColor));
        mPaint.setTextSize(mTextSizeOfAxis);
        mPaint.setStrokeWidth(DP);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mCanvas.drawText(mXAxisContents[0], getWidth() - mXAxisUsedWidth, getHeight() * 7 / 8 + mTextSizeOfAxis, mPaint);
        resetPaintWithAntiAlias(mPaint, true);
        mPaint.setColor(Color.parseColor(mTextColor));
        mPaint.setTextSize(mTextSizeOfAxis);
        mPaint.setStrokeWidth(DP);
        mPaint.setTextAlign(Paint.Align.RIGHT);
        mCanvas.drawText(mXAxisContents[1], getWidth(), getHeight() * 7 / 8 + mTextSizeOfAxis, mPaint);


        int colors[] = new int[]{Color.parseColor(mLineEndColor), Color.parseColor(mLineStartColor)};
        float positions[] = new float[]{0f, 0.5f};
        LinearGradient linearGradient = new LinearGradient(0, 0, 0, getHeight(), colors, positions, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        mPaint.setStrokeWidth(2 * DP);
        Path path = new Path();
        float lastXPoint = 0;
        float lastYPoint = 0;
        float currentXPoint = 0;
        float currentYPoint = 0;
        path.moveTo(getWidth() - mXAxisUsedWidth, getHeight() * 7 / 8);
        LogUtil.d(TAG,getWidth() - 400 +"moveTo" + getHeight() * 7 / 8);
        for (int i = 0; i < mYAxisPoints.length; i++) {
            float xPercent = (float) i / (mYAxisPoints.length - 1);
            float yPercent = (mYAxisPoints[i] - mMinYAxis) / (mMaxYAxis - mMinYAxis);
            if (i == 0) {
                lastXPoint = xPercent * mXAxisUsedWidth + getWidth() - mXAxisUsedWidth;
                lastYPoint = getHeight() * 7 / 8 - yPercent * getHeight() * 3 / 4;
                path.lineTo(lastXPoint, lastYPoint);
                LogUtil.d(TAG,lastXPoint + "@"+ lastYPoint);


            } else {
                currentXPoint = xPercent * mXAxisUsedWidth + getWidth() - mXAxisUsedWidth;
                currentYPoint = getHeight() * 7 / 8 - yPercent * getHeight() * 3 / 4;
                mCanvas.drawLine(lastXPoint, lastYPoint, currentXPoint, currentYPoint, mPaint);
                path.lineTo(currentXPoint, currentYPoint);
                LogUtil.d(TAG,currentXPoint + "#"+ currentYPoint);


                lastXPoint = currentXPoint;
                lastYPoint = currentYPoint;
            }
            if (i == mYAxisPoints.length - 1) {

                path.lineTo(lastXPoint, getHeight() * 7 / 8);
                LogUtil.d(TAG,lastXPoint + "$" + getHeight() * 7 / 8);



                //Log.v("test", "close");
                path.close();
                resetPaintWithAntiAlias(mPaint, true);
                LinearGradient linearGradient1 = new LinearGradient(0, 0, 0, getHeight(), Color.parseColor(mPathEndColor), Color.parseColor(mPathStartColor), Shader.TileMode.REPEAT);
                mPaint.setShader(linearGradient1);
                mCanvas.drawPath(path, mPaint);
            }
        }

        canvas.drawBitmap(mFullImage, 0, 0, null);
    }

    private int getPixelForDip(int dipValue) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dipValue,
                getResources().getDisplayMetrics());
    }


}
