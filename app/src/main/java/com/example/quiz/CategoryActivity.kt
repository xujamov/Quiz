package com.example.quiz

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
    }

    fun playBone() {
        val intent = Intent(this@CategoryActivity, ForestActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun playMuscle() {
        val intent = Intent(this@CategoryActivity, ClassroomActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun playOrgan() {
        val intent = Intent(this@CategoryActivity, OrgansActivity::class.java)
        startActivity(intent)
        finish()
    }
}