package com.example.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrgansActivity extends AppCompatActivity implements View.OnClickListener {
    ConstraintLayout body_layout, parts_layout;
    ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, ivQuestion, ivOpenQuestion;
    List<ImageView> listIv = new ArrayList<>();
    int questionIvId;
    // list of organs elements
    List<Integer> organList = new ArrayList<>();

    List<Integer> partsListAll = new ArrayList<>();
    List<Integer> partsList = new ArrayList<>();
    private int partsSize = 8;
    private String correctAnswer = "";
    private int correctAnswers = 0;

    List<Question> selectedQuestions = new ArrayList<>();
    List<Question> savollarAll = new ArrayList<>();
    List<Question> savollar = new ArrayList<>();
    List<String> javoblar = new ArrayList<>();
    List<Integer> textViews = new ArrayList<>();


    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organs);
        questionIvId = View.generateViewId();



        implementOrganLists();
        partsList = new ArrayList<>(organList);
        partsListAll = new ArrayList<>(organList);

        QuestionHelperMath QuestionHelper = new QuestionHelperMath(this);
//        savollar = QuestionHelper.getAllOfTheQuestions();
//        savollarAll = QuestionHelper.getAllOfTheQuestions();

        savollar.add(new Question("70 + 80 = ?", "150"));
        savollarAll.add(new Question("70 + 80 = ?", "150"));

        savollar.add(new Question("94 – x = 47\nx = ?", "47"));
        savollarAll.add(new Question("94 – x = 47\nx = ?", "47"));

        savollar.add(new Question("To`rtburchakning bitta uchini kessak necha burchak hosil bo`ladi?", "5"));
        savollarAll.add(new Question("To`rtburchakning bitta uchini kessak necha burchak hosil bo`ladi?", "5"));

        savollar.add(new Question("72 : 8 = ?", "9"));
        savollarAll.add(new Question("72 : 8 = ?", "9"));

        savollar.add(new Question("Bir yuz yetmish olti soni to`g`ri yozilgan qatorni aniqlang.", "176"));
        savollarAll.add(new Question("Bir yuz yetmish olti soni to`g`ri yozilgan qatorni aniqlang.", "176"));

        savollar.add(new Question("6 raqami o`nlik xona birligida joylashgan  uch xonali sonni toping.", "567"));
        savollarAll.add(new Question("6 raqami o`nlik xona birligida joylashgan  uch xonali sonni toping.", "567"));

        savollar.add(new Question("42 : 6 = ?", "7"));
        savollarAll.add(new Question("42 : 6 = ?", "7"));

        savollar.add(new Question("6 raqami birliklar xonasida joylashgan  uch xonali sonni toping.", "156"));
        savollarAll.add(new Question("6 raqami birliklar xonasida joylashgan  uch xonali sonni toping.", "156"));

        savollar.add(new Question("Do`konga jami 97 kilogram kartoshka keltirildi. Tushgacha 23 kilogram, tushdan so`ng yana 15 kilogram kartoshka sotildi. Do`konda qancha kartoshka qoldi?", "59"));
        savollarAll.add(new Question("Do`konga jami 97 kilogram kartoshka keltirildi. Tushgacha 23 kilogram, tushdan so`ng yana 15 kilogram kartoshka sotildi. Do`konda qancha kartoshka qoldi?", "59"));




        selectedQuestions = takeQuestions(partsSize);

        body_layout = (ConstraintLayout) findViewById(R.id.body_layout);
        parts_layout = (ConstraintLayout) findViewById(R.id.parts_layout);


        mediaPlayer = MediaPlayer.create(this, R.raw.organs);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        implementCards();
    }

    public void playMusic(int resId){
        MediaPlayer mp = MediaPlayer.create(this, resId);
        mp.start();
    }

    //Implement organ lists
    private void implementCards() {
        iv1 = (ImageView) findViewById(R.id.ivPart1);
        iv2 = (ImageView) findViewById(R.id.ivPart2);
        iv3 = (ImageView) findViewById(R.id.ivPart3);
        iv4 = (ImageView) findViewById(R.id.ivPart4);
        iv5 = (ImageView) findViewById(R.id.ivPart5);
        iv6 = (ImageView) findViewById(R.id.ivPart6);
        iv7 = (ImageView) findViewById(R.id.ivPart7);
        iv8 = (ImageView) findViewById(R.id.ivPart8);
        ivQuestion = (ImageView) findViewById(R.id.ivQuestion);
        ivOpenQuestion = (ImageView) findViewById(R.id.ivOpenQuestion);
        listIv.add(iv1);
        listIv.add(iv2);
        listIv.add(iv3);
        listIv.add(iv4);
        listIv.add(iv5);
        listIv.add(iv6);
        listIv.add(iv7);
        listIv.add(iv8);
        for (int i = 0; i < 8; i++) {
            listIv.get(i).setOnClickListener(this);
        }

    }

    //Implement organ lists
    private void implementOrganLists() {
        organList.add(R.drawable.organs_miya);
        organList.add(R.drawable.organs_opka);
        organList.add(R.drawable.organs_jigar);
        organList.add(R.drawable.organs_ichak);
        organList.add(R.drawable.organs_yurak);
        organList.add(R.drawable.organs_oshqozon);
        organList.add(R.drawable.organs_buyrak);
        organList.add(R.drawable.organs_yogon_ichak);
    }

    //Implement long click and drag listener
//    private void implementEvents() {
//        iv_board.setOnDragListener(OrgansActivity.this);
//        findViewById(R.id.full_body).setOnDragListener(OrgansActivity.this);
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }

//    @Override
//    public boolean onDrag(View view, DragEvent dragEvent) {
//        // Defines a variable to store the action type for the incoming event
//        int action = dragEvent.getAction();
//
//        // Handles each of the expected events
//        switch (action) {
//            case DragEvent.ACTION_DRAG_STARTED:
//                return dragEvent.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
//
//            case DragEvent.ACTION_DRAG_ENTERED:
//
//            case DragEvent.ACTION_DRAG_EXITED:
//                view.invalidate();
//                return true;
//
//            case DragEvent.ACTION_DRAG_LOCATION:
//                return true;
//
//            case DragEvent.ACTION_DROP:
//
//                ClipData.Item item = dragEvent.getClipData().getItemAt(0);
//                view.invalidate();
//
//                //get dragged view
//                View v = (View) dragEvent.getLocalState();
//                ViewGroup owner = (ViewGroup) v.getParent();
//
//                if (view.toString().contains("iv_board")) {
//                    owner.removeView(v); //remove the dragged view
//
//                    // parts tablega toza elent qoshish
//                    if (!partsList.isEmpty()) {
//                        owner.addView(tableImageView(partsList.get(0), v.getTag().toString()));
//                        partsList.remove(0);
//                    }
//
//                    // show part in shadow
//                    if(partsListAll.contains(v.getId())){
//                        setBlackboardResource(frameList.get(partsListAll.indexOf(v.getId())));
//                    }
//
//                } else {
//                    v.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE
//
//                }
//                return true;
//
//            case DragEvent.ACTION_DRAG_ENDED:
//                view.invalidate();
//
//                // invoke getResult(), and displays what happened.
//                if (dragEvent.getResult()) {
//                    if (correctAnswers == partsSize) {
//                        Guideline guideline = (Guideline) body_layout.getViewById(R.id.guiV25);
//                        body_layout.removeAllViews();
//                        body_layout.addView(guideline);
//                        setBlackboardResource(mainBlackboardRes);
//                        playMusic(R.raw.complete);
//                        new CountDownTimer(2100,1000){
//                            public void onTick(long millisUntilFinished){
//                                YoYo.with(Techniques.Tada)
//                                        .duration(700)
//                                        .repeat(3)
//                                        .playOn(findViewById(mainBlackboardRes));
//                            }
//                            public void onFinish(){
//                                Intent intent = new Intent(OrgansActivity.this, GameWonActivity.class);
//                                startActivity(intent);
//                                mediaPlayer.reset();
//                                finish();
//
//                            }
//                        } .start();
//                    }
//                }
//
//                // returns true; the value is ignored.
//                return true;
//
//            // An unknown action type was received.
//            default:
//                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
//                break;
//        }
//        return false;
//    }

    private void startGame() {
//        Question que = takeQuestion();
//        selectedQuestions = takeQuestions(partsSize);
        // do when click question

        // tanlangan n ta savoldan birma bir tanlash
        Question selectedQuestion = selectedQuestions.get(correctAnswers);
        setTextMainQuestion(selectedQuestion.getQuestion());

        // javoblarni kartochkalarga joylash
        for (int i = 0; i < partsSize; i++) {
            javoblar.add(selectedQuestions.get(i).getAnswer());

        }
        Collections.shuffle(javoblar);
        for (int i = 0; i < partsSize; i++) {
            setTextInImageView(javoblar.get(i), i);
        }

    }

    private void setTextMainQuestion(String text) {
        TextView question = new TextView(this);
        question.setText(text);
        question.setGravity(Gravity.CENTER);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.montserrat);
        question.setTypeface(typeface);
        question.setId(questionIvId);
        question.setLayoutParams(ivQuestion.getLayoutParams());
        body_layout.removeView(findViewById(questionIvId));
        body_layout.addView(question);
    }

    private void setTextInImageView(String text, int position) {
        TextView answer = new TextView(this);
        answer.setText(text);
        answer.setGravity(Gravity.CENTER);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.montserrat);
        answer.setTypeface(typeface);
//        listIv.get(position).setTag(position);
        int id = View.generateViewId();
        answer.setId(id);
        textViews.add(id);
        answer.setLayoutParams(listIv.get(position).getLayoutParams());
        body_layout.addView(answer);
    }

    private List<Question> takeQuestions(int n) {
        Collections.shuffle(savollar);
        return savollar.stream().limit(n).collect(Collectors.toList());
    }

    public void openQuestion(View view) {
        if (ivQuestion.getVisibility() == View.VISIBLE) {
//            hideElements();

//            ivQuestion.setVisibility(View.INVISIBLE);
//            findViewById(questionIvId).setVisibility(View.INVISIBLE);

//        } else if (selectedQuestions.size() > 0) {
//            showElements();
        } else {
            ivQuestion.setVisibility(View.VISIBLE);
            startGame();
        }
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < 8; i++){
            // kartochkadagi otvet tanlansa


            if (v.getId() == listIv.get(i).getId() && ivQuestion.getVisibility() == View.VISIBLE) {
                ImageView clickedView = listIv.get(i);
                TextView answer = (TextView) findViewById(textViews.get(i));


                // agar javob to'g'ri bo'lsa
                if (answer.getText().equals(selectedQuestions.get(correctAnswers).getAnswer())) {
                    // correct answer
//                    selectedQuestions.remove(0);
//                    body_layout.removeView(findViewById(textViews.get(i)));
                    // kartochkani rasmga o'zgartirish
                    clickedView.setImageResource(organList.get(i));
                    clickedView.setOnClickListener(null);
                    answer.setText("");
                    correctAnswers++;
                    if (correctAnswers == 8) {
                        Intent intent = new Intent(this, GameWonActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        setTextMainQuestion(selectedQuestions.get(correctAnswers).getQuestion());
                    }
//                    partsSize--;
//                    textViews.remove(answer.getId());
//                    listIv.remove(answer.getId());
//                    hideElements();
                } else {
                    Toast.makeText(this, "Hato", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

//    private void hideElements() {
//        for (int i=0; i < partsSize; i++) {
//            findViewById(textViews.get(i)).setVisibility(View.INVISIBLE);
//            listIv.get(i).setOnClickListener(null);
//        }
//        findViewById(questionIvId).setVisibility(View.INVISIBLE);
//    }
//
//    private void showElements() {
//        for (int i=0; i < partsSize; i++) {
//            findViewById(textViews.get(i)).setVisibility(View.VISIBLE);
//        }
//        findViewById(questionIvId).setVisibility(View.VISIBLE);
//    }
}