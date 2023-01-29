package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

public class BodyActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnDragListener{
    String category;
    FrameLayout frameLayout;

    // list of bone elements
    List<Integer> bonesList = new ArrayList<>();
    List<Integer> boneFrameList = new ArrayList<>();

    // list of muscle elements
    List<Integer> muscleList = new ArrayList<>();
    List<Integer> muscleFrameList = new ArrayList<>();

    // list of organ elements
    List<Integer> organList = new ArrayList<>();
    List<Integer> organFrameList = new ArrayList<>();

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
        setContentView(R.layout.activity_body);

        // current category
        Intent intent = getIntent();
        category = intent.getStringExtra("category");


        frameLayout = (FrameLayout) findViewById(R.id.body_frame);
        switch (category) {
            case "bone":
                implementBoneLists();
                setBlackboardResource(R.drawable.v4_skeleton_shadow);
                mainBlackboardRes = R.drawable.v3_skeleton;
                partsSize = bonesList.size();
                partsList = new ArrayList<>(bonesList);
                partsListAll = new ArrayList<>(bonesList);
                frameList = new ArrayList<>(boneFrameList);
                break;
            case "muscle":
                implementMuscleLists();
                setBlackboardResource(R.drawable.muscule_shadow);
                mainBlackboardRes = R.drawable.muscle;
                partsSize = muscleList.size();
                partsList = new ArrayList<>(muscleList);
                partsListAll = new ArrayList<>(muscleList);
                frameList = new ArrayList<>(muscleFrameList);
                break;
            case "organ":
//                TODO not finished
//                implementMuscleLists();
//                mainBlackboardRes = R.drawable.muscle;
                partsSize = organList.size();
                partsList = new ArrayList<>(organList);
                partsListAll = new ArrayList<>(organList);
                frameList = new ArrayList<>(organFrameList);
                break;
        }

        LinearLayout tableLayout = (LinearLayout) findViewById(R.id.table_layout);
        tableLayout.addView(tableImageView(partsList.get(0)));
        partsList.remove(0);
        tableLayout.addView(tableImageView(partsList.get(0)));
        partsList.remove(0);

        mediaPlayer = MediaPlayer.create(this, R.raw.game_level);
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
        frameLayout.setOnDragListener(BodyActivity.this);
        findViewById(R.id.full_body).setOnDragListener(BodyActivity.this);
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

                if (view.toString().contains("body_frame")) {
                    owner.removeView(v); //remove the dragged view
                    if (!partsList.isEmpty()) {
                        owner.addView(tableImageView(partsList.get(0)));
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
                        frameLayout.removeAllViews();
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
                                Intent intent = new Intent(BodyActivity.this, GameWonActivity.class);
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

    private int dpAsPixels(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void setBlackboardResource(int resId) {
        ImageView ivPart = new ImageView(BodyActivity.this);
        ivPart.setImageResource(resId);
        ivPart.setId(resId);
        int padding = dpAsPixels(30);
        ivPart.setPadding(padding, padding, padding, padding);
        frameLayout.addView(ivPart);
        correctAnswers++;
    }

    private ImageView tableImageView(int resId) {
        ImageView ivPart = new ImageView(BodyActivity.this);
        ivPart.setImageResource(resId);
        ivPart.setId(resId);
        int margin = dpAsPixels(10);
        ivPart.setTag("");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(margin, margin, margin, margin);
        ivPart.setLayoutParams(lp);
        ivPart.setOnClickListener(this::startGame);
        return ivPart;
    }

    private void startGame(View view) {
        switch(category) {
            case "bone":
                startQuizGame(view);
                break;
            case "muscle":
                startQuestionGame(view);
                break;
        }
    }

    private void startQuizGame(View view) {
        Intent intent = new Intent(BodyActivity.this, QuizGameActivity.class);
        intent.putExtra("bodyPartId", view.getId());
        startActivity(intent);
    }

    private void startQuestionGame(View view) {
        Intent intent = new Intent(BodyActivity.this, QuestionGameActivity.class);
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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(BodyActivity.this);
        int data = prefs.getInt("bodyPartId", 0); //no id: default value
        if (data != 0 && !partsList.contains(data)) {
            ImageView iv = (ImageView) findViewById(data);
            if (iv != null) {
                iv.setOnLongClickListener(BodyActivity.this);
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