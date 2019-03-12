package com.example.quiz_v1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AddQuestion extends AppCompatActivity {
    private EditText imie;
    private EditText pytanie;
    private EditText odpowiedz1;
    private EditText odpowiedz2;
    private EditText odpowiedz3;
    private EditText odpowiedz4;
    private EditText poprawna;
    private Button dodaj;
    private String imionko;
    QuizDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        dbHelper = new QuizDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        imie = findViewById(R.id.editText_imie);
        pytanie = findViewById(R.id.editText_pytanie);
        odpowiedz1 = findViewById(R.id.editText_odp1);
        odpowiedz2 = findViewById(R.id.editText_odp2);
        odpowiedz3 = findViewById(R.id.editText_odp3);
        odpowiedz4 = findViewById(R.id.editText_odp4);

        poprawna = findViewById(R.id.editText_poprawna);
        dodaj = findViewById(R.id.button_add);


        AddData();

    }


    public void AddData() {
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = poprawna.getText().toString();
                int a = Integer.parseInt(s);
                dbHelper.insertData(pytanie.getText().toString(), odpowiedz1.getText().toString(), odpowiedz2.getText().toString(), odpowiedz3.getText().toString(), odpowiedz4.getText().toString(), a, imie.getText().toString());
                /*   dbHelper.insertData(pytanie.getText().toString(),odpowiedz.getText().toString(),odpowiedz.getText().toString(), odpowiedz.getText().toString(),odpowiedz.getText().toString(),a,"Andrzej");*/
                Toast.makeText(AddQuestion.this, "Dodano pytanie!", Toast.LENGTH_SHORT).show();
                finishAdd();
            }
        });
    }

    private void finishAdd() {
        Intent intent = new Intent(this, MainActivity.class);

        finish();
        startActivity(intent);
    }


}
