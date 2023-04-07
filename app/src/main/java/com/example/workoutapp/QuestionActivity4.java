package com.example.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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


        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userGender = intent.getStringExtra("userGender");
        String userLocation = intent.getStringExtra("userLocation");
        String userClass = intent.getStringExtra("userClass");


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName ="최지혁";
                String userBirthday = btnBirthday.getText().toString();
                double userWeight = Double.parseDouble(txtWeight.getText().toString());
                double userHeight = Double.parseDouble(txtHeight.getText().toString());
                double bmiuh = userHeight/100;
                double result = userWeight / Math.pow(bmiuh,2);
                String bmi = String.format("%.2f", result);
                double userBmi = Double.parseDouble(bmi);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),"회원등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(QuestionActivity4.this, StartActivity.class);
                                startActivity(intent);

                            }else {
                                Toast.makeText(getApplicationContext(),"회원등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(userID, userName, userGender,
                        userBirthday ,userWeight, userHeight, userLocation, userClass, userBmi, responseListener);
                RequestQueue queue = Volley.newRequestQueue(QuestionActivity4.this);
                queue.add(registerRequest);

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionActivity4.this,QuestionActivity3.class);
                //getIntent().getExtras().remove(userID);
                //getIntent().getExtras().remove(userLocation);
                //getIntent().getExtras().remove(userGender);
                //getIntent().getExtras().remove(userClass);
                intent.putExtra("userID",userID);
                intent.putExtra("userGender",userGender);
                intent.putExtra("userLocation",userLocation);
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
        //정보 입력 안됐으면 버튼 비활성화
        if(txtHeight.getText().toString().replace(" ","").equals("")||txtHeight.getText().equals("-")){
            btnSubmit.setEnabled(false);
        }

        else if(txtHeight.getText().toString().replace(" ","").equals("")||txtHeight.getText().equals("-")){
            btnSubmit.setEnabled(false);
        }
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