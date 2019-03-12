package com.example.quiz_v1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.quiz_v1.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuizContract.QuestionsTable.TABLE_NAME + " ( " +
                QuizContract.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_USER + " TEXT, " +
                "UNIQUE(" + QuestionsTable.COLUMN_QUESTION + "," + QuestionsTable.COLUMN_USER + ") ON CONFLICT REPLACE" +
                ")";

        final String SQL_CREATE_HIGHSCORE = "CREATE TABLE " +
                HighScore.TABLE_NAME + " ( " +
                HighScore._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HighScore.COLUMN_USER + " TEXT, " +
                HighScore.COLUMN_SCORE + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        db.execSQL(SQL_CREATE_HIGHSCORE);

        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Jaki owoc lubisz najbardziej?", "mandarynki", "pomarancze", "jablka", "kiwi", 1, Question.ANDRZEJ);
        addQuestion(q1);
        Question q2 = new Question("Jakim zwierzeciem chcialbys byc?", "panda", "kojot", "kot", "pies", 2, Question.ANDRZEJ);
        addQuestion(q2);
        Question q3 = new Question("Co wolalbys pic do konca zycia?", "woda", "sok", "piwo", "denaturat", 3, Question.ANDRZEJ);
        addQuestion(q3);
        Question q4 = new Question("Najlepszy jezyk programownia?", "C++", "Python", "Swift", "JAVA", 4, Question.ANDRZEJ);
        addQuestion(q4);
        Question q5 = new Question("Jaki masz samochod?", "opel", "bmw", "dodge", "mercedes", 1, Question.ANDRZEJ);
        addQuestion(q5);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_USER, question.getName());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setName(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_USER)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public List<Question> getQuestions(String name) {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{name};

        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_USER + " = ?", selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setName(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_USER)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public List<String> getNames() {
        List<String> nameList = new ArrayList<>();
        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT " + QuestionsTable.COLUMN_USER + " FROM " + QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                String imie;
                imie = c.getString(c.getColumnIndex(QuestionsTable.COLUMN_USER));
                nameList.add(imie);
            } while (c.moveToNext());
        }
        c.close();
        return nameList;
    }

    public List<String> getHighScores() {
        List<String> highscores = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + HighScore.COLUMN_SCORE + " FROM " + HighScore.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                String wynik;
                wynik = c.getString(c.getColumnIndex(HighScore.COLUMN_SCORE));
                highscores.add(wynik);
            } while (c.moveToNext());
        }
        c.close();
        return highscores;
    }

    public List<String> getHighScoresName() {
        List<String> highscores = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + HighScore.COLUMN_USER + " FROM " + HighScore.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                String wynik;
                wynik = c.getString(c.getColumnIndex(HighScore.COLUMN_USER));
                highscores.add(wynik);
            } while (c.moveToNext());
        }
        c.close();
        return highscores;
    }

    public List<String> get_Q() {
        List<String> nameList = new ArrayList<>();
        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT " + QuestionsTable.COLUMN_QUESTION + " FROM " + QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                String imie;
                imie = c.getString(c.getColumnIndex(QuestionsTable.COLUMN_USER));
                nameList.add(imie);
            } while (c.moveToNext());
        }
        c.close();
        return nameList;
    }

    public void insertHighscore(String name, String wynik) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(HighScore.COLUMN_USER, name);
        cv.put(HighScore.COLUMN_SCORE, wynik);
        db.insert(HighScore.TABLE_NAME, null, cv);
    }

    public void insertData(String pytanie, String odpowiedz1, String odpowiedz2, String odpowiedz3, String odpowiedz4, int poprawna, String name) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, pytanie);
        cv.put(QuestionsTable.COLUMN_OPTION1, odpowiedz1);
        cv.put(QuestionsTable.COLUMN_OPTION2, odpowiedz2);
        cv.put(QuestionsTable.COLUMN_OPTION3, odpowiedz3);
        cv.put(QuestionsTable.COLUMN_OPTION4, odpowiedz4);
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, poprawna);
        cv.put(QuestionsTable.COLUMN_USER, name);
        db.insert(QuestionsTable.TABLE_NAME, null, cv);

    }


}
