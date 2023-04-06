package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
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

        ProgressBar pb = findViewById(R.id.progressbarPercent);
        pb.setProgress(20);

        Button btnMale = findViewById(R.id.btnMale);
        Button btnFemale = findViewById(R.id.btnFemale);
        Button btnEtc = findViewById(R.id.btnEtc);

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    //첫번째 버튼 행동
                    case R.id.btnMale:
                        Intent intent1 = new Intent(QuestionActivity1.this, QuestionActivity2.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
                        break;
                    //두번째 버튼 행동
                    case R.id.btnFemale:
                        Intent intent2 = new Intent(QuestionActivity1.this, QuestionActivity2.class);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
                        break;
                    case R.id.btnEtc:
                        Intent intent3 = new Intent(QuestionActivity1.this, QuestionActivity2.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
                        break;
                }
            }
        };

        btnMale.setOnClickListener(onClickListener);
        btnFemale.setOnClickListener(onClickListener);
        btnEtc.setOnClickListener(onClickListener);
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

//    ProgressBar progressBar = findViewById(R.id.progressbarPercent);
//    ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
//    animation
//    animation.setInterpolator(new DecelerateInterpolator());
//    animation.start();
}