package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

    private Button btnFinishSet;
    private TextView txtExerciseName;
    private LinearLayout mainLayout;
    private TextView txtExerciseTime;

    private int exerciseLength;
    private int exerciseSequence=0;
    private int eachSetCount=0;
    private int exerciseFinishedCount=0;
    private int exerciseCount = 0;
    private int minutes = 0;
    private int seconds = 0;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_exercise);

        /** 이전 엑티비티에서 데이터 호출 */
        getIntentData();

        /** 뷰 초기화 */
        initView();
        exerciseLength = selectedExercise.size();

        /** 운동 시작 뷰 추가 */
        addExerciseView(exerciseSequence);

        startTimer();

        /** 운동 완료버튼 눌렀을 때 동작 */
        btnFinishSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** 세트 완료 시 동작 */
                finishSet(exerciseSequence);
                /** 완료한 세트 색깔 변경 */
                finishedSetDecorator();
                /** 모든 운동 완료 시 엑티비티 종료 */
                if(exerciseFinishedCount == exerciseLength) {
                    finishActivity();
                }
            }
        });
    }

    private void finishedSetDecorator(){
        if(eachSetCount != 0) {
            LinearLayout decoratedLayout = (LinearLayout) mainLayout.getChildAt(eachSetCount - 1);
            decoratedLayout.setBackgroundColor(Color.BLUE);
        }
    }

    private void finishSet(int exerciseSequenceFor){

        eachSetCount++;
        int setCount = totalWeightList.get(exerciseSequenceFor).size();
        if(eachSetCount == setCount){
            removeViews(mainLayout);
            eachSetCount = 0;
            exerciseFinishedCount ++;
            exerciseSequence ++;
            if(exerciseFinishedCount == exerciseLength) {
                return;
            }
            addExerciseView(exerciseSequenceFor+1);
        }
    }

    private void removeViews(LinearLayout parent) {
        parent.removeAllViews();
    }

    private void getIntentData(){
        Intent intent = getIntent();
        userID = getIntent().getStringExtra("userID");
        date = getIntent().getStringExtra("Date");
        totalWeightList = (ArrayList) intent.getSerializableExtra("weightList");
        totalTimesList = (ArrayList) intent.getSerializableExtra("timesList");
        selectedExercise = (ArrayList) intent.getSerializableExtra("exerciseList");
    }

    private void addExerciseView(int exerciseSequenceFor){
        txtExerciseName.setText((CharSequence) selectedExercise.get(exerciseSequenceFor));
        int setCount = totalWeightList.get(exerciseSequenceFor).size();
        Log.d("First Set","First Set Cnt" + setCount);
        for (int i=0; i<setCount; i++){
            LinearLayout contentsLayout = new LinearLayout(this);
            contentsLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView txtSetNum = new TextView(this);
            String setNumString = String.valueOf(i+1);
            txtSetNum.setText(setNumString + "세트");

            TextView txtWeight = new TextView(this);
            int weight = totalWeightList.get(0).get(i);
            String weightString = String.valueOf(weight);
            txtWeight.setText(weightString + "KG");

            TextView txtTimes = new TextView(this);
            int times = totalTimesList.get(0).get(i);
            String timesString = String.valueOf(times);
            txtTimes.setText(timesString + "회");

            contentsLayout.addView(txtSetNum);
            contentsLayout.addView(txtWeight);
            contentsLayout.addView(txtTimes);

            /** 운동의 contentsLayout의 ID는 100~ */
            contentsLayout.setId(exerciseSequenceFor*100 +i );

            mainLayout.addView(contentsLayout);
        }

    }

    private void initView(){
        mainLayout = findViewById(R.id.mainLayout);
        txtExerciseName = findViewById(R.id.txtExerciseName);
        btnFinishSet = findViewById(R.id.btnFinishSet);
        txtExerciseTime = findViewById(R.id.txtExerciseTime);
        handler = new Handler(Looper.getMainLooper());
    }

    private void startTimer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 1초마다 실행되는 작업
                seconds++;
                if (seconds == 60) {
                    minutes++;
                    seconds = 0;
                }
                String time = String.format("%02d:%02d", minutes, seconds);
                txtExerciseTime.setText("운동 시간 : " + time);
                handler.postDelayed(this, 1000); // 1초 후에 다시 실행
            }
        }, 1000); // 초기 1초 후에 실행
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 액티비티가 종료될 때 타이머 중지
        handler.removeCallbacksAndMessages(null);
    }

    private void finishActivity(){
        Intent intent = new Intent(StartExerciseActivity.this,ReviewExerciseActivity.class);
        intent.putExtra("timesList",totalTimesList);
        intent.putExtra("weightList",totalWeightList);
        intent.putExtra("exerciseList",selectedExercise);
        intent.putExtra("userID",userID);
        intent.putExtra("Date",date);
        startActivity(intent);
        finish();
    }
}