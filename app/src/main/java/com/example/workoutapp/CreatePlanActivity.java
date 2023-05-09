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

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.time.LocalDate;

public class CreatePlanActivity extends AppCompatActivity {

    private MaterialCalendarView calendar;
    private int selectedyear;
    private int selectedmonth;
    private int seletedday;

    private LocalDate dateForIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        calendar = findViewById(R.id.weekCalendar);


        /** 이전 프레그먼트에서 데이터 가져오기,  */
        String dateFor = getIntent().getStringExtra("Date");
        String[] dateArray = dateFor.split("-");
        selectedyear = Integer.parseInt(dateArray[0]);
        selectedmonth = Integer.parseInt(dateArray[1]);
        seletedday = Integer.parseInt(dateArray[2]);

        // 달력에 표시하기 위해 CalendarDay 변수로 변환
        CalendarDay date = CalendarDay.from(selectedyear, selectedmonth, seletedday);

        // CreatePlan 엑티비티로 반환하기 위한 LocalTime 변수로 변환, 실제로 반환되는 변수는 dateFor
        dateForIntent = LocalDate.of(selectedyear,selectedmonth,seletedday);

        calendar.setSelectedDate(date);
        calendar.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();

        /** 사용자가 선택한 날짜 저장 */
        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectedyear = date.getYear();
                selectedmonth = date.getMonth();
                seletedday = date.getDay();
                dateForIntent = LocalDate.of(selectedyear,selectedmonth,seletedday);
            }
        });

        dateForIntent = LocalDate.of(selectedyear,selectedmonth,seletedday);

        Button btnCreatePlan = findViewById(R.id.btnCreatePlan);
        btnCreatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePlanActivity.this,SelectExercise.class);
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
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);
        backKeyPressedTime = System.currentTimeMillis();
        return;
    }

}