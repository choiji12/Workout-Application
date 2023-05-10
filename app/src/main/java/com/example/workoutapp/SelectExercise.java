package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.w3c.dom.Text;

public class SelectExercise extends AppCompatActivity implements View.OnClickListener {

    private int selectedyear;
    private int selectedmonth;
    private int seletedday;
    private String dateFor;

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

        int exerciseLength = 30;
        LinearLayout mainLayout = findViewById(R.id.exerciseLayout);
//        LinearLayout contentsLayout = findViewById(R.id.contentsLayout);

        for(int i=0; i<exerciseLength; i++){
            LinearLayout contentsLayout = new LinearLayout(this);
            contentsLayout.setOrientation(LinearLayout.HORIZONTAL);
            contentsLayout.setId(i+101);

            CheckBox chkExercise = new CheckBox(this);
            chkExercise.setId(i+1);
            chkExercise.setText("\t"+Integer.toString(i));
            chkExercise.setTextColor(getResources().getColorStateList(R.drawable.black_to_white_radio_text));
            chkExercise.setTypeface(mainFont);
            chkExercise.setTextSize(15);
            chkExercise.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//            chkExercise.setBackground(getResources().getDrawable(R.drawable.rounded_grey_radiobutton));
//            checkBox 크기 설정
            LinearLayout.LayoutParams paramsExercise = new LinearLayout.LayoutParams
                    ((int) (350 * getResources().getDisplayMetrics().density),(int) (80 * getResources().getDisplayMetrics().density));
            paramsExercise.setMargins(5,5,5,5);
            chkExercise.setLayoutParams(paramsExercise);

            contentsLayout.addView(chkExercise);

            Button btnExplain = new Button(this);
            contentsLayout.addView(btnExplain);

            btnExplain.setId(i+10001);
            btnExplain.setBackground(getResources().getDrawable(R.drawable.info));
//            btnExplain.setAllCaps(false);
//            btnExplain.setText("i");
            btnExplain.setTextSize(12f);

            // 테스트 ------------------------------------------------------------------------------------------
            btnExplain.setOnClickListener(this);

            // 테스트 ------------------------------------------------------------------------------------------

            LinearLayout.LayoutParams paramsExplain = new LinearLayout.LayoutParams
                    ((int) (30 * getResources().getDisplayMetrics().density),(int) (30 * getResources().getDisplayMetrics().density));
            paramsExplain.gravity = Gravity.CENTER;
            btnExplain.setLayoutParams(paramsExplain);

            chkExercise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        contentsLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_checked));
//                        btnExplain.setBackground(getResources().getDrawable(R.drawable.explan_circle_checked));

                    } else {
                        contentsLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_unchecked));
//                        btnExplain.setBackground(getResources().getDrawable(R.drawable.explan_circle_unchecked));
                    }
                }
            });

            contentsLayout.setBackground(getResources().getDrawable(R.drawable.exercise_chkbox_unchecked));
            LinearLayout.LayoutParams paramsContents = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            paramsContents.setMargins(10, 5, 10, 5);
            contentsLayout.setLayoutParams(paramsContents);

//            chkExercise.setButtonDrawable(android.R.color.transparent);
            mainLayout.addView(contentsLayout);
        }


    }

    // 테스트 ------------------------------------------------------------------------------------------
    @Override
    public void onClick(View view){
        int idx = 10001;
        while(true){
            if(view.getId() == idx){
                int ID = idx - 10000;
                CheckBox checkBox = (CheckBox) findViewById(ID);
                TextView tv = findViewById(R.id.tv_text1);
                tv.setText(checkBox.getText());


                SlidingUpPanelLayout slidingLayout = findViewById(R.id.main_frame);

                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
                break;
            }

            idx++;
        }
    }
    // 테스트 ------------------------------------------------------------------------------------------

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