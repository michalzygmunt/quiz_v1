package com.example.quiz_v1;

import android.provider.BaseColumns;

public final class QuizContract {

    private QuizContract() {
    }

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
        public static final String COLUMN_USER = "user";
    }

    public static class HighScore implements BaseColumns {
        public static final String TABLE_NAME = "highscores";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_SCORE = "score";
    }
}
