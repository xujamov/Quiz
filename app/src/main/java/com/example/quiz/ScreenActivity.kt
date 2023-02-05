package com.example.quiz

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ScreenActivity : AppCompatActivity() {
    private var playGame: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen)

        //PlayGame button - it will take you to the CategoryActivity
        playGame!!.setOnClickListener {
            val intent = Intent(this@ScreenActivity, CategoryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}