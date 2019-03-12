package com.example.quiz_v1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HighScoreActivity extends AppCompatActivity {
    LinearLayout layoutTest;
    RelativeLayout relativeLayout;
    private TextView textView;
    QuizDbHelper db;
    ListView lv;
    ListView lv2;
    List<String> wyniki = new ArrayList<>();
    List<String> imiona = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        db = new QuizDbHelper(this);

        lv = findViewById(R.id.listViewID);
        lv2 = findViewById(R.id.listViewID2);

        wyniki = db.getHighScores();
        imiona = db.getHighScoresName();


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, wyniki);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, imiona);
        lv.setAdapter(adapter);
        lv2.setAdapter(adapter2);


    }


}



