package com.example.user.myapplication.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.myapplication.R;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {


    private Geocoder mGeocoder = new Geocoder(this, Locale.KOREA);

    private double lat,lng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        TextView textTime = (TextView) findViewById(R.id.textTime);

        //현재 시간 textView로 출력
        String currentTime = DateFormat.getDateTimeInstance().format(new Date());

        textTime.setText(currentTime);

        //현재 위치 (도시명)


    }




}
