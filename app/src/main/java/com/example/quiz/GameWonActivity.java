package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.graphics.drawable.AnimationDrawable;

public class GameWonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_won);

        ImageView mImageViewFilling = (ImageView) findViewById(R.id.speak);
        ((AnimationDrawable) mImageViewFilling.getBackground()).start();
        playMusic(R.raw.well_done);
    }

    public void playMusic(int resId){
        MediaPlayer mp = MediaPlayer.create(this, resId);
        mp.start();
    }

    //This is onclick listener for button
    //it will navigate from this activity to MainGameActivity
    public void PlayAgain(View view) {
        Intent intent = new Intent(GameWonActivity.this, CategoryActivity.class);
        startActivity(intent);
        finish();
    }
}