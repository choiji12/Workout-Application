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

//    자바 코드
//
//    Response.Listener<String> responseListener = new Response.Listener<String>() {
//        @Override
//        public void onResponse(String response) {
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                boolean success = jsonObject.getBoolean("success");
//                if(success){
//                    Toast.makeText(getApplicationContext(),"회원등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
//
//
//                }else {
//                    Toast.makeText(getApplicationContext(),"회원등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//    };
//
//    CalenderIsRequest calenderIsRequest = new CalenderIsRequest(calenderDate, userID, calenderMessage,
//            calenderTime ,calenderStar, calenderWeight, calenderVolume, calenderRoutine, responseListener);
//    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//        queue.add(calenderIsRequest);