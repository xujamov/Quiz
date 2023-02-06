package com.example.quiz

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.quiz.databinding.ActivityScreenBinding
import com.example.quiz.databinding.ActivitySplashScreenBinding

class ScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //PlayGame button - it will take you to the CategoryActivity
        binding.playGame.setOnClickListener {
            val intent = Intent(this@ScreenActivity, CategoryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}