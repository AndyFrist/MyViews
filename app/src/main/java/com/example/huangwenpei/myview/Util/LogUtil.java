package com.example.huangwenpei.myview.Util;

import android.util.Log;

/**
 * 打印日志的控制类
 * 2015.8.20  xuxiaopeng
 * */
public class LogUtil {

	private LogUtil() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	private static final boolean isDebug = true;
	private static final String TAG = "com.alading6688";

	public static void i(String msg) {
		if (isDebug) {
            Log.i(TAG, msg);
        }
	}

	public static void d(String msg) {
		if (isDebug) {
            Log.d(TAG, msg);
        }
	}

	public static void e(String msg) {
		if (isDebug) {
            Log.e(TAG, msg);
        }
	}

	public static void v(String msg) {
		if (isDebug) {
            Log.v(TAG, msg);
        }
	}

	public static void i(String tag, String msg) {
		if (isDebug) {
            Log.i(tag, msg);
        }
	}

	public static void d(String tag, String msg) {
		if (isDebug) {
            Log.i(tag, msg);
        }
	}

	public static void e(String tag, String msg) {
		if (isDebug) {
            Log.i(tag, msg);
        }
	}

	public static void v(String tag, String msg) {
		if (isDebug) {
            Log.i(tag, msg);
        }
	}

}
