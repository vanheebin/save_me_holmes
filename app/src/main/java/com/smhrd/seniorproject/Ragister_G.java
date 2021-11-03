package com.smhrd.seniorproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Ragister_G extends AppCompatActivity {
    RadioButton radio1, radio2, radio3;
    Button phone_g, btn_save;
    EditText edt1, edt2;
    ImageButton go_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ragister_g);

        // 라디오버튼 1번
        radio1 = findViewById(R.id.radio1);
        radio1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(radio1.isChecked())
                    Toast.makeText(getApplicationContext(), "1 on", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "1 off", Toast.LENGTH_SHORT).show();
            }
        });
        // 라디오버튼 2번
        radio2 = findViewById(R.id.radio2);
        radio2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(radio2.isChecked())
                    Toast.makeText(getApplicationContext(), "2 on", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "2 off", Toast.LENGTH_SHORT).show();
            }
        });
        radio3 = findViewById(R.id.radio3);
        radio3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(radio3.isChecked())
                    Toast.makeText(getApplicationContext(), "3 on", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "3 off", Toast.LENGTH_SHORT).show();
            }
        });
        // 주소록 들어가기
        phone_g = findViewById(R.id.phone_go);
        phone_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()){
                    loadContractApp();
                }
                else{
                    requestPermissions();
                }

            }
        });
        // 완료버튼
        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main_gardian.class);
                startActivity(intent);
            }
        });

        //뒤돌아가기
        go_back = findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), )
            }
        });

    }

    private void loadContractApp() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(Uri.parse("content://com.android.contacts/data/phones"));
        startActivityForResult(intent, 1001);
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1002);
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);

        if(requestCode==1001 && resultCode==RESULT_OK){
            String id= Uri.parse(data.getDataString()).getLastPathSegment();
            Cursor cursor = getContentResolver().query(
                    ContactsContract.Data.CONTENT_URI,
                    new String[] {
                            ContactsContract.Contacts.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER },
                    ContactsContract.Data._ID + "=" + id, null, null);
            cursor.moveToFirst();

            String name = cursor.getString(0);
            String phone=cursor.getString(1);

            edt1.setText(phone);
            edt2.setText(name);

        }


    }

}