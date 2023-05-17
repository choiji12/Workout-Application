package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    private int exerciseLength;

    private LinearLayout exerciseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_exercise);
//co
        /** 이전 엑티비티에서 data 가져오기 */
        Intent intent = getIntent();
        userID = getIntent().getStringExtra("userID");
        date = getIntent().getStringExtra("Date");
        selectedExercise =(ArrayList) intent.getSerializableExtra("SelectedList");
        Log.d("Selected Exercise","Selected Exercise :" + selectedExercise);

        exerciseLength = selectedExercise.size();
        exerciseLayout = findViewById(R.id.exerciseLayout);
        mainFont = getResources().getFont(R.font.jamsil_regular);



        /** 세트 수 설정하는 버튼 생성 */
        for (int i=0; i<exerciseLength; i++){
            TextView txtExerciseName = new TextView(this);
            /** 운동이름 보여주는 textView ID는 1~ */
            txtExerciseName.setId((int) selectedExercise.get(i));

            /** 운동 이름 추가하시면 됩니다~ */
            txtExerciseName.setText(String.valueOf(i));
            txtExerciseName.setTypeface(mainFont);
            txtExerciseName.setTextSize(20);
//            txtExerciseName.setBackground(getResources().getDrawable(R.drawable.round_white_button_2));
            txtExerciseName.setTextColor(getResources().getColor(R.color.blue));
            txtExerciseName.setGravity(Gravity.LEFT);
            LinearLayout.LayoutParams paramsExercise = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsExercise.setMargins(0, 0, 30, 130);
            txtExerciseName.setLayoutParams(paramsExercise);
            exerciseLayout.addView(txtExerciseName);
        }

        /** 초기 생성 시에만 호출됌 */
        for (int i=1; i<=exerciseLength; i++) {
            exerciseLayout.addView(addContent(1), 1+(i-1)*2);
        }

        Response.Listener<String> infoResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if(success){
                        String eventExercise = jsonObject.getString("eventExercise");
                        String eventNO = jsonObject.getString("eventNo");
                        Log.d("user","userdd"+eventExercise);

                        TextView  txtExerciseName = (TextView)findViewById(Integer.parseInt(eventNO));

                        txtExerciseName.setText(eventExercise);
                    }
                    else {

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };
        for (int j =0; j<exerciseLength; j++){
            InfoRequest infoRequest = new InfoRequest(Integer.toString((int) selectedExercise.get(j)),infoResponseListener);
            RequestQueue queue = Volley.newRequestQueue(SetExerciseActivity.this);
            queue.add(infoRequest);
        }

    }

    private LinearLayout addContent(int setCnt){

        LinearLayout frameLayout = new LinearLayout(this);
        frameLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout contentsLayout = new LinearLayout(this);
        contentsLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView txtSetcnt = new TextView(this);
        txtSetcnt.setText(setCnt + "세트");
        contentsLayout.addView(txtSetcnt);

        EditText edtWeight = new EditText(this);
        edtWeight.setHint("KG");
        contentsLayout.addView(edtWeight);

        EditText edtTimes = new EditText(this);
        edtTimes.setHint("회");
        contentsLayout.addView(edtTimes);

        Button btnDeleteSet = new Button(this);
        btnDeleteSet.setText("세트 삭제");
        contentsLayout.addView(btnDeleteSet);

        btnDeleteSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lastIndex = frameLayout.getChildCount()-2;
                if (lastIndex >= 0) {
                    View lastChild = frameLayout.getChildAt(lastIndex);
                    frameLayout.removeView(lastChild);
                }
            }
        });

        Button btnAddSet = new Button(this);
        btnAddSet.setText("세트 추가");

        btnAddSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameLayout.removeView(btnAddSet);
                frameLayout.addView(addContent(setCnt +1));

            }
        });

        frameLayout.addView(contentsLayout);
        frameLayout.addView(btnAddSet);

        return frameLayout;
    }


}