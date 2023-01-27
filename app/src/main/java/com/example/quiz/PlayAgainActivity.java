package com.example.quiz;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class PlayAgainActivity extends Activity {

    Button playAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_again);
        //Initialize
        playAgain = (Button) findViewById(R.id.playAgainButton);

        //play again button onclick listener
        playAgain.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}