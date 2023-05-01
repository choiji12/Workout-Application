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

    private Button btnBegginer;
    private Button btnLow ;
    private Button btnMiddle ;
    private Button btnHigh ;
    private Button btnMaster ;

    private void setSpannableText(Spannable spannable, int start, int end) {
        spannable.setSpan(new RelativeSizeSpan(1.5f), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    //text 속성변경
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);

        btnBegginer = findViewById(R.id.btnBegginer);
        btnLow = findViewById(R.id.btnLow);
        btnMiddle = findViewById(R.id.btnMiddle);
        btnHigh = findViewById(R.id.btnHigh);
        btnMaster = findViewById(R.id.btnMaster);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userGender = intent.getStringExtra("userGender");
        String userLocation = intent.getStringExtra("userLocation");
        String userName = intent.getStringExtra("userName");
        String begginer = "입문";
        String low = "초급";
        String middle = "중급";
        String high = "고급";
        String master = "전문";

        //글자 꾸미기
        setSpannableText((Spannable) btnBegginer.getText(), 2, 2+begginer.length());
        setSpannableText((Spannable) btnLow.getText(), 2, 2+low.length());
        setSpannableText((Spannable) btnMiddle.getText(), 2, 2+middle.length());
        setSpannableText((Spannable) btnHigh.getText(), 2, 2+high.length());
        setSpannableText((Spannable) btnMaster.getText(), 2, 2+master.length());
        btnBegginer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String previousActivityClassName = "QuestionActivity3";
                Intent intent = new Intent(QuestionActivity3.this, QuestionActivity4.class);
                intent.putExtra("previous_activity", previousActivityClassName);
                intent.putExtra("userID",userID);
                intent.putExtra("userGender",userGender);
                intent.putExtra("userLocation",userLocation);
                intent.putExtra("userClass",begginer);
                intent.putExtra("userName",userName);
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
                intent.putExtra("userID",userID);
                intent.putExtra("userGender",userGender);
                intent.putExtra("userLocation",userLocation);
                intent.putExtra("userClass",low);
                intent.putExtra("userName",userName);
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
                intent.putExtra("userID",userID);
                intent.putExtra("userGender",userGender);
                intent.putExtra("userLocation",userLocation);
                intent.putExtra("userClass",middle);
                intent.putExtra("userName",userName);
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
                intent.putExtra("userID",userID);
                intent.putExtra("userGender",userGender);
                intent.putExtra("userLocation",userLocation);
                intent.putExtra("userClass",high);
                intent.putExtra("userName",userName);
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
                intent.putExtra("userID",userID);
                intent.putExtra("userGender",userGender);
                intent.putExtra("userLocation",userLocation);
                intent.putExtra("userClass",master);
                intent.putExtra("userName",userName);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionActivity3.this,QuestionActivity2.class);
                //getIntent().getExtras().remove(userID);
                //getIntent().getExtras().remove(userLocation);
                //getIntent().getExtras().remove(userGender);
                intent.putExtra("userID",userID);
                intent.putExtra("userGender",userGender);
                intent.putExtra("userName",userName);
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
            ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", 60, 80);
            animation.setDuration(1000);
            animation.start();
        }else{
            ProgressBar pb = findViewById(R.id.progressbarPercent);
            pb.setMax(100);
            pb.setProgress(0);
            ObjectAnimator animation = ObjectAnimator.ofInt(pb,"progress",100,80);
            animation.setDuration(1000);
            animation.start();
        }

    }

    private long backKeyPressedTime = 0;
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            //-------------------------------------------------------back intent
            Intent backintent = getIntent();
            String userID = backintent.getStringExtra("userID");
            String userName = backintent.getStringExtra("userName");
            String userGender = backintent.getStringExtra("userGender");
            //-------------------------------------------------------------------

            Intent intent = new Intent(QuestionActivity3.this,QuestionActivity2.class);
            intent.putExtra("userID",userID);
            intent.putExtra("userGender",userGender);
            intent.putExtra("userName",userName);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);
        }
    }
}