package com.example.huangwenpei.myview;

import android.app.Application;
import android.content.Context;

/**
 * Created by
 * huangwenpei on 2018/4/16.
 */

public class MyApp extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
