package com.example.huangwenpei.myview;

import android.app.Application;
import android.content.Context;

import com.example.huangwenpei.myview.zhifubao.AppConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Properties;

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
        init();
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 初始化
     */
    private void init() {

    }

}
