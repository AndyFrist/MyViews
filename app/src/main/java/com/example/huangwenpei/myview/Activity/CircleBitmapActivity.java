package com.example.huangwenpei.myview.Activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.Util.AndroidUtil;

public class CircleBitmapActivity extends BaseActivity {

    private ImageView head_imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_bitmap);
        head_imageview = findViewById(R.id.head_imageview);


        Resources res = getResources();
        Bitmap    bmp = BitmapFactory.decodeResource(res, R.drawable.parallax_img);

        bmp = AndroidUtil.circleBitmap(bmp);

        head_imageview.setImageBitmap(bmp);
    }
}
