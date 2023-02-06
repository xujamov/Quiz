package com.example.quiz

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.example.quiz.databinding.ActivityMainBinding
import com.example.quiz.databinding.ActivityScreenBinding
import com.example.quiz.databinding.ActivitySplashScreenBinding
import info.hoang8f.widget.FButton
import java.util.*

class QuizGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var quizHelper: QuizHelper? = null
    private var currentQuestion: Quiz? = null
    var list: MutableList<Quiz?> = ArrayList()
    private var qid = 0
    var timeValue = 30
    private var bodyPartId = 0
    private var countDownTimer: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Initializing variables
        val intent = intent
        bodyPartId = intent.getIntExtra("bodyPartId", 0)

        //Our database helper class
        quizHelper = QuizHelper(this)
        //Make db writable
        quizHelper!!.writableDatabase

        //It will check if the ques,options are already added in table or not
        //If they are not added then the getAllOfTheQuestions() will return a list of size zero
        if (quizHelper!!.allOfTheQuestions.isEmpty()) {
            //If not added then add the ques,options in table
            quizHelper!!.allQuestion()
        }

        //This will return us a list of data type Question
        // TODO db error
//        list = QuizHelper.getAllOfTheQuestions();
        list.add(Quiz("70 + 80 = ?", "150", "100", "110", "180", "150"))
        list.add(Quiz("94 – x = 47\nx = ?", "40", "47", "43", "49", "47"))
        list.add(
            Quiz(
                "To`rtburchakning bitta uchini kessak necha burchak hosil bo`ladi? ?",
                "3",
                "4",
                "5",
                "6",
                "5"
            )
        )
        list.add(Quiz("72 : 8 = ?", "7", "8", "9", "11", "9"))
        list.add(
            Quiz(
                "Bir yuz yetmish olti soni to`g`ri yozilgan qatorni aniqlang.",
                "167",
                "176",
                "716",
                "617",
                "176"
            )
        )
        list.add(
            Quiz(
                "6 raqami o`nlik xona birligida joylashgan  uch xonali sonni toping.",
                "26",
                "176",
                "6",
                "162",
                "176"
            )
        )
        list.add(Quiz("42 : 6 = ?", "8", "6", "7", "12", "7"))
        list.add(
            Quiz(
                "6 raqami birliklar xonasida joylashgan  uch xonali sonni toping.",
                "167",
                "764",
                "584",
                "176",
                "176"
            )
        )
        list.add(
            Quiz(
                "Do`konga jami 97 kilogram kartoshka keltirildi. Tushgacha 23 kilogram, tushdan so`ng yana 15 kilogram kartoshka sotildi. Do`konda qancha kartoshka qoldi?",
                "125",
                "105",
                "59",
                "60",
                "59"
            )
        )


        //Now we gonna shuffle the elements of the list so that we will get questions randomly
        list.shuffle()

        //currentQuestion will hold the que, 4 option and ans for particular id
        currentQuestion = list[qid]

        //countDownTimer
        countDownTimer = object : CountDownTimer(32000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                //here you can have your logic to set text to binding.timeText
                binding.timeText.text = timeValue.toString() + "\""

                //With each iteration decrement the time by 1 sec
                timeValue -= 1

                //This means the user is out of time so onFinished will called after this iteration
                if (timeValue == -1) {

                    //Since user is out of time setText as time up
                    binding.resultText.text = getString(R.string.timeup)
                    binding.timeText.visibility = View.INVISIBLE

                    //Since user is out of time he won't be able to click any buttons
                    //therefore we will disable all four options buttons using this method
                    disableButton()
                }
            }

            //Now user is out of time
            override fun onFinish() {
                //We will navigate him to the time up activity using below method
                timeUp()
            }
        }.start()

//        //This method will set the que and four options
        updateQueAndOptions()
    }

    private fun updateQueAndOptions() {

        //This method will setText for que and options
        binding.questionText.text = currentQuestion!!.question
        binding.buttonA.text = currentQuestion!!.optA
        binding.buttonB.text = currentQuestion!!.optB
        binding.buttonC.text = currentQuestion!!.optC
        binding.buttonD.text = currentQuestion!!.optD
        timeValue = 30

        //Now since the user has ans correct just reset timer back for another que- by cancel and start
        countDownTimer!!.cancel()
        countDownTimer!!.start()
    }

    //Onclick listener for first button
    fun buttonA(view: View?) {
        //compare the option with the ans if yes then make button color green
        if (currentQuestion!!.optA == currentQuestion!!.answer) {
            binding.buttonA.buttonColor = ContextCompat.getColor(applicationContext, R.color.lightGreen)
            //Check if user has not exceeds the que limit
            if (qid < list.size - 1) {

                //Now disable all the option button since user ans is correct so
                //user won't be able to press another option button after pressing one button
                disableButton()

                //Show the dialog that ans is correct
                correctDialog()
            } else {
                gameWon()
            }
        } else {
            gameLostPlayAgain()
        }
    }

    //Onclick listener for sec button
    fun buttonB(view: View?) {
        if (currentQuestion!!.optB == currentQuestion!!.answer) {
            binding.buttonB.buttonColor = ContextCompat.getColor(applicationContext, R.color.lightGreen)
            if (qid < list.size - 1) {
                disableButton()
                correctDialog()
            } else {
                gameWon()
            }
        } else {
            gameLostPlayAgain()
        }
    }

    //Onclick listener for third button
    fun buttonC(view: View?) {
        if (currentQuestion!!.optC == currentQuestion!!.answer) {
            binding.buttonC.buttonColor = ContextCompat.getColor(applicationContext, R.color.lightGreen)
            if (qid < list.size - 1) {
                disableButton()
                correctDialog()
            } else {
                gameWon()
            }
        } else {
            gameLostPlayAgain()
        }
    }

    //Onclick listener for fourth button
    fun buttonD(view: View?) {
        if (currentQuestion!!.optD == currentQuestion!!.answer) {
            binding.buttonD.buttonColor = ContextCompat.getColor(applicationContext, R.color.lightGreen)
            if (qid < list.size - 1) {
                disableButton()
                correctDialog()
            } else {
                gameWon()
            }
        } else {
            gameLostPlayAgain()
        }
    }

    //This method will navigate from current activity to GameWon
    private fun gameWon() {
        val intent = Intent(this, GameWonActivity::class.java)
        startActivity(intent)
        finish()
    }

    //This method is called when user ans is wrong
    //this method will navigate user to the activity PlayAgain
    private fun gameLostPlayAgain() {
        playMusic(R.raw.wrong)
        val intent = Intent(this, PlayAgainActivity::class.java)
        startActivity(intent)
        finish()
    }

    //This method is called when time is up
    //this method will navigate user to the activity Time_Up
    fun timeUp() {
        val intent = Intent(this, TimeUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    //If user press home button and come in the game from memory then this
    //method will continue the timer from the previous time it left
    override fun onRestart() {
        super.onRestart()
        countDownTimer!!.start()
    }

    //When activity is destroyed then this will cancel the timer
    override fun onStop() {
        super.onStop()
        countDownTimer!!.cancel()
    }

    //This will pause the time
    override fun onPause() {
        super.onPause()
        countDownTimer!!.cancel()
    }

    //On BackPressed
    override fun onBackPressed() {
        finish()
    }

    private fun playMusic(resId: Int) {
        val mp = MediaPlayer.create(this, resId)
        mp.start()
    }

    //This dialog is show to the user after he ans correct
    private fun correctDialog() {
        playMusic(R.raw.correct)
        val dialogCorrect = Dialog(this@QuizGameActivity)
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (dialogCorrect.window != null) {
            val colorDrawable = ColorDrawable(Color.TRANSPARENT)
            dialogCorrect.window!!.setBackgroundDrawable(colorDrawable)
        }
        dialogCorrect.setContentView(R.layout.dialog_correct)
        dialogCorrect.setCancelable(false)
        dialogCorrect.show()

        //Since the dialog is show to user just pause the timer in background
        onPause()
        val buttonNext = dialogCorrect.findViewById<View>(R.id.dialogNext) as FButton


        //OnCLick listener to go next que
        buttonNext.setOnClickListener {
            val prefs = getDefaultSharedPreferences(this@QuizGameActivity)
            val editor = prefs.edit()
            editor.putInt("bodyPartId", bodyPartId) //InputString: from the EditText
            editor.apply()
            dialogCorrect.dismiss()
            finish()
        }
    }

    //This method will disable all the option button
    fun disableButton() {
        binding.buttonA.isEnabled = false
        binding.buttonB.isEnabled = false
        binding.buttonC.isEnabled = false
        binding.buttonD.isEnabled = false
    }

    //This method will all enable the option buttons
    fun enableButton() {
        binding.buttonA.isEnabled = true
        binding.buttonB.isEnabled = true
        binding.buttonC.isEnabled = true
        binding.buttonD.isEnabled = true
    }
}