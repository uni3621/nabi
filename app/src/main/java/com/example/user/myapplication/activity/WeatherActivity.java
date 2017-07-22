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
import java.util.List;
import java.util.Locale;

/**
 * Created by Park Ji Hoon on 2017-07-19.
 */

public class WeatherActivity extends AppCompatActivity {

    private LocationManager locationManager = null;
    private LocationListener locationListener = null;

    private TextView textLocation = null;
    private ProgressBar pb = null;
    private Geocoder mGeocoder = new Geocoder(this, Locale.KOREA);
    String buff;
    private double lat,lng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        TextView textLocation = (TextView) findViewById(R.id.textLocation);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        String locationProvider = LocationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location lKL = locationManager.getLastKnownLocation(locationProvider);
        if(lKL != null){
            lng = lKL.getLongitude();
            lat = lKL.getLatitude();
        }

        mGeocoder = new Geocoder(this, Locale.KOREA);

        try {
            List<Address> addrs = mGeocoder.getFromLocation(lat,lng,1);
            for(Address addr : addrs){
                int index = addr.getMaxAddressLineIndex();
                for(int i = 0 ; i<= index ; ++i){
                    buff.append// buff가 뭔지 확인이 안됨
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}
