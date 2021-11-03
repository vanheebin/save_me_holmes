package com.smhrd.seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private static final String TAG = "MyFirebaseMsgService";

    Calendar myCalendar = Calendar.getInstance();
    Button btn_save;
    private RequestQueue queue;
    private StringRequest stringRequest;
    private String msg,token,msg_push;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        CustomFragment fragment = new CustomFragment();
        // 추가할 fragment 생성
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 프래그먼트 매니저 선언
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // 프래그먼트 트랜잭션 시작
        fragmentTransaction.add(R.id.frame,fragment); // 삽입할 위치, 삽입할 fragment
        fragmentTransaction.commit(); // 트랜잭션 종료

        sendRequest();
        //토큰 가져오기
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.v(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }else {

                        }
                        // Get new FCM registration token
                        token = task.getResult();
                        Log.v("냠",token);


                        // Log and
                        //String msg = {token};
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.v(TAG,msg);
                        Log.v("뭐냐구",msg);


                    }
                });
    }

    private void sendRequest() {
        queue = Volley.newRequestQueue(this);
        String url = "http://121.147.52.47:3002/tokenget";

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("resultValue",response );
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String value = jsonObject.getString("check");
                    //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    if(value.equals("true")){


                    }else{
                        Toast.makeText(getApplicationContext(),"아이디가 중복됩니다.",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "왜 안되요??????왜!!!", Toast.LENGTH_SHORT).show(); //error.toString()
                error.printStackTrace();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> paramas = new HashMap<String, String>();
                paramas.put("token", token); //서버로 보낼값
                return paramas;
            }
        };
        queue.add(stringRequest);

    }


}