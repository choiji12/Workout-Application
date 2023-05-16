package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SetExerciseActivity extends AppCompatActivity {

    private String userID;
    private String date;
    private ArrayList selectedExercise;
    private Typeface mainFont;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private int exerciseLength;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_exercise);

        /** 이전 엑티비티에서 data 가져오기 */
        Intent intent = getIntent();
        userID = getIntent().getStringExtra("userID");
        date = getIntent().getStringExtra("Date");
        selectedExercise =(ArrayList) intent.getSerializableExtra("SelectedList");
        Log.d("Selected Exercise","Selected Exercise :" + selectedExercise);

        exerciseLength = selectedExercise.size();
        LinearLayout exerciseLayout = findViewById(R.id.exerciseLayout);
        mainFont = getResources().getFont(R.font.jamsil_regular);


        /** 세트 수 설정하는 버튼 생성 */
        for (int i=0; i<exerciseLength; i++){
            Button btnSetExercise = new Button(this);
            /** 세트 수 설정하는 button ID는 101~ */
            btnSetExercise.setId(i + 101);

            /** 운동 이름 추가하시면 됩니다~ */
            btnSetExercise.setText(String.valueOf(i));
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

        slidingUpPanelLayout = findViewById(R.id.main_frame);

        for (int i=0; i<exerciseLength; i++){
            int buttonId = i + 101;
            Button btnSetExercise = (Button) findViewById(buttonId);
            btnSetExercise.setOnClickListener(slidingAction);
        }
    }

    /** btnExercise OnclickListener */
    View.OnClickListener slidingAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        }
    };

    private void initSlidingUp(){
        for (int i=0; i<exerciseLength; i++){
            int buttonId = i + 101;
            Button btnSetExercise = (Button) findViewById(buttonId);
            TextView txtExerciseName = findViewById(R.id.txtExerciseName);
            txtExerciseName.setText(btnSetExercise.getText());


            LinearLayout contentsLayout = new LinearLayout(this);
            contentsLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView txtSetCnt = new TextView();

        }
    }


}