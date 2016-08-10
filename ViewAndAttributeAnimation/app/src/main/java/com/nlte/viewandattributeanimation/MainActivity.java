package com.nlte.viewandattributeanimation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnViewAnimXml;
    private Button mBtnViewAnimJava;
    private Button mBtnObjectAnimXml;
    private Button mBtnObjectAnimJava;
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();

        mBtnViewAnimXml.setOnClickListener(this);
        mBtnViewAnimJava.setOnClickListener(this);
        mBtnObjectAnimXml.setOnClickListener(this);
        mBtnObjectAnimJava.setOnClickListener(this);
        mIv.setOnClickListener(this);
    }

    private void initUi() {
        mBtnViewAnimXml = (Button) findViewById(R.id.btn_view_anim_xml);
        mBtnViewAnimJava = (Button) findViewById(R.id.btn_view_anim_java);
        mBtnObjectAnimXml = (Button) findViewById(R.id.btn_object_anim_xml);
        mBtnObjectAnimJava = (Button) findViewById(R.id.btn_object_anim_java);
        mIv = (ImageView) findViewById(R.id.iv);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_view_anim_xml:
                mBtnViewAnimXml.startAnimation(AnimationUtils
                        .loadAnimation(this, R.anim.view_animatiom));
                break;
            case R.id.btn_view_anim_java:

                TranslateAnimation translateAnimationH = new TranslateAnimation(0, 60, 0, 0);
                translateAnimationH.setDuration(1000);

                TranslateAnimation translateAnimationV = new TranslateAnimation(0, 0, 0, 200);
                translateAnimationV.setDuration(1000);
                translateAnimationV.setStartOffset(1000);

                final AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(translateAnimationH);
                animationSet.addAnimation(translateAnimationV);

                mBtnViewAnimJava.startAnimation(animationSet);
                break;
            case R.id.btn_object_anim_xml:
                AnimatorSet animatorSetXml = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.obiect_anim);

                animatorSetXml.setTarget(mBtnObjectAnimXml);

                animatorSetXml.start();

                break;
            case R.id.btn_object_anim_java:

                // TODO: 2016/7/21 0021 有没有好的方法实现这种效果
                ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(mBtnObjectAnimJava, "translationX", 0, 60);
                objectAnimatorX.setDuration(1000);


                ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(mBtnObjectAnimJava, "translationY", 0, 200);
                objectAnimatorY.setDuration(1000);

                ObjectAnimator objectAnimatorX1 = ObjectAnimator.ofFloat(mBtnObjectAnimJava, "translationX", 60, 0);
                objectAnimatorX1.setDuration(1000);

                ObjectAnimator objectAnimatorY1 = ObjectAnimator.ofFloat(mBtnObjectAnimJava, "translationY", 200, 0);
                objectAnimatorY1.setDuration(1000);


                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(1000);
                animatorSet.setTarget(mBtnObjectAnimJava);
                animatorSet.playSequentially(
                        objectAnimatorX,
                        objectAnimatorY,
                        objectAnimatorY1,
                        objectAnimatorX1);
                animatorSet.start();

                break;
            case R.id.iv:
                AnimatorSet anim = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.iv_object_rotate);

                anim.setTarget(mIv);
                anim.start();

                break;
        }
    }
}
