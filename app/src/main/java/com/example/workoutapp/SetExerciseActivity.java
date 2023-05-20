package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.View;
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

import java.util.ArrayList;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_exercise);

        /** 이전 엑티비티에서 data 가져오기 */
        Intent intent = getIntent();
        userID = getIntent().getStringExtra("userID");
        date = getIntent().getStringExtra("Date");
        selectedExercise = (ArrayList) intent.getSerializableExtra("SelectedList");


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

        /** 세트 수 설정하는 버튼 생성 */
        for (int i = 0; i < exerciseLength; i++) {
            TextView txtExerciseName = new TextView(this);
            /** 운동이름 보여주는 textView ID는 1~ */
            txtExerciseName.setId((int) selectedExercise.get(i));

            /** 운동 이름 추가하시면 됩니다~ */
            txtExerciseName.setText(String.valueOf(i));
            txtExerciseName.setTypeface(mainFont);
            txtExerciseName.setTextSize(20);
            txtExerciseName.setTextColor(getResources().getColor(R.color.blue));
            txtExerciseName.setGravity(Gravity.LEFT);
            LinearLayout.LayoutParams paramsExercise = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsExercise.setMargins(0, 0, 30, 130);
            txtExerciseName.setLayoutParams(paramsExercise);
            exerciseLayout.addView(txtExerciseName);
        }


        /** 초기 생성 시에만 호출됌 */
        for (int i = 1; i <= exerciseLength; i++) {
            exerciseLayout.addView(addContent(1,i), 1 + (i - 1) * 2);
        }

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

                        exerciseNameList.add(eventExercise);

                        txtExerciseName.setText(eventExercise);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    requestCount--;
                    if (requestCount == 0) {
                        exerciseNameList = exerciseNameList;

                    }
                }
            }
        };
        for (int j = 0; j < exerciseLength; j++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            InfoRequest infoRequest = new InfoRequest(Integer.toString((int) selectedExercise.get(j)), infoResponseListener);
            RequestQueue queue = Volley.newRequestQueue(SetExerciseActivity.this);
            queue.add(infoRequest);
        }
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetExerciseActivity.this,StartExerciseActivity.class);
                intent.putExtra("timesList",totalTimesList);
                intent.putExtra("weightList",totalWeightList);
                intent.putExtra("exerciseList",exerciseNameList);
                intent.putExtra("userID",userID);
                intent.putExtra("Date",date);
                startActivity(intent);
                finish();
            }
        });


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

        LinearLayout contentsLayout = new LinearLayout(this);
        contentsLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView txtSetcnt = new TextView(this);
        final int[] counter = {setCnt};
        txtSetcnt.setText(counter[0] + "세트");
        contentsLayout.addView(txtSetcnt);

        EditText edtWeight = new EditText(this);
        edtWeight.setHint("KG");
        limitedKey(edtWeight);
        contentsLayout.addView(edtWeight);

        EditText edtTimes = new EditText(this);
        edtTimes.setHint("회");
        limitedKey(edtTimes);
        contentsLayout.addView(edtTimes);

        Button btnDeleteSet = new Button(this);
        btnDeleteSet.setText("세트 삭제");

        btnDeleteSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lastIndex = frameLayout.getChildCount() - 4;
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

                if (submittedList.contains(false)) {
                    btnFinish.setEnabled(false); // 아직 모든 버튼이 눌리지 않음
                } else {
                    btnFinish.setEnabled(true); // 모든 버튼이 눌림
                }
            }
        });

        Button btnAddSet = new Button(this);
        btnAddSet.setText("세트 추가");


        Button btnSubmitSet = new Button(this);
        // ID는 1001~
        btnSubmitSet.setId(Id + 1000);
        btnSubmitSet.setText("세트 저장");
        btnSubmitSet.setEnabled(false);

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

                int lastIndex = frameLayout.getChildCount() - 3;
                if (lastIndex >= 0) {
                    frameLayout.addView(simpleAddContent(counter[0]), lastIndex);
                }

                int exerciseIndexOrigin = exerciseLayout.indexOfChild(frameLayout);
                int exerciseIndex = ((exerciseIndexOrigin-1) /2);
                submittedList.set(exerciseIndex, false);

                if (submittedList.contains(false)) {
                    btnFinish.setEnabled(false); // 아직 모든 버튼이 눌리지 않음
                } else {
                    btnFinish.setEnabled(true); // 모든 버튼이 눌림
                }

            }
        });

        btnSubmitSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int contentsLength = frameLayout.getChildCount()-3;
                ArrayList<Integer> weightList = new ArrayList<>();
                ArrayList<Integer> timesList = new ArrayList<>();

                int exerciseIndexOrigin = exerciseLayout.indexOfChild(frameLayout);
                int exerciseIndex = ((exerciseIndexOrigin-1) /2);

                for(int i=0; i<contentsLength; i++){
                    LinearLayout contentsLayout = (LinearLayout) frameLayout.getChildAt(i);
                    EditText weightText = (EditText) contentsLayout.getChildAt(1);
                    EditText timesText = (EditText) contentsLayout.getChildAt(2);

                    weightList.add(Integer.parseInt(weightText.getText().toString()));
                    timesList.add(Integer.parseInt(timesText.getText().toString()));
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

                // 모든 btnSubmitSet 버튼이 눌렸는지 확인
                if (submittedList.contains(false)) {
                    btnFinish.setEnabled(false); // 아직 모든 버튼이 눌리지 않음
                } else {
                    btnFinish.setEnabled(true); // 모든 버튼이 눌림
                }
            }
        });

        frameLayout.addView(contentsLayout);
        frameLayout.addView(btnDeleteSet);
        frameLayout.addView(btnAddSet);
        frameLayout.addView(btnSubmitSet);

        return frameLayout;
    }

    private LinearLayout simpleAddContent(int setCnt) {
        LinearLayout contentsLayout = new LinearLayout(this);
        contentsLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView txtSetcnt = new TextView(this);
        txtSetcnt.setText(setCnt + "세트");
        contentsLayout.addView(txtSetcnt);

        EditText edtWeight = new EditText(this);
        edtWeight.setHint("KG");
        limitedKey(edtWeight);
        contentsLayout.addView(edtWeight);

        EditText edtTimes = new EditText(this);
        edtTimes.setHint("회");
        limitedKey(edtTimes);
        contentsLayout.addView(edtTimes);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 텍스트 변경 중 이벤트
                String weightString = edtWeight.getText().toString();
                String timesString = edtTimes.getText().toString();

                int exerciseIndexOrigin = exerciseLayout.indexOfChild((View) contentsLayout.getParent());
                int exerciseIndex = ((exerciseIndexOrigin-1) /2);

                Button btnSubmitSet = findViewById(exerciseIndex+1001);
                btnSubmitSet.setEnabled(false);

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