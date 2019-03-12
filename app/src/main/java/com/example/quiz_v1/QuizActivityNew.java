package com.example.quiz_v1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuizActivityNew extends AppCompatActivity {

    public static final String EXTRA_SCORE = "extraScore";
    private TextView textViewQuestion;

    private TextView textViewQuestionCount;

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;

    private ColorStateList textColorDefaultRb;


    private List<Question> questionList;
    private List<String> nameList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;
    private boolean answered;
    QuizDbHelper dbHelper;
    String imie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_new);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alert.setView(input);
        alert.setTitle("Jak sie nazywasz?");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                imie = input.getText().toString();
            }
        });

        alert.show();


        textViewQuestion = findViewById(R.id.text_view_question);

        textViewQuestionCount = findViewById(R.id.text_view_question_count);

        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);

        textColorDefaultRb = rb1.getTextColors();


        dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();
        nameList = dbHelper.getNames();

        questionCountTotal = questionList.size();


        for (int i = 0; i < questionList.size(); i++) {
            for (int j = 1; j < questionList.size(); j++) {
                if ((i != j) && (questionList.get(i).getQuestion().equals(questionList.get(j).getQuestion()))) {
                    questionList.remove(j);

                }
            }
        }

        Collections.shuffle(questionList);


        questionCountTotal = questionList.size();

        showNextQuestion();

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(QuizActivityNew.this, "Prosze wybierz jakas odpowiedz", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    showNextQuestion();
                }
            }
        });
    }


    private void showNextQuestion() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rb4.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confrim");
        } else {
            finishQuiz();
        }
    }

    private void checkAnswer() {
        answered = true;
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.EXTRA_NAME);
        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        dbHelper.insertData(currentQuestion.getQuestion(), currentQuestion.getOption1(), currentQuestion.getOption2(), currentQuestion.getOption3(), currentQuestion.getOption4()
                , answerNr, imie);
        textViewQuestion.setText(currentQuestion.getQuestion());
        rb1.setText(currentQuestion.getOption1());
        rb2.setText(currentQuestion.getOption2());
        rb3.setText(currentQuestion.getOption3());
        rb4.setText(currentQuestion.getOption4());

        showSolution();
    }

    private void showSolution() {

        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
        } else {
            buttonConfirmNext.setText("Finish");
        }

    }

    private void saveNextQuestion(int a) {
        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);


            dbHelper.insertData(currentQuestion.getQuestion(), currentQuestion.getOption1(), currentQuestion.getOption2(), currentQuestion.getOption3(), currentQuestion.getOption4()
                    , a, "Janusz");
            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());
        } else {
            finishQuiz();
        }


    }


    private void finishQuiz() {
        Intent intent = new Intent(this, MainActivity.class);

        finish();
        startActivity(intent);
    }


}