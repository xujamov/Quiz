package com.example.quiz

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.example.quiz.databinding.ActivityOrgansBinding
import java.util.*
import java.util.stream.Collectors

class OrgansActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityOrgansBinding

    private var listIv: MutableList<ImageView?> = ArrayList()
    private var questionIvId = 0

    // list of organs elements
    private var organList: MutableList<Int> = ArrayList()
    private var partsListAll: List<Int> = ArrayList()
    private var partsList: List<Int> = ArrayList()
    private val partsSize = 8
    private var correctAnswers = 0
    private var selectedQuestions: List<Question?> = ArrayList()
    private var savollarAll: MutableList<Question> = ArrayList()
    private var savollar: MutableList<Question?> = ArrayList()
    private var javoblar: MutableList<String?> = ArrayList()
    private var textViews: MutableList<Int> = ArrayList()
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrgansBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        questionIvId = View.generateViewId()
        implementOrganLists()
        partsList = ArrayList(organList)
        partsListAll = ArrayList(organList)
        val QuestionHelper = QuestionHelperMath(this)
        //        savollar = QuestionHelper.getAllOfTheQuestions();
//        savollarAll = QuestionHelper.getAllOfTheQuestions();
        savollar.add(Question("70 + 80 = ?", "150"))
        savollarAll.add(Question("70 + 80 = ?", "150"))
        savollar.add(Question("94 – x = 47\nx = ?", "47"))
        savollarAll.add(Question("94 – x = 47\nx = ?", "47"))
        savollar.add(
            Question(
                "To`rtburchakning bitta uchini kessak necha burchak hosil bo`ladi?",
                "5"
            )
        )
        savollarAll.add(
            Question(
                "To`rtburchakning bitta uchini kessak necha burchak hosil bo`ladi?",
                "5"
            )
        )
        savollar.add(Question("72 : 8 = ?", "9"))
        savollarAll.add(Question("72 : 8 = ?", "9"))
        savollar.add(
            Question(
                "Bir yuz yetmish olti soni to`g`ri yozilgan qatorni aniqlang.",
                "176"
            )
        )
        savollarAll.add(
            Question(
                "Bir yuz yetmish olti soni to`g`ri yozilgan qatorni aniqlang.",
                "176"
            )
        )
        savollar.add(
            Question(
                "6 raqami o`nlik xona birligida joylashgan  uch xonali sonni toping.",
                "567"
            )
        )
        savollarAll.add(
            Question(
                "6 raqami o`nlik xona birligida joylashgan  uch xonali sonni toping.",
                "567"
            )
        )
        savollar.add(Question("42 : 6 = ?", "7"))
        savollarAll.add(Question("42 : 6 = ?", "7"))
        savollar.add(
            Question(
                "6 raqami birliklar xonasida joylashgan  uch xonali sonni toping.",
                "156"
            )
        )
        savollarAll.add(
            Question(
                "6 raqami birliklar xonasida joylashgan  uch xonali sonni toping.",
                "156"
            )
        )
        savollar.add(
            Question(
                "Do`konga jami 97 kilogram kartoshka keltirildi. Tushgacha 23 kilogram, tushdan so`ng yana 15 kilogram kartoshka sotildi. Do`konda qancha kartoshka qoldi?",
                "59"
            )
        )
        savollarAll.add(
            Question(
                "Do`konga jami 97 kilogram kartoshka keltirildi. Tushgacha 23 kilogram, tushdan so`ng yana 15 kilogram kartoshka sotildi. Do`konda qancha kartoshka qoldi?",
                "59"
            )
        )
        selectedQuestions = takeQuestions(partsSize)
        mediaPlayer = MediaPlayer.create(this, R.raw.organs)
//        mediaPlayer.setLooping(true)
//        mediaPlayer.start()
        implementCards()
    }

    fun playMusic(resId: Int) {
        val mp = MediaPlayer.create(this, resId)
        mp.start()
    }

    private fun implementCards() {
        listIv.add(binding.ivPart1)
        listIv.add(binding.ivPart2)
        listIv.add(binding.ivPart3)
        listIv.add(binding.ivPart4)
        listIv.add(binding.ivPart5)
        listIv.add(binding.ivPart6)
        listIv.add(binding.ivPart7)
        listIv.add(binding.ivPart8)
        for (i in 0..7) {
            listIv[i]!!.setOnClickListener(this)
        }
    }

    //Implement organ lists
    private fun implementOrganLists() {
        organList.add(R.drawable.organs_miya)
        organList.add(R.drawable.organs_opka)
        organList.add(R.drawable.organs_jigar)
        organList.add(R.drawable.organs_ichak)
        organList.add(R.drawable.organs_yurak)
        organList.add(R.drawable.organs_oshqozon)
        organList.add(R.drawable.organs_buyrak)
        organList.add(R.drawable.organs_yogon_ichak)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mediaPlayer!!.stop()
    }

    private fun startGame() {

        // tanlangan n ta savoldan birma bir tanlash
        val selectedQuestion = selectedQuestions[correctAnswers]
        setTextMainQuestion(selectedQuestion!!.question)

        // javoblarni kartochkalarga joylash
        for (i in 0 until partsSize) {
            javoblar.add(selectedQuestions[i]!!.answer)
        }
        javoblar.shuffle()
        for (i in 0 until partsSize) {
            setTextInImageView(javoblar[i], i)
        }
    }

    private fun setTextMainQuestion(text: String) {
        val question = TextView(this)
        question.text = text
        question.gravity = Gravity.CENTER
        val typeface = ResourcesCompat.getFont(this, R.font.montserrat)
        question.typeface = typeface
        question.id = questionIvId
        question.layoutParams = binding.ivQuestion.layoutParams
        binding.bodyOrganLayout.removeView(findViewById(questionIvId))
        binding.bodyOrganLayout.addView(question)
    }

    private fun setTextInImageView(text: String?, position: Int) {
        val answer = TextView(this)
        answer.text = text
        answer.gravity = Gravity.CENTER
        val typeface = ResourcesCompat.getFont(this, R.font.montserrat)
        answer.typeface = typeface
        //        listIv.get(position).setTag(position);
        val id = View.generateViewId()
        answer.id = id
        textViews.add(id)
        answer.layoutParams = listIv[position]!!.layoutParams
        binding.bodyOrganLayout.addView(answer)
    }

    private fun takeQuestions(n: Int): List<Question?> {
        savollar.shuffle()
        return savollar.stream().limit(n.toLong()).collect(Collectors.toList())
    }

    fun openQuestion(view: View?) {
        if (binding.ivQuestion.visibility == View.VISIBLE) {
//            hideElements();

//            ivQuestion.setVisibility(View.INVISIBLE);
//            findViewById(questionIvId).setVisibility(View.INVISIBLE);

//        } else if (selectedQuestions.size() > 0) {
//            showElements();
        } else {
            binding.ivQuestion.visibility = View.VISIBLE
            startGame()
        }
    }

    override fun onClick(v: View) {
        for (i in 0..7) {
            // kartochkadagi otvet tanlansa
            if (v.id == listIv[i]!!.id && binding.ivQuestion.visibility == View.VISIBLE) {
                val clickedView = listIv[i]
                val answer = findViewById<View>(textViews[i]) as TextView


                // agar javob to'g'ri bo'lsa
                if (answer.text == selectedQuestions[correctAnswers]!!.answer) {

                    // kartochkani rasmga o'zgartirish
                    clickedView!!.setImageResource(organList[i])
                    clickedView.setOnClickListener(null)
                    answer.text = ""
                    correctAnswers++
                    if (correctAnswers == 8) {
                        val intent = Intent(this, GameWonActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        setTextMainQuestion(selectedQuestions[correctAnswers]!!.question)
                    }
                } else {
                    Toast.makeText(this, "Hato", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}