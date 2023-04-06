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

    }
}