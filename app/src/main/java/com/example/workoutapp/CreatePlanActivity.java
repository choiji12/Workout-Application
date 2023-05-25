package com.example.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreatePlanActivity extends AppCompatActivity {

    private MaterialCalendarView calendar;
    private int selectedyear;
    private int selectedmonth;
    private int seletedday;

    private LocalDate dateForIntent;
    private String userID;


    private String selectedDate;
    private int year;
    private int month;
    private int day;

    private LocalDate calenderDate;
    private Button btnLoadPlan;
    RatingBar rateExercise;
    private float star;

    private void todayWidgetVisible(){
        Button btnCreatePlan = findViewById(R.id.btnCreatePlan);
        Button btnLoadPlan = findViewById(R.id.btnLoadPlan);
        Button btnRest = findViewById(R.id.btnRest);
        TextView textView18 = findViewById(R.id.textView18);
        TextView textView19 = findViewById(R.id.textView19);
        TextView textView20 = findViewById(R.id.textView20);

        btnCreatePlan.setVisibility(View.VISIBLE);
        btnLoadPlan.setVisibility(View.VISIBLE);
        btnRest.setVisibility(View.VISIBLE);
        textView18.setVisibility(View.VISIBLE);
        textView19.setVisibility(View.VISIBLE);
        textView20.setVisibility(View.VISIBLE);
    }

    private void todayWidgetGone(){
        Button btnCreatePlan = findViewById(R.id.btnCreatePlan);
        Button btnLoadPlan = findViewById(R.id.btnLoadPlan);
        Button btnRest = findViewById(R.id.btnRest);
        TextView textView18 = findViewById(R.id.textView18);
        TextView textView19 = findViewById(R.id.textView19);
        TextView textView20 = findViewById(R.id.textView20);

        btnCreatePlan.setVisibility(View.GONE);
        btnLoadPlan.setVisibility(View.GONE);
        btnRest.setVisibility(View.GONE);
        textView18.setVisibility(View.GONE);
        textView19.setVisibility(View.GONE);
        textView20.setVisibility(View.GONE);
    }

    private void pastAndFutureGone(){
        RatingBar reteExercise = findViewById(R.id.rateExercise);
        TextView textView23 = findViewById(R.id.textView23);
        TextView textView24 = findViewById(R.id.textView24);
        TextView textView25 = findViewById(R.id.textView25);
        TextView textView26 = findViewById(R.id.textView26);
        TextView textView27 = findViewById(R.id.textView27);
        TextView textView28 = findViewById(R.id.textView28);
        ScrollView scrollView = findViewById(R.id.scrollView2);

        reteExercise.setVisibility(View.GONE);
        textView23.setVisibility(View.GONE);
        textView24.setVisibility(View.GONE);
        textView25.setVisibility(View.GONE);
        textView26.setVisibility(View.GONE);
        textView27.setVisibility(View.GONE);
        textView28.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);

//        textView23.setText("");
//        textView25.setText("");
//        textView27.setText("");
    }

    private void pastGoneFutureVisible(){
        RatingBar reteExercise = findViewById(R.id.rateExercise);
        TextView textView23 = findViewById(R.id.textView23);
        TextView textView24 = findViewById(R.id.textView24);
        TextView textView25 = findViewById(R.id.textView25);
        TextView textView26 = findViewById(R.id.textView26);
        TextView textView27 = findViewById(R.id.textView27);
        ScrollView scrollView = findViewById(R.id.scrollView2);

        reteExercise.setVisibility(View.GONE);
        textView23.setVisibility(View.GONE);
        textView24.setVisibility(View.GONE);
        textView25.setVisibility(View.GONE);
        textView26.setVisibility(View.GONE);
        textView27.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);


        TextView textView28 = findViewById(R.id.textView28);
        textView28.setVisibility(View.VISIBLE);

//        textView23.setText("");
//        textView25.setText("");
//        textView27.setText("");
    }
    private void pastVisibleFutureGone(){
        RatingBar reteExercise = findViewById(R.id.rateExercise);
        TextView textView23 = findViewById(R.id.textView23);
        TextView textView24 = findViewById(R.id.textView24);
        TextView textView25 = findViewById(R.id.textView25);
        TextView textView26 = findViewById(R.id.textView26);
        TextView textView27 = findViewById(R.id.textView27);
        ScrollView scrollView = findViewById(R.id.scrollView2);

        reteExercise.setVisibility(View.VISIBLE);
        textView23.setVisibility(View.VISIBLE);
        textView24.setVisibility(View.VISIBLE);
        textView25.setVisibility(View.VISIBLE);
        textView26.setVisibility(View.VISIBLE);
        textView27.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);

        TextView textView28 = findViewById(R.id.textView28);
        textView28.setVisibility(View.GONE);

//        textView23.setText("");
//        textView25.setText("");
//        textView27.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        rateExercise = findViewById(R.id.rateExercise);

        calendar = findViewById(R.id.weekCalendar);
        btnLoadPlan = findViewById(R.id.btnLoadPlan);

        /** 이전 프레그먼트에서 데이터 가져오기,  */
        userID = getIntent().getStringExtra("userID");
        String dateFor = getIntent().getStringExtra("Date");
        String[] dateArray = dateFor.split("-");
        selectedyear = Integer.parseInt(dateArray[0]);
        selectedmonth = Integer.parseInt(dateArray[1]);
        seletedday = Integer.parseInt(dateArray[2]);

        // 달력에 표시하기 위해 CalendarDay 변수로 변환
        CalendarDay date = CalendarDay.from(selectedyear, selectedmonth, seletedday);

        // CreatePlan 엑티비티로 반환하기 위한 LocalTime 변수로 변환, 실제로 반환되는 변수는 dateFor
        dateForIntent = LocalDate.of(selectedyear,selectedmonth,seletedday);
        calenderDate = LocalDate.of(selectedyear,selectedmonth,seletedday);

        calendar.setSelectedDate(date);
        calendar.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();

        LocalDate now = LocalDate.now();

        year = Integer.parseInt(String.valueOf(now.getYear()));
        month = Integer.parseInt(String.valueOf(now.getMonth().getValue()));
        day = Integer.parseInt(String.valueOf(now.getDayOfMonth()));
        selectedDate = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if(LocalDate.parse(selectedDate, formatter).compareTo(dateForIntent) == 0){  //오늘
            todayWidgetVisible();
            pastAndFutureGone();

        }else if(LocalDate.parse(selectedDate, formatter).compareTo(dateForIntent) < 0){ //내일
            todayWidgetGone();
            pastGoneFutureVisible();

        }else if(LocalDate.parse(selectedDate, formatter).compareTo(dateForIntent) > 0 ){ //과거
            todayWidgetGone();
            pastVisibleFutureGone();
        }



        /** 사용자가 선택한 날짜 저장 */
        calendar.setOnDateChangedListener(new OnDateSelectedListener() {

            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                selectedyear = date.getYear();
                selectedmonth = date.getMonth();
                seletedday = date.getDay();
                dateForIntent = LocalDate.of(selectedyear,selectedmonth,seletedday);

                LocalDate now = LocalDate.now();

                /** 사용자가 선택 안할 시 자동으로 오늘 날짜 지정 */
                year = Integer.parseInt(String.valueOf(now.getYear()));
                month = Integer.parseInt(String.valueOf(now.getMonth().getValue()));
                day = Integer.parseInt(String.valueOf(now.getDayOfMonth()));
                selectedDate = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                if(LocalDate.parse(selectedDate, formatter).compareTo(dateForIntent) == 0){  //오늘
                    todayWidgetVisible();
                    pastAndFutureGone();

                }else if(LocalDate.parse(selectedDate, formatter).compareTo(dateForIntent) < 0){ //내일
                    todayWidgetGone();
                    pastGoneFutureVisible();

                }else if(LocalDate.parse(selectedDate, formatter).compareTo(dateForIntent) > 0){ //과거
                    todayWidgetGone();
                    pastVisibleFutureGone();

                }

                Response.Listener<String> infoResponseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            JSONArray tuples = jsonObject.getJSONArray("tuples"); // 튜플 배열을 가져옵니다.

                            if(success){
                                RatingBar reteExercise = findViewById(R.id.rateExercise);
                                TextView textView23 = findViewById(R.id.textView23);
                                TextView textView24 = findViewById(R.id.textView24);
                                TextView textView25 = findViewById(R.id.textView25);
                                TextView textView26 = findViewById(R.id.textView26);
                                TextView textView27 = findViewById(R.id.textView27);
                                ScrollView scrollView = findViewById(R.id.scrollView2);

                                String routine = "";
                                int time = 0;
                                String weight = "";

                                String calenderRoutine;
                                String calenderTime;
                                String calenderWeight;

                                float starSum = 0;
                                star = 0;


                                for (int i = 0; i < tuples.length(); i++) {
                                    JSONObject tuple = tuples.getJSONObject(i);

                                    //String calenderMessage = jsonObject.getString("calenderMessage");
                                    calenderRoutine = tuple.getString("calenderRoutine");
                                    calenderTime = tuple.getString("calenderTime");
                                    calenderWeight = tuple.getString("calenderWeight");

                                    starSum += Float.parseFloat( tuple.getString("calenderStar"));


                                    routine += "\n" + calenderRoutine + "\n";
                                    time += Integer.parseInt(calenderTime);
                                    weight = calenderWeight;
                                }

                                int hour = time / 60;
                                int minute = time % 60;
                                star = starSum / tuples.length();

                                textView23.setText((routine.equals("")) ? "\n\n\n\n\n이날은 운동을 하지 않았습니다." : routine);
                                textView25.setText((weight.equals("")) ? "0kg" : weight + "kg");
                                textView27.setText((time == 0) ? "0H 0M" : hour + "H " + minute + "M");

                                rateExercise.setRating(star);
                            }
                            else {
                                RatingBar reteExercise = findViewById(R.id.rateExercise);
                                TextView textView23 = findViewById(R.id.textView23);
                                TextView textView24 = findViewById(R.id.textView24);
                                TextView textView25 = findViewById(R.id.textView25);
                                TextView textView26 = findViewById(R.id.textView26);
                                TextView textView27 = findViewById(R.id.textView27);
                                ScrollView scrollView = findViewById(R.id.scrollView2);

                                textView23.setText("");
                                textView25.setText("");
                                textView27.setText("");
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };

                CalenderRequest calenderRequest = new CalenderRequest(String.valueOf(dateForIntent), userID, infoResponseListener);
                RequestQueue queue = Volley.newRequestQueue(CreatePlanActivity.this);
                queue.add(calenderRequest);

            }
        });

        Button btnCreatePlan = findViewById(R.id.btnCreatePlan);
        btnCreatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePlanActivity.this,SelectExercise.class);
                intent.putExtra("userID",userID);
                intent.putExtra("Date",dateForIntent.toString());
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });


        btnLoadPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePlanActivity.this,LoadExerciseActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("Date",dateForIntent.toString());
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);

            }
        });


        Response.Listener<String> infoResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    JSONArray tuples = jsonObject.getJSONArray("tuples"); // 튜플 배열을 가져옵니다.

                    if(success){
                        RatingBar reteExercise = findViewById(R.id.rateExercise);
                        TextView textView23 = findViewById(R.id.textView23);
                        TextView textView24 = findViewById(R.id.textView24);
                        TextView textView25 = findViewById(R.id.textView25);
                        TextView textView26 = findViewById(R.id.textView26);
                        TextView textView27 = findViewById(R.id.textView27);
                        ScrollView scrollView = findViewById(R.id.scrollView2);

                        String routine = "";
                        int time = 0;
                        String weight = "";

                        String calenderRoutine;
                        String calenderTime;
                        String calenderWeight;

                        float starSum = 0;
                        star = 0;

                        for (int i = 0; i < tuples.length(); i++) {
                            JSONObject tuple = tuples.getJSONObject(i);

                            //String calenderMessage = jsonObject.getString("calenderMessage");
                            calenderRoutine = tuple.getString("calenderRoutine");
                            calenderTime = tuple.getString("calenderTime");
                            calenderWeight = tuple.getString("calenderWeight");

                            starSum += Float.parseFloat( tuple.getString("calenderStar"));

                            routine += "\n" + calenderRoutine + "\n";
                            time += Integer.parseInt(calenderTime);
                            weight = calenderWeight;
                        }

                        int hour = time / 60;
                        int minute = time % 60;
                        star = starSum / tuples.length();

                        textView23.setText((routine.equals("")) ? "\n\n\n\n\n이날은 운동을 하지 않았습니다." : routine);
                        textView25.setText((weight.equals("")) ? "0kg" : weight + "kg");
                        textView27.setText((time == 0) ? "0H 0M" : hour + "H " + minute + "M");

                        rateExercise.setRating(star);

                    }
                    else {
                        RatingBar reteExercise = findViewById(R.id.rateExercise);
                        TextView textView23 = findViewById(R.id.textView23);
                        TextView textView24 = findViewById(R.id.textView24);
                        TextView textView25 = findViewById(R.id.textView25);
                        TextView textView26 = findViewById(R.id.textView26);
                        TextView textView27 = findViewById(R.id.textView27);
                        ScrollView scrollView = findViewById(R.id.scrollView2);

                        textView23.setText("");
                        textView25.setText("");
                        textView27.setText("");
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };
        CalenderRequest calenderRequest = new CalenderRequest(String.valueOf(calenderDate), userID, infoResponseListener);
        RequestQueue queue = Volley.newRequestQueue(CreatePlanActivity.this);
        queue.add(calenderRequest);


    }

    /** 오늘 날짜 색깔 지정 Decorator */
    public class TodayDecorator implements DayViewDecorator {
        private final CalendarDay date;
        private final int color;

        public TodayDecorator(CalendarDay date, int color) {
            this.date = date;
            this.color = color;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(color));
        }
    }
    /** 뒤로가기 버튼 기능 구현 */
    private long backKeyPressedTime = 0;
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CreatePlanActivity.this,MainActivity.class);
        intent.putExtra("userID",userID);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);
        backKeyPressedTime = System.currentTimeMillis();
        return;
    }

}