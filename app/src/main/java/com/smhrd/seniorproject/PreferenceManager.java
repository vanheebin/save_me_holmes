package com.smhrd.seniorproject;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREFERENCE_NAME ="my_app"; //메모장 이름 지정
    private static final String DEFAULT_VALUE_STRING =""; // 값을 아무것도 저장 하지 않을때?
    private static final boolean DEFAULT_VALUE_BOOLEAN = false; //보통 이렇게 넣음
    private static final int DEFAULT_VALUE_INT = -1; //보통 이렇게 넣음 '없다는것을 표현'
    private static final long DEFAULT_VALUE_LONG = -1L;
    private static final float DEFAULT_VALUE_FLOAT = -1F;

    //혹시라도 훔쳐갈까봐 만든건데 없어도됨
    private static SharedPreferences getPreferences(Context context){
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE); //Mode는 필드값을 가지고 오는것
    }

    //String 값 저장 (외부에서 객체 생성없이 쓰기 위한것은 static을 쓰는것)
    public static void setString(Context context, String key , String value ){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();

    }

    //key : content  형태임
    //boolean 값 저장
    public static void  setBoolean(Context context, String key , Boolean value){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    //int 값 저장
    public static void  setInt(Context context, String key , int value){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    //Long 값 저장
    public static void  setLong(Context context, String key , long value){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    //float 값 저장
    public static void  setfloat(Context context, String key , float value){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    //쉐어드 프리페런스는 아쉽게도 객체?는 없음

    //String 값 꺼내기
    public static String getString(Context context, String key){
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key,DEFAULT_VALUE_STRING);
        return  value;
    }
    //Boolean 값 꺼내기
    public static Boolean getBoolean(Context context, String key){
        SharedPreferences prefs = getPreferences(context);
        Boolean value = prefs.getBoolean(key,DEFAULT_VALUE_BOOLEAN);
        return  value;
    }

    public static int getint (Context context, String key){
        SharedPreferences prefs = getPreferences(context);
        int value = prefs.getInt(key,DEFAULT_VALUE_INT);
        return  value;
    }

    public static Long getLong (Context context, String key){
        SharedPreferences prefs = getPreferences(context);
        Long value = prefs.getLong(key,DEFAULT_VALUE_LONG);
        return  value;
    }

    public static Float getFloat (Context context, String key){
        SharedPreferences prefs = getPreferences(context);
        Float value = prefs.getFloat(key,DEFAULT_VALUE_FLOAT);
        return  value;
    }

    //key 값과 일치하는 것 삭제
    public static void removeKey(Context context, String key){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor =prefs.edit();
        editor.remove(key);
        editor.commit();
    }

    //모든 저장 데이터 삭제
    public static void clear(Context context){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

}
