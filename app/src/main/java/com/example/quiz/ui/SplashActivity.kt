package com.example.quiz.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.quiz.R
import com.example.quiz.ScreenActivity
import com.example.quiz.databinding.ActivitySplashScreenBinding
import com.romainpiel.shimmer.Shimmer

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    var shimmer: Shimmer? = null
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.imageView.let {
            Glide.with(this)
                .asGif()
                .load(R.drawable.coding)
                .into(it)
        }
        object : CountDownTimer(7000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                shimmer = Shimmer()
                shimmer!!.start(binding.myShimmerTextView)
            }

            override fun onFinish() {
                val intent = Intent(this@SplashActivity, ScreenActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }
}