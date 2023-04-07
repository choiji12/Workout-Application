package com.example.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Calendar;

public class QuestionActivity4 extends AppCompatActivity {
    Button btnBirthday;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question4);

        btnBirthday = findViewById(R.id.btnBirthday);
        // 캘린더 팝업 띄우기
        btnBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                // 년월일 저장 변수, month는 0부터 시작해서 +1해서 저장해야 됌
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(QuestionActivity4.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                btnBirthday.setText(year + "년" + (month+1) + "월" +dayOfMonth + "일");
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        EditText txtWeight = findViewById(R.id.txtWeight);
        EditText txtHeight = findViewById(R.id.txtHeight);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionActivity4.this,QuestionActivity3.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);
            }
        });

        //프로그래스바 애니메이션
        String previousActivityClassName = getIntent().getStringExtra("previous_activity");

        if("QuestionActivity3".equals(previousActivityClassName)) {
            ProgressBar pb = findViewById(R.id.progressbarPercent);
            pb.setMax(100);
            pb.setProgress(0);
            ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", 75, 100);
            animation.setDuration(1000);
            animation.start();
        }

        //edittext 실수값만 입력 가능하게
        txtHeight.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        txtWeight.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        txtHeight.setKeyListener(new DigitsKeyListener(false,true){
            @Override
            public int getInputType(){
                return InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL;
            }
            @Override
            protected char[] getAcceptedChars(){
                return new char[]{'.', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
            }
        });
        txtWeight.setKeyListener(new DigitsKeyListener(false,true){
            @Override
            public int getInputType(){
                return InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL;
            }
            @Override
            protected char[] getAcceptedChars(){
                return new char[]{'.', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
            }
        });

        //정보 입력 안됐으면 버튼 비활성화
        Drawable enabledButtonBackground = ResourcesCompat.getDrawable(getResources(),R.drawable.roundshape_white_button,null);
        Drawable disnabledButtonBackground = ResourcesCompat.getDrawable(getResources(),R.drawable.roundshape_grey_button,null);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 문자열 비교를 위한 String 형식의 체중과 키
                String weightString = txtWeight.getText().toString();
                String heightString = txtHeight.getText().toString();
                btnSubmit.setBackground(disnabledButtonBackground);
                // "-"이거나 공백인 경우에 버튼 비활성화
                if (weightString.equals("-") || heightString.equals("-") || weightString.trim().isEmpty() || heightString.trim().isEmpty()) {
                    btnSubmit.setEnabled(false);
                    btnSubmit.setBackground(disnabledButtonBackground);
                } else {
                    btnSubmit.setEnabled(true);
                    btnSubmit.setBackground(enabledButtonBackground);
                    btnSubmit.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                    // btnSubmit 클릭 시 버튼 background 변경
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            btnSubmit.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                        }
                    });
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {       }
        };

        txtWeight.addTextChangedListener(textWatcher);
        txtHeight.addTextChangedListener(textWatcher);

        btnSubmit.setEnabled(false);
        // btnSubmit 클릭 시 textcolor 변경
        btnSubmit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (btnSubmit.isEnabled()) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        btnSubmit.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                        btnSubmit.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                    }
                }
                return false;
            }
        });
    }
    private long backKeyPressedTime = 0;
    private Toast toast;
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            Intent intent = new Intent(QuestionActivity4.this,QuestionActivity3.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);
            backKeyPressedTime = System.currentTimeMillis();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
        }
    }
}