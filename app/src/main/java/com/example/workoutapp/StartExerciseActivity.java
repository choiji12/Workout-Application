package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private TextView txtExerciseName;

    private LinearLayout mainLayout;
    private int exerciseCount = 0;
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

        mainLayout = findViewById(R.id.mainLayout);

        btnFinishSet = findViewById(R.id.btnFinishSet);

        addFirstExercise();
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

    private void addFirstExercise(){
        int setCount = totalWeightList.get(0).size();
        for (int i=0; i<setCount; i++){
            LinearLayout contentsLayout = new LinearLayout(this);
            contentsLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView txtSetNum = new TextView(this);
            txtSetNum.setText(i+1);

            TextView txtWeight = new TextView(this);
            int weight = totalWeightList.get(0).get(i);
            txtWeight.setText(weight);

            TextView txtTimes = new TextView(this);
            int times = totalTimesList.get(0).get(i);
            txtTimes.setText(times);

            contentsLayout.addView(txtSetNum);
            contentsLayout.addView(txtWeight);
            contentsLayout.addView(txtTimes);

            mainLayout.addView(contentsLayout);

        }
    }
}