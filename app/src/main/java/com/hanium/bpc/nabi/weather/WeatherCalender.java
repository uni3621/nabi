package com.hanium.bpc.nabi.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.hanium.bpc.nabi.R;

public class WeatherCalender extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weathercalender);

        CalendarView weathercal = (CalendarView)findViewById(R.id.weathercal);
        //날짱 변경시 반응하는 리스너
        weathercal.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            //선택된 날짜를 알려주는 메서드
            public void onSelectedDayChange(CalendarView view, int year, int month, int day){
                Toast.makeText(WeatherCalender.this,year+"년 "+(month+1)+"월"+ day +"일",Toast.LENGTH_SHORT).show();
            }
        });

    }
}