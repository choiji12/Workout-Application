package com.example.workoutapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView txtToday;
    private TextView welcome;
    private Button btnExercise;
    private int selectedyear;
    private int selectedmonth;
    private int seletedday;
    private LocalDate dateFor;
    private String userID;
    private TextView userWeight;
    private TextView userBmi;
    private TextView userVolume;
    private TextView changeWeight;
    private TextView changeBmi;
    private TextView changeVolume;
    private ImageButton btnSetting;
    private AdView adView;

    private TextView volumee;

    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        welcome = view.findViewById(R.id.welcome);
        txtToday = view.findViewById(R.id.txtToday);
        btnExercise = view.findViewById(R.id.btnExerciseToday);
        volumee = view.findViewById(R.id.textView77);

        userWeight = view.findViewById(R.id.txtUserWeight);
        userBmi = view.findViewById(R.id.txtUserBMI);
        userVolume = view.findViewById(R.id.txtUserCalory);

        changeWeight = view.findViewById(R.id.txtUserWeightChange);
        changeBmi = view.findViewById(R.id.txtUserBMIChange);
        changeVolume = view.findViewById(R.id.txtUserCaloryChange);

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // txtToday 꾸미는 함수
        volumee.setText("Volume");

        setTxtToday();

        /** MainActivty에서 넘긴 UserID Fragment에서 호출 및 할당 */
        Bundle bundle = getArguments();
        if (bundle !=null) {
            userID = bundle.getString("userID");
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        String userID = jsonObject.getString("userID");
                        String userName = jsonObject.getString("userName");
                        String userOldWeight = jsonObject.getString("userOldWeight");
                        String userNewWeight = jsonObject.getString("userNewWeight");
                        String userOldVolume = jsonObject.getString("userOldVolume");
                        String userNewVolume = jsonObject.getString("userNewVolume");
                        String userHeight = jsonObject.getString("userHeight");
                        String userOldBmi = jsonObject.getString("userOldBmi");
                        String userNewBmi = jsonObject.getString("userNewBmi");

                        Log.d("user ID","User ID :" + userName);

                        welcome.setText(userName+"님 화이팅!!!");
                        userWeight.setText(userNewWeight);
                        userVolume.setText(userNewVolume);

                        float newWeight = Float.parseFloat(userNewWeight);
                        float oldWeight = Float.parseFloat(userOldWeight);
                        float Height = Float.parseFloat(userHeight);
                        int newVolume = Integer.parseInt(userNewVolume);
                        int oldVolume = Integer.parseInt(userOldVolume);
                        float newBmi = Float.parseFloat(userNewBmi);
                        float oldBmi = Float.parseFloat(userOldBmi);




                        userBmi.setText(userNewBmi);

                        float weight = newWeight - oldWeight;
                        weight = Math.round(weight * 100) / 100f;
                        if(weight > 0){
                            changeWeight.setText(Float.toString(weight) + "KG ▲️️");
                            changeWeight.setTextColor(Color.RED);
                        } else if(weight == 0){
                            changeWeight.setText(Float.toString(weight) + "KG --️");
                            changeWeight.setTextColor(Color.BLACK);
                        }else{
                            changeWeight.setText(Float.toString(weight) + "KG ▼️️");
                            changeWeight.setTextColor(getResources().getColor(R.color.blue));
                            changeWeight.setTextColor(Color.RED);
                        }

                        int volume = newVolume - oldVolume;
                        if(volume > 0){
                            changeVolume.setText(Integer.toString(volume) + "KG ▲️️");
                            changeVolume.setTextColor(Color.RED);
                        } else if(volume == 0){
                            changeVolume.setText(Float.toString(volume) + "KG --️");
                            changeVolume.setTextColor(Color.BLACK);
                        }else{
                            changeVolume.setText(Integer.toString(Math.abs(volume)) + "KG ▼️️");
                            changeVolume.setTextColor(getResources().getColor(R.color.blue));
                        }

                        float bmi = newBmi - oldBmi;
                        bmi = Math.round(bmi * 100) / 100f;

                        if(bmi > 0){
                            changeBmi.setText(Float.toString(bmi) + " ▲️️");
                            changeBmi.setTextColor(Color.RED);
                        } else if(bmi == 0){
                            changeBmi.setText(Float.toString(bmi) + " --️");
                            changeBmi.setTextColor(Color.BLACK);
                        }else{
                            changeBmi.setText(Float.toString(bmi) + " ▼️️");
                            changeBmi.setTextColor(getResources().getColor(R.color.blue));

                        }


                    }else {

                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        MemberRequest loginRequest = new MemberRequest(userID ,  responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(loginRequest);



        /** 오늘 운동하기 버튼 누르면, 오늘 날짜 Intent로 넘어감 */
        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate today = LocalDate.now();
                Intent intent = new Intent(getActivity(),CreatePlanActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("Date",today.toString());
                startActivity(intent);
//                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);

            }
        });


        btnSetting=view.findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SettingActivity.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });
        return view;
    }

    /** txtToday에 오늘 날짜 보이게 하고, 요일만 다른 색깔로 보이게 함 */
    private void setTxtToday(){
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd.");
        String dayOfWeekInKorean = now.getDayOfWeek().getDisplayName(
                TextStyle.FULL,
                Locale.KOREAN
        );
        String todayText = now.format(formatter) + " " + dayOfWeekInKorean ;

        SpannableString spannableString = new SpannableString(todayText);
        int spanStartIndex = todayText.lastIndexOf(dayOfWeekInKorean);
        int spanEndIndex =  spanStartIndex + dayOfWeekInKorean.length();
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(getActivity(),R.color.blue));
        spannableString.setSpan(foregroundColorSpan, spanStartIndex, spanEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtToday.setText(spannableString);
    }

}