package com.example.user.myapplication.dialog;

import java.util.Calendar;

import com.example.user.myapplication.dialog.MomusWeather.Weather;
/**
 * Created by JiHoon on 2017-08-15.
 */

public class OneDayData {

    Calendar cal;
    Weather weather;
    private CharSequence msg = "";

    public OneDayData(){
        this.cal = Calendar.getInstance();
        this.weather = Weather.SUNSHINE;
    }

    public  void setDay(int year , int month , int day){
        cal = Calendar.getInstance(); //cal 에 정보 저장
        cal.set(year,month,day);
    }

    public void setDay(Calendar cal){
        this.cal = (Calendar) cal.clone(); //cal값 자체를 clone 생성
    }

    public Calendar getDay(){
        return cal;
    }

    public int get(int field) throws IllegalArgumentException,ArrayIndexOutOfBoundsException{   //모르겠는 부분.
        return cal.get(field);
    }

    public void setWeather(Weather weather){ //날씨 정보를 지정
        this.weather = weather;
    }

    public Weather getWeather(){ //날씨 정보를 가져옴
        return this.weather;
    }

    public  CharSequence getMsg(){
        return msg;
    }
    public void setMsg(CharSequence msg){
        this.msg = msg;
    }
}
