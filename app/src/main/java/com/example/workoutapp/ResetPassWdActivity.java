package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ResetPassWdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass_wd);

        Button btnReset = findViewById(R.id.btnResetPw);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResetPassWdActivity.this,StartActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(ResetPassWdActivity.this,"비밀번호가 초기화 되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}