package com.example.workoutapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    // 구글 코드
    private SignInButton btn_google;
    private GoogleApiClient googleApiClient;
    private static final int REQ_SIGN_GOOGLE = 100; //구글 로그인 결과 코드


    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증 변수
    private DatabaseReference mDatabaseRef; // 실시간 DB
    private EditText txtEmail, txtPasswd;
    private boolean isPasswordVisible = false;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //비밀번호 보이게 하기
        LottieAnimationView aniShowPw = findViewById(R.id.aniShowPw);
        LottieAnimationView aniShowPw2 = findViewById(R.id.aniShowPw2);

        aniShowPw.setAnimation(R.raw.password_show);

        EditText txtPassword = findViewById(R.id.txtPasswd);
        EditText txtPasswdCheck = findViewById(R.id.txtPasswdCheck);
        aniShowPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int inputType = txtPasswd.getInputType();
                if(isPasswordVisible){
                    //비밀번호가 보여질 때 안보이게
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    aniShowPw.setMinAndMaxProgress(0.5f,1.0f);
                    aniShowPw.setSpeed(-1.0f);
                    aniShowPw.playAnimation();
                } else{
                    //비밀번호가 숨겨질 때 보이게
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    aniShowPw.setMinAndMaxProgress(0.5f,1.0f);
                    aniShowPw.setSpeed(1.0f);
                    aniShowPw.playAnimation();
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("workoutapp").child("UserAccount");

        txtEmail = findViewById(R.id.txtEmail);
        txtPasswd = findViewById(R.id.txtPasswd);

        // 구글 코드 ~ 72
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        btn_google = findViewById(R.id.btn_google);
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, REQ_SIGN_GOOGLE);
            }
        });


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
                        boolean emailCheck = false; // 이메일 존재 여부 체크 변수

                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            UserAccount accounts = snapshot1.getValue(UserAccount.class);

                            if(strEmail.equals(accounts.getEmailId())){
                                emailCheck = true;
                                mFirebaseAuth.signInWithEmailAndPassword(accounts.getEmailId(),strPasswd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                boolean isEmail = user.isEmailVerified();
                                                if(isEmail){
                                                    //로그인 성공
                                                    String previousActivityClassName = "LoginActivity";
                                                    Intent intent = new Intent(LoginActivity.this, QuestionActivity1.class);
                                                    intent.putExtra("previous_activity", previousActivityClassName);
                                                    intent.putExtra("userID", strEmail);
                                                    startActivity(intent);
                                                    finish(); //현재 엑티비티 파괴
                                                }else {
                                                    Toast.makeText(LoginActivity.this,"이메일 인증을 해주세요.",Toast.LENGTH_SHORT).show();
                                                    mFirebaseAuth.signOut();
                                                }

                                            } else {
                                                Toast.makeText(LoginActivity.this,"비밀번호를 잘못 입력 하였습니다",Toast.LENGTH_SHORT).show();

                                            }

                                        }

                                    });

                            }
                        }

                        if(!emailCheck){
                            Toast.makeText(LoginActivity.this,"존재하지 않는 이메일 입니다.",Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                });

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this,StartActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_left_enter,R.anim.slide_left_exit);
        backKeyPressedTime = System.currentTimeMillis();
        return;
    }


    //구글 코드

    //구글 로그인 인증을 요청했을 때 결과 값을 되돌려 받는 곳
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_SIGN_GOOGLE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount(); //구글로그인 정보 (닉네임, 프로필, 이메일 등)
                resultLogin(account);  //로그인 결과 갑 출력 수행하라는 메소드
            }
        }
    }

    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "안녕하세요.", Toast.LENGTH_SHORT).show();
                            String previousActivityClassName = "LoginActivity";
                            Intent intent = new Intent(getApplicationContext(), QuestionActivity1.class);
                            intent.putExtra("previous_activity", previousActivityClassName);
                            finish(); //현재 엑티비티 파괴
                            // 재엽이형 원본 Intent intent = new Intent(getApplicationContext(), QuestionActivity1.class);

//                            intent.putExtra("email", account.getEmail());
//                            intent.putExtra("nickName", account.getDisplayName());
//                            intent.putExtra("photoURL", String.valueOf(account.getPhotoUrl()));

                            startActivity(intent);
                        }else {
                            Toast.makeText(LoginActivity.this, "로그인 실패.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}