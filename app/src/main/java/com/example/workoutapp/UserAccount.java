package com.example.workoutapp;

/**
 * 사용자 계정 정보 모델 클래스
 */
public class UserAccount
{
    private String idToken; // Firebase Uid (고유id 토큰)
    private String emailId;
    private String password;
    // 닉네임, 개인정보 등 확장 가능

    public UserAccount() { }
    // Firebase에서 Realtime DB 에서 모델 클래스로 가져올 때 빈 생성자 필요


    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
