package com.example.huangwenpei.myview.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.huangwenpei.myview.R;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2017/5/3.
 * 嵌套了viewpager的图片浏览
 */

public class ShowImagesDialog extends Dialog {

    private View mView;
    private Activity mContext;
    private PhotoView photoView;
    private String imgUrls;

    public ShowImagesDialog(@NonNull Activity context, String imgUrls) {
        super(context, R.style.transparentBgDialog);
        this.mContext = context;
        this.imgUrls = imgUrls;


        initView();
        initData();
    }

    private void initView() {
        photoView = new uk.co.senab.photoview.PhotoView(mContext);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(photoView);
        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;

        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        wl.height = metrics.heightPixels;
        wl.width = metrics.widthPixels;
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
    }

    private void initData() {
        //点击图片监听

        Glide.with(mContext)
                .load(imgUrls)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        photoView.setImageDrawable(resource);
                    }
                });

    }

}
