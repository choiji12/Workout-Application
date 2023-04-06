package com.example.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증 변수
    private DatabaseReference mDatabaseRef; // 실시간 DB
    private EditText txtEmail, txtPasswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("workoutapp").child("UserAccount");

        txtEmail = findViewById(R.id.txtEmail);
        txtPasswd = findViewById(R.id.txtPasswd);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인 요청
                String strEmail = txtEmail.getText().toString();
                String strPasswd = txtPasswd.getText().toString();

                if(strEmail.equals("")){
                    Toast.makeText(LoginActivity.this,"이메일을 입력 해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }else if(strPasswd.equals("")){
                    Toast.makeText(LoginActivity.this,"비밀번호를 입력 해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }


                mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            UserAccount accounts = snapshot1.getValue(UserAccount.class);
                            if(strEmail.equals(accounts.getEmailId())){
                                String salt = accounts.getSalt();
                                String pwd = accounts.getPassword();
                                String email = accounts.getEmailId();
                                String pwdgetEnctypt = Encryption.getEncrypt(strPasswd, salt);

                                if(!(email.equals(strEmail))){
                                    Toast.makeText(LoginActivity.this,"존재하지 않는 이메일 입니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if(pwdgetEnctypt.equals(pwd)){

                                    mFirebaseAuth.signInWithEmailAndPassword(strEmail,pwdgetEnctypt).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                boolean isEmail = user.isEmailVerified();
                                                if(isEmail){
                                                    //로그인 성공
                                                    Intent intent = new Intent(LoginActivity.this, QuestionActivity1.class);
                                                    startActivity(intent);
                                                    finish(); //현재 엑티비티 파괴
                                                }else {
                                                    Toast.makeText(LoginActivity.this,"이메일 인증을 해주세요.",Toast.LENGTH_SHORT).show();
                                                    mFirebaseAuth.signOut();
                                                }

                                            } else {
                                                Toast.makeText(LoginActivity.this,"로그인에 실패하였습니다",Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                    });

                                }else{
                                    Toast.makeText(LoginActivity.this,"비밀번호를 잘못 입력 하였습니다",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                });

//                mFirebaseAuth.signInWithEmailAndPassword(strEmail,strPasswd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            //로그인 성공
//                            Intent intent = new Intent(LoginActivity.this, QuestionActivity1.class);
//                            startActivity(intent);
//                            finish(); //현재 엑티비티 파괴
//                        } else {
//                            Toast.makeText(LoginActivity.this,"로그인에 실패하였습니다",Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
            }
        });

        ImageButton btnCancle = findViewById(R.id.btnCancle);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,StartActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);
            }
        });

    }
    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            Intent intent = new Intent(LoginActivity.this,StartActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }
}