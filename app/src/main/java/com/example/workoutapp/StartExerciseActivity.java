package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class StartExerciseActivity extends AppCompatActivity {

    private String userID;
    private String date;
    private ArrayList selectedExercise;

    private ArrayList<ArrayList<Integer>> totalWeightList;
    private ArrayList<ArrayList<Integer>> totalTimesList;

    private ArrayList firstExerciseTimes;
    private ArrayList getFirstExerciseWeight;
    private int exerciseLength;

    private Button btnFinishSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_exercise);

        Intent intent = getIntent();
        userID = getIntent().getStringExtra("userID");
        date = getIntent().getStringExtra("Date");
        totalWeightList = (ArrayList) intent.getSerializableExtra("weightList");
        totalTimesList = (ArrayList) intent.getSerializableExtra("timesList");
        selectedExercise = (ArrayList) intent.getSerializableExtra("exerciseList");

//
//        Log.d("Selected Exercise", "Selected Exercise :" + userID);
//        Log.d("Selected Exercise", "Selected Exercise :" + date);
//        Log.d("Selected Exercise", "Selected Exercise :" + selectedExercise);
//        Log.d("Selected Exercise", "Selected Exercise :" + totalWeightList);
//        Log.d("Selected Exercise", "Selected Exercise :" + totalTimesList);


        btnFinishSet = findViewById(R.id.btnFinishSet);
        btnFinishSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartExerciseActivity.this,ReviewExerciseActivity.class);
                intent.putExtra("timesList",totalTimesList);
                intent.putExtra("weightList",totalWeightList);
                intent.putExtra("exerciseList",selectedExercise);
                intent.putExtra("userID",userID);
                intent.putExtra("Date",date);
                startActivity(intent);
                finish();
            }
        });


    }
}