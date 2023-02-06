package com.example.quiz

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Guideline
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.quiz.databinding.ActivityClassroomBinding

class ClassroomActivity : AppCompatActivity(), OnLongClickListener, OnDragListener {
    private lateinit var binding: ActivityClassroomBinding

    // list of muscle elements
    private var muscleList: MutableList<Int> = ArrayList()
    private var muscleFrameList: MutableList<Int> = ArrayList()
    private var partsListAll: ArrayList<Int> = ArrayList()
    private var partsList: ArrayList<Int> = ArrayList()
    private var frameList: ArrayList<Int> = ArrayList()
    var mainBlackboardRes = 0
    private var partsSize = 0
    private var correctAnswers = 0
    var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassroomBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val mImageViewFilling = findViewById<View>(R.id.think) as ImageView
        (mImageViewFilling.drawable as AnimationDrawable).start()
        implementMuscleLists()
        mainBlackboardRes = R.drawable.muscle
        partsSize = muscleList.size
        partsList = ArrayList(muscleList)
        partsListAll = ArrayList(muscleList)
        frameList = ArrayList(muscleFrameList)

        binding.ivMuscle1.id = partsList[0]
        binding.ivMuscle1.setOnClickListener { view: View -> startGame(view) }
        binding.ivMuscle2.id = partsList[1]
        binding.ivMuscle2.setOnClickListener { view: View -> startGame(view) }
        partsList.removeAt(0)
        partsList.removeAt(0)
        mediaPlayer = MediaPlayer.create(this, R.raw.classroom)
//        mediaPlayer.setLooping(true)
//        mediaPlayer.start()
        implementEvents()
    }

    private fun playMusic(resId: Int) {
        val mp = MediaPlayer.create(this, resId)
        mp.start()
    }

    //Implement muscle lists
    private fun implementMuscleLists() {
        muscleList.add(R.drawable.muscle_part_belly)
        muscleList.add(R.drawable.muscle_part_chest)
        muscleList.add(R.drawable.muscle_part_hand)
        muscleList.add(R.drawable.muscle_part_legs)
        muscleList.add(R.drawable.muscle_part_head)
        muscleFrameList.add(R.drawable.muscle_belly)
        muscleFrameList.add(R.drawable.muscle_chest)
        muscleFrameList.add(R.drawable.muscle_hand)
        muscleFrameList.add(R.drawable.muscle_legs)
        muscleFrameList.add(R.drawable.muscle_head)
    }

    //Implement long click and drag listener
    private fun implementEvents() {
        binding.ivClassroomBoard.setOnDragListener(this@ClassroomActivity)
        findViewById<View>(R.id.full_body).setOnDragListener(this@ClassroomActivity)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mediaPlayer!!.stop()
    }

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
                if (view.toString().contains("ivClassroomBoard")) {
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
                        val guideline = binding.bodyClassroomLayout.getViewById(R.id.guiV25) as Guideline
                        binding.bodyClassroomLayout.removeAllViews()
                        binding.bodyClassroomLayout.addView(guideline)
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
                                    Intent(this@ClassroomActivity, GameWonActivity::class.java)
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
        val ivPart = ImageView(this@ClassroomActivity)
        ivPart.setImageResource(resId)
        ivPart.id = resId
        ivPart.layoutParams = binding.bodyClassroomFrame.layoutParams
        binding.bodyClassroomLayout.addView(ivPart)
        correctAnswers++
    }

    private fun tableImageView(resId: Int, position: String): ImageView {
        val ivPart = ImageView(this@ClassroomActivity)
        ivPart.setImageResource(resId)
        ivPart.id = resId
        if (position == "left") ivPart.layoutParams =
            binding.ivMuscle1.layoutParams else ivPart.layoutParams = binding.ivMuscle2.layoutParams
        ivPart.tag = position
        ivPart.setOnClickListener { view: View -> startGame(view) }
        return ivPart
    }

    private fun startGame(view: View) {
        startQuestionGame(view)
    }

    private fun startQuestionGame(view: View) {
        val intent = Intent(this@ClassroomActivity, LanguageQuestionGameActivity::class.java)
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
        val prefs = getDefaultSharedPreferences(this@ClassroomActivity)
        val data = prefs.getInt("bodyPartId", 0) //no id: default value
        if (data != 0 && !partsList.contains(data)) {
            val iv = findViewById<View>(data) as? ImageView
            iv?.setOnLongClickListener(this@ClassroomActivity)
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