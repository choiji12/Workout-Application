package com.example.workoutapp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.airbnb.lottie.L;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
//--
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.github.mikephil.charting.utils.FileUtils;

import com.github.mikephil.charting.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnalysisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnalysisFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RadioGroup peroid;
    public AnalysisFragment() {
        // Required empty public constructor
    }

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


    private ArrayList<Float> testArray;
    private ArrayList<Float> testArrayWm;

    private ArrayList<Float> testArray3;
    private ArrayList<Float> testArrayVd;
    private ArrayList<Float> testArrayVm;
    private ArrayList<Float> testArrayVy;

    private String userID;

    private RadioGroup function;
    private LineChart chart;

    private ImageButton btnSetting;

    @Override
    public void onResume() {

        super.onResume();
        peroid.clearCheck();
        function.clearCheck();
        chart.clear();
        RadioButton weight = getView().findViewById(R.id.btnWeight);
        weight.setChecked(true);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);

        testArray = new ArrayList();
        testArrayWm = new ArrayList();
        testArray3 = new ArrayList();
        testArrayVd = new ArrayList();
        testArrayVm = new ArrayList();
        testArrayVy = new ArrayList();


        /** UserID mainActivity에서 호출 및 할당 */
        Bundle bundle = getArguments();
        if (bundle != null) {
            userID = bundle.getString("userID");
        }
        Log.d("user ID", "User ID :" + userID);
        Log.d("start","start");

        peroid = view.findViewById(R.id.radioGroupPeriod);
        function = view.findViewById(R.id.radioGroupFunction);
        chart = view.findViewById(R.id.chartAnalysis);

        btnSetting=view.findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SettingActivity.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        /** 버튼 누르면 Chart Update */

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

        Response.Listener<String> infoResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        String analyzWeight = jsonObject.getString("analyzWeight");
                        String analyzVolume = jsonObject.getString("analyzVolume");

                        testArray.add(Float.parseFloat(analyzWeight));
                        testArrayVd.add(Float.parseFloat(analyzVolume));
                    } else {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    testArray.add(0f);
                    testArrayVd.add(0f);
                }
            }
        };



        for (int i = 0; i < 14; i++) {
            try {
                Thread.sleep(45);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LocalDate now = LocalDate.now();

            LocalDate modifiedDate = now.minusDays(i);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = modifiedDate.format(formatter);

            AnalyzeRequest analyzeRequest = new AnalyzeRequest(formattedDate, userID, infoResponseListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(analyzeRequest);

        }

        Response.Listener<String> aMResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {

                        String analyzWeight = jsonObject.getString("analyzWeight");
                        String analyzVolume = jsonObject.getString("analyzVolume");

                        if (analyzWeight == null || analyzWeight.isEmpty()) {
                            testArrayWm.add(0f);
                        } else {
                            try {
                                testArrayWm.add(Float.parseFloat(analyzWeight));
                            } catch (NumberFormatException e) {

                                testArrayWm.add(0f);
                            }
                        }

                        if (analyzVolume == null || analyzVolume.isEmpty()) {
                            testArrayVm.add(0f);
                        } else {
                            try {
                                testArrayVm.add(Float.parseFloat(analyzVolume));
                            } catch (NumberFormatException e) {

                                testArrayVm.add(0f);
                            }
                        }

                    } else {

                    }

                } catch (JSONException e) {

                    testArrayWm.add(0f);
                    testArrayVm.add(0f);
                }
            }
        };


        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            int currentYear = LocalDate.now().getYear(); // 현재 년도
            int currentMonth = LocalDate.now().getMonthValue(); // 현재 월
            String strMonth = "" ;
            int year = currentYear;
            int month = currentMonth - i;

            if (month <= 0) {
                year--;
                month += 12;
            }
            strMonth = String.format("%d-%02d%%", year, month);



            AnalyzeMRequest analyzeMRequest = new AnalyzeMRequest(strMonth, userID, aMResponseListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(analyzeMRequest);


        }


        //---년도
        Response.Listener<String> aYResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {

                        String analyzWeight = jsonObject.getString("analyzWeight");
                        String analyzVolume = jsonObject.getString("analyzVolume");

                        if (analyzWeight == null || analyzWeight.isEmpty()) {
                            testArray3.add(0f);
                        } else {
                            try {
                                testArray3.add(Float.parseFloat(analyzWeight));
                            } catch (NumberFormatException e) {

                                testArray3.add(0f);
                            }
                        }

                        if (analyzWeight == null || analyzWeight.isEmpty()) {
                            testArrayVy.add(0f);
                        } else {
                            try {
                                testArrayVy.add(Float.parseFloat(analyzVolume));
                            } catch (NumberFormatException e) {

                                testArrayVy.add(0f);
                            }
                        }
                    } else {
                    }

                } catch (JSONException e) {

                    testArray3.add(0f);
                    testArrayVy.add(0f);
                }
            }
        };

        int currentYear = LocalDate.now().getYear();
        String strYear = "" ;
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(39);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            int year = currentYear-i;

            strYear = String.format("%d%%", year);

            AnalyzeYRequest analyzeYRequest = new AnalyzeYRequest(strYear, userID, aYResponseListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(analyzeYRequest);

        }

        //updateGraph(chart);
        return view;
    }

    /** 라디오 버튼 클릭 시 그래프를 업데이트 하는 함수 */
    private void updateGraph(LineChart updateChart) {

        int peroidCheckedId= peroid.getCheckedRadioButtonId();
        int functionCheckedId = function.getCheckedRadioButtonId();
//384
        if (peroidCheckedId == R.id.btnWeek && functionCheckedId == R.id.btnWeight) {
            // 주간 체중 변화량 그래프
            float[] weights = new float[testArray.size()];
            int lastIndex = testArray.size() - 1;

            for (int i = 0; i < testArray.size(); i++) {
                weights[i] = testArray.get(lastIndex - i);
                Log.d("iusd", "mweights[" + i + "] = " + weights[i]);
            }

            setChart(updateChart, weights, "week", "weight");
            peroid.clearCheck();
        }
        else if (peroidCheckedId == R.id.btnMonth && functionCheckedId == R.id.btnWeight) {

            float[] mmweights = new float[testArrayWm.size()];

            int lastIndex2 = testArrayWm.size() - 1;

            for (int i = 0; i < testArrayWm.size(); i++) {
                mmweights[i] = testArrayWm.get(lastIndex2 - i);
                Log.d("iusd", "mweights[" + i + "] = " + mmweights[i]);
            }
            // 월간 체중 변화량 그래프

            setChart(updateChart,mmweights,"month","weight");
            peroid.clearCheck();
        } else if (peroidCheckedId == R.id.btnYear && functionCheckedId == R.id.btnWeight) {

            float[] yweights = new float[testArray3.size()];
            int lastIndex3 = testArray3.size() - 1;

            for (int i = 0; i < testArray3.size(); i++) {
                yweights[i] = testArray3.get(lastIndex3 - i);
            }
            // 연간 체중 변화량 그래프
            setChart(updateChart,yweights,"year","weight");
            peroid.clearCheck();
        } else if (peroidCheckedId == R.id.btnWeek && functionCheckedId == R.id.btnCalory) {
            // 주간 칼로리 소모량 그래프
            float[] Volumed = new float[testArrayVd.size()];
            int lastIndexVd = testArrayVd.size() - 1;

            for (int i = 0; i < testArrayVd.size(); i++) {
                Volumed[i] = testArrayVd.get(lastIndexVd - i);
            }
            setChart(updateChart,Volumed,"week","calory");
            peroid.clearCheck();
        } else if (peroidCheckedId == R.id.btnMonth && functionCheckedId == R.id.btnCalory) {
            // 월간 칼로리 소모량 그래프
            float[] Volumem = new float[testArrayVm.size()];
            int lastIndexVm = testArrayVm.size() - 1;

            for (int i = 0; i < testArrayVm.size(); i++) {
                Volumem[i] = testArrayVm.get(lastIndexVm - i);
            }
            setChart(updateChart,Volumem,"month","calory");
            peroid.clearCheck();
        } else if (peroidCheckedId == R.id.btnYear && functionCheckedId == R.id.btnCalory){
            // 연간 칼로리 소모량 그래프
            float[] Volumey = new float[testArrayVy.size()];
            int lastIndexVy = testArrayVy.size() - 1;

            for (int i = 0; i < testArrayVy.size(); i++) {
                Volumey[i] = testArrayVy.get(lastIndexVy - i);
            }
            setChart(updateChart,Volumey,"year","calory");
            peroid.clearCheck();
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

            String[] yearArray = new String[14];
            for (int i = 0; i < 14; i++) {

                LocalDate now = LocalDate.now();

                LocalDate modifiedDate = now.minusDays(i);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
                String strYear = modifiedDate.format(formatter);
                yearArray[i] = strYear;
            }

            // X축 데이터에 날짜 추가하는 곳
            xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]
                    {
                            //yearArray[29],yearArray[28],yearArray[27],yearArray[26],yearArray[25],yearArray[24],yearArray[23],yearArray[22],yearArray[21],yearArray[20],yearArray[19],yearArray[18],
                            //yearArray[17],yearArray[16],yearArray[15]
                            yearArray[13],yearArray[12],yearArray[11],yearArray[10],yearArray[9],yearArray[8],yearArray[7],yearArray[6],yearArray[5],
                            yearArray[4],yearArray[3],yearArray[2],yearArray[1],yearArray[0]
                            }));
        } else if(peroid.equals("month")){
            chart.setVisibleXRangeMinimum(3);
            chart.setVisibleXRangeMaximum(7);
            chart.moveViewToX(dataSet.getXMax());

            int currentYear = LocalDate.now().getYear(); // Current year
            int currentMonth = LocalDate.now().getMonthValue(); // Current month
            String[] monthArray = new String[6];

            for (int j = 0; j < 6; j++) {
                int year = currentYear;
                int month = currentMonth - j;

                if (month <= 0) {
                    year--;
                    month += 12;
                }
                String strMonth = String.format("%d-%02d", year, month);
                monthArray[j] = strMonth;
            }
            xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]
                    {
                            //monthArray[11],monthArray[10],monthArray[9],monthArray[8],monthArray[7],monthArray[6],
                           monthArray[5],monthArray[4],monthArray[3],monthArray[2],monthArray[1],monthArray[0]}));
        } else if(peroid.equals("year")){
            chart.setVisibleXRangeMinimum(3);
            chart.setVisibleXRangeMaximum(5);
            chart.moveViewToX(dataSet.getXMax());

            int currentYear = LocalDate.now().getYear();
            String[] yearArray = new String[5];

            String strYear = "" ;
            for (int i = 0; i < 5; i++) {

                int year = currentYear-i;

                strYear = String.format("%d", year);
                yearArray[i] = strYear;
            }

            xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]
                    {yearArray[4],yearArray[3],yearArray[2],yearArray[1],yearArray[0]}));
        }

        YAxis leftAxis = chart.getAxisLeft();
        if(function.equals("weight")){
            leftAxis.setAxisMinimum(0);
            leftAxis.setAxisMaximum(maxWeight+8);
            leftAxis.setEnabled(false);
        } else if(function.equals("calory")){
            leftAxis.setAxisMinimum(0);
            leftAxis.setAxisMaximum(maxCalory+20);
            leftAxis.setEnabled(false);
        }

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        chart.getDescription().setEnabled(false);
        chart.invalidate();
        chart.getLegend().setEnabled(false);

        chart.animateY(1000,Easing.EaseInOutQuad);



    }
}
