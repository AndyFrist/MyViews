package com.example.huangwenpei.myview.Util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 创建日期：2019/9/23
 * 创建人：崔斌浩
 * QQ:785248126
 * 说明：
 */
public class BitmapUtil {
    private static BitmapUtil bitmapUtil;
    private BitmapCallback bitmapCallback;

    private BitmapUtil(){}

    private interface BitmapCallback{
        void returnBitmap(Bitmap bitmap);
    }

    public void setBitmapCallback(BitmapCallback bitmapCallback) {
        this.bitmapCallback = bitmapCallback;
    }

    public static BitmapUtil getInstance(){
        if (null == bitmapUtil){
            synchronized (BitmapUtil.class){
                if (null == bitmapUtil){
                    bitmapUtil = new BitmapUtil();
                }
            }
        }
        return bitmapUtil;
    }

    /**
     * URL地址生成Bitmap
     * @param url
     * @param bitmapCallback
     */
    public void returnBitmap(final String url, final BitmapCallback bitmapCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bm = null;
                    URL iconUrl = new URL(url);
                    URLConnection conn = iconUrl.openConnection();
                    HttpURLConnection http = (HttpURLConnection) conn;
                    int length = http.getContentLength();
                    conn.connect();
                    // 获得图像的字符流
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is, length);
                    bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    is.close();
                    if (bm != null) {
                        bitmapCallback.returnBitmap(bm);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * ImageView转Bitmap
     * @param imageView
     * @return
     */
    public Bitmap imgToBitmap(ImageView imageView){
        return ((BitmapDrawable)imageView.getDrawable()).getBitmap();
    }

    /**
     * 保存文件到指定路径
     * @param context
     * @param bmp
     * @return
     */
    public boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG,60, fos);
            fos.flush();
            fos.close();

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
