package com.example.quiz.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.quiz.R
import com.example.quiz.ScreenActivity
import com.romainpiel.shimmer.Shimmer
import com.romainpiel.shimmer.ShimmerTextView

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private var imageView: ImageView? = null
    var shimmer: Shimmer? = null
    var myShimmerTextView: ShimmerTextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        imageView?.let {
            Glide.with(this)
                .asGif()
                .load(R.drawable.coding)
                .into(it)
        }
        object : CountDownTimer(7000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                shimmer = Shimmer()
                shimmer!!.start<ShimmerTextView>(myShimmerTextView)
            }

            override fun onFinish() {
                val intent = Intent(this@SplashActivity, ScreenActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }
}