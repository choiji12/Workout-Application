package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SetExerciseActivity extends AppCompatActivity {

    private String userID;
    private String date;
    private ArrayList selectedExercise;
    private Typeface mainFont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_exercise);

        /** 이전 엑티비티에서 data 가져오기 */
        Intent intent = getIntent();
        userID = getIntent().getStringExtra("userID");
        date = getIntent().getStringExtra("Date");
        selectedExercise =(ArrayList) intent.getSerializableExtra("SelectedList");
        Collections.sort(selectedExercise);
        Log.d("Selected Exercise","Selected Exercise :" + selectedExercise);

        int exerciseLength = selectedExercise.size();
        LinearLayout exerciseLayout = findViewById(R.id.exerciseLayout);
        mainFont = getResources().getFont(R.font.jamsil_regular);


        /** 세트 수 설정하는 버튼 생성 */
        for (int i=0; i<exerciseLength; i++){
            Button btnSetExercise = new Button(this);
            /** 세트 수 설정하는 button ID는 101~ */
            btnSetExercise.setId((int) selectedExercise.get(i));



            /** 운동 이름 추가하시면 됩니다~ */
//            btnSetExercise.setText(String.valueOf(i));
            btnSetExercise.setTypeface(mainFont);
            btnSetExercise.setTextSize(15);
            btnSetExercise.setBackground(getResources().getDrawable(R.drawable.round_white_button_2));
            btnSetExercise.setTextColor(getResources().getColorStateList(R.drawable.white_to_blue_button_text));
            btnSetExercise.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams paramsExercise = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, (int) (80 * getResources().getDisplayMetrics().density));
            paramsExercise.setMargins(30, 0, 30, 130);
            btnSetExercise.setLayoutParams(paramsExercise);
            exerciseLayout.addView(btnSetExercise);
        }




        SlidingUpPanelLayout slidingUpPanelLayout = findViewById(R.id.main_frame);

        for (int i=0; i<exerciseLength; i++){
            int buttonId = (int) selectedExercise.get(i);
            //int buttonId = i + 101;
            Button btnSetExercise = (Button) findViewById(buttonId);
            btnSetExercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                }
            });
        }
        Response.Listener<String> infoResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");


                    if(success){
                        String eventExercise = jsonObject.getString("eventExercise");
                        String eventNo = jsonObject.getString("eventNo");

                        Button btnSetExercise = (Button) findViewById(Integer.parseInt(eventNo));

                        btnSetExercise.setText(eventExercise);
                        Log.d("user","uesrName" +eventExercise);
                        Log.d("user","uesrName" +eventNo);


                    }
                    else {

                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };
        for (int j = 0; j<exerciseLength; j++){
            InfoRequest infoRequest = new InfoRequest(Integer.toString((int) selectedExercise.get(j)), infoResponseListener);
            RequestQueue queue = Volley.newRequestQueue(SetExerciseActivity.this);
            queue.add(infoRequest);}
    }

    private void setSlidingUpLayout(LinearLayout linearLayout){
        int setCnt = 3;

        TextView txtExerciseName = findViewById(R.id.txtExerciseName);
        txtExerciseName.setText("운동 이름 추가해");

        LinearLayout contentLayout = new LinearLayout(this);
        contentLayout.setOrientation(LinearLayout.HORIZONTAL);
        contentLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_unchecked));

        for (int i=0; i<setCnt; i++){
            TextView txtSetCnt = new TextView(this);
            txtSetCnt.setText(setCnt + "세트");
            txtSetCnt.setTypeface(mainFont);
            txtSetCnt.setTextSize(15);

        }
    }


}