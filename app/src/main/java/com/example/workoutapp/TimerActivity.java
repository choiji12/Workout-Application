package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity {
    private ProgressBar pbTimer;
    private TextView txtRemainTime;
    private CountDownTimer countDownTimer;

    private int restTime;
    private int remainTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        pbTimer = findViewById(R.id.pbTimer);
        txtRemainTime = findViewById(R.id.txtRemainingTime);

        /** DB에서 휴식시간 받아와서 restTime에 할당 필요 */
        restTime = 8;  // 1000은 1초

        startTimer(restTime,pbTimer);
    }

    /** btnFinish 이벤트 리스너 */
    public void finishButtonClick(View view){
        Intent intent = new Intent(TimerActivity.this,SelectExercise.class);
        startActivity(intent);
        finish();
    }

    /** btnPlus 이벤트 리스너 */
    public void onPlusClick(View view){
        restTime = remainTime + 11;
        countDownTimer.cancel();
        startTimer(restTime,pbTimer);
    }

    /** btnMinus 이벤트 리스너 */
    public void onMinusClick(View view) {
        if (restTime > 10) {
            restTime = remainTime - 9;
            countDownTimer.cancel(); // 기존 타이머 취소
            startTimer(restTime,pbTimer);
        } else {
            restTime = 1;
            countDownTimer.cancel();
            startTimer(restTime,pbTimer);
        }
    }

    public int getRemainTime(int remainTime){
        return remainTime;
    }

    /** 타이머 설정 메소드 */
    private void startTimer(final int restTime,ProgressBar progressBar){

        if(countDownTimer != null){
            countDownTimer.cancel();
        }


        countDownTimer = new CountDownTimer(restTime * 1000, 1000){
            @Override
            public void onTick(long millisUntilFinished){
                int progress = restTime - (int) (millisUntilFinished / 1000); // 프로그레스바를 위한 값 계산
                progressBar.setProgress(progress);
                progressBar.setSecondaryProgress(progress + 1);
                String timeLeft = String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                txtRemainTime.setText(timeLeft);

                remainTime = (int)TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                getRemainTime(remainTime);

                progressBar.setMax(restTime);
                progressBar.setProgress(0);
                progressBar.setSecondaryProgress(0);
                ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", restTime);
                animation.setDuration(restTime * 1000);
                animation.setInterpolator(new LinearInterpolator());
                animation.start();

            }
            @Override
            public void onFinish(){
                txtRemainTime.setTextColor(Color.RED);
//                알림음 코드... R.raw에 파일을 추가해야함
//                MediaPlayer mediaPlayer = MediaPlayer.create(TimerActivity.this,R.raw.alarmsound);
//                mediaPlayer.start();
                Drawable progressDrawable = ContextCompat.getDrawable(TimerActivity.this, R.drawable.timer_progressbar_finished);

                pbTimer.setProgressDrawable(progressDrawable);
                pbTimer.invalidate();

                /** 타이머가 종료되고 1초뒤에 자동으로 Intent 실행 */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(TimerActivity.this,SelectExercise.class);
                        startActivity(intent);
                        finish();
                    }
                },1000);
            }
        }.start();

        countDownTimer.start();
    }

}