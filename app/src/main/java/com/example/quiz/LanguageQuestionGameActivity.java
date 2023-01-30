package com.example.quiz;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import info.hoang8f.widget.FButton;

public class LanguageQuestionGameActivity extends AppCompatActivity {
    FButton buttonCheck;
    TextView questionText, triviaQuizText, timeText, resultText;
    QuestionHelperLanguage QuestionHelper;
    Question currentQuestion;
    List<Question> list = new ArrayList<>();;
    int qid = 0;
    int timeValue = 60;
    int bodyPartId = 0;
    CountDownTimer countDownTimer;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang_game);

        //Initializing variables
        questionText = (TextView) findViewById(R.id.Question);
        editText = (EditText) findViewById(R.id.etAnswer);
        buttonCheck = (FButton) findViewById(R.id.buttonCheck);

        triviaQuizText = (TextView) findViewById(R.id.triviaQuizText);
        timeText = (TextView) findViewById(R.id.timeText);
        resultText = (TextView) findViewById(R.id.resultText);

        Intent intent = getIntent();
        bodyPartId = intent.getIntExtra("bodyPartId", 0);

        //Our database helper class
        QuestionHelper = new QuestionHelperLanguage(this);
        //Make db writable
        QuestionHelper.getWritableDatabase();

        //It will check if the ques,options are already added in table or not
        //If they are not added then the getAllOfTheQuestions() will return a list of size zero
        if (QuestionHelper.getAllOfTheQuestions().size() == 0) {
            //If not added then add the ques,options in table
            QuestionHelper.allQuestion();
        }

        //This will return us a list of data type Question
        // TODO fix db
//        list = QuestionHelper.getAllOfTheQuestions();

        list.add(new Question("Quyida berilgan so`zga ma`nodosh so`zni toping: rohat.", "maza"));

        list.add(new Question("5 yoshida 500 ta kitob òqigan bolaning ismini yozing. ", "Lev"));

        list.add(new Question("3-sinf darsligida berilgan “Ona yurt” she`rining muallifi kim?", "Dilshod Rajab"));

        list.add(new Question("3-sinf darsligida berilgan “Vatan” hikoyasining muallifi kim?", "Xudoyberdi To`xtaboyev"));

        list.add(new Question("3-sinf darsligida berilgan “Vatan” hikoyasida podshoning suyukli xotini Malika faqat nima yer ekan?", "qush"));

        //Now we gonna shuffle the elements of the list so that we will get questions randomly
        Collections.shuffle(list);

        //currentQuestion will hold the que, 4 option and ans for particular id
        currentQuestion = list.get(qid);

        //countDownTimer
        countDownTimer = new CountDownTimer(62000, 1000) {
            public void onTick(long millisUntilFinished) {

                //here you can have your logic to set text to timeText
                timeText.setText(timeValue + "\"");

                //With each iteration decrement the time by 1 sec
                timeValue -= 1;

                //This means the user is out of time so onFinished will called after this iteration
                if (timeValue == -1) {

                    //Since user is out of time setText as time up
                    resultText.setText(getString(R.string.timeup));

                    timeText.setVisibility(View.INVISIBLE);

                    //Since user is out of time he won't be able to click any buttons
                    //therefore we will disable all four options buttons using this method
                    disableButton();
                }
            }

            //Now user is out of time
            public void onFinish() {
                //We will navigate him to the time up activity using below method
                timeUp();
            }
        }.start();

        //        //This method will set the que and four options
        updateQueAndOptions();

    }

    public void updateQueAndOptions() {

        //This method will setText for que and options
        questionText.setText(currentQuestion.getQuestion());

        timeValue = 60;

        //Now since the user has ans correct just reset timer back for another que- by cancel and start
        countDownTimer.cancel();
        countDownTimer.start();

    }

    public void checkAnswer(View view) {
        //compare the option with the ans if yes then make button color green
        String answer = editText.getText().toString().toLowerCase().replaceAll("[^a-zA-Z0-9]", " ");
        List<String> answerWords =  Arrays.asList(currentQuestion.getAnswer().toLowerCase().replaceAll("[^a-zA-Z0-9]", " ").split(" "));
        List<String> enteredWords = Arrays.asList(answer.split(" "));

        Set<String> result = enteredWords.stream()
                .distinct()
                .filter(answerWords::contains)
                .collect(Collectors.toSet());
        if (result.size() > 0) {
            //user won't be able to press another option button after pressing one button
            disableButton();

            //Show the dialog that ans is correct
            correctDialog();
        }
        //User ans is wrong then just navigate him to the PlayAgain activity
        else {

            gameLostPlayAgain();

        }
    }

    //This method is called when user ans is wrong
    //this method will navigate user to the activity PlayAgain
    public void gameLostPlayAgain() {
        playMusic(R.raw.wrong);
        Intent intent = new Intent(this, PlayAgainActivity.class);
        startActivity(intent);
        finish();
    }

    //This method is called when time is up
    //this method will navigate user to the activity Time_Up
    public void timeUp() {
        Intent intent = new Intent(this, TimeUpActivity.class);
        startActivity(intent);
        finish();
    }

    //If user press home button and come in the game from memory then this
    //method will continue the timer from the previous time it left
    @Override
    protected void onRestart() {
        super.onRestart();
        countDownTimer.start();
    }

    //When activity is destroyed then this will cancel the timer
    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    //This will pause the time
    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    //On BackPressed
    @Override
    public void onBackPressed() {
        finish();
    }

    public void playMusic(int resId){
        MediaPlayer mp = MediaPlayer.create(this, resId);
        mp.start();
    }

    //This dialog is show to the user after he ans correct
    public void correctDialog() {
        playMusic(R.raw.correct);
        final Dialog dialogCorrect = new Dialog(LanguageQuestionGameActivity.this);
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialogCorrect.getWindow() != null) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
            dialogCorrect.getWindow().setBackgroundDrawable(colorDrawable);
        }
        dialogCorrect.setContentView(R.layout.dialog_correct);
        dialogCorrect.setCancelable(false);
        dialogCorrect.show();

        //Since the dialog is show to user just pause the timer in background
        onPause();


        FButton buttonNext = (FButton) dialogCorrect.findViewById(R.id.dialogNext);


        //OnCLick listener to go next que
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LanguageQuestionGameActivity.this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("bodyPartId", bodyPartId); //InputString: from the EditText
                editor.apply();
                dialogCorrect.dismiss();
                finish();
            }
        });
    }

    //This method will disable all the option button
    public void disableButton() {
        buttonCheck.setEnabled(false);
    }
}