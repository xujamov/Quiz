package com.example.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quiz.databinding.ActivityTimeUpBinding

class TimeUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimeUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //play again button onclick listener
        binding.playAgainButton2.setOnClickListener { finish() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}