package com.example.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    AnalysisFragment analysisFragment;
    CalendarFragment calendarFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent memberIntent = getIntent();
        String userID = memberIntent.getStringExtra("userID");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        String userID = jsonObject.getString("userID");
                        String userName = jsonObject.getString("userName");
                    }else {
                        Toast.makeText(getApplicationContext(),"회원정보를 읽지 못했습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MemberRequest loginRequest = new MemberRequest(userID ,  responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(loginRequest);


        init();

        /** UserID Fragment로 전달 */

        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        bundle.putString("userID",userID);
        homeFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.main_layout,homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tab_home: {
                        Bundle bundle = new Bundle();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        bundle.putString("userID",userID);
                        homeFragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.main_layout,homeFragment).commit();
                        return true;
                    }
                    case R.id.tab_analysis: {
                        Bundle bundle = new Bundle();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        bundle.putString("userID",userID);
                        analysisFragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.main_layout,analysisFragment).commit();
                        return true;
                    }
                    case R.id.tab_calander: {
                        Bundle bundle = new Bundle();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        bundle.putString("userID",userID);
                        calendarFragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.main_layout,calendarFragment).commit();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /** 초기화 */
    private void init(){
        homeFragment = new HomeFragment();
        analysisFragment = new AnalysisFragment();
        calendarFragment = new CalendarFragment();

        bottomNavigationView = findViewById(R.id.main_navigator);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,homeFragment).commitAllowingStateLoss();
    }

    /** 뒤로가기 버튼 구현 */
    private long backKeyPressedTime = 0;
    private Toast toast;
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finishAffinity();
            System.runFinalization();
            System.exit(0);
            toast.cancel();
        }
    }

}