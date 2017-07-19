package com.example.user.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.user.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.id;

/**
 * Created by Park Ji Hoon on 2017-07-19.
 */

public class WeatherActivity extends AppCompatActivity{

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdfNow = new SimpleDateFormat("MM//dd HH:mm:ss");
    String formatDate = sdfNow.format(date);

    TextView dateNow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
    }

    dateNow = (TextView) findViewById(R.id.dateNow);

    dateNow.setText(formatDate);


}
