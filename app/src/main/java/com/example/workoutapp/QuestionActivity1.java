package com.example.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class QuestionActivity1 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        Button btnMale = findViewById(R.id.btnMale);
        Button btnFemale = findViewById(R.id.btnFemale);
        Button btnEtc = findViewById(R.id.btnEtc);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userName = intent.getStringExtra("userName");
        String male = "남성";
        String female = "여성";
        String etc = "기타";

        btnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String previousActivityClassName = "QuestionActivity1";
                Intent intent = new Intent(QuestionActivity1.this, QuestionActivity2.class);
                intent.putExtra("previous_activity", previousActivityClassName);
                intent.putExtra("userID",userID);
                intent.putExtra("userGender",male);
                intent.putExtra("userName",userName);


                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });

        btnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String previousActivityClassName = "QuestionActivity1";
                Intent intent = new Intent(QuestionActivity1.this, QuestionActivity2.class);
                intent.putExtra("previous_activity", previousActivityClassName);
                intent.putExtra("userID",userID);
                intent.putExtra("userGender",female);
                intent.putExtra("userName",userName);

                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });

        btnEtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String previousActivityClassName = "QuestionActivity1";
                Intent intent = new Intent(QuestionActivity1.this, QuestionActivity2.class);
                intent.putExtra("previous_activity", previousActivityClassName);
                intent.putExtra("userID",userID);
                intent.putExtra("userGender",etc);
                intent.putExtra("userName",userName);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DB 입력 수정해야 됌
                Intent intent = new Intent(QuestionActivity1.this,QuestionActivity0.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);

            }
        });

        String previousActivityClassName = getIntent().getStringExtra("previous_activity");

        //프로그래스바 애니메이션
        if("QuestionActivity0".equals(previousActivityClassName)){
            ProgressBar pb = findViewById(R.id.progressbarPercent);
            pb.setMax(100);
            pb.setProgress(0);
            ObjectAnimator animation = ObjectAnimator.ofInt(pb,"progress",20,40);
            animation.setDuration(1000);
            animation.start();
        }

        else{
            ProgressBar pb = findViewById(R.id.progressbarPercent);
            pb.setMax(100);
            pb.setProgress(0);
            ObjectAnimator animation = ObjectAnimator.ofInt(pb,"progress",60,40);
            animation.setDuration(1000);
            animation.start();
        }

    }
    private long backKeyPressedTime = 0;
    public void onBackPressed() {

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            Intent backintent = getIntent();
            String userID = backintent.getStringExtra("userID");

            Intent intent = new Intent(QuestionActivity1.this,QuestionActivity0.class);
            intent.putExtra("userID",userID);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);
            backKeyPressedTime = System.currentTimeMillis();
            return;
        }
    }
}