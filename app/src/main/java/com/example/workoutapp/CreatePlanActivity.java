package com.example.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CreatePlanActivity extends AppCompatActivity {

    private MaterialCalendarView calendar;
    private int selectedyear;
    private int selectedmonth;
    private int seletedday;

    private LocalDate dateForIntent;
    private String userID;

    //----------------------------------------
    private String selectedDate;
    private int year;
    private int month;
    private int day;
    //----------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        calendar = findViewById(R.id.weekCalendar);


        /** 이전 프레그먼트에서 데이터 가져오기,  */
        userID = getIntent().getStringExtra("userID");
        String dateFor = getIntent().getStringExtra("Date");
        String[] dateArray = dateFor.split("-");
        selectedyear = Integer.parseInt(dateArray[0]);
        selectedmonth = Integer.parseInt(dateArray[1]);
        seletedday = Integer.parseInt(dateArray[2]);
        //-----------------------------------------------------------------------------------------------------------
        year = Integer.parseInt(dateArray[0]);
        month = Integer.parseInt(dateArray[1]);
        day = Integer.parseInt(dateArray[2]);
        //-----------------------------------------------------------------------------------------------------------

        Log.d("user ID","User ID :" + userID);

        // 달력에 표시하기 위해 CalendarDay 변수로 변환
        CalendarDay date = CalendarDay.from(selectedyear, selectedmonth, seletedday);

        // CreatePlan 엑티비티로 반환하기 위한 LocalTime 변수로 변환, 실제로 반환되는 변수는 dateFor
        dateForIntent = LocalDate.of(selectedyear,selectedmonth,seletedday);

        calendar.setSelectedDate(date);
        calendar.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();

        //==============================================================
        //텍스트뷰 22 ~ 27
        //scoll scrollView2

        TextView textView22 = findViewById(R.id.textView22);
        TextView textView23 = findViewById(R.id.textView23);
        TextView textView24 = findViewById(R.id.textView24);
        TextView textView25 = findViewById(R.id.textView25);
        TextView textView26 = findViewById(R.id.textView26);
        TextView textView27 = findViewById(R.id.textView27);
        ScrollView scrollView = findViewById(R.id.scrollView2);

        textView22.setVisibility(View.GONE);
        textView23.setVisibility(View.GONE);
        textView24.setVisibility(View.GONE);
        textView25.setVisibility(View.GONE);
        textView26.setVisibility(View.GONE);
        textView27.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
        //==============================================================


        /** 사용자가 선택한 날짜 저장 */
        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectedyear = date.getYear();
                selectedmonth = date.getMonth();
                seletedday = date.getDay();
                dateForIntent = LocalDate.of(selectedyear,selectedmonth,seletedday);

                //-------------------------------------------------------------------------------------
                LocalDate now = LocalDate.now();

                /** 사용자가 선택 안할 시 자동으로 오늘 날짜 지정 */
                year = Integer.parseInt(String.valueOf(now.getYear()));
                month = Integer.parseInt(String.valueOf(now.getMonth().getValue()));
                day = Integer.parseInt(String.valueOf(now.getDayOfMonth()));
                selectedDate = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                if(LocalDate.parse(selectedDate, formatter).compareTo(dateForIntent) == 0){  //오늘
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

                    //==============================================================
                    //텍스트뷰 22 ~ 27
                    //scoll scrollView2

                    TextView textView22 = findViewById(R.id.textView22);
                    TextView textView23 = findViewById(R.id.textView23);
                    TextView textView24 = findViewById(R.id.textView24);
                    TextView textView25 = findViewById(R.id.textView25);
                    TextView textView26 = findViewById(R.id.textView26);
                    TextView textView27 = findViewById(R.id.textView27);
                    ScrollView scrollView = findViewById(R.id.scrollView2);

                    textView22.setVisibility(View.GONE);
                    textView23.setVisibility(View.GONE);
                    textView24.setVisibility(View.GONE);
                    textView25.setVisibility(View.GONE);
                    textView26.setVisibility(View.GONE);
                    textView27.setVisibility(View.GONE);
                    scrollView.setVisibility(View.GONE);
                    //==============================================================

                }else if(LocalDate.parse(selectedDate, formatter).compareTo(dateForIntent) < 0){ //내일
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

                    //==============================================================
                    //텍스트뷰 22 ~ 27
                    //scoll scrollView2

                    TextView textView22 = findViewById(R.id.textView22);
                    TextView textView23 = findViewById(R.id.textView23);
                    TextView textView24 = findViewById(R.id.textView24);
                    TextView textView25 = findViewById(R.id.textView25);
                    TextView textView26 = findViewById(R.id.textView26);
                    TextView textView27 = findViewById(R.id.textView27);
                    ScrollView scrollView = findViewById(R.id.scrollView2);

                    textView22.setVisibility(View.GONE);
                    textView23.setVisibility(View.GONE);
                    textView24.setVisibility(View.GONE);
                    textView25.setVisibility(View.GONE);
                    textView26.setVisibility(View.GONE);
                    textView27.setVisibility(View.GONE);
                    scrollView.setVisibility(View.GONE);
                    //==============================================================

                }else if(LocalDate.parse(selectedDate, formatter).compareTo(dateForIntent) > 0){ //과거
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

                    //==============================================================
                    //텍스트뷰 22 ~ 27
                    //scoll scrollView2

                    TextView textView22 = findViewById(R.id.textView22);
                    TextView textView23 = findViewById(R.id.textView23);
                    TextView textView24 = findViewById(R.id.textView24);
                    TextView textView25 = findViewById(R.id.textView25);
                    TextView textView26 = findViewById(R.id.textView26);
                    TextView textView27 = findViewById(R.id.textView27);
                    ScrollView scrollView = findViewById(R.id.scrollView2);

                    textView22.setVisibility(View.VISIBLE);
                    textView23.setVisibility(View.VISIBLE);
                    textView24.setVisibility(View.VISIBLE);
                    textView25.setVisibility(View.VISIBLE);
                    textView26.setVisibility(View.VISIBLE);
                    textView27.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                    //==============================================================
                }


                //-------------------------------------------------------------------------------------

            }
        });

//        dateForIntent = LocalDate.of(selectedyear,selectedmonth,seletedday);



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