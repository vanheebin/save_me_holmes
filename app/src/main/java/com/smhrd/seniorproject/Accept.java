package com.smhrd.seniorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class Accept extends AppCompatActivity {
    private Button btn_ac, btn_no;
    private TextView tv_name,tv_phone;
    private RequestQueue queue;
    private StringRequest stringRequest;
    private String P_ID,MB_ID,MB_TEL,MB_NAME;
    private String ans = "Y";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);

        btn_ac = findViewById(R.id.btn_ac);
        btn_no = findViewById(R.id.btn_no);
        tv_phone = findViewById(R.id.tv_phone);
        tv_name = findViewById(R.id.tv_name);

        //실제 매칭용 P_ID가지고 오는것
        String login_info = PreferenceManager.getString(getApplicationContext(),"info");
        try {
            JSONObject jsonObject = new JSONObject(login_info);
            P_ID = jsonObject.getString("tel");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //PATIENTS 테이블에서 NB_ID 값 가져오기+ 환자테이블에 정말 등록이 되어있는지 check!
        sendRequest();


        btn_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //수락 버튼을 눌렀을 시 MATCHINGS테이블에 'Y'넣기
                sendRequest1();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"수락하지 않으면 서비스를 사용할 수 없습니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendRequest() {
        queue = Volley.newRequestQueue(this);
        String url ="http://192.168.56.1:3002/MB_IDDAT";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("보호자의 값",response);
                if(!response.equals("null")){
                    Log.v("보호자의 값1",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        MB_ID = jsonObject.getString("id");
                        MB_TEL = jsonObject.getString("tel");
                        MB_NAME =jsonObject.getString("m_name");
                        Log.v("mb_id확인",MB_ID);
                        Log.v("mb_tel확인",MB_TEL);
                        Log.v("mb_name확인",MB_NAME);

                        //보호자의 이름과 번호
                        tv_name.setText(MB_NAME);
                        tv_phone.setText(MB_TEL);
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
                paramas.put("P_ID",P_ID);
                return paramas;
            }
        };
        queue.add(stringRequest);
    }

    public void sendRequest1() {
        queue = Volley.newRequestQueue(this);
        String url ="http://121.147.52.47:3002/Matching";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("성공5",response);
                if(!response.equals("null")){
                    Log.v("성공6",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String matching = jsonObject.getString("update_data");

                        Log.v("제발 나와라",matching);

                        //Gson gson = new Gson(matching);
                        //String value_m = gson.toJson(matching);
                        PreferenceManager.setString(getApplicationContext(), "result", matching);

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

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
                Log.v("mbid보내기",paramas+"");
                paramas.put("P_ID",P_ID);
                paramas.put("MB_ID",MB_ID);
                paramas.put("update_data",ans);
                return paramas;
            }
        };
        queue.add(stringRequest);
    }
}