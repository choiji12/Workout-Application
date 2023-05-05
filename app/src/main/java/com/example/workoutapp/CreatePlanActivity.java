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
import android.widget.FrameLayout;

import com.jakewharton.threetenabp.AndroidThreeTen;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        calendar = findViewById(R.id.weekCalendar);
        AndroidThreeTen.init(this);


        /** 이전 프레그먼트에서 데이터 가져오기,  */
        String dateFor = getIntent().getStringExtra("Date");
        String[] dateArray = dateFor.split("-");
        selectedyear = Integer.parseInt(dateArray[0]);
        selectedmonth = Integer.parseInt(dateArray[1]);
        seletedday = Integer.parseInt(dateArray[2]);

        CalendarDay date = CalendarDay.from(selectedyear, selectedmonth, seletedday);

        calendar.setSelectedDate(date);
        calendar.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();

        /** 사용자가 선택한 날짜 저장 */
        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectedyear = date.getYear();
                selectedmonth = date.getMonth();
                seletedday = date.getDay();
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
/** 뒤로가기 버튼 기능 구현... 계속 에러 발생 수정필요
    private long backKeyPressedTime = 0;
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        CalendarFragment calendarFragment = new CalendarFragment();
        fragmentTransaction.add(R.id.fragment_container,calendarFragment).commit();

        overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);
        backKeyPressedTime = System.currentTimeMillis();
        return;
    }
    */
}