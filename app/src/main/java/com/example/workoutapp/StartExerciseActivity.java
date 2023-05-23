package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
            decoratedLayout.setBackgroundColor(Color.BLUE);
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
    }

    private void addExerciseView(int exerciseSequenceFor){
        txtExerciseName.setText((CharSequence) selectedExercise.get(exerciseSequenceFor));

        Log.d("First Set","First Set Cnt" + selectedExercise);

        int setCount = totalWeightList.get(exerciseSequenceFor).size();
        Log.d("First Set","First Set Cnt" + setCount);
        for (int i=0; i<setCount; i++){
            LinearLayout contentsLayout = new LinearLayout(this);
            contentsLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView txtSetNum = new TextView(this);
            String setNumString = String.valueOf(i+1);
            txtSetNum.setText(setNumString + "세트");

            TextView txtWeight = new TextView(this);
            int weight = totalWeightList.get(exerciseSequenceFor).get(i);
            String weightString = String.valueOf(weight);
            txtWeight.setText(weightString + "KG");

            TextView txtTimes = new TextView(this);
            int times = totalTimesList.get(exerciseSequenceFor).get(i);
            String timesString = String.valueOf(times);
            txtTimes.setText(timesString + "회");

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
//                알림음 코드... R.raw에 파일을 추가해야함
//                MediaPlayer mediaPlayer = MediaPlayer.create(TimerActivity.this,R.raw.alarmsound);
//                mediaPlayer.start();
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
//                txtRemainTime.setTextColor(Color.RED);
////                알림음 코드... R.raw에 파일을 추가해야함
////                MediaPlayer mediaPlayer = MediaPlayer.create(TimerActivity.this,R.raw.alarmsound);
////                mediaPlayer.start();
//                Drawable progressDrawable = ContextCompat.getDrawable(StartExerciseActivity.this, R.drawable.timer_progressbar_finished);
//
//                pbTimer.setProgressDrawable(progressDrawable);
//                pbTimer.invalidate();
                /** 타이머가 종료되고 1초뒤에 자동으로 Intent 실행 */
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
        startActivity(intent);
        finish();
    }
}