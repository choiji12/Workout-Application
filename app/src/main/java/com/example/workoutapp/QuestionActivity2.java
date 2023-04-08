package com.example.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class QuestionActivity2 extends AppCompatActivity {

//    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        Button btnGym = findViewById(R.id.btnGym);
        Button btnHome = findViewById(R.id.btnHome);

        btnGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String previousActivityClassName = "QuestionActivity2";
                Intent intent = new Intent(QuestionActivity2.this, QuestionActivity3.class);
                intent.putExtra("previous_activity", previousActivityClassName);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String previousActivityClassName = "QuestionActivity2";
                Intent intent = new Intent(QuestionActivity2.this, QuestionActivity3.class);
                intent.putExtra("previous_activity", previousActivityClassName);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });


        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionActivity2.this,QuestionActivity1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);
            }
        });

        //프로그래스바 애니메이션
        String previousActivityClassName = getIntent().getStringExtra("previous_activity");

        if("QuestionActivity1".equals(previousActivityClassName)) {
            ProgressBar pb = findViewById(R.id.progressbarPercent);
            pb.setMax(100);
            pb.setProgress(0);
            ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", 25, 50);
            animation.setDuration(1000);
            animation.start();
        }else{
            ProgressBar pb = findViewById(R.id.progressbarPercent);
            pb.setMax(100);
            pb.setProgress(0);
            ObjectAnimator animation = ObjectAnimator.ofInt(pb,"progress",75,50);
            animation.setDuration(1000);
            animation.start();
        }
    }

    private long backKeyPressedTime = 0;
    public void onBackPressed() {

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            Intent intent = new Intent(QuestionActivity2.this,QuestionActivity1.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);
            backKeyPressedTime = System.currentTimeMillis();
            return;
        }
    }
}