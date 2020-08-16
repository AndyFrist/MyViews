package com.example.huangwenpei.myview.Activity.animater;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.huangwenpei.myview.R;

public class BaseAnimatorActivity extends AppCompatActivity implements View.OnClickListener {

    private Button translationBtn ;
    private Button roationBtn ;
    private Button scalBtn ;
    private Button alphaBtn ;
    private Button listBtn ;

    private Button testBtn ;

    private ImageView iv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_animator);

        translationBtn = (Button)findViewById(R.id.translation);
        roationBtn = (Button) findViewById(R.id.roation);
        scalBtn = (Button) findViewById(R.id.scal);
        alphaBtn = (Button) findViewById(R.id.alpha);
        listBtn = (Button) findViewById(R.id.list);

        testBtn = (Button) findViewById(R.id.test);

        iv = (ImageView) findViewById(R.id.iv);


        translationBtn.setOnClickListener(this);
        roationBtn.setOnClickListener(this);
        scalBtn.setOnClickListener(this);
        testBtn.setOnClickListener(this);
        alphaBtn.setOnClickListener(this);
        listBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.translation:
                translation();
                break;
            case R.id.roation:
                rotation();
                break;
            case R.id.scal:
                scal();
                break;
            case R.id.alpha:
                alpha();
                break;
            case R.id.list:
                list();
                break;

            case R.id.test:
                Toast.makeText(this, "测试", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //帧动画
    private void list() {
        //方式一
        AnimationDrawable drawable = (AnimationDrawable) iv.getBackground();
        if(drawable.isRunning()){
            drawable.stop();
        }else {
            drawable.start();
        }

        //方式二
//        AnimationDrawable animationDrawable = new AnimationDrawable() ;
//        int[] ids = {R.drawable.anim_1,R.drawable.anim_2,R.drawable.anim_3,R.drawable.anim_4};
//        //通过循环将每一帧添加到AnimationDrawable中
//        for(int i = 0 ; i < ids.length ; i ++){
//            Drawable frame = getResources().getDrawable(ids[i]);
//            animationDrawable.addFrame(frame,200);
//        }
//        animationDrawable.setOneShot(false);
//
//        iv.setBackground(animationDrawable);
//
//        animationDrawable.start();



    }

    //透明度动画
    private void alpha() {
        //方式一
        /*Animation animation = AnimationUtils.loadAnimation(this, R.anim.base_anim_alpha);
        testBtn.startAnimation(animation);*/
        //方式二
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f,0.2f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
        testBtn.startAnimation(alphaAnimation);

    }

    //缩放动画
    private void scal() {
        //方式一
        /*Animation animation = AnimationUtils.loadAnimation(this, R.anim.base_anim_scal);
        testBtn.startAnimation(animation);*/

        //方式二
        ScaleAnimation scaleAnimation = new ScaleAnimation(1,2,1,2, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setFillAfter(true);
        testBtn.startAnimation(scaleAnimation);
    }

    //旋转动画
    private void rotation() {
        //方式一
        /*Animation animation = AnimationUtils.loadAnimation(this, R.anim.base_anim_rotation);
        testBtn.startAnimation(animation);*/

        //方式二
        RotateAnimation rotateAnimation = new RotateAnimation(0,90,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);

        testBtn.startAnimation(rotateAnimation);
    }

    //平移动画
    private void translation() {
        //方式一
        /*Animation animation = AnimationUtils.loadAnimation(this, R.anim.base_anim_translation);
        testBtn.startAnimation(animation);*/

        //方式二
        TranslateAnimation translateAnimation = new TranslateAnimation(0,200,0,200);
        translateAnimation.setDuration(500);//设置执行时间
        translateAnimation.setFillAfter(true);

        testBtn.startAnimation(translateAnimation);
    }
}
