package com.smhrd.seniorproject;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {
    Switch push_switch, background_switch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Switch p_sw = findViewById(R.id.push_switch);
        p_sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getApplicationContext(), "푸시 on", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "푸시 off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Switch b_sw = findViewById(R.id.background_switch);
        b_sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getApplicationContext(), "백그라운드 on", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "백그라운드 off", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}