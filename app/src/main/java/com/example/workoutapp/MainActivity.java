package com.example.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;


//회원탈퇴 기능 구현 => mFirebaseAuth.getCurrentUser().delete();
public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    AnalysisFragment analysisFragment;
    CalendarFragment calendarFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tab_home: {
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.main_layout,new HomeFragment())
                                .commit();
                        return true;
                    }
                    case R.id.tab_analysis: {
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.main_layout,new AnalysisFragment())
                                .commit();
                        return true;
                    }
                    case R.id.tab_calander: {
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.main_layout,new CalendarFragment())
                                .commit();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void init(){
        homeFragment = new HomeFragment();
        analysisFragment = new AnalysisFragment();
        calendarFragment = new CalendarFragment();

        bottomNavigationView = findViewById(R.id.main_navigator);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,homeFragment).commitAllowingStateLoss();
    }

}