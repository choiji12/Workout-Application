package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SettingActivity extends AppCompatActivity {


    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getIntentData();
        Log.d("USER","USERID =="+userID);
    }

    private void getIntentData(){
        Intent idIntent = getIntent();
        userID = idIntent.getStringExtra("userID");
    }
}