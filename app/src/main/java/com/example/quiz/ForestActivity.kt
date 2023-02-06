package com.example.quiz

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.quiz.databinding.ActivityForestBinding
import com.example.quiz.databinding.ActivityScreenBinding
import com.example.quiz.databinding.ActivitySplashScreenBinding

class ForestActivity : AppCompatActivity(), OnLongClickListener, OnDragListener {
    private lateinit var binding: ActivityForestBinding

    // list of bone elements
    private var bonesList: MutableList<Int> = ArrayList()
    private var boneFrameList: MutableList<Int> = ArrayList()
    private var partsListAll: ArrayList<Int> = ArrayList()
    private var partsList: ArrayList<Int> = ArrayList()
    private var frameList: ArrayList<Int> = ArrayList()
    var mainBlackboardRes = 0
    private var partsSize = 0
    private var correctAnswers = 0
    var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        implementBoneLists()

        mainBlackboardRes = R.drawable.v3_skeleton
        partsSize = bonesList.size
        partsList = ArrayList(bonesList)
        partsListAll = ArrayList(bonesList)
        frameList = ArrayList(boneFrameList)
        binding.ivBone1.id = partsList[0]
        binding.ivBone1.setOnClickListener { view: View -> startGame(view) }
        binding.ivBone2.id = partsList[1]
        binding.ivBone2.setOnClickListener { view: View -> startGame(view) }
        partsList.removeAt(0)
        partsList.removeAt(0)
        mediaPlayer = MediaPlayer.create(this, R.raw.forest)
//        mediaPlayer.setLooping(true)
//        mediaPlayer.start()
        implementEvents()
    }

    private fun playMusic(resId: Int) {
        val mp = MediaPlayer.create(this, resId)
        mp.start()
    }

    //Implement bone lists
    private fun implementBoneLists() {
        bonesList.add(R.drawable.v4_skeletone_part_panal_0)
        bonesList.add(R.drawable.v4_skeletone_part_panal_1)
        bonesList.add(R.drawable.v4_skeletone_part_panal_2)
        bonesList.add(R.drawable.v4_skeletone_part_panal_3)
        bonesList.add(R.drawable.v4_skeletone_part_panal_4)
        bonesList.add(R.drawable.v4_skeletone_part_panal_5)
        bonesList.add(R.drawable.v4_skeletone_part_panal_6)
        boneFrameList.add(R.drawable.v4_skeletone_part_use_0)
        boneFrameList.add(R.drawable.v4_skeletone_part_use_1)
        boneFrameList.add(R.drawable.v4_skeletone_part_use_2)
        boneFrameList.add(R.drawable.v4_skeletone_part_use_3)
        boneFrameList.add(R.drawable.v4_skeletone_part_use_4)
        boneFrameList.add(R.drawable.v4_skeletone_part_use_5)
        boneFrameList.add(R.drawable.v4_skeletone_part_use_6)
    }

    //Implement long click and drag listener
    private fun implementEvents() {
        binding.ivBoard.setOnDragListener(this@ForestActivity)
        findViewById<View>(R.id.fullBody).setOnDragListener(this@ForestActivity)
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        mediaPlayer!!.stop()
//    }

    override fun onDrag(view: View, dragEvent: DragEvent): Boolean {
        // Defines a variable to store the action type for the incoming event
        when (dragEvent.action) {
            DragEvent.ACTION_DRAG_STARTED -> return dragEvent.clipDescription.hasMimeType(
                ClipDescription.MIMETYPE_TEXT_PLAIN
            )
            DragEvent.ACTION_DRAG_ENTERED, DragEvent.ACTION_DRAG_EXITED -> {
                view.invalidate()
                return true
            }
            DragEvent.ACTION_DRAG_LOCATION -> return true
            DragEvent.ACTION_DROP -> {
                val item = dragEvent.clipData.getItemAt(0)
                view.invalidate()

                //get dragged view
                val v = dragEvent.localState as View
                val owner = v.parent as ViewGroup
                Log.e("view", view.toString())
                if (view.toString().contains("ivBoard")) {
                    owner.removeView(v) //remove the dragged view

                    // parts tablega toza elent qoshish
                    if (partsList.isNotEmpty()) {
                        owner.addView(tableImageView(partsList[0], v.tag.toString()))
                        partsList.removeAt(0)
                    }

                    // show part in shadow
                    if (partsListAll.contains(v.id)) {
                        setBlackboardResource(frameList[partsListAll.indexOf(v.id)])
                    }
                } else {
                    v.visibility = VISIBLE //finally set Visibility to VISIBLE
                }
                return true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                view.invalidate()

                // invoke getResult(), and displays what happened.
                if (dragEvent.result) {
                    if (correctAnswers == partsSize) {
                        binding.bodyLayout.removeAllViews()
                        setBlackboardResource(mainBlackboardRes)
                        playMusic(R.raw.complete)
                        object : CountDownTimer(2100, 1000) {
                            override fun onTick(millisUntilFinished: Long) {
                                YoYo.with(Techniques.Tada)
                                    .duration(700)
                                    .repeat(3)
                                    .playOn(findViewById(mainBlackboardRes))
                            }

                            override fun onFinish() {
                                val intent =
                                    Intent(this@ForestActivity, GameWonActivity::class.java)
                                startActivity(intent)
                                mediaPlayer!!.reset()
                                finish()
                            }
                        }.start()
                    }
                }

                // returns true; the value is ignored.
                return true
            }
            else -> Log.e("DragDrop Example", "Unknown action type received by OnDragListener.")
        }
        return false
    }

    private fun setBlackboardResource(resId: Int) {
        val ivPart = ImageView(this@ForestActivity)
        ivPart.setImageResource(resId)
        ivPart.id = resId
        ivPart.layoutParams = binding.bodyFrame.layoutParams
        binding.bodyLayout.addView(ivPart)
        correctAnswers++
    }

    private fun tableImageView(resId: Int, position: String): ImageView {
        val ivPart = ImageView(this@ForestActivity)
        ivPart.setImageResource(resId)
        ivPart.id = resId
        if (position == "left") ivPart.layoutParams =
            binding.ivBone1.layoutParams else ivPart.layoutParams = binding.ivBone2.layoutParams
        ivPart.tag = position
        ivPart.setOnClickListener { view: View -> startGame(view) }
        return ivPart
    }

    private fun startGame(view: View) {
        startQuizGame(view)
    }

    private fun startQuizGame(view: View) {
        val intent = Intent(this@ForestActivity, QuizGameActivity::class.java)
        intent.putExtra("bodyPartId", view.id)
        startActivity(intent)
    }

    override fun onLongClick(view: View): Boolean {
        val data1 = ClipData.newPlainText("", "")
        val shadowBuilder = DragShadowBuilder(view)
        view.startDragAndDrop(data1, shadowBuilder, view, 0)
        view.visibility = INVISIBLE
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onPostResume() {
        super.onPostResume()
        val prefs = getDefaultSharedPreferences(this@ForestActivity)
        val data = prefs.getInt("bodyPartId", 0) //no id: default value
        if (data != 0 && !partsList.contains(data)) {
            val iv = findViewById<View>(data) as? ImageView
            iv?.setOnLongClickListener(this@ForestActivity)
            iv?.setOnClickListener(null)
            iv?.let {
                YoYo.with(Techniques.Tada)
                    .duration(700)
                    .repeat(5)
                    .playOn(it)
            }
            iv?.setOnTouchListener { _: View?, event: MotionEvent ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    val data1 = ClipData.newPlainText("", "")
                    val shadowBuilder = DragShadowBuilder(iv)
                    iv.startDragAndDrop(data1, shadowBuilder, iv, 0)
                    iv.visibility = INVISIBLE
                    return@setOnTouchListener true
                } else {
                    return@setOnTouchListener false
                }
            }
        }
    }
}