package com.example.user.myapplication;


import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;

import com.example.user.myapplication.activity.HomeActivity;
import com.example.user.myapplication.activity.MyPageActivity;
import com.example.user.myapplication.activity.SpendActivity;
import com.example.user.myapplication.activity.TrafficActivity;
import com.example.user.myapplication.activity.WeatherActivity;



public class MainActivity extends ActivityGroup {
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        final Toolbar mainToolBar = (Toolbar)findViewById(R.id.mainToolbar);
        TabHost mainTabHost = (TabHost)findViewById(R.id.mainTabHost);
        mainTabHost.setup(getLocalActivityManager());

        TabHost.TabSpec homeTab = mainTabHost.newTabSpec("메인 화면");
        homeTab.setContent(new Intent(this, HomeActivity.class));

        homeTab.setIndicator("", getResources().getDrawable(R.drawable.home_icon));
        mainTabHost.addTab(homeTab);


        TabHost.TabSpec trafficTab = mainTabHost.newTabSpec("교통");
        trafficTab.setContent(new Intent(this, TrafficActivity.class));
        trafficTab.setIndicator("", getResources().getDrawable(R.drawable.traffic_icon));
        mainTabHost.addTab(trafficTab);

        TabHost.TabSpec weatherTab = mainTabHost.newTabSpec("날씨");
        weatherTab.setContent(new Intent(this, WeatherActivity.class));
        weatherTab.setIndicator("", getResources().getDrawable(R.drawable.weather_icon));
        mainTabHost.addTab(weatherTab);

        TabHost.TabSpec spendingTab = mainTabHost.newTabSpec("지출관리");
        spendingTab.setContent(new Intent(this, SpendActivity.class));
        spendingTab.setIndicator("", getResources().getDrawable(R.drawable.spending_icon));
        mainTabHost.addTab(spendingTab);

        TabHost.TabSpec myPageTab = mainTabHost.newTabSpec("마이페이지");
        myPageTab.setContent(new Intent(this, MyPageActivity.class));
        myPageTab.setIndicator("", getResources().getDrawable(R.drawable.mypage_icon));
        mainTabHost.addTab(myPageTab);

        mainTabHost.setCurrentTab(0);
        mainToolBar.setTitle("메인화면");
        mainTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mainToolBar.setTitle(tabId);
            }
        });

    }
}
