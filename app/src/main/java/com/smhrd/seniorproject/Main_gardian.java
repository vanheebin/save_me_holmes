package com.smhrd.seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Main_gardian extends AppCompatActivity {
    private Toolbar toolbar;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageButton go_life_pattern_g;

    ActionBar actionBar;

    private ListView gardianList;
    private GardianAdapter adapter = new GardianAdapter();

    private RequestQueue queue;
    private StringRequest stringRequest;

    private ImageButton go_ragister;
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gardian);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nabigationView);
        drawerLayout = findViewById(R.id.drawer_layout);

        //자동 초기화 관련 런타임 호출
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_mypage:
                        item.setChecked(true);
                        Intent intent = new Intent(getApplicationContext(), Mypage_gardian.class);
                        startActivity(intent);
                        displtMessage("마이페이지");
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_setting:
                        item.setChecked(true);
                        Intent intent1 = new Intent(getApplicationContext(), Setting.class);
                        startActivity(intent1);
//                        displtMessage("세팅");
                        drawerLayout.closeDrawers();
                        return true;
                }

                return false;
            }
        });

        //액션바 객체
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //뒤로가기버튼 이미지 적용
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayShowTitleEnabled(false);

        //토큰 가져오기
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.v(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.v(TAG,msg);

                    }
                });


        ///////////////////////////////////////////////////////////////////////////
        // 라이프 패턴으로 가는곳
        go_life_pattern_g = findViewById(R.id.go_life_pattern_g);
//        go_life_pattern_g.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), LifePattern.class);
//                startActivity(intent);
//            }
//        });

        //////////////////////////////////////////////////////////////////////////
        //리스트 불러오기
        gardianList = findViewById(R.id.gardianList);
        sendRequest();

        //////////////////////////////////////////////////////////////////////////
        //보호인 추가
        go_ragister = findViewById(R.id.go_ragister);
        go_ragister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);

                startActivity(intent);
            }
        });

    }
    private void displtMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // 메뉴 선택시 네비게이션 호출
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void sendRequest() {
        queue = Volley.newRequestQueue(this);
        String url = "http://210.223.239.194:8082/AndroidWebServer/MemberList";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Server로 부터 데이터를 받아온 곳
                Log.v("resultValue", response);
                adapter.addItem("이름");
                try {
                    JSONArray array = new JSONArray(response);
                    for(int i=0; i<array.length(); i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        adapter.addItem(name);
                        gardianList.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Server 통신시 Error발생 하면 오는 곳
                error.printStackTrace();
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                //파싱: 데이터를 가져오다.
                try {
                    String utf8String = new String(response.data, "UTF8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Server로 데이터를 보낼 시 넣어주는 곳
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        queue.add(stringRequest);

    }
}