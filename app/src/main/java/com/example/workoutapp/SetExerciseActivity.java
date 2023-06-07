package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class SetExerciseActivity extends AppCompatActivity {

    private String userID;
    private String date;
    private ArrayList selectedExercise;
    private Typeface mainFont;
    private int exerciseLength;
    private LinearLayout exerciseLayout;

    private ArrayList<ArrayList<Integer>> totalWeightList;
    private ArrayList<ArrayList<Integer>> totalTimesList;

    private ArrayList weightList;
    private ArrayList timesList;

    private ArrayList exerciseNameList;

    int requestCount;

    private Button btnFinish;

    private ArrayList<Boolean> submittedList;
    private Boolean submitIsClicked = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_exercise);

        /** 이전 엑티비티에서 data 가져오기 */
        getDataFromActivity();

        /** 엑티비티에 필요한 데이터 초기화 */
        initView();

        /** 운동 이름 보여주는 TextView 생성 */
        makeExerciseNameView();

        /** 생성된 TextView에 운동 이름 DB에서 받아와서 할당함 */
        setExerciseName();

        /** 초기 생성 시에만 호출됌 */
        for (int i = 1; i <= exerciseLength; i++) {
            exerciseLayout.addView(addContent(1,i), 1 + (i - 1) * 2);
        }

        /** 루틴 생성완료 버튼을 누르면 엑티비티 전환 */
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }

        });
    }

    private void initView(){
        exerciseLength = selectedExercise.size();
        exerciseLayout = findViewById(R.id.exerciseLayout);
        mainFont = getResources().getFont(R.font.jamsil_regular);

        weightList = new ArrayList<Integer>();
        timesList = new ArrayList<Integer>();
        submittedList = new ArrayList<>(exerciseLength);

        for (int i = 0; i < exerciseLength; i++) {
            submittedList.add(false);
        }

        totalWeightList = new ArrayList<ArrayList<Integer>>();
        totalTimesList = new ArrayList<ArrayList<Integer>>();

        exerciseNameList = new ArrayList<>();
        requestCount = exerciseLength;

        btnFinish = findViewById(R.id.btnSubmit);
        btnFinish.setEnabled(false);
    }

    private void getDataFromActivity(){
        Intent intent = getIntent();
        userID = getIntent().getStringExtra("userID");
        date = getIntent().getStringExtra("Date");
        selectedExercise = (ArrayList) intent.getSerializableExtra("SelectedList");
    }

    private void makeExerciseNameView(){
        for (int i = 0; i < exerciseLength; i++) {
            TextView txtExerciseName = new TextView(this);
            /** 운동이름 보여주는 textView ID는 1~ */
            txtExerciseName.setId((int) selectedExercise.get(i));

            txtExerciseName.setText(String.valueOf(i));
            txtExerciseName.setTypeface(mainFont);
            txtExerciseName.setTextSize(20);
            txtExerciseName.setTextColor(getResources().getColor(R.color.blue));
            LinearLayout.LayoutParams paramsExercise = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsExercise.setMargins(30, 0, 30, 20);
            txtExerciseName.setLayoutParams(paramsExercise);
            exerciseLayout.addView(txtExerciseName);
        }
    }

    private void setExerciseName(){
        Response.Listener<String> infoResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        String eventExercise = jsonObject.getString("eventExercise");
                        String eventNO = jsonObject.getString("eventNo");
                        TextView txtExerciseName = (TextView) findViewById(Integer.parseInt(eventNO));
                        txtExerciseName.setText(eventExercise);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    requestCount--;
                    if (requestCount == 0) {
                        for(int idx=0; idx<exerciseLength; idx++){
                            TextView tvName = findViewById((int) selectedExercise.get(idx));
                            exerciseNameList.add(tvName.getText());
                        }

                    }
                }
            }
        };

        for (int j = 0; j < exerciseLength; j++) {
            InfoRequest infoRequest = new InfoRequest(Integer.toString((int) selectedExercise.get(j)), infoResponseListener);
            RequestQueue queue = Volley.newRequestQueue(SetExerciseActivity.this);
            queue.add(infoRequest);

        }
    }

    private void finishActivity(){
        Intent intent = new Intent(SetExerciseActivity.this,StartExerciseActivity.class);
        intent.putExtra("timesList",totalTimesList);
        intent.putExtra("weightList",totalWeightList);
        intent.putExtra("exerciseList",exerciseNameList);
        intent.putExtra("userID",userID);
        intent.putExtra("Date",date);
        intent.putExtra("NewOrOld","new");
        startActivity(intent);
        finish();
    }

    /** 숫자만 입력가능하게 키보드 제한 */
    private void limitedKey(EditText editText){
        editText.setKeyListener(new DigitsKeyListener(false,true){
            @Override
            public int getInputType(){
                return InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL;
            }
            @Override
            protected char[] getAcceptedChars(){
                return new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
            }
        });
    }

    private LinearLayout addContent(int setCnt,int Id) {
        LinearLayout frameLayout = new LinearLayout(this);
        frameLayout.setOrientation(LinearLayout.VERTICAL);

        frameLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_unchecked));
        LinearLayout.LayoutParams paramsFrame = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsFrame.setMargins(30, 0, 30, 30);
        frameLayout.setLayoutParams(paramsFrame);

        LinearLayout contentsLayout = new LinearLayout(this);
        contentsLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams paramsContents = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsContents.setMargins(10, 0, 0, 5);
        contentsLayout.setLayoutParams(paramsContents);

        TextView txtSetcnt = new TextView(this);
        final int[] counter = {setCnt};
        txtSetcnt.setText(" " +counter[0] + "세트");

        txtSetcnt.setTypeface(mainFont);
        txtSetcnt.setTextSize(17);
        txtSetcnt.setTextColor(getResources().getColor(R.color.black));
        contentsLayout.addView(txtSetcnt);

        EditText edtWeight = new EditText(this);
        edtWeight.setHint("KG");

        LinearLayout.LayoutParams paramsEdtWeight = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsEdtWeight.width = 150;  // 너비 설정 (픽셀 단위)
        paramsEdtWeight.height = 110; // 높이 설정 (픽셀 단위)
        paramsEdtWeight.setMargins(10,0,10,0);
        edtWeight.setLayoutParams(paramsEdtWeight);

        limitedKey(edtWeight);

        edtWeight.setTypeface(mainFont);
        edtWeight.setTextSize(17);
        edtWeight.setTextColor(getResources().getColor(R.color.black));
        edtWeight.setGravity(Gravity.CENTER);
        contentsLayout.addView(edtWeight);

        EditText edtTimes = new EditText(this);
        edtTimes.setHint("회");

        LinearLayout.LayoutParams paramsEdtTimes = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsEdtTimes.width = 150;  // 너비 설정 (픽셀 단위)
        paramsEdtTimes.height = 110; // 높이 설정 (픽셀 단위)
        paramsEdtTimes.setMargins(10,0,10,0);
        edtTimes.setLayoutParams(paramsEdtTimes);

        limitedKey(edtTimes);

        edtTimes.setTypeface(mainFont);
        edtTimes.setTextSize(17);
        edtTimes.setTextColor(getResources().getColor(R.color.black));
        edtTimes.setGravity(Gravity.CENTER);
        contentsLayout.addView(edtTimes);

        Button btnDeleteSet = new Button(this);
        btnDeleteSet.setText("세트삭제");
        btnDeleteSet.setTypeface(mainFont);
        btnDeleteSet.setTextSize(15);
        btnDeleteSet.setTextColor(getResources().getColor(R.color.black));
        btnDeleteSet.setBackground(getResources().getDrawable(R.drawable.round_white_button));
        LinearLayout.LayoutParams paramsDeleteSet = new LinearLayout.LayoutParams
                ((int) (80 * getResources().getDisplayMetrics().density), (int) (40 * getResources().getDisplayMetrics().density));
        paramsDeleteSet.setMarginEnd(15);
        btnDeleteSet.setLayoutParams(paramsDeleteSet);


        btnDeleteSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lastIndex = frameLayout.getChildCount() - 3;
                if (lastIndex >= 1) {
                    View lastChild = frameLayout.getChildAt(lastIndex);
                    frameLayout.removeView(lastChild);

                    counter[0]--;
                } else {
                    Toast.makeText(getApplicationContext(),"최소 한개의 세트는 있어야합니다.",Toast.LENGTH_SHORT).show();
                }

                int exerciseIndexOrigin = exerciseLayout.indexOfChild(frameLayout);
                int exerciseIndex = ((exerciseIndexOrigin-1) /2);
                submittedList.set(exerciseIndex, false);

                submitIsClicked = false;

                if (submittedList.contains(false)) {
                    btnFinish.setEnabled(false); // 아직 모든 버튼이 눌리지 않음
                } else {
                    btnFinish.setEnabled(true); // 모든 버튼이 눌림
                }
            }
        });

        Button btnAddSet = new Button(this);
        btnAddSet.setText("세트추가");
        btnAddSet.setTypeface(mainFont);
        btnAddSet.setTextSize(15);
        btnAddSet.setTextColor(getResources().getColor(R.color.black));
        btnAddSet.setBackground(getResources().getDrawable(R.drawable.round_white_button));
        LinearLayout.LayoutParams paramsAddSet = new LinearLayout.LayoutParams
                ((int) (80 * getResources().getDisplayMetrics().density), (int) (40 * getResources().getDisplayMetrics().density));
        paramsAddSet.setMarginEnd(10);
        btnAddSet.setLayoutParams(paramsAddSet);

        Button btnSubmitSet = new Button(this);
        // ID는 1001~
        btnSubmitSet.setId(Id + 1000);
        btnSubmitSet.setText("세트 저장");
        btnSubmitSet.setBackground(getResources().getDrawable(R.drawable.blue_round_enable_button));
        btnSubmitSet.setTextColor(getResources().getColor(R.color.black_to_white_enable));
        btnSubmitSet.setTypeface(mainFont);

        LinearLayout.LayoutParams paramsBtnSubmit = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        paramsBtnSubmit.setMargins(10,10,10,10);
        btnSubmitSet.setLayoutParams(paramsBtnSubmit);

        btnSubmitSet.setEnabled(false);

        /** edtWeight나 edtTimes에 공백이 있으면 btnSubmitSet을 비활성화 */
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 텍스트 변경 중 이벤트
                String weightString = edtWeight.getText().toString();
                String timesString = edtTimes.getText().toString();

                // 버튼 활성화 또는 비활성화
                boolean enableButton = !weightString.isEmpty() && !timesString.isEmpty();
                btnSubmitSet.setEnabled(enableButton);
            }
            @Override
            public void afterTextChanged(Editable s) {            }
        };

        edtWeight.addTextChangedListener(textWatcher);
        edtTimes.addTextChangedListener(textWatcher);


        btnAddSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter[0]++;

                int exerciseIndexOrigin = exerciseLayout.indexOfChild(frameLayout);
                int exerciseIndex = ((exerciseIndexOrigin-1) /2);

                int lastIndex = frameLayout.getChildCount() - 2;
                if (lastIndex >= 0) {
                    frameLayout.addView(simpleAddContent(counter[0],exerciseIndex), lastIndex);
                }

                submittedList.set(exerciseIndex, false);

                if (submittedList.contains(false)) {
                    btnFinish.setEnabled(false); // 아직 모든 버튼이 눌리지 않음
                } else {
                    btnFinish.setEnabled(true); // 모든 버튼이 눌림
                }

                submitIsClicked = false;

            }
        });

        btnSubmitSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitIsClicked = true;

                int contentsLength = frameLayout.getChildCount()-2;
                ArrayList<Integer> weightList = new ArrayList<>();
                ArrayList<Integer> timesList = new ArrayList<>();

                int exerciseIndexOrigin = exerciseLayout.indexOfChild(frameLayout);
                int exerciseIndex = ((exerciseIndexOrigin-1) /2);

                for(int i=0; i<contentsLength; i++){
                    LinearLayout contentsLayout = (LinearLayout) frameLayout.getChildAt(i);
                    EditText weightText = (EditText) contentsLayout.getChildAt(1);
                    EditText timesText = (EditText) contentsLayout.getChildAt(2);

                    String weight = weightText.getText().toString().trim();
                    String times = timesText.getText().toString().trim();

                    if(weight.isEmpty()){
                        Toast.makeText(getApplicationContext(),"무게를 입력하세요",Toast.LENGTH_SHORT).show();
                        weightList.add(0);
                        return;
                    } else {
                        weightList.add(Integer.parseInt(weight));
                    }
                    if(times.isEmpty()){
                        Toast.makeText(getApplicationContext(),"세트 수를 입력하세요",Toast.LENGTH_SHORT).show();
                        timesList.add(0);
                        return;
                    } else {
                        timesList.add(Integer.parseInt(times));
                    }
                }

                while (totalWeightList.size() <= exerciseIndex) {
                    totalWeightList.add(new ArrayList<>());
                }
                while (totalTimesList.size() <= exerciseIndex) {
                    totalTimesList.add(new ArrayList<>());
                }
                totalWeightList.set(exerciseIndex, weightList);
                totalTimesList.set(exerciseIndex, timesList);
                submittedList.set(exerciseIndex, true);

                Toast.makeText(getApplicationContext(),"세트저장 완료",Toast.LENGTH_SHORT).show();

                // 모든 btnSubmitSet 버튼이 눌렸는지 확인
                if (submittedList.contains(false)) {
                    btnFinish.setEnabled(false); // 아직 모든 버튼이 눌리지 않음
                } else {
                    btnFinish.setEnabled(true); // 모든 버튼이 눌림
                }
            }
        });


        if(submitIsClicked == false){
            btnSubmitSet.setBackground(getResources().getDrawable(R.drawable.white_round_button));
        }
        if(submitIsClicked == true){
            btnSubmitSet.setBackground(getResources().getDrawable(R.drawable.blue_round_enable_button));
        }

        LinearLayout buttonsLayout = new LinearLayout(this);
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout.setGravity(Gravity.RIGHT);

        buttonsLayout.addView(btnDeleteSet);
        buttonsLayout.addView(btnAddSet);

        frameLayout.addView(contentsLayout);

        frameLayout.addView(buttonsLayout);
        frameLayout.addView(btnSubmitSet);

        return frameLayout;
    }

    private LinearLayout simpleAddContent(int setCnt,int Id) {
        LinearLayout contentsLayout = new LinearLayout(this);
        contentsLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams paramsContents = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        paramsContents.setMargins(10, 0, 0, 5);
        contentsLayout.setLayoutParams(paramsContents);

        Button btnSubmitSet = findViewById(1001+Id);
        btnSubmitSet.setEnabled(false);

        TextView txtSetcnt = new TextView(this);
        txtSetcnt.setText(" " +setCnt + "세트");

        txtSetcnt.setTypeface(mainFont);
        txtSetcnt.setTextSize(17);
        txtSetcnt.setTextColor(getResources().getColor(R.color.black));
        contentsLayout.addView(txtSetcnt);

        EditText edtWeight = new EditText(this);
        edtWeight.setHint("KG");

        LinearLayout.LayoutParams paramsEdtWeight = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsEdtWeight.width = 150;  // 너비 설정 (픽셀 단위)
        paramsEdtWeight.height = 110; // 높이 설정 (픽셀 단위)
        paramsEdtWeight.setMargins(10,0,10,0);
        edtWeight.setLayoutParams(paramsEdtWeight);

        limitedKey(edtWeight);

        edtWeight.setTypeface(mainFont);
        edtWeight.setTextSize(17);
        edtWeight.setTextColor(getResources().getColor(R.color.black));
        edtWeight.setGravity(Gravity.CENTER);
        contentsLayout.addView(edtWeight);

        EditText edtTimes = new EditText(this);
        edtTimes.setHint("회");

        LinearLayout.LayoutParams paramsEdtTimes = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsEdtTimes.width = 150;  // 너비 설정 (픽셀 단위)
        paramsEdtTimes.height = 110; // 높이 설정 (픽셀 단위)
        paramsEdtTimes.setMargins(10,0,10,0);
        edtTimes.setLayoutParams(paramsEdtTimes);

        limitedKey(edtTimes);

        edtTimes.setTypeface(mainFont);
        edtTimes.setTextSize(17);
        edtTimes.setTextColor(getResources().getColor(R.color.black));
        edtTimes.setGravity(Gravity.CENTER);
        contentsLayout.addView(edtTimes);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 텍스트 변경 중 이벤트
                String weightString = edtWeight.getText().toString();
                String timesString = edtTimes.getText().toString();

                // 버튼 활성화 또는 비활성화
                boolean enableButton = !weightString.isEmpty() && !timesString.isEmpty();
                btnSubmitSet.setEnabled(enableButton);
            }
            @Override
            public void afterTextChanged(Editable s) {            }
        };

        edtWeight.addTextChangedListener(textWatcher);
        edtTimes.addTextChangedListener(textWatcher);

        return contentsLayout;
    }
}