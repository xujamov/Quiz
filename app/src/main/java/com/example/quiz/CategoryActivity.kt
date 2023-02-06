package com.example.quiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
    }

    fun playBone(view: View) {
        val intent = Intent(this@CategoryActivity, ForestActivity::class.java)
        startActivity(intent)
        finish()
    }

//    fun playMuscle(view: View) {
//        val intent = Intent(this@CategoryActivity, ClassroomActivity::class.java)
//        startActivity(intent)
//        finish()
//    }
//
//    fun playOrgan() {
//        val intent = Intent(this@CategoryActivity, OrgansActivity::class.java)
//        startActivity(intent)
//        finish()
//    }
}