package com.example.huangwenpei.myview.Util;

import android.content.Context;
import android.view.WindowManager;


import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/11/27.
 */

public class AndroidUtils {
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
    /**
     * @param context
     * @return 状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int px2dip(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static String omit4Float(float f) {
        BigDecimal b = new BigDecimal(f);
        float f1 = b.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue();
        DecimalFormat decimalFormat = new DecimalFormat("##0.0000");
        String s = decimalFormat.format(f1);
        return s;
    }
}
