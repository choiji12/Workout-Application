package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class SelectExercise extends AppCompatActivity implements View.OnClickListener {

    private int selectedyear;
    private int selectedmonth;
    private int seletedday;
    private String dateFor;

    private RadioButton btnLeg;
    private RadioButton btnChest;
    private RadioButton btnBack;
    private RadioButton btnShoulder;
    private RadioButton btnCardio;
    private RadioButton btnAbs;

    private LinearLayout contentsLayout;

    private CheckBox chkExercise;
    private String eventExercise;

    private JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_exercise);

        /** 이전 프레그먼트에서 데이터 가져오기,  */
        dateFor = getIntent().getStringExtra("Date");
        String[] dateArray = dateFor.split("-");
        selectedyear = Integer.parseInt(dateArray[0]);
        selectedmonth = Integer.parseInt(dateArray[1]);
        seletedday = Integer.parseInt(dateArray[2]);

        TextView txtDate = findViewById(R.id.txtDate);
        txtDate.setText(selectedmonth + "월 " +seletedday +"일");

        Typeface mainFont = getResources().getFont(R.font.jamsil_regular);

        int exerciseLength = 100;

        LinearLayout mainLayout = findViewById(R.id.exerciseLayout);

        btnLeg = findViewById(R.id.btnLeg);
        btnChest = findViewById(R.id.btnChest);
        btnBack = findViewById(R.id.btnBack);
        btnShoulder = findViewById(R.id.btnShoulder);
        btnCardio = findViewById(R.id.btnCardio);
        btnAbs = findViewById(R.id.btnAbs);

        for(int i=0; i<exerciseLength; i++) {
            contentsLayout = new LinearLayout(this);
            contentsLayout.setOrientation(LinearLayout.HORIZONTAL);
            contentsLayout.setId(i + 101);

            chkExercise = new CheckBox(this);
            chkExercise.setId(i + 1);

            Response.Listener<String> infoResponseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if(success){
                            eventExercise = jsonObject.getString("eventExercise");
                            Log.d("user","uesrName" +eventExercise);

                        }
                        else {

                        }

                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            };
            InfoRequest infoRequest = new InfoRequest(Integer.toString(i+1), infoResponseListener);
            RequestQueue queue = Volley.newRequestQueue(SelectExercise.this);
            queue.add(infoRequest);

            chkExercise.setText(eventExercise);
            chkExercise.setTextColor(getResources().getColorStateList(R.drawable.black_to_white_radio_text));
            chkExercise.setTypeface(mainFont);
            chkExercise.setTextSize(15);
            chkExercise.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//            checkBox 크기 설정
            LinearLayout.LayoutParams paramsExercise = new LinearLayout.LayoutParams
                    ((int) (350 * getResources().getDisplayMetrics().density), (int) (80 * getResources().getDisplayMetrics().density));
            paramsExercise.setMargins(5, 5, 5, 5);
            chkExercise.setLayoutParams(paramsExercise);

            contentsLayout.addView(chkExercise);

            Button btnExplain = new Button(this);
            contentsLayout.addView(btnExplain);

            btnExplain.setId(i + 10001);
            btnExplain.setBackground(getResources().getDrawable(R.drawable.info));
            btnExplain.setTextSize(12f);

            btnExplain.setOnClickListener(this);

            LinearLayout.LayoutParams paramsExplain = new LinearLayout.LayoutParams
                    ((int) (30 * getResources().getDisplayMetrics().density), (int) (30 * getResources().getDisplayMetrics().density));
            paramsExplain.gravity = Gravity.CENTER;
            btnExplain.setLayoutParams(paramsExplain);

            chkExercise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        contentsLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_checked));

                    } else {
                        contentsLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_unchecked));
                    }
                }
            });

            contentsLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_unchecked));
            LinearLayout.LayoutParams paramsContents = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            paramsContents.setMargins(10, 5, 10, 5);
            contentsLayout.setLayoutParams(paramsContents);
            contentsLayout.setVisibility(View.VISIBLE);
            mainLayout.addView(contentsLayout);

        }

        for (int j = 1; j < 101; j++) {
            int id = j + 100;
            LinearLayout LegLayout = (LinearLayout) findViewById(id);
            Log.d("Leg Id", "LEGID :" +LegLayout.getId());
            LegLayout.setVisibility(View.VISIBLE);

            int chkId = j;
            CheckBox checkBox = findViewById(chkId);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        LegLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_checked));

                    } else {
                        LegLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_unchecked));
                    }
                }
            });
        }

        btnLeg.setOnClickListener(rdoScreen);
        btnChest.setOnClickListener(rdoScreen);
        btnBack.setOnClickListener(rdoScreen);
        btnShoulder.setOnClickListener(rdoScreen);
        btnCardio.setOnClickListener(rdoScreen);
        btnAbs.setOnClickListener(rdoScreen);
    }

    View.OnClickListener rdoScreen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int LegLength = 20;
            int BackLength = 20;
            int ChestLength = 20;
            int ShoulderLength = 20;
            int CardioLength = 10;
            int AbsLength = 10;
            /** 하체, 등 버튼 기능 추가 */
            if (btnLeg.isChecked()) {
                for (int i=101; i<201; i++){
                    LinearLayout layout = (LinearLayout) findViewById(i);
                    layout.setVisibility(View.GONE);
                }
                for (int j = 0; j < LegLength; j++) {
                    int id = j + 101;
                    LinearLayout LegLayout = (LinearLayout) findViewById(id);
                    Log.d("Leg Id", "LEGID :" +LegLayout.getId());
                    LegLayout.setVisibility(View.VISIBLE);

                    int chkId = j+101-100;
                    CheckBox checkBox = findViewById(chkId);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                LegLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_checked));

                            } else {
                                LegLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_unchecked));
                            }
                        }
                    });
                }
            }

            //ㅏㅓ
            else if (btnChest.isChecked()) {
                for (int i=101; i<201; i++){
                    LinearLayout layout = (LinearLayout) findViewById(i);
                    layout.setVisibility(View.GONE);
                }
                for (int j = LegLength; j < LegLength + ChestLength; j++) {
                    int id = j + 101;
                    LinearLayout LegLayout = (LinearLayout) findViewById(id);
                    LegLayout.setVisibility(View.VISIBLE);

                    int chkId = j+101-100;
                    CheckBox checkBox = findViewById(chkId);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                LegLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_checked));

                            } else {
                                LegLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_unchecked));
                            }
                        }
                    });
                }
            }
            else if (btnBack.isChecked()) {
                for (int i=101; i<201; i++){
                    LinearLayout layout = (LinearLayout) findViewById(i);
                    layout.setVisibility(View.GONE);
                }
                for (int j = (LegLength + ChestLength); j < LegLength + BackLength + ChestLength; j++) {
                    int id = j + 101;
                    LinearLayout LegLayout = (LinearLayout)findViewById(id);
                    LegLayout.setVisibility(View.VISIBLE);

                    int chkId = j+101-100;
                    CheckBox checkBox = findViewById(chkId);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                LegLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_checked));

                            } else {
                                LegLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_unchecked));
                            }
                        }
                    });
                }
            }
            else if (btnShoulder.isChecked()) {
                for (int i=101; i<201; i++){
                    LinearLayout layout = (LinearLayout) findViewById(i);
                    layout.setVisibility(View.GONE);
                }
                for (int j = (LegLength + BackLength + ChestLength); j < LegLength + BackLength + ChestLength + ShoulderLength; j++) {
                    int id = j + 101;
                    LinearLayout LegLayout = (LinearLayout)findViewById(id);
                    LegLayout.setVisibility(View.VISIBLE);

                    int chkId = j+101-100;
                    CheckBox checkBox = findViewById(chkId);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                LegLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_checked));

                            } else {
                                LegLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_unchecked));
                            }
                        }
                    });
                }
            }
            else if (btnCardio.isChecked()) {
                for (int i=101; i<201; i++){
                    LinearLayout layout = (LinearLayout) findViewById(i);
                    layout.setVisibility(View.GONE);
                }
                for (int j = (LegLength + BackLength + ChestLength + ShoulderLength); j < LegLength + BackLength + ChestLength + ShoulderLength + CardioLength; j++) {
                    int id = j + 101;
                    LinearLayout LegLayout = (LinearLayout)findViewById(id);
                    LegLayout.setVisibility(View.VISIBLE);

                    int chkId = j+101-100;
                    CheckBox checkBox = findViewById(chkId);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                LegLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_checked));

                            } else {
                                LegLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_unchecked));
                            }
                        }
                    });
                }
            }
            else if (btnAbs.isChecked()) {
                for (int i=101; i<201; i++){
                    LinearLayout layout = (LinearLayout) findViewById(i);
                    layout.setVisibility(View.GONE);
                }
                for (int j = (LegLength + BackLength + ChestLength + ShoulderLength +CardioLength); j < LegLength + BackLength + ChestLength + ShoulderLength + CardioLength +AbsLength; j++) {
                    int id = j + 101;
                    LinearLayout LegLayout = (LinearLayout)findViewById(id);
                    LegLayout.setVisibility(View.VISIBLE);

                    int chkId = j+101-100;
                    CheckBox checkBox = findViewById(chkId);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                LegLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_checked));

                            } else {
                                LegLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_unchecked));
                            }
                        }
                    });
                }
            }
        }
    };

    /** 슬라이딩 업 , 운동 설명과 운동DB */
    @Override
    public void onClick(View view){
        int idx = 10001;
        while(true){
            if(view.getId() == idx){
                int ID = idx - 10000;
                CheckBox checkBox = (CheckBox) findViewById(ID);
                TextView tv = findViewById(R.id.txtExerciseName);
                tv.setText(checkBox.getText());

                SlidingUpPanelLayout slidingLayout = findViewById(R.id.main_frame);

                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                break;
            }

            idx++;
        }
    }

    /** 뒤로가기 버튼 기능 구현 */
    private long backKeyPressedTime = 0;
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SelectExercise.this,CreatePlanActivity.class);
        intent.putExtra("Date",dateFor.toString());
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);
        backKeyPressedTime = System.currentTimeMillis();
        return;
    }
}