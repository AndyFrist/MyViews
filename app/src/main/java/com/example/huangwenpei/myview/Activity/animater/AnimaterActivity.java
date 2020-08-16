package com.example.huangwenpei.myview.Activity.animater;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.example.huangwenpei.myview.R;
import com.example.huangwenpei.myview.View.CircleView;

public class AnimaterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button translationBtn ;
    private Button roationBtn ;
    private Button scalBtn ;
    private Button alphaBtn ;
    private Button valueBtn ;
    private Button setBtn ;

    private CircleView mycircle ;

    private Button testBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animater);


        translationBtn = (Button)findViewById(R.id.translation);
        roationBtn = (Button) findViewById(R.id.roation);
        scalBtn = (Button) findViewById(R.id.scal);
        alphaBtn = (Button) findViewById(R.id.alpha);
        setBtn = (Button) findViewById(R.id.setBtn);
        valueBtn = (Button) findViewById(R.id.value);

        testBtn = (Button) findViewById(R.id.test);

        mycircle = (CircleView) findViewById(R.id.mycircle);



        translationBtn.setOnClickListener(this);
        roationBtn.setOnClickListener(this);
        scalBtn.setOnClickListener(this);
        testBtn.setOnClickListener(this);
        alphaBtn.setOnClickListener(this);
        setBtn.setOnClickListener(this);
        valueBtn.setOnClickListener(this);
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
            case R.id.setBtn:
                set();
                break;
            case R.id.value:
                value();
                mycircle.startAnimater();
                break;
            case R.id.test:
                Toast.makeText(this, "属性动画测试", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void value(){
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new CharEvaluator(),new Character('A'), new Character('Z'));
        valueAnimator.setEvaluator(new CharEvaluator());
        valueAnimator.setDuration(10000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                char value = (char) valueAnimator.getAnimatedValue();
                testBtn.setText(""+value);
            }
        });
        valueAnimator.start();
    }

    class CharEvaluator implements TypeEvaluator<Character>{


        @Override
        public Character evaluate(float fraction, Character startValue, Character endValue) {
            //计算规则
            int reslut = (int) (startValue + fraction * (endValue - startValue));

            return (char)reslut;
        }
    }








    private void set() {
        PropertyValuesHolder translateXAnimaotr = PropertyValuesHolder.ofFloat("translationX", 0f, 200f);
        //Y方向平移
        PropertyValuesHolder translateYAnimaotr = PropertyValuesHolder.ofFloat("translationY", 0f, 300f);
        //实现旋转动画，也可以单独使用rotationX或rotationY
        //rotationX 表示围绕 X 轴旋转
        //rotationY:表示围绕 Y 轴旋转
        //rotation:表示围绕 Z 旋转
        PropertyValuesHolder rotationAnimaotr = PropertyValuesHolder.ofFloat("rotation", 0f, 360f, 0f);
        //缩放动画，也有X及Y两个方向上设置
        PropertyValuesHolder scaleXAnimator = PropertyValuesHolder.ofFloat("scaleX", 1f, 3f);
        PropertyValuesHolder scaleYAnimator = PropertyValuesHolder.ofFloat("scaleY", 1f, 3f);
        //透明度动画
        PropertyValuesHolder alphaAnimator = PropertyValuesHolder.ofFloat("alpha", 0.5f, 1f);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(testBtn, translateXAnimaotr, translateYAnimaotr, rotationAnimaotr, scaleXAnimator, scaleYAnimator,alphaAnimator);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }


    private void alpha(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(testBtn,"alpha",0.2f);
        animator.setDuration(500);
        animator.start();

    }
    private void scal() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(testBtn,"scaleX",2);
        animator.setDuration(500);
        animator.start();
    }

    private void rotation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(testBtn,"rotation",90);
        animator.setDuration(500);
        animator.start();
    }

    private void translation() {
        //1常用
        /*ObjectAnimator animator = ObjectAnimator.ofFloat(testBtn,"translationX",200);
        animator.setDuration(500);
        animator.start();*/

        //2同时改变多个属性值
//        testBtn.animate().translationX(200).setDuration(500).translationY(200).start();

        //3通过PropertyValuesHolder改变多个属性值
        /*PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("translationX",200);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("translationY",200);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(testBtn, holder1, holder2);
        animator.setDuration(500);
        animator.start();*/

        //4关键帧
        Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
        Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 400);
        Keyframe keyframe3 = Keyframe.ofFloat(1f, 200);

        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofKeyframe("translationX", keyframe1, keyframe2, keyframe3);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(testBtn,valuesHolder);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }
}
