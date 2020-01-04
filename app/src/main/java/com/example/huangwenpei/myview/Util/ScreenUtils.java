package com.example.huangwenpei.myview.Util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class ScreenUtils {

    public static int screenHeight;
    public static int screenWidth;

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInputmethod(Context context)

    {
        Activity activity = (Activity) context;
        if (null != activity.getCurrentFocus()) {
            InputMethodManager im = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(activity.getCurrentFocus()
                            .getApplicationWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void showSoftInputmethod(Context context)

    {
        Activity activity = (Activity) context;
        if (null != activity.getCurrentFocus()) {
            InputMethodManager im = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(activity.getCurrentFocus()
                            .getApplicationWindowToken(),
                    InputMethodManager.SHOW_FORCED);
        }
    }

    public static int dp2px(Context context, float dpValue) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        if (screenWidth == 0) {
            setScreenProperties(context);
        }
        return screenWidth;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenHeight(Context context) {
        if (screenHeight == 0) {
            setScreenProperties(context);
        }
        return screenHeight;
    }

    /**
     * 私有方法，一次性，将全部的属性获取出来，静态保存
     */
    private static void setScreenProperties(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics metric = new DisplayMetrics();
            display.getMetrics(metric);
            screenWidth = metric.widthPixels;
            screenHeight = metric.heightPixels;
        }
    }



}
