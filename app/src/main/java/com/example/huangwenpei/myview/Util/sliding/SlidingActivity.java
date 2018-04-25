package com.example.huangwenpei.myview.Util.sliding;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.huangwenpei.myview.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by
 * xuxiaopeng on 14-3-17.
 */
public class SlidingActivity extends FragmentActivity implements SlidingLayout.SlidingListener {
    private View mPreview;

    private float mInitOffset;      //开关偏移量
    private boolean hideTitle = false;
    private int titleResId = -1;
    private String mBitmapId;
    protected ImageView back_title;
    protected TextView label_title;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.slide_layout);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        LayoutInflater inflater = LayoutInflater.from(this);
        mInitOffset = -(1.f / 3) * metrics.widthPixels;
        mPreview = findViewById(R.id.iv_preview);


        FrameLayout contentView = findViewById(R.id.content_view);
        if (!hideTitle) {
            int resId = -1 == titleResId ? R.layout.title_layout : titleResId;

            if (titleResId == -1) {
                resId = R.layout.title_layout;
            }else{
                resId = titleResId;
            }

            View title_view = inflater.inflate(resId, contentView);
            if (titleResId != -1) {
                back_title  = title_view.findViewById(R.id.back_title);
                label_title  = title_view.findViewById(R.id.label_title);
                label_title.setText(getClass().getSimpleName());
            }
        }
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT, Gravity.BOTTOM);
        final int marginTop = hideTitle ? 0 : (int) (metrics.density * 48.f + .5f);
        lp.setMargins(0, marginTop, 0, 0);
        contentView.addView(inflater.inflate(layoutResID, null), lp);




        final SlidingLayout slideLayout = findViewById(R.id.slide_layout);
        slideLayout.setShadowResource(R.drawable.sliding_back_shadow);
        slideLayout.setSlidingListener(this);
        slideLayout.setEdgeSize((int) (metrics.density * 20));
        mBitmapId = getIntent().getExtras().getString("bitmap_id");
        Bitmap bitmap = IntentUtils.getInstance().getBitmap(mBitmapId);
        if (null != bitmap) {
            if (Build.VERSION.SDK_INT >= 16) {
                mPreview.setBackgroundDrawable(new BitmapDrawable(bitmap));
            } else {
                mPreview.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }
            IntentUtils.getInstance().setIsDisplayed(mBitmapId, true);
        }
    }

    protected void setContentView(int layoutResID, int titleResId) {
        this.titleResId = titleResId;
        setContentView(layoutResID);
    }

    protected void setContentView(int layoutResID, boolean hideTitle) {
        this.hideTitle = hideTitle;
        setContentView(layoutResID);
    }


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        ((TextView) findViewById(R.id.label_title)).setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        ((TextView) findViewById(R.id.label_title)).setText(titleId);
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        if (slideOffset <= 0) {
        } else if (slideOffset < 1) {
            mPreview.setTranslationX(mInitOffset * (1 - slideOffset));
        } else {
            mPreview.setTranslationX(0);
            finish();
            overridePendingTransition(0, 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IntentUtils.getInstance().setIsDisplayed(mBitmapId, false);
    }
}
