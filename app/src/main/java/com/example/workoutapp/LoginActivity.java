package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnResister = findViewById(R.id.btnRegister);
        btnResister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 버튼 누르면 회원가입 엑티비티로 이동
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
    }
}