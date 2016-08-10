package com.android.ticket.ticket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.android.ticket.MainActivity;
import com.android.ticket.R;


public class AppStartActivity extends Activity {
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        LayoutInflater flater = LayoutInflater.from(this);
        final View view = flater.inflate(R.layout.start_app_layout,null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);

        //渐变展示启动�?
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.1f);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setFillAfter(true);
        view.startAnimation(alphaAnimation);
        //放大效果
        //平移效果

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void redirectTo(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
