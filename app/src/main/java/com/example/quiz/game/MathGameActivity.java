package com.example.quiz.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quiz.R;
import com.example.quiz.wheelview.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MathGameActivity extends Activity implements View.OnClickListener {
    WheelView a, b, c, d, e;
    ImageView imageViewCheck;
    ImageView imageViewExit;

    TextView tvQuestion;

    String question = "", correctAnswer = "";
    int number1,number2,number3,number4,number5;
    List<WheelView> wheels = new ArrayList<>();
    List<String> selectedNumber = new ArrayList<>(Arrays.asList("", "", "", "", "", ""));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_view);
        imageViewCheck = findViewById(R.id.imageViewCheck);

        tvQuestion = findViewById(R.id.tvQuestion);

        imageViewExit = findViewById(R.id.imageViewExit);
        imageViewExit.setOnClickListener(this);

        a = findViewById(R.id.textView1);
        b = findViewById(R.id.textView2);
        c = findViewById(R.id.textView3);
        d = findViewById(R.id.textView4);
        e = findViewById(R.id.textView5);
        wheels.add(a);
        wheels.add(b);
        wheels.add(c);
        wheels.add(d);
        wheels.add(e);
        number1 = -1;

        for (int i = 0; i < wheels.size(); i++) {
            WheelView selectedWheel = wheels.get(i);
            selectedWheel.setEntries(new String []{
                    " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"});

            int index = i;
            selectedWheel.setOnWheelChangedListener((wheel, oldIndex, newIndex) -> {
                if(newIndex > 1 || (newIndex > 0 && index != 0))
                    selectedNumber.set(index, Integer.toString(newIndex - 1));
                else
                    selectedNumber.set(index, "");
            });
        }

        generateQuestionAndSetText();
    }

    private void generateQuestion(){
        Random random = new Random();
        int number1 = random.nextInt(100);
        int number2 = random.nextInt(100);
        List<String> operations = new ArrayList<>();
        operations.add(" + ");
        operations.add(" - ");
        operations.add(" * ");
        operations.add(" / ");
        String operation = operations.get(random.nextInt(4));
        switch (operation) {
            case " + ":
                question = number1 + operation + number2 + " = ?";
                correctAnswer = Integer.toString(number1 + number2);
                break;
            case " - ":
                if (number1 > number2) {
                    question = number1 + operation + number2 + " = ?";
                    correctAnswer = Integer.toString(number1 - number2);
                } else {
                    question = number2 + operation + number1;
                    correctAnswer = Integer.toString(number2 - number1);
                }
                break;
            case " * ":
                number1 = random.nextInt(21);
                number2 = random.nextInt(11);
                question = number1 + operation + number2 + " = ?";
                correctAnswer = Integer.toString(number1 * number2);
                break;
            case " / ":
                number2 = 1 + random.nextInt(15);
                number1 = number2 * random.nextInt(10);
                question = number1 + operation + number2 + " = ?";
                correctAnswer = Integer.toString(number1 / number2);
                break;
        }
    }

    private String getEnteredAnswer(){
        StringBuilder answer = new StringBuilder();
        for (String number: selectedNumber) {
            answer.append(number);
        }
        return answer.toString();
    }

    public void CheckAnswer(View v) {
        String enteredAnswer = getEnteredAnswer();
        new CountDownTimer(1000,1000){
            public void onTick(long millisUntilFinished){
                imageViewCheck.setImageResource(R.drawable.button_check_click);
            }
            public void onFinish(){
                imageViewCheck.setImageResource(R.drawable.button_check);
            }
        } .start();

//        Log.e("MyLogs", enteredAnswer);
//        Log.e("MyLogs", correctAnswer);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout;
        if (enteredAnswer.equals(correctAnswer)){
            playMusic(R.raw.correct);
            generateQuestionAndSetText();

        }
        else {
            playMusic(R.raw.wrong);
        }
        updateWheel();
    }

    private void generateQuestionAndSetText(){
        generateQuestion();
        tvQuestion.setText(question);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageViewExit) {
            finish();
        }
    }

    private void updateWheel() {
        a.setCurrentIndex(0);
        b.setCurrentIndex(0);
        c.setCurrentIndex(0);
        d.setCurrentIndex(0);
        e.setCurrentIndex(0);

        selectedNumber = new ArrayList<>(Arrays.asList("", "", "", "", "", ""));
    }

    public void playMusic(int resId){
        MediaPlayer mp = MediaPlayer.create(this, resId);
        mp.start();
    }
}
