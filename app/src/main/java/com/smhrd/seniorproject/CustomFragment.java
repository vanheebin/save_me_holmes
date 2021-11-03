package com.smhrd.seniorproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CustomFragment extends Fragment {

    EditText name;
    EditText age;
    EditText birthday;
    ImageButton go_back;
    Button save;

    Date curDate = new Date(); // 현재
    final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
    // SimpleDateFormat 으로 포맷 결정
    String result = dataFormat.format(curDate);

    public CustomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        // 프래그먼트에 findViewByld 적용위해 View 선언
//        name = view.findViewById(R.id.editTextTextPersonName);
//        age = view.findViewById(R.id.editTextTextPersonName2);

        birthday = view.findViewById(R.id.editTextTextPersonName5);
        birthday.setText(result); // 오늘 날짜로 초기화
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(); // 생년월일 버튼 클릭 시 showDateDialog() 함수 호출
            }
        });

//        Button save = view.findViewById(R.id.button2);
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String nameStr = name.getText().toString();
//                String ageStr = age.getText().toString();
//                String birthStr = birthday.getText().toString();
//
//                Toast.makeText(getContext(),"이름 : "+nameStr +", 나이 : "+ageStr+", 생년월일 : "+birthStr
//                        ,Toast.LENGTH_SHORT).show();
//            }
//        }); // 저장 버튼 클릭 시 입력한 정보 표시


        ///////////////////////////////////////////////////////
        //뒤돌아가기
        go_back = view.findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Main_gardian.class);

                startActivity(intent);
            }
        });

        ////////////////////////////////////////////////////////////
        //저장(등록)
        save = view.findViewById(R.id.btn_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Main_gardian.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void showDateDialog(){
        Calendar calendar = Calendar.getInstance();
        try {
            curDate = dataFormat.parse(birthday.getText().toString());
            // 문자열로 된 생년월일을 Date로 파싱
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        calendar.setTime(curDate);

        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH);
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        // 년,월,일 넘겨줄 변수

        DatePickerDialog dialog = new DatePickerDialog(getContext(),  birthDateSetListener,  curYear, curMonth, curDay);
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener birthDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(Calendar.YEAR, year);
            selectedCalendar.set(Calendar.MONTH, month);
            selectedCalendar.set(Calendar.DAY_OF_MONTH, day);
            // 달력의 년월일을 버튼에서 넘겨받은 년월일로 설정

            Date curDate = selectedCalendar.getTime(); // 현재를 넘겨줌
            setSelectedDate(curDate);
        }
    };

    private void setSelectedDate(Date curDate) {
        String selectedDateStr = dataFormat.format(curDate);
        birthday.setText(selectedDateStr); // 버튼의 텍스트 수정
    }
}
