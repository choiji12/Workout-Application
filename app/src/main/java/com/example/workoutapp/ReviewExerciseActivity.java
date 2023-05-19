package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ReviewExerciseActivity extends AppCompatActivity {


    private String userID;
    private String date;
    private ArrayList selectedExercise;

    private ArrayList<ArrayList<Integer>> totalWeightList;
    private ArrayList<ArrayList<Integer>> totalTimesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_exercise);

        Intent intent = getIntent();
        userID = getIntent().getStringExtra("userID");
        date = getIntent().getStringExtra("Date");
        totalWeightList = (ArrayList) intent.getSerializableExtra("weightList");
        totalTimesList = (ArrayList) intent.getSerializableExtra("timesList");
        selectedExercise = (ArrayList) intent.getSerializableExtra("exerciseList");

        Log.d("Selected Exercise", "Selected Exercise :" + userID);
        Log.d("Selected Exercise", "Selected Exercise :" + date);
        Log.d("Selected Exercise", "Selected Exercise :" + selectedExercise);
        Log.d("Selected Exercise", "Selected Exercise :" + totalWeightList);
        Log.d("Selected Exercise", "Selected Exercise :" + totalTimesList);
    }
}