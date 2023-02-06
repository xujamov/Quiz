package com.example.quiz

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.quiz.databinding.ActivityForestBinding
import com.example.quiz.databinding.ActivityGameWonBinding

class GameWonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameWonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameWonBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        (binding.speakingGirl.background as AnimationDrawable).start()
        playMusic(R.raw.well_done)
    }

    private fun playMusic(resId: Int) {
        val mp = MediaPlayer.create(this, resId)
        mp.start()
    }

    //This is onclick listener for button
    //it will navigate from this activity to CategoryActivity
    fun playAgain(view: View?) {
        val intent = Intent(this@GameWonActivity, CategoryActivity::class.java)
        startActivity(intent)
        finish()
    }
}