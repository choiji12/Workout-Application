package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class SelectExercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);

        Typeface mainFont = getResources().getFont(R.font.jamsil_regular);
        int exerciseLength = 30;
        LinearLayout mainLayout = findViewById(R.id.exerciseLayout);

        for(int i=0; i<exerciseLength; i++){
            CheckBox chkExercise = new CheckBox(this);
            chkExercise.setId(i+1);
            chkExercise.setText("\t"+Integer.toString(i));
            chkExercise.setTextColor(getResources().getColorStateList(R.drawable.black_to_white_radio_text));
            chkExercise.setTypeface(mainFont);
            chkExercise.setTextSize(15);
            chkExercise.setBackground(getResources().getDrawable(R.drawable.rounded_grey_radiobutton));
            chkExercise.setButtonDrawable(android.R.color.transparent);

            //checkBox 크기 설정
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT,(int) (60 * getResources().getDisplayMetrics().density));
            params.setMargins(5,5,5,5);
            chkExercise.setLayoutParams(params);

            mainLayout.addView(chkExercise);
        }
    }
}