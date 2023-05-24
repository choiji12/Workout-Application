package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoadExerciseActivity extends AppCompatActivity {
    private Button btnYes;
    private Button btnNo;
    private Button btnDelete;
    private LinearLayout mainLayout;
    private String userID;
    private String date;
    private ArrayList routineNames;

    //----------------------------------------------------- 인텐트를 위한 리스트

    private ArrayList<ArrayList<Integer>> WeightList;
    private ArrayList<ArrayList<Integer>> TimesList;
    private ArrayList<ArrayList<Integer>> SetList;


    private ArrayList EventList;
    private ArrayList strWeightList;
    private ArrayList strTimesList;
    private ArrayList strSetList;
    //-----------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_exercise);

        getIntentData();

        initView();

        // 루틴 이름 리스트를 만들기 위한 디비 코드
        routineNames = new ArrayList<>();

        WeightList = new ArrayList<>();
        TimesList = new ArrayList<>();
        SetList = new ArrayList<>();

        strWeightList = new ArrayList<>();
        strTimesList = new ArrayList<>();
        strSetList = new ArrayList<>();

        EventList = new ArrayList<>();

        Response.Listener<String> rNameResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    JSONArray tuples = jsonObject.getJSONArray("tuples"); // 튜플 배열을 가져옵니다.

                    if (success) {
                        for (int i = 0; i < tuples.length(); i++) {
                            JSONObject tuple = tuples.getJSONObject(i);
                            String routineName = tuple.getString("routineName");
                            routineNames.add(routineName);
                            Log.d("Selected Exercise", "Selectedtime :" + routineNames);
                        }
                        displayRoutineNames();
                    } else {
                        Toast.makeText(getApplicationContext(), "정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RoutineNameRequest routineNameRequest = new RoutineNameRequest(userID, rNameResponseListener);
        RequestQueue queue = Volley.newRequestQueue(LoadExerciseActivity.this);
        queue.add(routineNameRequest);





        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SlidingUpPanelLayout slidingLayout = findViewById(R.id.main_frame);
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

    }

    private void initView(){
        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);
        mainLayout = findViewById(R.id.mainLayout);
    }

    private void getIntentData(){
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        date = intent.getStringExtra("Date");
    }
    private void displayRoutineNames(){
        for (int i=0; i<routineNames.size(); i++){
            Button exerciseName = new Button(this);
            /** 버튼의 ID는 1~ */
            exerciseName.setId(i+1);
            exerciseName.setText(String.valueOf(routineNames.get(i)));
            Log.d("Selected Exercise", "Selectedtime :" + exerciseName.getId());
            Log.d("Selected Exercise", "Selectedtime :" + routineNames.get(i));

            exerciseName.setOnClickListener(info);

            mainLayout.addView(exerciseName);
        }
    }


    View.OnClickListener info = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Response.Listener<String> rResponseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        EventList.clear();
                        strSetList.clear();
                        strWeightList.clear();
                        strTimesList.clear();

                        WeightList.clear();
                        TimesList.clear();
                        SetList.clear();
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        JSONArray tuples = jsonObject.getJSONArray("tuples"); // 튜플 배열을 가져옵니다.

                        if (success) {
                            for (int i = 0; i < tuples.length(); i++) {
                                JSONObject tuple = tuples.getJSONObject(i);
                                String routineEvent = tuple.getString("routineEvent");
                                String routineSet = tuple.getString("routineSet");
                                String routineWeight = tuple.getString("routineWeight");
                                String routineReps = tuple.getString("routineReps");

                                EventList.add(routineEvent);
                                strSetList.add(routineSet);
                                strWeightList.add(routineWeight);
                                strTimesList.add(routineReps);
                            }

                            WeightList = convertArrayList(strWeightList);
                            TimesList = convertArrayList(strTimesList);
                            SetList = convertArrayList(strSetList);
                            Log.d("Selected Exercise", "totalOver :" + WeightList);
                            Log.d("Selected Exercise", "totalOver :" + TimesList);
                            Log.d("Selected Exercise", "totalOver :" + SetList);

                            btnYes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(LoadExerciseActivity.this, StartExerciseActivity.class);
                                    intent.putExtra("timesList",TimesList);
                                    intent.putExtra("weightList",WeightList);
                                    intent.putExtra("exerciseList",EventList);
                                    intent.putExtra("userID",userID);
                                    intent.putExtra("Date",date);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                            displayRoutineComp();

                        } else {
                            Toast.makeText(getApplicationContext(), "정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            RoutineReadRequest routineReadRequest = new RoutineReadRequest(String.valueOf(routineNames.get((int)v.getId()-1)), userID, rResponseListener);
            RequestQueue queue = Volley.newRequestQueue(LoadExerciseActivity.this);
            queue.add(routineReadRequest);


            // delete
            btnDelete = findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Response.Listener<String> delResponseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");

                                if (success) {
                                    Button delBtn = findViewById(v.getId());
                                    mainLayout.removeView(delBtn);

                                    SlidingUpPanelLayout slidingLayout = findViewById(R.id.main_frame);
                                    slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

                                    Toast.makeText(getApplicationContext(), "루틴을 삭제 했습니다.", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getApplicationContext(), "루틴을 삭제하는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    RoutineDeleteRequest routineDeleteRequest = new RoutineDeleteRequest(String.valueOf(routineNames.get((int)v.getId()-1)), userID, delResponseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoadExerciseActivity.this);
                    queue.add(routineDeleteRequest);
                }
            });

        }
    };



    private void displayRoutineComp(){
        SlidingUpPanelLayout slidingLayout = findViewById(R.id.main_frame);
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);//COLLAPSED

        String routine = "";

        for(int i=0; i<EventList.size(); i++){
            routine += "\n" + EventList.get(i) + "\n\n";
            for(int j=0; j<SetList.get(i).size(); j++){
                routine += SetList.get(i).get(j) + "세트 " + WeightList.get(i).get(j) + "kg "
                        + TimesList.get(i).get(j) + "회\n";
            }
            routine += "\n";
        }

        TextView tv = findViewById(R.id.txtRoutineList);
        tv.setText(routine);

    }

    private static ArrayList<ArrayList<Integer>> convertArrayList(ArrayList<String> inputList) {
        ArrayList<ArrayList<Integer>> outputList = new ArrayList<>();

        for (String str : inputList) {
            String[] numbers = str.split(",");
            ArrayList<Integer> convertedList = new ArrayList<>();
            for (String number : numbers) {
                convertedList.add(Integer.parseInt(number.trim()));
            }
            outputList.add(convertedList);
        }

        return outputList;
    }

}