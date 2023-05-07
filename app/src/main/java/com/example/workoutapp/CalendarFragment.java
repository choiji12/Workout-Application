package com.example.workoutapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.time.LocalDate;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
    }
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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

    private MaterialCalendarView calendar;
    private TextView txtDate;
    private Button btnExercise;
    private int selectedyear;
    private int selectedmonth;
    private int seletedday;
    private String selectedDate;

    private LocalDate dateFor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container,false);
        AndroidThreeTen.init(getActivity());
        calendar = view.findViewById(R.id.calendar);
        txtDate = view.findViewById(R.id.txtDate);
        btnExercise = view.findViewById(R.id.btnExercise);

        calendar.setSelectionColor(ContextCompat.getColor(requireContext(), R.color.blue));
        calendar.setSelectedDate(CalendarDay.today());
        calendar.addDecorator(new TodayDecorator(CalendarDay.today(),Color.MAGENTA));

        LocalDate now = LocalDate.now();

        /** 사용자가 선택 안할 시 자동으로 오늘 날짜 지정 */
        selectedyear = Integer.parseInt(String.valueOf(now.getYear()));
        selectedmonth = Integer.parseInt(String.valueOf(now.getMonth().getValue()));
        seletedday = Integer.parseInt(String.valueOf(now.getDayOfMonth()));
        selectedDate = now.getYear() + "년 " + now.getMonth().getValue() + "월 " + now.getDayOfMonth() + "일";
        txtDate.setText(selectedDate);

        /** 사용자가 선택할 날짜 저장 */
        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectedyear = Integer.parseInt(String.valueOf(date.getYear()));
                selectedmonth = Integer.parseInt(String.valueOf(date.getMonth()));
                seletedday = Integer.parseInt(String.valueOf(date.getDay()));
                selectedDate = selectedyear + "년 " + selectedmonth + "월 " + seletedday + "일";
                dateFor = LocalDate.of(selectedyear,selectedmonth,seletedday);
                txtDate.setText(selectedDate);
            }
        });
        // CreatePlan 엑티비티로 반환하기 위한 LocalTime 변수로 변환, 실제로 반환되는 변수는 dateFor
        dateFor = LocalDate.of(selectedyear,selectedmonth,seletedday);

        /** 운동하기 버튼 누르면 엑티비티 이동 */
        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),CreatePlanActivity.class);
                intent.putExtra("Date",dateFor.toString());
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });


        return view;
    }

    /** 수정필요 */
    public class SelectedDecorator implements DayViewDecorator{

        private final Drawable drawable;

        public SelectedDecorator() {
            drawable = ContextCompat.getDrawable(requireContext(), R.drawable.calendar_selected);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return calendar.getSelectedDate() != null && day.equals(calendar.getSelectedDate());
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setSelectionDrawable(drawable);
        }

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

}
