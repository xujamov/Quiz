package com.example.quiz.ui;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.quiz.R;
import com.example.quiz.game.MathGameActivity;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;


public class SplashActivity extends AppCompatActivity {

    private AnimationDrawable mAnimationDrawable;
//    NewtonCradleLoading newtonCradleLoading;
    ImageView imageView;
    Shimmer shimmer;
    ShimmerTextView myShimmerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        myShimmerTextView = (ShimmerTextView) findViewById(R.id.myShimmerTextView);
//        newtonCradleLoading = (NewtonCradleLoading)findViewById(R.id.newton_cradle_loading);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setBackgroundResource(R.drawable.fitnesscat);


        mAnimationDrawable = (AnimationDrawable) imageView.getBackground();

        /*
         * When we get the menu from json
         * use splash
         */
   /*     new CountDownTimer(6000,1000){
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

            }
        } .start();*/

        new CountDownTimer(6000,1000){
            public void onTick(long millisUntilFinished){
                mAnimationDrawable.start();
                shimmer = new Shimmer();
                shimmer.start(myShimmerTextView);
//                newtonCradleLoading.start();
//                newtonCradleLoading.setLoadingColor(R.color.colorPrimary);
            }
            public void onFinish(){
                Intent intent = new Intent(SplashActivity.this, MathGameActivity.class);
                startActivity(intent);
                finish();

            }
        } .start();

    }

}
