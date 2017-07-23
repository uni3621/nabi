package com.example.user.myapplication.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.myapplication.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {



    private double lat,lng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        final Intent intent = new Intent(this, weathercalender.class);


        //현재 위치 (도시명)



        //button 입력
        Button calButton = (Button)findViewById(R.id.calbutton);
        calButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);
            }
        });
    }

    protected void onResume(){
        super.onResume();
        //현재 시간 textView로 출력
        TextView textTime = (TextView) findViewById(R.id.textTime);
        String currentTime = DateFormat.getDateTimeInstance().format(new Date());

        textTime.setText(currentTime);

    }



}
