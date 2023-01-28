package com.example.quiz;

import android.app.Activity;

public class Question extends Activity {
    private int id;
    private String question;
    private String answer;

    public Question(String q, String ans) {

        question = q;
        answer = ans;
    }

    public Question() {
        id = 0;
        question = "";
        answer = "";
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setId(int i) {
        id = i;
    }

    public void setQuestion(String q1) {
        question = q1;
    }

    public void setAnswer(String ans) {
        answer = ans;
    }


}
