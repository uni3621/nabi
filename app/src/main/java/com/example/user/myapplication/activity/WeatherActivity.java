package com.example.user.myapplication.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.myapplication.R;

import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import android.os.Bundle;
import android.os.AsyncTask;
public class WeatherActivity extends AppCompatActivity {

    LocationManager locationManager;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean isGetLocation = false; // gps상태값

    Location location;

    double lat,lon; //위도,경도

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1000; //최소 gps 업데이트 거리 1000미터
    private static final long MIN_TIME_BW_UPDATES = 1000*6*1;// 최소 업데이트시간 1분


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);



        final Intent intent = new Intent(this, weathercalender.class);

/*
        //현재 위치 (도시명)
        Geocoder mGeocoder = new Geocoder(this,Locale.KOREA);
        try {
            List<Address> addrs = mGeocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            for(Address addr : addrs){
                int index = addr.getMaxAddressLineIndex();
                for (int i = 0; i<index ; i++){

                }
        }
*/

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
    public void getWeather(){
        
    }


}
