package com.example.workoutapp;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CalendarFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
    }
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

    private ImageButton btnSetting;
    private String userID;
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
        calendar.addDecorator(new TodayDecorator(CalendarDay.today(),Color.BLUE));

        LocalDate now = LocalDate.now();

        /** MainActivty에서 넘긴 UserID Fragment에서 호출 및 할당 */
        Bundle bundle = getArguments();
        if (bundle !=null) {
            userID = bundle.getString("userID");
        }
        Log.d("user ID","User ID :" + userID);

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

        /** 운동하기 버튼 누르면 엑티비티 이동, userID와 선택한 날짜가 intent로 넘어감 */
        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),CreatePlanActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("Date",dateFor.toString());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_right_enter,R.anim.slide_right_exit);
            }
        });

        btnSetting = view.findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SettingActivity.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        List<CalendarDay> exercisedDate = new ArrayList<>();
        Response.Listener<String> infoResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    JSONArray tuples = jsonObject.getJSONArray("tuples"); // 튜플 배열을 가져옵니다.


                    if (success) {
                        for (int i = 0; i < tuples.length(); i++) {
                            JSONObject tuple = tuples.getJSONObject(i);
                            String calenderDate= tuple.getString("analyzDate");
                            Log.d("dat","date"+calenderDate);
                            String[] dateArray = calenderDate.split("-");
                            Log.d("addddd","addddd"+dateArray);
                            selectedyear = Integer.parseInt(dateArray[0]);
                            selectedmonth = Integer.parseInt(dateArray[1]);
                            seletedday = Integer.parseInt(dateArray[2]);
                            Log.d("abcd","ddddd"+seletedday);
                            exercisedDate.add(CalendarDay.from(selectedyear, selectedmonth, seletedday));

                        }
                        Drawable decorator = ContextCompat.getDrawable(requireContext(), R.drawable.calendar_selected);

                        for(CalendarDay date : exercisedDate) {
                            calendar.addDecorator(new EventDecorator(decorator, date));
                            Log.d("asdsdsad","asdsadasd"+date);
                        }
                    } else {
                       
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        CheckedCalenderRequest checkedCalenderRequest = new CheckedCalenderRequest(userID, infoResponseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(checkedCalenderRequest);

        //DotSpan dotSpan = new DotSpan();



        /** 운동을 한 날짜들... DB에서 받아와야 함 */

        return view;
    }

    /** 운동한 날짜를 달력에 꾸미는 Decorator */
    // 체크 표시를 위한 데코레이터 클래스 생성
    class EventDecorator implements DayViewDecorator {
        private final Drawable drawable;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(Drawable drawable, CalendarDay... dates) {
            this.drawable = drawable;
            this.dates = new HashSet<>(Arrays.asList(dates));
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(drawable);
        }
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
