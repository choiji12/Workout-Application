package com.example.workoutapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnalysisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnalysisFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AnalysisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnalysisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnalysisFragment newInstance(String param1, String param2) {
        AnalysisFragment fragment = new AnalysisFragment();
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

    private RadioGroup peroid;
    private RadioGroup function;
    private LineChart chart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analysis, container,false);

        chart = view.findViewById(R.id.chartAnalysis);

        peroid = view.findViewById(R.id.radioGroupPeriod);
        function = view.findViewById(R.id.radioGroupFunction);

        peroid.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                updateGraph();
            }
        });

        function.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                updateGraph();
            }
        });

        // 차트 체중 데이터


        return view;
    }

    /** 라디오 버튼 클릭 시 그래프를 업데이트 하는 함수 */
    private void updateGraph() {
        int peroidCheckedId= peroid.getCheckedRadioButtonId();
        int functionCheckedId = function.getCheckedRadioButtonId();

        if (peroidCheckedId == R.id.btnWeek && functionCheckedId == R.id.btnWeight) {
            // 주간 체중 변화량 그래프
            float[] weights = new float[]{(float) 43.3, (float) 44.1, (float) 43.2, (float) 42.6, (float) 42.4, (float) 43.2, (float) 43.6};
            setWeekChart(chart,weights);
        } else if (peroidCheckedId == R.id.btnMonth && functionCheckedId == R.id.btnWeight) {
            // 월간 체중 변화량 그래프
            // ...
        } else if (peroidCheckedId == R.id.btnYear && functionCheckedId == R.id.btnWeight) {
            // 연간 체중 변화량 그래프
            //
        } else if (peroidCheckedId == R.id.btnWeek && functionCheckedId == R.id.btnCalory) {
            // 주간 칼로리 소모량 그래프
            // ...
        } else if (peroidCheckedId == R.id.btnMonth && functionCheckedId == R.id.btnCalory) {
            // 월간 칼로리 소모량 그래프
            // ...
        } else if (peroidCheckedId == R.id.btnYear && functionCheckedId == R.id.btnCalory){
            // 연간 칼로리 소모량 그래프
        }

    }
/** 일주일 체중 출력 선 그래프, 매개변수는 chart와 float[] */
    private void setWeekChart(LineChart chart, float[] weights){
        List<Entry> values = new ArrayList<>();
        for(int i=0; i<weights.length; i++){
            values.add(new Entry(i,weights[i]));
        }
        //y축 범위 설정을 위한 체중 최댓값 최솟값
        float maxWeight = Float.MIN_VALUE;
        float minWeight = Float.MAX_VALUE;
        for (float weight : weights) {
            if (weight > maxWeight) {
                maxWeight = weight;
            }
            if (weight < minWeight) {
                minWeight = weight;
            }
        }

        LineDataSet dataSet = new LineDataSet(values,"weight");
        dataSet.setColor(Color.parseColor("#6C91FA"));
        dataSet.setCircleColor(Color.BLACK);
        dataSet.setCircleHoleColor(Color.parseColor("#6C91FA"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setLineWidth(1.5f);
        dataSet.enableDashedLine(10f,5f,0f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCubicIntensity(0.2f);
        dataSet.setDrawFilled(true);
        dataSet.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.main_gradation_color_alpha50));
        dataSet.setValueTextSize(10);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]
                {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(minWeight-1);
        leftAxis.setAxisMaximum(maxWeight+1);
        leftAxis.setEnabled(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        chart.getDescription().setEnabled(false);
        chart.invalidate();
        chart.getLegend().setEnabled(false);
        chart.animateX(1000,Easing.EaseInOutQuad);
    }


    private void setMonthChart(LineChart chart, float[] weights){
        List<Entry> values = new ArrayList<>();
        for(int i=0; i<weights.length; i++){
            values.add(new Entry(i,weights[i]));
        }
        //y축 범위 설정을 위한 체중 최댓값 최솟값
        float maxWeight = Float.MIN_VALUE;
        float minWeight = Float.MAX_VALUE;
        for (float weight : weights) {
            if (weight > maxWeight) {
                maxWeight = weight;
            }
            if (weight < minWeight) {
                minWeight = weight;
            }
        }

        LineDataSet dataSet = new LineDataSet(values,"weight");
        dataSet.setColor(Color.parseColor("#6C91FA"));
        dataSet.setCircleColor(Color.BLACK);
        dataSet.setCircleHoleColor(Color.parseColor("#6C91FA"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setLineWidth(1.5f);
        dataSet.enableDashedLine(10f,5f,0f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCubicIntensity(0.2f);
        dataSet.setDrawFilled(true);
        dataSet.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.main_gradation_color_alpha50));
        dataSet.setValueTextSize(10);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]
                {"Week 1", "Week 2", "Week 3", "Week 4"}));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(minWeight-1);
        leftAxis.setAxisMaximum(maxWeight+1);
        leftAxis.setEnabled(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        chart.getDescription().setEnabled(false);
        chart.invalidate();
        chart.getLegend().setEnabled(false);
        chart.animateX(1000,Easing.EaseInOutQuad);
    }

    private void setYearChart(LineChart chart, float[] weights){
        List<Entry> values = new ArrayList<>();
        for(int i=0; i<weights.length; i++){
            values.add(new Entry(i,weights[i]));
        }
        //y축 범위 설정을 위한 체중 최댓값 최솟값
        float maxWeight = Float.MIN_VALUE;
        float minWeight = Float.MAX_VALUE;
        for (float weight : weights) {
            if (weight > maxWeight) {
                maxWeight = weight;
            }
            if (weight < minWeight) {
                minWeight = weight;
            }
        }

        LineDataSet dataSet = new LineDataSet(values,"weight");
        dataSet.setColor(Color.parseColor("#6C91FA"));
        dataSet.setCircleColor(Color.BLACK);
        dataSet.setCircleHoleColor(Color.parseColor("#6C91FA"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setLineWidth(1.5f);
        dataSet.enableDashedLine(10f,5f,0f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setCubicIntensity(0.2f);
        dataSet.setDrawFilled(true);
        dataSet.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.main_gradation_color_alpha50));
        dataSet.setValueTextSize(10);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]
                {"1", "2", "3", "4", "5", "6", "7","1", "8", "9", "10", "11", "12"}));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(minWeight-1);
        leftAxis.setAxisMaximum(maxWeight+1);
        leftAxis.setEnabled(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        chart.getDescription().setEnabled(false);
        chart.invalidate();
        chart.getLegend().setEnabled(false);
        chart.animateX(1000,Easing.EaseInOutQuad);
    }
}