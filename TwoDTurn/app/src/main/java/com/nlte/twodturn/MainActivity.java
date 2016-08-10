package com.nlte.twodturn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView mIvA;
    private ImageView mIvB;
    private ScaleAnimation mScaleAnimationA;
    private ScaleAnimation mScaleAnimationB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化动画
        initAnimation();

        //初始化界面UI
        initUi();
    }

    public void rootViewClick(View view){
        if (mIvA.getVisibility() == View.VISIBLE){
            mIvA.startAnimation(mScaleAnimationA);
        }else {
            mIvB.startAnimation(mScaleAnimationA);
        }
    }

    private void showA(){
        mIvA.setVisibility(View.VISIBLE);
        mIvB.setVisibility(View.INVISIBLE);
    }
    private void showB(){
        mIvA.setVisibility(View.INVISIBLE);
        mIvB.setVisibility(View.VISIBLE);
    }

    private void initAnimation() {
        mScaleAnimationA = new ScaleAnimation(1, 0, 1, 1,
                Animation.RELATIVE_TO_PARENT, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0.5f);
        mScaleAnimationA.setDuration(500);

        mScaleAnimationB = new ScaleAnimation(0, 1, 1, 1,
                Animation.RELATIVE_TO_PARENT, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0.5f);
        mScaleAnimationB.setDuration(500);

        mScaleAnimationA.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mIvA.getVisibility() == View.VISIBLE){
                    mIvA.setAnimation(null);
                    showB();
                    mIvB.startAnimation(mScaleAnimationB);
                }else {
                    mIvB.setAnimation(null);
                    showA();
                    mIvA.startAnimation(mScaleAnimationB);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void initUi() {
        mIvA = (ImageView) findViewById(R.id.ivA);
        mIvB = (ImageView) findViewById(R.id.ivB);
        showA();
    }
}
