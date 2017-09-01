package com.example.user.myapplication.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.myapplication.R;


import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import android.os.Bundle;
import android.os.AsyncTask;
public class WeatherActivity extends AppCompatActivity implements LocationListener {

    //위치 관련
    LocationManager locationManager;
    Location location ;
    String thoroughfare;
    String placeName;
    String country;
    double lat,lon;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        final Intent intent = new Intent(this, weathercalender.class);


        //button 입력
        Button calButton = (Button)findViewById(R.id.calbutton);
        calButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);
            }
        });

         locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);//위치 정보를 컨트롤 할 수 있는 LocationManager 인스턴스 생성

        Iterator<String> providers = locationManager.getAllProviders().iterator();// 모든 프로바이더를 가져와서 providers 에 저장
        while (providers.hasNext()) {
            Log.v("Location", providers.next());
        }


        Criteria criteria = new Criteria();//원하는 프로바이더의 조건을 설정하는 클래스

        criteria.setAccuracy(Criteria.NO_REQUIREMENT);//정확도를 설정
        criteria.setPowerRequirement(Criteria.POWER_LOW);//전원 사용량을설정

        String best = locationManager.getBestProvider(criteria, true);// 설정한 프로바이더를 best에 저장


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
        locationManager.requestLocationUpdates(best, 1000, 1, (LocationListener) WeatherActivity.this);
        location = locationManager.getLastKnownLocation(best);
        lat = location.getLatitude();
        lon = location.getLongitude();

    }

    Location lastLocation =null;

    protected void onResume(){
        super.onResume();
        //현재 시간 textView로 출력
        TextView textTime = (TextView) findViewById(R.id.textTime);
        String currentTime = DateFormat.getDateTimeInstance().format(new Date());

        textTime.setText(currentTime);

        search(lastLocation);


    }

    public void search(Location location){
        Geocoder coder = new Geocoder(this, Locale.KOREA);
        try {
            Iterator<Address> addresses = coder.getFromLocation(lat,lon,3).iterator();
            if (addresses !=null){
                while(addresses.hasNext()){
                    Address nameLoc = addresses.next();
                    thoroughfare = nameLoc.getThoroughfare();
                    placeName = nameLoc.getLocality();
                    country = nameLoc.getCountryName();
                    TextView locationName = (TextView)findViewById(R.id.locationName);
                    if(country != null){

                        locationName.setText(country);
                    }
                    if(placeName != null){                                                          // "if(XX!=null)" : very important (적지 않으면, localname 값이 'null' 값이된다.

                        locationName.append(" "+placeName);
                    }
                    if (thoroughfare != null){

                        locationName.append(" "+thoroughfare);
                    }


                }
            }
        } catch (IOException e) {
            Log.e("GPS","Failed to get address",e);
        }
    }
    @Override
    public void onLocationChanged(Location location){

        lastLocation = location;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}
