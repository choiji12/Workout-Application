package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class LoadExerciseActivity extends AppCompatActivity {
    private Button btnYes;
    private Button btnNo;
    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_exercise);

        initView();

        for (int i=0; i<3; i++){
            Button exerciseName = new Button(this);
            /** 버튼의 ID는 1~ */
            exerciseName.setId(i+1);
            exerciseName.setText(i +"번째 운동");
            mainLayout.addView(exerciseName);
            exerciseName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SlidingUpPanelLayout slidingLayout = findViewById(R.id.main_frame);
                    slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);//COLLAPSED
                }
            });
        }

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

}