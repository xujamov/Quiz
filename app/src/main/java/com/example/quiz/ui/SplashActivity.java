package com.example.quiz.ui;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.quiz.R;
import com.example.quiz.ScreenActivity;
import com.example.quiz.game.MathGameActivity;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;


public class SplashActivity extends AppCompatActivity {
    ImageView imageView;
    Shimmer shimmer;
    ShimmerTextView myShimmerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        myShimmerTextView = findViewById(R.id.myShimmerTextView);
        imageView = findViewById(R.id.imageView);

        Glide.with(this)
                .asGif()
                .load(R.drawable.coding)
                .into(imageView);

        new CountDownTimer(7000,1000){
            public void onTick(long millisUntilFinished){
                shimmer = new Shimmer();
                shimmer.start(myShimmerTextView);
            }
            public void onFinish(){
                Intent intent = new Intent(SplashActivity.this, MathGameActivity.class);
                startActivity(intent);
                finish();
            }
        } .start();

    }

}
