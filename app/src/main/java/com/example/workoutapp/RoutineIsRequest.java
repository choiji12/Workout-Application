package com.example.workoutapp;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RoutineIsRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://wlgur0914.dothome.co.kr/RoutineIs.php";
    private Map<String, String> map;


    public RoutineIsRequest(String routineName, String userID, String routineEvent, String routineSet,
                             String routineReps ,String routineWeight, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("routineName",routineName);
        map.put("userID",userID);
        map.put("routineEvent",routineEvent);
        map.put("routineSet",routineSet);
        map.put("routineReps",routineReps);
        map.put("routineWeight",routineWeight);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
