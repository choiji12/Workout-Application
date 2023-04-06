package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import java.util.Calendar;

public class QuetionActivity4 extends AppCompatActivity {
    Button btnBirthday;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quetion4);

        ProgressBar pb = findViewById(R.id.progressbarPercent);
        pb.setProgress(80);

        btnBirthday = findViewById(R.id.btnBirthday);

        btnBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(QuetionActivity4.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                btnBirthday.setText(year + "년" + (month+1) + "월" +dayOfMonth + "일");
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

    }
}