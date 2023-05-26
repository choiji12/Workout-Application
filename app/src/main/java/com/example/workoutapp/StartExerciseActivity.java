package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class StartExerciseActivity extends AppCompatActivity {

    private String userID;
    private String date;
    private ArrayList selectedExercise;
    private ArrayList<ArrayList<Integer>> totalWeightList;
    private ArrayList<ArrayList<Integer>> totalTimesList;

    private Button btnFinishSet;
    private TextView txtExerciseName;
    private LinearLayout mainLayout;
    private TextView txtExerciseTime;

    private ProgressBar pbTimer;
    private TextView txtRemainTime;
    private CountDownTimer countDownTimer;
    private SlidingUpPanelLayout slidingLayout;

    private int restTime;
    private int remainTime;

    private int exerciseLength;
    private int exerciseSequence=0;
    private int eachSetCount=0;
    private int exerciseFinishedCount=0;
    private int exerciseCount = 0;
    private int minutes = 0;
    private int seconds = 0;
    private Handler handler;
    private String newOrOld;
    private Typeface mainFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_exercise);

        /** 이전 엑티비티에서 데이터 호출 */
        getIntentData();

        /** 뷰 초기화 */
        initView();
        exerciseLength = selectedExercise.size();

        /** 운동 시작 뷰 추가 */
        addExerciseView(exerciseSequence);

        startExerciseTimer();

        /** 운동 완료버튼 눌렀을 때 동작 */
        btnFinishSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                startTimer(restTime,pbTimer);
                /** 세트 완료 시 동작 */
                finishSet(exerciseSequence);
                /** 완료한 세트 색깔 변경 */
                finishedSetDecorator();
                /** 모든 운동 완료 시 엑티비티 종료 */
                if(exerciseFinishedCount == exerciseLength) {
                    finishActivity();
                }
            }
        });
    }

    private void finishedSetDecorator(){
        if(eachSetCount != 0) {
            LinearLayout decoratedLayout = (LinearLayout) mainLayout.getChildAt(eachSetCount - 1);
            decoratedLayout.setBackgroundColor(getResources().getColor(R.color.blue));
            for(int i=0; i<3; i++) {
                TextView decoratedText = (TextView) decoratedLayout.getChildAt(i);
                decoratedText.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }


    private void removeViews(LinearLayout parent) {
        parent.removeAllViews();
    }

    private void finishSet(int exerciseSequenceFor){
        eachSetCount++;
        int setCount = totalWeightList.get(exerciseSequenceFor).size();
        if(eachSetCount == setCount){
            removeViews(mainLayout);
            eachSetCount = 0;
            exerciseFinishedCount ++;
            exerciseSequence ++;
            if(exerciseFinishedCount == exerciseLength) {
                return;
            }
            addExerciseView(exerciseSequence);
        }
    }

    private void getIntentData(){
        Intent intent = getIntent();
        userID = getIntent().getStringExtra("userID");
        date = getIntent().getStringExtra("Date");
        totalWeightList = (ArrayList) intent.getSerializableExtra("weightList");
        totalTimesList = (ArrayList) intent.getSerializableExtra("timesList");
        selectedExercise = (ArrayList) intent.getSerializableExtra("exerciseList");
        newOrOld = intent.getStringExtra("NewOrOld");
    }

    private void addExerciseView(int exerciseSequenceFor){
        txtExerciseName.setText((CharSequence) selectedExercise.get(exerciseSequenceFor));

        int setCount = totalWeightList.get(exerciseSequenceFor).size();
        for (int i=0; i<setCount; i++){
            LinearLayout contentsLayout = new LinearLayout(this);
            contentsLayout.setOrientation(LinearLayout.HORIZONTAL);
            contentsLayout.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView txtSetNum = new TextView(this);
            String setNumString = String.valueOf(i+1);
            txtSetNum.setText(setNumString + "세트");

            LinearLayout.LayoutParams paramsTxtSetNum = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsTxtSetNum.width = 150;  // 너비 설정 (픽셀 단위)
            paramsTxtSetNum.height = 110; // 높이 설정 (픽셀 단위)
            paramsTxtSetNum.setMargins(0,0,15,0);
            txtSetNum.setLayoutParams(paramsTxtSetNum);
            txtSetNum.setGravity(Gravity.CENTER_VERTICAL);

            txtSetNum.setTypeface(mainFont);
            txtSetNum.setTextColor(getResources().getColor(R.color.black));
            txtSetNum.setTextSize(17);

            TextView txtWeight = new TextView(this);
            int weight = totalWeightList.get(exerciseSequenceFor).get(i);
            String weightString = String.valueOf(weight);
            txtWeight.setText(weightString + "KG");

            LinearLayout.LayoutParams paramsTxtWeight = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsTxtWeight.width = 150;  // 너비 설정 (픽셀 단위)
            paramsTxtWeight.height = 110; // 높이 설정 (픽셀 단위)
            paramsTxtWeight.setMargins(0,0,15,0);
            txtWeight.setLayoutParams(paramsTxtWeight);
            txtWeight.setGravity(Gravity.CENTER_VERTICAL);

            txtWeight.setTypeface(mainFont);
            txtWeight.setTextColor(getResources().getColor(R.color.black));
            txtWeight.setTextSize(17);

            TextView txtTimes = new TextView(this);
            int times = totalTimesList.get(exerciseSequenceFor).get(i);
            String timesString = String.valueOf(times);
            txtTimes.setText(timesString + "회");

            LinearLayout.LayoutParams paramsTxtTimes = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsTxtTimes.width = 150;  // 너비 설정 (픽셀 단위)
            paramsTxtTimes.height = 110; // 높이 설정 (픽셀 단위)
            paramsTxtTimes.setMargins(0,0,15,0);
            txtTimes.setLayoutParams(paramsTxtTimes);
            txtTimes.setGravity(Gravity.CENTER_VERTICAL);

            txtTimes.setTypeface(mainFont);
            txtTimes.setTextColor(getResources().getColor(R.color.black));
            txtTimes.setTextSize(17);

            contentsLayout.addView(txtSetNum);
            contentsLayout.addView(txtWeight);
            contentsLayout.addView(txtTimes);

            /** 운동의 contentsLayout의 ID는 100~ */
            contentsLayout.setId(exerciseSequenceFor*100 +i );

            mainLayout.addView(contentsLayout);
        }

    }

    private void initView(){
        mainLayout = findViewById(R.id.mainLayout);
        txtExerciseName = findViewById(R.id.txtExerciseName);
        btnFinishSet = findViewById(R.id.btnFinishSet);
        txtExerciseTime = findViewById(R.id.txtExerciseTime);
        handler = new Handler(Looper.getMainLooper());
        pbTimer = findViewById(R.id.pbTimer);
        txtRemainTime = findViewById(R.id.txtRemainingTime);
        slidingLayout = findViewById(R.id.main_frame);
        mainFont = getResources().getFont(R.font.jamsil_regular);

        /** DB에서 휴식시간 받아와서 restTime에 할당 필요 */
        restTime = 60;  // 1000은 1초
    }

    private void startExerciseTimer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 1초마다 실행되는 작업
                seconds++;
                if (seconds == 60) {
                    minutes++;
                    seconds = 0;
                }
                String time = String.format("%02d:%02d", minutes, seconds);
                txtExerciseTime.setText("운동 시간 : " + time);
                handler.postDelayed(this, 1000); // 1초 후에 다시 실행
            }
        }, 1000); // 초기 1초 후에 실행
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 액티비티가 종료될 때 타이머 중지
        handler.removeCallbacksAndMessages(null);
    }

    public void finishButtonClick(View view){
        closeSliding();
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

    private void closeSliding(){
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        restTime = 60;
    }

    public int getRemainTime(int remainTime){
        return remainTime;
    }

    /** 타이머 설정 메소드 */
    private void startTimer(final int restTime, ProgressBar progressBar){

        txtRemainTime.setTextColor(getResources().getColor(R.color.blue));
        Drawable progressDrawableOrigin = ContextCompat.getDrawable(StartExerciseActivity.this, R.drawable.timer_progressbar);
        pbTimer.setProgressDrawable(progressDrawableOrigin);
        pbTimer.invalidate();

        if(countDownTimer != null){
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(restTime * 1000, 1000){
            @Override
            public void onTick(long millisUntilFinished){

                String timeLeft = String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                txtRemainTime.setText(timeLeft);

                remainTime = (int)TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                getRemainTime(remainTime);

                if(remainTime < 5){
                    txtRemainTime.setTextColor(Color.RED);
                    Drawable progressDrawable = ContextCompat.getDrawable(StartExerciseActivity.this, R.drawable.timer_progressbar_finished);

                    pbTimer.setProgressDrawable(progressDrawable);
                    pbTimer.invalidate();
                }

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
                // 알림음 울림
                MediaPlayer mediaPlayer = MediaPlayer.create(StartExerciseActivity.this,R.raw.timer_end);
                mediaPlayer.start();
                // 진동
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1000);
                /** 타이머가 종료되고 1초뒤에 자동으로 SlidingView Close */
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closeSliding();
                    }
                },1000);

            }
        }.start();

        countDownTimer.start();
    }

    private void finishActivity(){
        Intent intent = new Intent(StartExerciseActivity.this,ReviewExerciseActivity.class);
        intent.putExtra("timesList",totalTimesList);
        intent.putExtra("weightList",totalWeightList);
        intent.putExtra("exerciseList",selectedExercise);
        intent.putExtra("userID",userID);
        intent.putExtra("Date",date);
        intent.putExtra("ExerciseTime",String.valueOf(minutes));
        intent.putExtra("NewOrOld",newOrOld);
        startActivity(intent);
        finish();
    }
}