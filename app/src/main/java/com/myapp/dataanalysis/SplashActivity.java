package com.myapp.dataanalysis;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class SplashActivity extends AppCompatActivity {


    public static int splashTimeOut=2000;  //time in mili seconds
    LinearLayout layout1,l2;
    Animation uptodown,downtoup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Animate text in 360 degree in y axis
        //txtAppName.animate().rotationY(360f).setDuration(3000);
        layout1 = (LinearLayout) findViewById(R.id.layout1);
        // l2 = (LinearLayout) findViewById(R.id.l2);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        // downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        layout1.setAnimation(uptodown);
        // l2.setAnimation(downtoup);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,HomeActivity.class);
                startActivity(intent);

                finish();
            }
        },splashTimeOut);

    }
}
