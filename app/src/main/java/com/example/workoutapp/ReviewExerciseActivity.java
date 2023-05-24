package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ReviewExerciseActivity extends AppCompatActivity {


    private String userID;
    private String date;
    private ArrayList selectedExercise;

    private ArrayList<ArrayList<Integer>> totalWeightList;
    private ArrayList<ArrayList<Integer>> totalTimesList;
    private String exerciseTime;

    //------------------------------------------------------------------------------------------------------
    private Button btnCompletion;
    private Button btnRoutineStore;

    ArrayList<String> strTimesList;
    ArrayList<String> strWeightList;
    ArrayList<String> strSetList;

    private int volumeSum;
    //------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_exercise);

        Intent intent = getIntent();
        userID = getIntent().getStringExtra("userID");
        date = getIntent().getStringExtra("Date");
        exerciseTime = getIntent().getStringExtra("ExerciseTime");
        totalWeightList = (ArrayList) intent.getSerializableExtra("weightList");
        totalTimesList = (ArrayList) intent.getSerializableExtra("timesList");
        selectedExercise = (ArrayList) intent.getSerializableExtra("exerciseList");

        Log.d("Selected Exercise", "Selected Exercise :" + userID);
        Log.d("Selected Exercise", "Selected Exercise :" + date);
        Log.d("Selected Exercise", "Selected Exercise :" + selectedExercise);
        Log.d("Selected Exercise", "Selected Exercise :" + totalWeightList);
        Log.d("Selected Exercise", "Selected Exercise :" + totalTimesList);
        Log.d("Selected Exercise", "Selected Exercise :" + exerciseTime);


        //------------------------------------------------------------------------------------------------------
        // 중량 총합
        ArrayList<Integer> multipliedArray = new ArrayList<>();
        int result = 0;

        for(int i=0; i<totalWeightList.size(); i++){
            for(int j=0; j<totalWeightList.get(i).size(); j++){
                result = totalWeightList.get(i).get(j) * totalTimesList.get(i).get(j);
                multipliedArray.add(result);
            }
        }

        volumeSum = 0;
        for (int num : multipliedArray) {
            volumeSum += num;
        }


        btnCompletion = findViewById(R.id.btnCompletion);
        btnCompletion.setOnClickListener(calenderStore);

        btnRoutineStore = findViewById(R.id.btnRoutineStore);
        btnRoutineStore.setOnClickListener(routineStore);

        //------------------------------------------------------------------------------------------------------
    }

    /*
    * 루틴 입력란 만들기 convertToStringList
    * */
    View.OnClickListener routineStore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            strTimesList = convertToStringList(totalTimesList);
            strWeightList = convertToStringList(totalWeightList);
            strSetList = new ArrayList<>();
            String setlist = "";
            for(int i=0; i<totalTimesList.size(); i++){
                for(int j=0; j<totalTimesList.get(i).size(); j++){
                    setlist += j+1;
                    setlist += ", ";
                }
                StringBuilder sb = new StringBuilder(setlist);
                if (sb.length() > 0) {
                    sb.setLength(sb.length() - 2); // 마지막 쉼표와 공백 제거
                }
                strSetList.add(sb.toString());
                setlist = "";

            }


            Log.d("Selected Exercise", "Selectedtime :" + strTimesList.get(0));
            Log.d("Selected Exercise", "Selected :" + strWeightList);
            Log.d("Selected Exercise", "Selected :" + selectedExercise);

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if(success){
                            Toast.makeText(getApplicationContext(),"루틴을 저장 하였습니다.",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"루틴 저장에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };

            for(int idx=0; idx<selectedExercise.size(); idx++){
                RoutineIsRequest routineIsRequest = new RoutineIsRequest(/*routineName*/"테스트용3", userID,
                        String.valueOf(selectedExercise.get(idx)),
                        strSetList.get(idx), strTimesList.get(idx),
                        strWeightList.get(idx), String.valueOf(idx+1), responseListener);
                RequestQueue queue = Volley.newRequestQueue(ReviewExerciseActivity.this);
                queue.add(routineIsRequest);
            }

        }
    };


    View.OnClickListener calenderStore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String routine = "";
            for(int i=0; i<selectedExercise.size(); i++){
                routine += selectedExercise.get(i) + "\n";
                for(int j=0; j<totalWeightList.get(i).size(); j++){
                    routine += String.valueOf(j+1) + "세트 " + totalWeightList.get(i).get(j) + "kg "
                                    + totalTimesList.get(i).get(j) + "회\n";
                }
                routine += "\n";
            }

            //자바 코드
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if(success){
                            Toast.makeText(getApplicationContext(),"운동 완료 하였습니다.",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"운동 완료에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };
                                        // volume = 무게 * 횟스
            CalenderIsRequest calenderIsRequest = new CalenderIsRequest(date, userID, "",
                    exerciseTime ,"5", "80.5", String.valueOf(volumeSum), routine, /*bmi*/20.1, responseListener);
            RequestQueue queue = Volley.newRequestQueue(ReviewExerciseActivity.this);
            queue.add(calenderIsRequest);
        }
    };

    public static ArrayList<String> convertToStringList(ArrayList<ArrayList<Integer>> twoDimensionalArrayList) {
        ArrayList<String> convertedList = new ArrayList<>();

        for (ArrayList<Integer> row : twoDimensionalArrayList) {
            StringBuilder sb = new StringBuilder();

            for (int value : row) {
                sb.append(value).append(", ");
            }

            if (sb.length() > 0) {
                sb.setLength(sb.length() - 2); // 마지막 쉼표와 공백 제거
            }

            convertedList.add(sb.toString());
        }

        return convertedList;
    }
}

