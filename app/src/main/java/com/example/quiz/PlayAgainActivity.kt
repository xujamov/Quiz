package com.example.quiz

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.example.quiz.databinding.ActivityPlayAgainBinding

class PlayAgainActivity : Activity() {
    private lateinit var binding: ActivityPlayAgainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayAgainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.playAgainButton.setOnClickListener { view: View? -> finish() }
    }

    override fun onBackPressed() {
        finish()
    }
}