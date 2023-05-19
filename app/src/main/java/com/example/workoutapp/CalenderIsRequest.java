package com.example.workoutapp;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CalenderIsRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://wlgur0914.dothome.co.kr/CalenderIs.php";
    private Map<String, String> map;


    public CalenderIsRequest(String calenderDate, String userID, String calenderMessage, String calenderTime,
                             String calenderStar ,String calenderWeight, String calenderVolume, String calenderRoutine, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("calenderDate",calenderDate);
        map.put("userID",userID);
        map.put("calenderMessage",calenderMessage);
        map.put("calenderTime",calenderTime);
        map.put("calenderStar",calenderStar);
        map.put("calenderWeight",calenderWeight);
        map.put("calenderVolume",calenderVolume);
        map.put("calenderRoutine",calenderRoutine);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
