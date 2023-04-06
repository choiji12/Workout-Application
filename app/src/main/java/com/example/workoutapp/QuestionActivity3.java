package com.example.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class QuestionActivity3 extends AppCompatActivity {

    //text 속성변경
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);

        Button btnBegginer = findViewById(R.id.btnBegginer);
        Button btnLow = findViewById(R.id.btnLow);
        Button btnMiddle = findViewById(R.id.btnMiddle);
        Button btnHigh = findViewById(R.id.btnHigh);
        Button btnMaster = findViewById(R.id.btnMaster);

        //글자 꾸미기
        Spannable spanBeginner = (Spannable) btnBegginer.getText();
        spanBeginner.setSpan(new RelativeSizeSpan(1.5f),2,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanBeginner.setSpan(new StyleSpan(Typeface.BOLD),2,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable spanLow = (Spannable) btnLow.getText();
        spanLow.setSpan(new RelativeSizeSpan(1.5f),2,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanLow.setSpan(new StyleSpan(Typeface.BOLD),2,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable spanMiddle = (Spannable) btnMiddle.getText();
        spanMiddle.setSpan(new RelativeSizeSpan(1.5f),2,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanMiddle.setSpan(new StyleSpan(Typeface.BOLD),2,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable spanHigh = (Spannable) btnHigh.getText();
        spanHigh.setSpan(new RelativeSizeSpan(1.5f),2,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanHigh.setSpan(new StyleSpan(Typeface.BOLD),2,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable spanMaster = (Spannable) btnMaster.getText();
        spanMaster.setSpan(new RelativeSizeSpan(1.5f),2,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanMaster.setSpan(new StyleSpan(Typeface.BOLD),2,4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        btnBegginer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String previousActivityClassName = "QuestionActivity3";
                Intent intent = new Intent(QuestionActivity3.this, QuestionActivity4.class);
                intent.putExtra("previous_activity", previousActivityClassName);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });

        btnLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String previousActivityClassName = "QuestionActivity3";
                Intent intent = new Intent(QuestionActivity3.this, QuestionActivity4.class);
                intent.putExtra("previous_activity", previousActivityClassName);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });

        btnMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String previousActivityClassName = "QuestionActivity3";
                Intent intent = new Intent(QuestionActivity3.this, QuestionActivity4.class);
                intent.putExtra("previous_activity", previousActivityClassName);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });

        btnHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String previousActivityClassName = "QuestionActivity3";
                Intent intent = new Intent(QuestionActivity3.this, QuestionActivity4.class);
                intent.putExtra("previous_activity", previousActivityClassName);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });

        btnMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String previousActivityClassName = "QuestionActivity3";
                Intent intent = new Intent(QuestionActivity3.this, QuestionActivity4.class);
                intent.putExtra("previous_activity", previousActivityClassName);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionActivity3.this,QuestionActivity2.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);
            }
        });

        //프로그래스바 애니메이션
        String previousActivityClassName = getIntent().getStringExtra("previous_activity");

        if("QuestionActivity2".equals(previousActivityClassName)) {
            ProgressBar pb = findViewById(R.id.progressbarPercent);
            pb.setMax(100);
            pb.setProgress(0);
            ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", 50, 75);
            animation.setDuration(1000);
            animation.start();
        }else{
            ProgressBar pb = findViewById(R.id.progressbarPercent);
            pb.setMax(100);
            pb.setProgress(0);
            ObjectAnimator animation = ObjectAnimator.ofInt(pb,"progress",100,75);
            animation.setDuration(1000);
            animation.start();
        }

    }

    private long backKeyPressedTime = 0;
    private Toast toast;
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            Intent intent = new Intent(QuestionActivity3.this,QuestionActivity2.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }
}