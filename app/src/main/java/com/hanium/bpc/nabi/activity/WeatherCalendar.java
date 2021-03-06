package com.hanium.bpc.nabi.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.weather.HLog;
import com.hanium.bpc.nabi.weather.MConfig;
import com.hanium.bpc.nabi.weather.OneDayView;
import com.hanium.bpc.nabi.weather.MConfig;

import java.util.Calendar;


public class WeatherCalendar extends FragmentActivity {

    private static final String TAG = MConfig.TAG;
    private static final String NAME = "WeatherCalendar";
    private final String CLASS = NAME + "@" + Integer.toHexString(hashCode());

    private TextView thisMonthTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_calendar);

        Button addButton = (Button) findViewById(R.id.main_add_bt);
        Button monthButton = (Button) findViewById(R.id.main_monthly_bt);
        Button weekButton = (Button) findViewById(R.id.main_weekly_bt);
        Button dayButton = (Button) findViewById(R.id.main_daily_bt);
        thisMonthTv = (TextView) findViewById(R.id.this_month_tv);

        MonthlyFragment mf = (MonthlyFragment) getSupportFragmentManager().findFragmentById(R.id.monthly);
        mf.setOnMonthChangeListener(new MonthlyFragment.OnMonthChangeListener() {

            @Override
            public void onChange(int year, int month) {
                HLog.d(TAG, CLASS, "onChange " + year + "." + month);
                thisMonthTv.setText(year + "." + (month + 1));
            }

            @Override
            public void onDayClick(OneDayView dayView) {
                Toast.makeText(WeatherCalendar.this, "Click  " + dayView.get(Calendar.MONTH) + "/" + dayView.get(Calendar.DAY_OF_MONTH), Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}