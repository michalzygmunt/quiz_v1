package com.example.quiz_v1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ = 1;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";
    public static final String EXTRA_NAME = "extraName";

    private TextView textViewHighscore;
    private Spinner spinnerName;
    private int highscore;

    QuizDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new QuizDbHelper(this);
        spinnerName = findViewById(R.id.spinner_name);


        List<String> imiona = dbHelper.getNames();
        Set<String> set = new HashSet<>(imiona);
        imiona.clear();
        imiona.addAll(set);
        ArrayAdapter<String> adapterNames = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, imiona);
        adapterNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerName.setAdapter(adapterNames);


        textViewHighscore = findViewById(R.id.text_view_highscore);
        //  loadHiscore();

        Button buttonStartQuiz = findViewById(R.id.button_start_quiz);
        Button buttonAddQuestion = findViewById(R.id.button_add_question);
        Button buttonStartQuiz_a = findViewById(R.id.button_start_quiz_a);
        Button buttonHighscores = findViewById(R.id.button_highscores);

        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz();
            }
        });
        buttonStartQuiz_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz_a();
            }
        });
        buttonHighscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHighscores();
            }
        });

        buttonAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuestion();
            }
        });

    }

    private void startHighscores() {
        Intent intent = new Intent(this, HighScoreActivity.class);
        this.startActivity(intent);
    }

    private void addQuestion() {
        Intent intent = new Intent(this, AddQuestion.class);
        this.startActivity(intent);
    }

    private void startQuiz_a() {


        Intent intent = new Intent(this, QuizActivityNew.class);

        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    private void startQuiz() {
        String name = spinnerName.getSelectedItem().toString();

        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra(EXTRA_NAME, name);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);

                //    updateHighscore(score);

            }
        }
    }

    private void loadHiscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Highscore: " + highscore);
    }

    private void updateHighscore(int highscoreNew) {
        highscore = highscoreNew;
        textViewHighscore.setText("Highscore: " + highscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }
}
