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
import android.widget.EditText;
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
            /** 세트 수 설정하는 button ID는 1~ */
            btnSetExercise.setId(i + 1);

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

        for (int i=0; i<exerciseLength; i++){
            int buttonId = i + 1;
            Button btnSetExercise = (Button) findViewById(buttonId);
            btnSetExercise.setOnClickListener(slidingAction);
        }
    }

    /** btnExercise OnclickListener */
    View.OnClickListener slidingAction = new View.OnClickListener() {
        private boolean isFirstClick = true;
        private SlidingUpPanelLayout slidingUpPanelLayout;
        @Override
        public void onClick(View v) {
            int clickedId = v.getId();

            if(isFirstClick) {
                slidingUpPanelLayout = addSlidingUpPanel(clickedId);
                addSet(clickedId, 1);
                isFirstClick = false;
            }

            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);

        }
    };

    private SlidingUpPanelLayout addSlidingUpPanel(int clickedId) {

        ConstraintLayout mainConstraint = findViewById(R.id.mainConstraint);
        SlidingUpPanelLayout slidingUpPanelLayout = new SlidingUpPanelLayout(this);
        slidingUpPanelLayout.setId(clickedId + 100);

        int panelHeight = (int) (getResources().getDisplayMetrics().heightPixels * 0.7);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        slidingUpPanelLayout.setLayoutParams(layoutParams);

        Button btnClosePanel = new Button(this);
        btnClosePanel.setText("패널 닫기");
        btnClosePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            }
        });
        slidingUpPanelLayout.addView(btnClosePanel);


        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        mainConstraint.addView(slidingUpPanelLayout);
        return slidingUpPanelLayout;
    }

    private void addSet(int clickedId, int setCnt) {
        SlidingUpPanelLayout slidingUpPanelLayout = findViewById(clickedId + 100);
        if (slidingUpPanelLayout != null) {
            LinearLayout contentsLayout = new LinearLayout(this);
            contentsLayout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams contentsLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            contentsLayout.setLayoutParams(contentsLayoutParams);

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

            Button btnClosePanel = new Button(this);
            btnClosePanel.setText("패널 닫기");
            btnClosePanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                }
            });
            contentsLayout.addView(btnClosePanel);

            slidingUpPanelLayout.addView(contentsLayout);
        }
    }


//    private void addSet(int clickedId, int setCnt) {
//        SlidingUpPanelLayout slidingUpPanelLayout = findViewById(clickedId + 100);
//        if (slidingUpPanelLayout != null) {
//            LinearLayout contentsLayout = new LinearLayout(this);
//            contentsLayout.setOrientation(LinearLayout.HORIZONTAL);
//
//            LinearLayout.LayoutParams contentsLayoutParams = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//            );
//            contentsLayout.setLayoutParams(contentsLayoutParams);
//
//
//            TextView txtSetcnt = new TextView(this);
//            txtSetcnt.setText(setCnt + "세트");
//            contentsLayout.addView(txtSetcnt);
//
//            EditText edtWeight = new EditText(this);
//            edtWeight.setHint("KG");
//            contentsLayout.addView(edtWeight);
//
//            EditText edtTimes = new EditText(this);
//            edtTimes.setHint("회");
//            contentsLayout.addView(edtTimes);
//
//            Button btnDeleteSet = new Button(this);
//            btnDeleteSet.setText("세트 삭제");
//            contentsLayout.addView(btnDeleteSet);
//
//            Button btnClosePanel = new Button(this);
//            btnClosePanel.setText("패널 닫기");
//            btnClosePanel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
//                }
//            });
//            slidingUpPanelLayout.addView(btnClosePanel);
//
//            Button btnAddSet = new Button(this);
//            btnAddSet.setText("세트 추가하기");
//            LinearLayout.LayoutParams addButtonParams = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//            );
//            addButtonParams.weight = 1;
//            contentsLayout.addView(btnAddSet, addButtonParams);
//
//            slidingUpPanelLayout.addView(contentsLayout);
////            slidingUpPanelLayout.addView(btnAddSet);
//        }
//    }

}