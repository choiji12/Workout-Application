package com.example.workoutapp;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
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

        peroid = view.findViewById(R.id.radioGroupPeriod);
        function = view.findViewById(R.id.radioGroupFunction);
        chart = view.findViewById(R.id.chartAnalysis);

        RadioButton btnWeek = view.findViewById(R.id.btnWeek);
        RadioButton btnWeight = view.findViewById(R.id.btnWeight);
        btnWeek.setChecked(true);
        btnWeight.setChecked(true);

        updateGraph(chart);
        peroid.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                updateGraph(chart);
            }
        });

        function.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                updateGraph(chart);
            }
        });
        return view;
    }

    /** 라디오 버튼 클릭 시 그래프를 업데이트 하는 함수 */
    private void updateGraph(LineChart updateChart) {
        int peroidCheckedId= peroid.getCheckedRadioButtonId();
        int functionCheckedId = function.getCheckedRadioButtonId();

        if (peroidCheckedId == R.id.btnWeek && functionCheckedId == R.id.btnWeight) {
            // 주간 체중 변화량 그래프
            float[] weights = new float[]{(float) 43.3, (float) 44.1, (float) 43.2, (float) 42.6, (float) 42.4, (float) 43.2, (float) 43.6,(float) 40.6,(float) 42.6,(float) 46.6};
            setChart(updateChart,weights,"week","weight");
        } else if (peroidCheckedId == R.id.btnMonth && functionCheckedId == R.id.btnWeight) {
            // 월간 체중 변화량 그래프
            float[] weights = new float[]{(float) 43.3, (float) 41.1, (float) 39.5, (float) 42.2};
            setChart(updateChart,weights,"month","weight");
        } else if (peroidCheckedId == R.id.btnYear && functionCheckedId == R.id.btnWeight) {
            // 연간 체중 변화량 그래프
            float[] weights = new float[]{(float) 41.3, (float) 42.1, (float) 45.2, (float) 48.6, (float) 50.4, (float) 47.2,
                    (float) 45.6,(float) 43.3, (float) 42.1, (float) 40.2, (float) 38.2, (float) 38.1, (float) 36.2};
            setChart(updateChart,weights,"year","weight");
        } else if (peroidCheckedId == R.id.btnWeek && functionCheckedId == R.id.btnCalory) {
            // 주간 칼로리 소모량 그래프
            float[] calorys = new float[]{(float) 440.3, (float) 400.1, (float) 500.2};
            setChart(updateChart,calorys,"week","calory");
        } else if (peroidCheckedId == R.id.btnMonth && functionCheckedId == R.id.btnCalory) {
            // 월간 칼로리 소모량 그래프
            float[] calorys = new float[]{(float) 400.2, (float) 341.1};
            setChart(updateChart,calorys,"month","calory");
        } else if (peroidCheckedId == R.id.btnYear && functionCheckedId == R.id.btnCalory){
            // 연간 칼로리 소모량 그래프
            float[] calorys = new float[]{(float) 501.3, (float) 302.1, (float) 445.2, (float) 148.6, (float) 30.4, (float) 600.2};
            setChart(updateChart,calorys,"year","calory");
        }

    }
    /** 사용자 분석 선 그래프, 매개변수는 chart와 일주일 간의 데이터를 저장하는 float[] , 기간을 지정하는 문자열, 기능을 지정하는 문자열 */
    private void setChart(LineChart chart, float[] datas,String peroid,String function){
        List<Entry> values = new ArrayList<>();

        float maxWeight = 0;
        float minWeight = 0;
        float maxCalory = 0;
        float minCalory = 0;
        if (function == "weight"){
            for(int i=0; i<datas.length; i++) {
                values.add(new Entry(i, datas[i]));
            }
//            y축 범위 설정을 위한 체중 최댓값 최솟값
            minWeight = 200;
            for (float weight : datas) {
                if (weight > maxWeight) {
                    maxWeight = weight;
                }
                if (weight < minWeight) {
                    minWeight = weight;
                }
            }
        } else if(function == "calory"){
            for(int i =0; i<datas.length; i++){
                values.add(new Entry(i,datas[i]));
            }
            for (float calory : datas){
                if (calory > maxCalory){
                    maxCalory = calory;
                }
                if (calory < minCalory){
                    minCalory = calory;
                }
            }
        }
//        Typeface font = Typeface.createFromAsset(asset);

        LineDataSet dataSet = new LineDataSet(values,"weight");
        dataSet.setColor(Color.parseColor("#6C91FA"));
        dataSet.setCircleColor(Color.parseColor("#6C91FA"));
        dataSet.setCircleHoleColor(Color.parseColor("#6C91FA"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setLineWidth(3.0f);
        dataSet.setCircleSize(5.0f);
//        dataSet.setDrawCircles(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        dataSet.setCubicIntensity(0.2f);
        dataSet.setDrawFilled(true);
        dataSet.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.main_gradation_color_alpha50));
        dataSet.setValueTextSize(12);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        /** X축 label을 날짜를 추가하는 기능 추가해야됌... */
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        if(peroid.equals("week") ) {
            chart.setVisibleXRangeMinimum(3);
            chart.setVisibleXRangeMaximum(7);
            chart.moveViewToX(dataSet.getXMax());
            // X축 데이터에 날짜 추가하는 곳
            xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]
                    {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}));
        } else if(peroid.equals("month")){
            chart.setVisibleXRangeMinimum(3);
            chart.setVisibleXRangeMaximum(7);
            chart.moveViewToX(dataSet.getXMax());
            xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]
                    {"Week 1", "Week 2", "Week 3", "Week 4"}));
        } else if(peroid.equals("year")){
            chart.setVisibleXRangeMinimum(3);
            chart.setVisibleXRangeMaximum(5);
            chart.moveViewToX(dataSet.getXMax());
            xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]
                    {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
        }

        YAxis leftAxis = chart.getAxisLeft();
        if(function.equals("weight")){
            leftAxis.setAxisMinimum(minWeight-1);
            leftAxis.setAxisMaximum(maxWeight+1);
            leftAxis.setEnabled(false);
        } else if(function.equals("calory")){
            leftAxis.setAxisMinimum(minCalory-20);
            leftAxis.setAxisMaximum(maxCalory+20);
            leftAxis.setEnabled(false);
        }

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        chart.getDescription().setEnabled(false);
        chart.invalidate();
        chart.getLegend().setEnabled(false);

        chart.animateY(1000,Easing.EaseInOutQuad);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(chart,"scaleX",0f,1f);
//        animator.setDuration(1000);
//        animator.start();
    }
}