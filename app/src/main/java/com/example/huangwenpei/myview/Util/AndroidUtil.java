package com.example.huangwenpei.myview.Util;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by
 * huangwenpei on 2018/4/16.
 */

public class AndroidUtil {

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
    }
}
