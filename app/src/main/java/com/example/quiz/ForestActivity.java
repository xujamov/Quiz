package com.example.quiz;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

public class ForestActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnDragListener{
    ConstraintLayout body_layout, parts_layout;
    ImageView body_frame, iv_board, iv_bone1, iv_bone2;

    // list of bone elements
    List<Integer> bonesList = new ArrayList<>();
    List<Integer> boneFrameList = new ArrayList<>();

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
        setContentView(R.layout.activity_forest);

        implementBoneLists();
        mainBlackboardRes = R.drawable.v3_skeleton;
        partsSize = bonesList.size();
        partsList = new ArrayList<>(bonesList);
        partsListAll = new ArrayList<>(bonesList);
        frameList = new ArrayList<>(boneFrameList);

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

        mediaPlayer = MediaPlayer.create(this, R.raw.forest);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        implementEvents();
    }

    public void playMusic(int resId){
        MediaPlayer mp = MediaPlayer.create(this, resId);
        mp.start();
    }

    //Implement bone lists
    private void implementBoneLists() {
        bonesList.add(R.drawable.v4_skeletone_part_panal_0);
        bonesList.add(R.drawable.v4_skeletone_part_panal_1);
        bonesList.add(R.drawable.v4_skeletone_part_panal_2);
        bonesList.add(R.drawable.v4_skeletone_part_panal_3);
        bonesList.add(R.drawable.v4_skeletone_part_panal_4);
        bonesList.add(R.drawable.v4_skeletone_part_panal_5);
        bonesList.add(R.drawable.v4_skeletone_part_panal_6);

        boneFrameList.add(R.drawable.v4_skeletone_part_use_0);
        boneFrameList.add(R.drawable.v4_skeletone_part_use_1);
        boneFrameList.add(R.drawable.v4_skeletone_part_use_2);
        boneFrameList.add(R.drawable.v4_skeletone_part_use_3);
        boneFrameList.add(R.drawable.v4_skeletone_part_use_4);
        boneFrameList.add(R.drawable.v4_skeletone_part_use_5);
        boneFrameList.add(R.drawable.v4_skeletone_part_use_6);
    }

    //Implement long click and drag listener
    private void implementEvents() {
        iv_board.setOnDragListener(ForestActivity.this);
        findViewById(R.id.full_body).setOnDragListener(ForestActivity.this);
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
                        body_layout.removeAllViews();
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
                                Intent intent = new Intent(ForestActivity.this, GameWonActivity.class);
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
        ImageView ivPart = new ImageView(ForestActivity.this);
        ivPart.setImageResource(resId);
        ivPart.setId(resId);
        ivPart.setLayoutParams(body_frame.getLayoutParams());
        body_layout.addView(ivPart);
        correctAnswers++;
    }

    private ImageView tableImageView(int resId, String position) {
        ImageView ivPart = new ImageView(ForestActivity.this);
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
        startQuizGame(view);
    }

    private void startQuizGame(View view) {
        Intent intent = new Intent(ForestActivity.this, QuizGameActivity.class);
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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ForestActivity.this);
        int data = prefs.getInt("bodyPartId", 0); //no id: default value
        if (data != 0 && !partsList.contains(data)) {
            ImageView iv = (ImageView) findViewById(data);
            if (iv != null) {
                iv.setOnLongClickListener(ForestActivity.this);
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