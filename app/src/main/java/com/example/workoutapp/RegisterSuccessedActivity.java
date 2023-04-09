package com.example.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

public class RegisterSuccessedActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_successed);

        LottieAnimationView aniRegisterSuccess = findViewById(R.id.aniRegisterSuccess);
        aniRegisterSuccess.setAnimation(R.raw.register_success);
        aniRegisterSuccess.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 0.5초 뒤에 애니메이션 시작
                aniRegisterSuccess.playAnimation(); // 애니메이션 시작

                // 애니메이션의 리스너 설정
                aniRegisterSuccess.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        aniRegisterSuccess.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(RegisterSuccessedActivity.this,StartActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 1000); // 1초(1000ms) 후에 실행되도록 설정
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {                    }
                });
            }
        }, 500); // 0.5초(500ms) 후에 실행되도록 설정
    }
}