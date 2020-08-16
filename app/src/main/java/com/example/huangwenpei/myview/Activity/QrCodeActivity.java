package com.example.huangwenpei.myview.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.Util.BitmapUtil;
import com.example.huangwenpei.myview.Util.ZxingQrCodeUtil;
import com.google.zxing.Result;

public class QrCodeActivity extends AppCompatActivity {
    private ImageView imageView;
    private PopupWindow popupWindow;
    // 要申请的权限
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        initView();
        initData();
    }

    private void initData() {
        //  生成二维码
//        Bitmap zxingqrCode = ZxingQrCodeUtil.getInstance().createQRCode("18531939305",200);
//        imageView.setImageBitmap(zxingqrCode);

        imageView.setImageResource(R.drawable.qrcode);

        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < permissions.length; i++) {
                // 检查该权限是否已经获取
                int permission = ContextCompat.checkSelfPermission(this,permissions[i]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // 如果没有授予该权限，就去提示用户请求
                    ActivityCompat.requestPermissions(this, permissions,i);
                }
            }
        }
    }

    private void initView() {
        imageView = findViewById(R.id.img_qr);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //  显示dialog
                View customView = LayoutInflater.from(QrCodeActivity.this).inflate(R.layout.dialog_long_img, null);
                popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);// 取得焦点
                //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
                popupWindow.setBackgroundDrawable(getDrawable(R.color.white));
                //点击外部消失
                popupWindow.setOutsideTouchable(true);
                //设置可以点击
                popupWindow.setTouchable(true);
                popupWindow.showAtLocation(customView, Gravity.BOTTOM,0,0);
                /**
                 *  获取popupwindow控件
                 */
                TextView saveImg = customView.findViewById(R.id.tv_long_check_save);
                TextView discernImg = customView.findViewById(R.id.tv_long_check_discern);
                TextView cancel = customView.findViewById(R.id.cancel);
                //  保存图片
                saveImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bitmap bitmap = BitmapUtil.getInstance().imgToBitmap(imageView);
                        boolean b = BitmapUtil.getInstance().saveImageToGallery(QrCodeActivity.this, bitmap);
                        if (b){
                            Toast.makeText(QrCodeActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(QrCodeActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                        }
                        popupWindow.dismiss();
                    }
                });

                //  识别图中二维码
                discernImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bitmap bitmap = BitmapUtil.getInstance().imgToBitmap(imageView);
                        Result result = ZxingQrCodeUtil.getInstance().returnQrCode(bitmap);
                        Toast.makeText(QrCodeActivity.this,"QrCode:"+result.getText(), Toast.LENGTH_LONG).show();
                        popupWindow.dismiss();
                    }
                });

                //  取消
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                return false;
            }
        });
    }
}