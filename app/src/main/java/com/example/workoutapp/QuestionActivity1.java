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
        String male = "남성";
        String female = "여성";

        btnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String previousActivityClassName = "QuestionActivity1";
                Intent intent = new Intent(QuestionActivity1.this, QuestionActivity2.class);
                intent.putExtra("previous_activity", previousActivityClassName);
                intent.putExtra("userID",userID);
                intent.putExtra("userGender",male);


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
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });

        String previousActivityClassName = getIntent().getStringExtra("previous_activity");

        //프로그래스바 애니메이션
        if("LoginActivity".equals(previousActivityClassName)){
            ProgressBar pb = findViewById(R.id.progressbarPercent);
            pb.setMax(100);
            pb.setProgress(0);
            ObjectAnimator animation = ObjectAnimator.ofInt(pb,"progress",0,25);
            animation.setDuration(1000);
            animation.start();
        }

        else{
            ProgressBar pb = findViewById(R.id.progressbarPercent);
            pb.setMax(100);
            pb.setProgress(0);
            ObjectAnimator animation = ObjectAnimator.ofInt(pb,"progress",50,25);
            animation.setDuration(1000);
            animation.start();
        }

    }
    private long backKeyPressedTime = 0;
    private Toast toast;
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finishAffinity();
            System.runFinalization();
            System.exit(0);
            toast.cancel();
        }
    }
}