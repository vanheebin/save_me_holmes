package com.smhrd.seniorproject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

import lecho.lib.hellocharts.model.Line;


public class Join extends AppCompatActivity {

    private TextView LineTextView;
    private EditText LineEditText,edt_email_head;
    private String[] items = {"직접입력", "naver.com", "daum.net", "gmail.com", "nate.com"};

    private CheckBox cb1, cb2, cb3, cb4;

    private Button btn_join;

    private RequestQueue queue;
    private StringRequest stringRequest;
    private String JJin_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        edt_email_head = findViewById(R.id.edt_email_head);

        Spinner spinner = findViewById(R.id.spinner);
        LineTextView = findViewById(R.id.LineTextView);
        LineEditText = findViewById(R.id.LineEditText);

        // 스피너
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (items[position] == "직접입력") {
                    LineEditText.setVisibility(View.VISIBLE);
                    LineTextView.setVisibility(View.INVISIBLE);
                    LineEditText.setText("입력하세요");
                } else {
                    LineEditText.setVisibility(View.INVISIBLE);
                    LineTextView.setVisibility(View.VISIBLE);
                    LineTextView.setText(items[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                LineTextView.setText("선택");
            }
        });


        // 체크박스
        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        cb4 = findViewById(R.id.cb4);

        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb1.isChecked() == true) {
                    cb2.setChecked(true);
                    cb3.setChecked(true);
                    cb4.setChecked(true);
                }else{
                    cb2.setChecked(false);
                    cb3.setChecked(false);
                    cb4.setChecked(false);
                }
            }
        });


        // 회원가입 버튼
        btn_join = findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                //email+스피너
                JJin_email = edt_email_head.getText().toString()+"@"+spinner.getSelectedItem().toString();
                Log.v("찐이야",JJin_email);

                if(cb2.isChecked()==false || cb3.isChecked()==false || cb4.isChecked()==false){
                    Toast.makeText(getApplicationContext(), "필수 약관에 동의해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    sendRequest();
                }
            }
        });

    }

    private void sendRequest() {
        queue = Volley.newRequestQueue(this);
        String url = "http://192.168.56.1:3002/EmailCheck";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("resultValue",response );
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String value = jsonObject.getString("check");
                    //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    if(value.equals("true")){

                        Intent intent = new Intent(getApplicationContext(),Subscription_Information.class);
                        intent.putExtra("MB_ID",JJin_email);

                        startActivity(intent);
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
               paramas.put("MB_ID",JJin_email); //서버로 보낼값
                return paramas;
            }
        };
        queue.add(stringRequest);
    }

}