package com.example.huangwenpei.myview.Activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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

        initListener();

    }


    private void initListener() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            Toast.makeText(this, "手机没有闪光灯,开启屏幕最强亮度!", Toast.LENGTH_SHORT).show();
            head_imageview.setVisibility(View.INVISIBLE);
            screenLight();
        } else {
            head_imageview.setOnClickListener(v -> {
                if (isOpen) {
                    openTorch();
                    head_imageview.setBackgroundResource(R.mipmap.flashlight);
                } else {
                    closeTorch();
                    head_imageview.setBackgroundResource(R.mipmap.flashlight);
                }
                isOpen = !isOpen;
            });
        }
    }

    private void screenLight() {
        Window localWindow = this.getWindow();
        WindowManager.LayoutParams params = localWindow.getAttributes();
        params.screenBrightness = 1.0f;
        localWindow.setAttributes(params);
    }

    private void openTorch() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                manager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
                if (manager != null) {
                    manager.setTorchMode("0", true);
                }
            } else {
                camera = Camera.open();
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeTorch() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (manager == null) {
                    return;
                }
                manager.setTorchMode("0", false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (camera == null) {
                return;
            }
            camera.stopPreview();
            camera.release();
        }
    }

    @Override
    protected void onDestroy() {
        closeTorch();
        super.onDestroy();
    }

    private Camera camera;
    private CameraManager manager;

    private boolean isOpen = true;
}
