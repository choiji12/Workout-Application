package com.example.workoutapp;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.google.android.material.datepicker.MaterialCalendar;
import com.google.android.material.shape.MaterialShapeDrawable;

import java.util.Calendar;

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
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
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
    private CalendarView calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analysis, container,false);
//        calendar = view.findViewById(R.id.calendar);
        MaterialShapeDrawable shapeDrawable = new MaterialShapeDrawable();
        shapeDrawable.setFillColor(ContextCompat.getColorStateList(requireContext(), R.color.blue));
        shapeDrawable.setCornerSize(16f);
//
//        // Set today's date color to purple
//        calendar.addDe(new TodayDecorator(ContextCompat.getColor(requireContext(), R.color.purple_500)));
//
//        // Set selected date border
//        calendar.addDecorator(new SelectedDateDecorator(ContextCompat.getColor(requireContext(), R.color.black), 4f));
//
//        // Set blue text for Saturdays
//        calendar.addDecorator(new WeekdayTextDecorator(ContextCompat.getColor(requireContext(), R.color.blue), Calendar.SATURDAY));
//
//        // Set red text for every week
//        calendar.addDecorator(new WeekdayTextDecorator(ContextCompat.getColor(requireContext(), R.color.red), Calendar.DAY_OF_WEEK));
//
//        // Set font and text color
//        calendar.setTypeface(ResourcesCompat.getFont(requireContext(), R.font.jamsil_regular));
//        calendar.setTileTextColor(ContextCompat.getColor(requireContext(), R.color.black));


        return view;
    }
}