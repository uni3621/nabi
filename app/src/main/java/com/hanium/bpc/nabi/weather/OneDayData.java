package com.hanium.bpc.nabi.weather;

import java.util.Calendar;
/**
 * Created by JiHoon on 2017-08-15.
 */

public class OneDayData {       //하루 소스를 담고있는 DATA

    Calendar cal;
    MomusWeather.Weather weather;
    private CharSequence msg = "";

    public OneDayData(){
        this.cal = Calendar.getInstance();
        this.weather = MomusWeather.Weather.SUNSHINE;
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

    public void setWeather(MomusWeather.Weather weather){ //날씨 정보를 지정
        this.weather = weather;
    }

    public MomusWeather.Weather getWeather(){ //날씨 정보를 가져옴
        return this.weather;
    }

    public  CharSequence getMsg(){
        return msg;
    }
    public void setMsg(CharSequence msg){
        this.msg = msg;
    }
}
