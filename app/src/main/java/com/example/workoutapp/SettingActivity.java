package com.example.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.Attributes;
public class SettingActivity extends AppCompatActivity {


    private String userID;


    private void getIntentData(){
        Intent idIntent = getIntent();
        userID = idIntent.getStringExtra("userID");
        Log.d("user","ss"+userID);
    }

    //------------------------------------------------------------------------------------------- 탈퇴 코드
    private Button btnWithdrawal;

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증 변수
    private DatabaseReference mDatabaseRef; // 실시간 DB
    //-------------------------------------------------------------------------------------------


    private Button buttonChangeNickname;
    private Button buttonChangeGender;
    private Button buttonChangeLocation;
    private Button buttonChangeClass;
    private Button buttonChangeHeight;
    private Button buttonChangeBirthday;
    private TextView txtName;
    private TextView txtGender;
    private TextView txtLocation;
    private TextView txtClass;
    private TextView txtHeight;
    private TextView txtBirth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getIntentData();
        Log.d("USER","USERID =="+userID);
        buttonChangeNickname = findViewById(R.id.buttonChangeNickname);
        buttonChangeGender = findViewById(R.id.buttonChangeGender);
        buttonChangeLocation = findViewById(R.id.buttonChangeLocation);
        buttonChangeClass = findViewById(R.id.buttonChangeClass);
        buttonChangeHeight = findViewById(R.id.buttonChangeHeight);
        buttonChangeBirthday = findViewById(R.id.buttonChangeBirthday);
        txtName = findViewById(R.id.textViewName);
        txtGender = findViewById(R.id.textViewGender);
        txtLocation = findViewById(R.id.textViewLocation);
        txtClass = findViewById(R.id.textViewClass);
        txtHeight = findViewById(R.id.textViewHeight);
        txtBirth = findViewById(R.id.textViewBirth);

        //------------------------------------------------------------------------------------------- 탈퇴 코드
        btnWithdrawal = findViewById(R.id.btnWithdrawal);

        btnWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDeletefunc();
            }
        });
        //-------------------------------------------------------------------------------------------


        buttonChangeNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeNicknamePopup();
            }
        });

        buttonChangeGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeGenderPopup();
            }
        });

        buttonChangeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLocationPopup();
            }
        });

        buttonChangeClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeClassPopup();
            }
        });

        buttonChangeHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeHeightPopup();
            }
        });

        buttonChangeBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeBirthdayPopup();
            }
        });

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String name = jsonObject.getString("userName");
                    String gender = jsonObject.getString("userGender");
                    String Location = jsonObject.getString("userLocation");
                    String Class = jsonObject.getString("userClass");
                    String Height = jsonObject.getString("userHeight");
                    String Birth = jsonObject.getString("userBirthday");

                    txtBirth.setText(Birth);
                    txtName.setText(name);
                    txtGender.setText(gender);
                    txtLocation.setText(Location);
                    txtHeight.setText(Height);
                    txtClass.setText(Class);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        MemberRequest memberRequest = new MemberRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SettingActivity.this);
        queue.add(memberRequest);



    }

    private void refreshActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    //------------------------------------------------------------------------------------------- 탈퇴 코드
    private  void userDeletefunc(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("주의");
        builder.setMessage("정말로 회원 탈퇴 하시겠습니까?");

        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 예 버튼 클릭 시 동작
                mFirebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("workoutapp").child("UserAccount");

                if (user != null) {
                    String userId = user.getUid();
                    mDatabaseRef.child(userId).removeValue();
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SettingActivity.this, "회원 탈퇴하였습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(SettingActivity.this, "회원 탈퇴는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(SettingActivity.this, "로그인된 사용자가 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){

                            }else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                UserDeleteRequest userDeleteRequest = new UserDeleteRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SettingActivity.this);
                queue.add(userDeleteRequest);
            }
        });

        builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 아니오 버튼 클릭 시 동작
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    //-------------------------------------------------------------------------------------------


    //닉네임
    private void showChangeNicknamePopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_change_nickname, null);
        builder.setView(dialogView);

        final EditText editTextNickname = dialogView.findViewById(R.id.editTextNickname);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newNickname = editTextNickname.getText().toString();
                if (!newNickname.isEmpty()){

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };


                upDateRequest nameupRequest = new upDateRequest("userName",newNickname, userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SettingActivity.this);
                queue.add(nameupRequest);}
                else {

                    showChangeNicknamePopup();
                    Toast.makeText(getApplicationContext(),"닉네임을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
                refreshActivity();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //성별
    private void showChangeGenderPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_change_gender, null);
        builder.setView(dialogView);


        final RadioButton radioButtonMale = dialogView.findViewById(R.id.radioMan);
        final RadioButton radioButtonFemale = dialogView.findViewById(R.id.radioWoman);
        final RadioButton radioButtonEtc = dialogView.findViewById(R.id.radioEtc);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newGender;
                if (radioButtonMale.isChecked()) {
                    newGender = "남자";
                } else if (radioButtonFemale.isChecked()) {
                    newGender = "여자";
                }
                else if (radioButtonEtc.isChecked()) {
                    newGender = "기타";
                }else {
                    Toast.makeText(getApplicationContext(), "성별을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),"회원등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();


                            }else {
                                Toast.makeText(getApplicationContext(),"회원등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                upDateRequest nameupRequest = new upDateRequest("userGender",newGender, userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SettingActivity.this);
                queue.add(nameupRequest);

                dialog.dismiss();
                refreshActivity();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    //장소
    private void showChangeLocationPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_change_location, null);
        builder.setView(dialogView);


        final RadioButton radioButtonGym = dialogView.findViewById(R.id.radioGym);
        final RadioButton radioButtonHome = dialogView.findViewById(R.id.radioHome);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newLocation;
                if (radioButtonGym.isChecked()) {
                    newLocation = "헬스장";
                } else if (radioButtonHome.isChecked()) {
                    newLocation = "집";
                }
                else {
                    Toast.makeText(getApplicationContext(), "장소을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                upDateRequest nameupRequest = new upDateRequest("userLocation",newLocation, userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SettingActivity.this);
                queue.add(nameupRequest);


                dialog.dismiss();
                refreshActivity();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //수준
    private void showChangeClassPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_change_class, null);
        builder.setView(dialogView);


        final RadioButton radioButtonBegin = dialogView.findViewById(R.id.radioBegin);
        final RadioButton radioButtonLow = dialogView.findViewById(R.id.radioLow);
        final RadioButton radioButtonMiddle = dialogView.findViewById(R.id.radioMiddle);
        final RadioButton radioButtonHigh = dialogView.findViewById(R.id.radioHigh);
        final RadioButton radioButtonMaster = dialogView.findViewById(R.id.radioMaster);


        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newClass;
                if (radioButtonBegin.isChecked()) {
                    newClass = "입문";
                } else if (radioButtonLow.isChecked()) {
                    newClass = "초급";
                }
                else if (radioButtonMiddle.isChecked()) {
                    newClass = "중급";
                }
                else if (radioButtonHigh.isChecked()) {
                    newClass = "고급";
                }
                else if (radioButtonMaster.isChecked()) {
                    newClass = "전문";
                }
                else {
                    Toast.makeText(getApplicationContext(), "수준을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                upDateRequest nameupRequest = new upDateRequest("userClass",newClass, userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SettingActivity.this);
                queue.add(nameupRequest);


                dialog.dismiss();
                refreshActivity();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    // 키
    private void showChangeHeightPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_change_height, null);
        builder.setView(dialogView);

        final EditText editTextHeight = dialogView.findViewById(R.id.editTextHeight);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String heightText = editTextHeight.getText().toString();
                if (!heightText.isEmpty()) {
                    if (heightText.matches("\\d+(\\.\\d+)?")) {

                        float newHeight = Float.parseFloat(heightText);
                        if (newHeight > 0) {

                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            String newHeightString = String.valueOf(newHeight);
                            upDateRequest heightUpdateRequest = new upDateRequest("userHeight", newHeightString, userID, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(SettingActivity.this);
                            queue.add(heightUpdateRequest);
                        } else {

                            Toast.makeText(getApplicationContext(),"키는 0 이상이어야 합니다.",Toast.LENGTH_SHORT).show();

                            showChangeHeightPopup();
                        }
                    } else {

                        Toast.makeText(getApplicationContext(),"숫자로만 입력해주세요.",Toast.LENGTH_SHORT).show();

                        showChangeHeightPopup();
                    }
                } else {

                    Toast.makeText(getApplicationContext(),"키를 입력해주세요.",Toast.LENGTH_SHORT).show();


                    showChangeHeightPopup();
                }

                dialog.dismiss();
                refreshActivity();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    //생일
    private void showChangeBirthdayPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_change_birthday, null);
        builder.setView(dialogView);

        final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                final String newBirthday;
                newBirthday = year + "년" + (month + 1) + "월" + day +"일";

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                upDateRequest heightUpdateRequest = new upDateRequest("userBirthday", newBirthday, userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SettingActivity.this);
                queue.add(heightUpdateRequest);

                Log.d("uwe", "변경된 생년월일: " + year + "년" + (month + 1) + "월" + day+"일");


                dialog.dismiss();
                refreshActivity();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //삭제
    public void showChangedeletePopup(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("계정 삭제");
        builder.setMessage("정말로 계정을 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.");

        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                DeleteRequest deleteRequest = new DeleteRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SettingActivity.this);
                queue.add(deleteRequest);

                Toast.makeText(SettingActivity.this, "계정이 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SettingActivity.this,StartActivity.class);
                startActivity(intent);

            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //고객센터
    public void showChangeCallPopup(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_change_calls, null);
        builder.setView(dialogView);

        builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}