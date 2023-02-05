package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

    }

    public void PlaySkelet(View view) {
        Intent intent = new Intent(CategoryActivity.this, ForestActivity.class);
        startActivity(intent);
        finish();
    }

    public void PlayMuscle(View view) {
        Intent intent = new Intent(CategoryActivity.this, ClassroomActivity.class);
        startActivity(intent);
        finish();
    }

    public void PlayOrgan(View view) {
        Intent intent = new Intent(CategoryActivity.this, OrgansActivity.class);
        startActivity(intent);
        finish();
    }
}