package com.example.quiz

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class QuizHelper(private val context: Context) : SQLiteOpenHelper(
    context, DB_NAME, null, DB_VERSION
) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        //OnCreate is called only once
        sqLiteDatabase.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        //OnUpgrade is called when ever we upgrade or increment our database version no
        sqLiteDatabase.execSQL(DROP_TABLE)
        onCreate(sqLiteDatabase)
    }

    fun allQuestion() {
        val arraylist = ArrayList<Quiz>()
        arraylist.add(Quiz("70 + 80 = ?", "150", "100", "110", "180", "150"))
        arraylist.add(Quiz("94 – x = 47\nx = ?", "40", "47", "43", "49", "47"))
        arraylist.add(
            Quiz(
                "To`rtburchakning bitta uchini kessak necha burchak hosil bo`ladi? ?",
                "3",
                "4",
                "5",
                "6",
                "5"
            )
        )
        arraylist.add(Quiz("72 : 8 = ?", "7", "8", "9", "11", "9"))
        arraylist.add(
            Quiz(
                "Bir yuz yetmish olti soni to`g`ri yozilgan qatorni aniqlang.",
                "167",
                "176",
                "716",
                "617",
                "176"
            )
        )
        arraylist.add(
            Quiz(
                "6 raqami o`nlik xona birligida joylashgan  uch xonali sonni toping.",
                "26",
                "176",
                "6",
                "162",
                "176"
            )
        )
        arraylist.add(Quiz("42 : 6 = ?", "8", "6", "7", "12", "7"))
        arraylist.add(
            Quiz(
                "6 raqami birliklar xonasida joylashgan  uch xonali sonni toping.",
                "167",
                "764",
                "584",
                "176",
                "176"
            )
        )
        arraylist.add(
            Quiz(
                "Do`konga jami 97 kilogram kartoshka keltirildi. Tushgacha 23 kilogram, tushdan so`ng yana 15 kilogram kartoshka sotildi. Do`konda qancha kartoshka qoldi?",
                "125",
                "105",
                "59",
                "60",
                "59"
            )
        )
        addAllQuestions(arraylist)
    }

    private fun addAllQuestions(allQuestions: ArrayList<Quiz>) {
        val db = this.writableDatabase
        db.beginTransaction()
        try {
            val values = ContentValues()
            for (question in allQuestions) {
                values.put(QUESTION, question.question)
                values.put(OPTA, question.optA)
                values.put(OPTB, question.optB)
                values.put(OPTC, question.optC)
                values.put(OPTD, question.optD)
                values.put(ANSWER, question.answer)
                db.insert(TABLE_NAME, null, values)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    val allOfTheQuestions: List<Quiz>
        get() {
            val questionsList: MutableList<Quiz> = ArrayList()
            val db = this.writableDatabase
            db.beginTransaction()
            val column = arrayOf(UID, QUESTION, OPTA, OPTB, OPTC, OPTD, ANSWER)
            val cursor = db.query(TABLE_NAME, column, null, null, null, null, null)
            while (cursor.moveToNext()) {
                val question = Quiz()
                question.setId(cursor.getInt(0))
                question.question = cursor.getString(1)
                question.optA = cursor.getString(2)
                question.optB = cursor.getString(3)
                question.optC = cursor.getString(4)
                question.optD = cursor.getString(5)
                question.answer = cursor.getString(6)
                questionsList.add(question)
            }
            db.setTransactionSuccessful()
            db.endTransaction()
            cursor.close()
            db.close()
            return questionsList
        }

    companion object {
        private const val DB_NAME = "TQuiz.db"

        //If you want to add more questions or wanna update table values
        //or any kind of modification in db just increment version no.
        private const val DB_VERSION = 3

        //Table name
        private const val TABLE_NAME = "QUIZ"

        //Id of question
        private const val UID = "_UID"

        //Question
        private const val QUESTION = "QUESTION"

        //Option A
        private const val OPTA = "OPTA"

        //Option B
        private const val OPTB = "OPTB"

        //Option C
        private const val OPTC = "OPTC"

        //Option D
        private const val OPTD = "OPTD"

        //Answer
        private const val ANSWER = "ANSWER"

        //So basically we are now creating table with first column-id , sec column-question , third column -option A, fourth column -option B , Fifth column -option C , sixth column -option D , seventh column - answer(i.e ans of  question)
        private const val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ( $UID INTEGER PRIMARY KEY AUTOINCREMENT , $QUESTION VARCHAR(255), $OPTA VARCHAR(255), $OPTB VARCHAR(255), $OPTC VARCHAR(255), $OPTD VARCHAR(255), $ANSWER VARCHAR(255));"

        //Drop table query
        private const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}