package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

class QuestionHelperLanguage extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "TQuestion.db";

    //If you want to add more questions or wanna update table values
    //or any kind of modification in db just increment version no.
    private static final int DB_VERSION = 3;
    //Table name
    private static final String TABLE_NAME = "LANGUAGE";
    //Id of question
    private static final String UID = "_UID";
    //Question
    private static final String QUESTION = "QUESTION";
    //Answer
    private static final String ANSWER = "ANSWER";

    //So basically we are now creating table with first column-id , sec column-question , third column -option A, fourth column -option B , Fifth column -option C , sixth column -option D , seventh column - answer(i.e ans of  question)
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + UID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QUESTION + " VARCHAR(255), " + ANSWER + " VARCHAR(255));";
    //Drop table query
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    QuestionHelperLanguage(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //OnCreate is called only once
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //OnUpgrade is called when ever we upgrade or increment our database version no
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    void allQuestion() {
        ArrayList<Question> arraylist = new ArrayList<>();

        arraylist.add(new Question("Quyida berilgan so`zga ma`nodosh so`zni toping: rohat.", "maza"));

        arraylist.add(new Question("5 yoshida 500 ta kitob òqigan bolaning ismini yozing. ", "Lev"));

        arraylist.add(new Question("3-sinf darsligida berilgan “Ona yurt” she`rining muallifi kim?", "Dilshod Rajab"));

        arraylist.add(new Question("3-sinf darsligida berilgan “Vatan” hikoyasining muallifi kim?", "Xudoyberdi To`xtaboyev"));

        arraylist.add(new Question("3-sinf darsligida berilgan “Vatan” hikoyasida podshoning suyukli xotini Malika faqat nima yer ekan?", "qush"));

        this.addAllQuestions(arraylist);

    }


    private void addAllQuestions(ArrayList<Question> allQuestions) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (Question question : allQuestions) {
                values.put(QUESTION, question.getQuestion());
                values.put(ANSWER, question.getAnswer());
                db.insert(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    List<Question> getAllOfTheQuestions() {

        List<Question> questionsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[] = {UID, QUESTION, ANSWER};
        Cursor cursor = db.query(TABLE_NAME, coloumn, null, null, null, null, null);


        while (cursor.moveToNext()) {
            Question question = new Question();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1));
            question.setAnswer(cursor.getString(2));
            questionsList.add(question);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        cursor.close();
        db.close();
        return questionsList;
    }
}

