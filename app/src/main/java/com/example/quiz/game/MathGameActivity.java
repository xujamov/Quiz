package com.example.quiz.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.quiz.R;
import com.cncoderx.wheelview.OnWheelChangedListener;
import com.cncoderx.wheelview.WheelView;

public class MathGameActivity extends Activity implements View.OnClickListener {
    WheelView  a, b, c, d, e;
    ImageView imageViewCheck;
    ImageView imageViewExit;
    int number1,number2,number3,number4,number5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_view);

        imageViewCheck = (ImageView) findViewById(R.id.imageViewCheck);
        imageViewCheck.setOnClickListener(this);
        imageViewCheck.setImageResource(R.drawable.button_check);

        imageViewExit = (ImageView) findViewById(R.id.imageViewExit);
        imageViewExit.setOnClickListener(this);

        a = (WheelView) findViewById(R.id.textView1);
        b = (WheelView) findViewById(R.id.textView2);
        c = (WheelView) findViewById(R.id.textView3);
        d = (WheelView) findViewById(R.id.textView4);
        e = (WheelView) findViewById(R.id.textView5);
        number1 = -1;
        a.setEntries(new String []{
                " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"});
        a.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldIndex, int newIndex) {
                number1=newIndex-1;
            }
        });

       b.setEntries(new String[]{
               " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"});

        b.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldIndex, int newIndex) {
                number2=newIndex-1;
            }
        });


        c.setEntries(new String[]{
                " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"});

        c.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldIndex, int newIndex) {
                 number3=newIndex-1;
            }
        });


        d.setEntries(new String[]{
                " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"});

        d.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldIndex, int newIndex) {
                number4=newIndex-1;
            }
        });

        e.setEntries(new String[]{
                " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"});

         e.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldIndex, int newIndex) {
                number5=newIndex-1;
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewCheck:

                new CountDownTimer(1000,1000){
                    public void onTick(long millisUntilFinished){
                        imageViewCheck.setImageResource(R.drawable.button_check_click);

                        }
                    public void onFinish(){
                        imageViewCheck.setImageResource(R.drawable.button_check);


                    }
                } .start();


                if(number1 ==-1 && number2== 9 && number3 ==5 && number4 ==9 && number5 == 4) {
                    Intent intent = new Intent(NumberPickerTestActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if(number4 == 1 && number5 == 3){
                    // tabrik game win
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogLayout = inflater.inflate(R.layout.activity_fail, null);
                    builder.setView(dialogLayout);
                    builder.show();
                    updateWheel();
                }
                break;

            case R.id.imageViewExit:
                finish();
                break;
        }
    }

    private void updateWheel() {
        a.setCurrentIndex(0);
        b.setCurrentIndex(0);
        c.setCurrentIndex(0);
        d.setCurrentIndex(0);
        e.setCurrentIndex(0);
    }
}
