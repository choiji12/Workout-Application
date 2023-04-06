package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FindPwActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        Button btnFindPw = findViewById(R.id.btnFindPw);

        btnFindPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindPwActivity.this,ResetPassWdActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });
    }
}