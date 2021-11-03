package com.smhrd.seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private EditText edt_email,edt_pw;
    private CheckBox ch_login,ch_email;
    private TextView find_email_tv, find_pw_tv, join_tv;
    private Button btn_login;
    private RequestQueue queue;
    private StringRequest stringRequest;
    private String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_email = findViewById(R.id.edt_email);
        edt_pw = findViewById(R.id.edt_pw);
        btn_login=findViewById(R.id.btn_login);
        join_tv = findViewById(R.id.join_tv);
        find_email_tv = findViewById(R.id.find_email_tv);
        find_pw_tv = findViewById(R.id.find_pw_tv);

//        String result = "N";
//        PreferenceManager.setString(getApplicationContext(),"result",result);



        //로그인 버튼
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자 유형에 따라 인텐트 변동(보호자/보호인/초기사용자/등록이 있는자)
                // 자동로그인, 이메일기억 체크에따라 변동해야함.

                sendRequest();

            }
        });

        //회원가입 버튼
        join_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Join.class);
                startActivity(intent);
            }
        });

        // 이메일 찾기 버튼
        find_email_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Check_email.class);
                startActivity(intent);
            }
        });

        // 비밀번호 찾기 버튼
        find_pw_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Check_password.class);
                startActivity(intent);
            }
        });

    }


    // 서버 관련
    public void sendRequest(){
        queue = Volley.newRequestQueue(this);
        String url ="http://121.147.52.47:3002/Login";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("성공1",response);
                if(!response.equals("null")){
                    Log.v("성공2",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String id = jsonObject.getString("id");
                        String pw = jsonObject.getString("pw");
                        String name = jsonObject.getString("name");
                        String tel = jsonObject.getString("tel");
                        String type = jsonObject.getString("type");

                        Log.d("확인", id + pw + name + tel+ type);

                        MemberDTO info = new MemberDTO(id,pw,name,tel,type);
                        Gson gson = new Gson();
                        String value = gson.toJson(info);

                        PreferenceManager.setString(getApplicationContext(), "info", value);
                        Log.v("성공+보호자",type);
                            if(type.equals("보호자")){
                                //String result
                                Intent intent = new Intent(getApplicationContext(), Register.class);
                                //intent.putExtra("info",info);
                                startActivity(intent);
                            }else if(type.equals("보호인")){
                                Log.v("성공3",type);
                                String result1 = PreferenceManager.getString(getApplicationContext(),"result");
                                if(result1.equals("Y")){
                                    Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent1);
                                }else{
                                    Intent intent1 = new Intent(getApplicationContext(),Accept.class);
                                    startActivity(intent1);
                                }

                            }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"로그인에 실패 했습니다..",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data,"UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return super.parseNetworkResponse(response);
                //리스폰스 전에 먼저 여기로 온다.
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Server로 데이터를 보낼 시 넣어주는 곳
                Map<String, String> paramas = new HashMap<String, String>();
                paramas.put("MB_ID",edt_email.getText().toString());
                paramas.put("MB_PW",edt_pw.getText().toString());
                return paramas;
            }
        };
        queue.add(stringRequest);
    }
}