package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;

public class TimerActivity extends AppCompatActivity {
    private ProgressBar pbTimer;
    private TextView txtRemainTime;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        pbTimer = findViewById(R.id.pbTimer);
        txtRemainTime = findViewById(R.id.txtRemainingTime);

        final long restTime = 1000 * 3;  // 1000은 1초
        pbTimer.setMax(100);
        pbTimer.setProgress(100);
//        ObjectAnimator animation = ObjectAnimator.ofInt(pbTimer,"progress",100,0);
//        animation.setDuration(restTime);
//        animation.start();

        countDownTimer = new CountDownTimer(restTime, 1000){
            @Override
            public void onTick(long millisUntilFinished){
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                seconds = seconds % 60;
                String time = String.format("%02d:%02d", minutes, seconds);
                txtRemainTime.setText(time);

                int progress = (int) ((millisUntilFinished * 100) / restTime);
                pbTimer.setProgress(progress);
            }
            @Override
            public void onFinish(){
                txtRemainTime.setTextColor(Color.RED);
//                pbTimer.setProgressDrawable(R.color.red);
//                알림음 코드... R.raw에 파일을 추가해야함
//                MediaPlayer mediaPlayer = MediaPlayer.create(TimerActivity.this,R.raw.alarmsound);
//                mediaPlayer.start();
            }
        };

        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // CountDownTimer 객체 해제
        countDownTimer.cancel();
    }
//    private void updateUI() {
//        int progress = (int) (restTime * 100 / (1000 * 60));  // 남은 시간의 백분율을 계산
//        pbTimer.setProgress(progress);
//
//        int seconds = (int) (restTime / 1000) % 60;
//        int minutes = (int) (restTime / (1000 * 60));
//        String remainTime = String.format("%d:%02d", minutes, seconds);
//        txtRemainTime.setText(remainTime);
//    }

}