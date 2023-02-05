package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

public class ClassroomActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnDragListener{
    ConstraintLayout body_layout, parts_layout;
    ImageView body_frame, iv_board, iv_bone1, iv_bone2;

    // list of muscle elements
    List<Integer> muscleList = new ArrayList<>();
    List<Integer> muscleFrameList = new ArrayList<>();

    List<Integer> partsListAll = new ArrayList<>();
    List<Integer> partsList = new ArrayList<>();
    List<Integer> frameList = new ArrayList<>();
    int mainBlackboardRes;
    private int partsSize = 0;
    private int correctAnswers = 0;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);

        ImageView mImageViewFilling = (ImageView) findViewById(R.id.think);
        ((AnimationDrawable) mImageViewFilling.getDrawable()).start();

        implementMuscleLists();
        mainBlackboardRes = R.drawable.muscle;
        partsSize = muscleList.size();
        partsList = new ArrayList<>(muscleList);
        partsListAll = new ArrayList<>(muscleList);
        frameList = new ArrayList<>(muscleFrameList);

        body_layout = (ConstraintLayout) findViewById(R.id.body_layout);
        parts_layout = (ConstraintLayout) findViewById(R.id.parts_layout);
        iv_board = (ImageView) findViewById(R.id.iv_board);
        body_frame = (ImageView) findViewById(R.id.body_frame);
        iv_bone1 = (ImageView) findViewById(R.id.iv_bone1);
        iv_bone1.setId(partsList.get(0));
        iv_bone1.setOnClickListener(this::startGame);
        iv_bone2 = (ImageView) findViewById(R.id.iv_bone2);
        iv_bone2.setId(partsList.get(1));
        iv_bone2.setOnClickListener(this::startGame);

        partsList.remove(0);
        partsList.remove(0);

        mediaPlayer = MediaPlayer.create(this, R.raw.classroom);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        implementEvents();
    }

    public void playMusic(int resId){
        MediaPlayer mp = MediaPlayer.create(this, resId);
        mp.start();
    }

    //Implement muscle lists
    private void implementMuscleLists() {
        muscleList.add(R.drawable.muscle_part_belly);
        muscleList.add(R.drawable.muscle_part_chest);
        muscleList.add(R.drawable.muscle_part_hand);
        muscleList.add(R.drawable.muscle_part_legs);
        muscleList.add(R.drawable.muscle_part_head);

        muscleFrameList.add(R.drawable.muscle_belly);
        muscleFrameList.add(R.drawable.muscle_chest);
        muscleFrameList.add(R.drawable.muscle_hand);
        muscleFrameList.add(R.drawable.muscle_legs);
        muscleFrameList.add(R.drawable.muscle_head);
    }



    //Implement long click and drag listener
    private void implementEvents() {
        iv_board.setOnDragListener(ClassroomActivity.this);
        findViewById(R.id.full_body).setOnDragListener(ClassroomActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        // Defines a variable to store the action type for the incoming event
        int action = dragEvent.getAction();

        // Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                return dragEvent.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);

            case DragEvent.ACTION_DRAG_ENTERED:

            case DragEvent.ACTION_DRAG_EXITED:
                view.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                return true;

            case DragEvent.ACTION_DROP:

                ClipData.Item item = dragEvent.getClipData().getItemAt(0);
                view.invalidate();

                //get dragged view
                View v = (View) dragEvent.getLocalState();
                ViewGroup owner = (ViewGroup) v.getParent();

                if (view.toString().contains("iv_board")) {
                    owner.removeView(v); //remove the dragged view

                    // parts tablega toza elent qoshish
                    if (!partsList.isEmpty()) {
                        owner.addView(tableImageView(partsList.get(0), v.getTag().toString()));
                        partsList.remove(0);
                    }

                    // show part in shadow
                    if(partsListAll.contains(v.getId())){
                        setBlackboardResource(frameList.get(partsListAll.indexOf(v.getId())));
                    }

                } else {
                    v.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE

                }
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                view.invalidate();

                // invoke getResult(), and displays what happened.
                if (dragEvent.getResult()) {
                    if (correctAnswers == partsSize) {
                        Guideline guideline = (Guideline) body_layout.getViewById(R.id.guiV25);
                        body_layout.removeAllViews();
                        body_layout.addView(guideline);
                        setBlackboardResource(mainBlackboardRes);
                        playMusic(R.raw.complete);
                        new CountDownTimer(2100,1000){
                            public void onTick(long millisUntilFinished){
                                YoYo.with(Techniques.Tada)
                                        .duration(700)
                                        .repeat(3)
                                        .playOn(findViewById(mainBlackboardRes));
                            }
                            public void onFinish(){
                                Intent intent = new Intent(ClassroomActivity.this, GameWonActivity.class);
                                startActivity(intent);
                                mediaPlayer.reset();
                                finish();

                            }
                        } .start();
                    }
                }

                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }

    private void setBlackboardResource(int resId) {
        ImageView ivPart = new ImageView(ClassroomActivity.this);
        ivPart.setImageResource(resId);
        ivPart.setId(resId);
        ivPart.setLayoutParams(body_frame.getLayoutParams());
        body_layout.addView(ivPart);
        correctAnswers++;
    }

    private ImageView tableImageView(int resId, String position) {
        ImageView ivPart = new ImageView(ClassroomActivity.this);
        ivPart.setImageResource(resId);
        ivPart.setId(resId);
        if (position.equals("left"))
            ivPart.setLayoutParams(iv_bone1.getLayoutParams());
        else
            ivPart.setLayoutParams(iv_bone2.getLayoutParams());
        ivPart.setTag(position);
        ivPart.setOnClickListener(this::startGame);
        return ivPart;
    }

    private void startGame(View view) {
        startQuestionGame(view);
    }

    private void startQuestionGame(View view) {
        Intent intent = new Intent(ClassroomActivity.this, LanguageQuestionGameActivity.class);
        intent.putExtra("bodyPartId", view.getId());
        startActivity(intent);
    }

    @Override
    public boolean onLongClick(View view) {
        ClipData data1 = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

        view.startDragAndDrop(data1, shadowBuilder, view, 0);
        view.setVisibility(View.INVISIBLE);
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onPostResume() {
        super.onPostResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ClassroomActivity.this);
        int data = prefs.getInt("bodyPartId", 0); //no id: default value
        if (data != 0 && !partsList.contains(data)) {
            ImageView iv = (ImageView) findViewById(data);
            if (iv != null) {
                iv.setOnLongClickListener(ClassroomActivity.this);
                iv.setOnClickListener(null);
                YoYo.with(Techniques.Tada)
                        .duration(700)
                        .repeat(5)
                        .playOn(iv);

                iv.setOnTouchListener((v, event) -> {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        ClipData data1 = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(iv);

                        iv.startDragAndDrop(data1, shadowBuilder, iv, 0);
                        iv.setVisibility(View.INVISIBLE);
                        return true;
                    } else {
                        return false;
                    }
                });
            }
        }
    }
}