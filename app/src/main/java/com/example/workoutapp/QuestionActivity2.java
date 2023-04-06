package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class QuestionActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        ProgressBar pb = findViewById(R.id.progressbarPercent);
        pb.setProgress(40);

        Button btnGym = findViewById(R.id.btnGym);
        Button btnHome = findViewById(R.id.btnHome);
        btnGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionActivity2.this,QuestionActivity3.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionActivity2.this,QuestionActivity3.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });

    }
    private long backKeyPressedTime = 0;
    private Toast toast;
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            Intent intent = new Intent(QuestionActivity2.this,QuestionActivity1.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);
            backKeyPressedTime = System.currentTimeMillis();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }
}