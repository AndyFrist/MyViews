package com.example.huangwenpei.myview.Activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.Util.AndroidUtil;
import com.example.huangwenpei.myview.View.AutoProgressBar;
import com.example.huangwenpei.myview.View.AutoProgressView;

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

        AutoProgressView autoProgressView = findViewById(R.id.auto_process);

        autoProgressView.setProgress(0.75);
        autoProgressView.setRateBackgroundColor("#e40000");
        autoProgressView.setOrientation(LinearLayout.VERTICAL);
        autoProgressView.setAnimRate((int) (0.75 * 30));

        AutoProgressBar auto_process_bar = findViewById(R.id.auto_process_bar);
        auto_process_bar.setProgressValue(80, AutoProgressBar.Orientation.Vertical);

        AutoProgressBar auto_process_bar2 = findViewById(R.id.auto_process_bar2);
        auto_process_bar2.setProgressValue(80, AutoProgressBar.Orientation.Horizontal);

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
