package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

//        ImageView mImageViewFilling = (ImageView) findViewById(R.id.speak);
//        ((AnimationDrawable) mImageViewFilling.getBackground()).start();

    }

    public void PlaySkelet(View view) {
        Intent intent = new Intent(CategoryActivity.this, BodyActivity.class);
        startActivity(intent);
        finish();
    }
}