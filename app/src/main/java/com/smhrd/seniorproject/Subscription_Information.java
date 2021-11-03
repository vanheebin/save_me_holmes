package com.smhrd.seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Subscription_Information extends AppCompatActivity {
    private EditText edt_name, edt_password, edt_password_check, edt_phone_number;
    private RadioButton check_g, check_p;
    private Button btn_join_finish;
    private RequestQueue queue;
    private StringRequest stringRequest;
    private String type,MB_ID,formatDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_information);

        edt_name = findViewById(R.id.edt_name);
        edt_password = findViewById(R.id.edt_password);
        edt_password_check = findViewById(R.id.edt_password_check);
        edt_phone_number = findViewById(R.id.edt_phone_number);
        check_g = findViewById(R.id.check_g);
        check_p = findViewById(R.id.check_p);
        btn_join_finish = findViewById(R.id.btn_join_finish);

        // 현재 날짜
        long mNow = System.currentTimeMillis();
        Date mReDate = new Date(mNow);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
        formatDate = mFormat.format(mReDate);


        Intent intent = getIntent();
        MB_ID = intent.getStringExtra("MB_ID");

        btn_join_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();

            }
        });
        

        //보호자,보호인 TYPE
        RadioGroup rd_group = findViewById(R.id.rd_group);
        rd_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.check_g) {                // 보호자
                    type = "보호자";
                } else if (checkedId == R.id.check_p) {      // 보호인
                    type = "보호인";
                }
            }
        });


    }

    private void sendRequest() {
        queue = Volley.newRequestQueue(this);
        String url ="http://192.168.56.1:3002/Join";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("resultValue",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String value = jsonObject.getString("check");
                    if (value.equals("true")) {
                        Intent intent = new Intent(getApplicationContext(),Login.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "회원가입에 실패 했습니다.", Toast.LENGTH_SHORT).show();
                        edt_name.setText("");
                        edt_password.setText("");
                        edt_password_check.setText("");
                        edt_phone_number.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> paramas = new HashMap<String, String>();
                //인텐트 받은 값 서버에 올리기
                paramas.put("MB_ID",MB_ID);
                paramas.put("MB_PW",edt_password.getText().toString());
                paramas.put("MB_NAME",edt_name.getText().toString());
                paramas.put("MB_PHONE",edt_phone_number.getText().toString());
                paramas.put("MB_JOINDATE",formatDate);
                paramas.put("MB_TYPE",type);
                return paramas;
            }

        };
        queue.add(stringRequest);
    }
}