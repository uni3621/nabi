package com.example.user.myapplication;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.widget.TabHost;

import com.example.user.myapplication.activity.WeatherActivity;
=======
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;

import com.example.user.myapplication.activity.MyPageActivity;
import com.example.user.myapplication.activity.SpendActivity;
>>>>>>> 71fb32a1873559086ed304a6f2e927c79ae087d2

public class MainActivity extends ActivityGroup {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar mainToolBar = (Toolbar)findViewById(R.id.mainToolbar);
        TabHost maintabHost = (TabHost)findViewById(R.id.mainTabHost);
        maintabHost.setup(getLocalActivityManager());

        TabHost.TabSpec homeTab = maintabHost.newTabSpec("메인 화면");
        homeTab.setContent(R.id.home_tab);
        homeTab.setIndicator("", getResources().getDrawable(R.drawable.home_icon));
        maintabHost.addTab(homeTab);

        TabHost.TabSpec trafficTab = maintabHost.newTabSpec("교통");
        trafficTab.setContent(R.id.traffic_tab);
        trafficTab.setIndicator("", getResources().getDrawable(R.drawable.traffic_icon));
        maintabHost.addTab(trafficTab);

<<<<<<< HEAD
        TabHost.TabSpec weatherTab = maintabHost.newTabSpec("Weather Tab");
        weatherTab.setContent(new Intent(this, WeatherActivity.class));
=======
        TabHost.TabSpec weatherTab = maintabHost.newTabSpec("날씨");
        weatherTab.setContent(R.id.weather_tab);
>>>>>>> 71fb32a1873559086ed304a6f2e927c79ae087d2
        weatherTab.setIndicator("", getResources().getDrawable(R.drawable.weather_icon));
        maintabHost.addTab(weatherTab);

        TabHost.TabSpec spendingTab = maintabHost.newTabSpec("지출관리");
        spendingTab.setContent(new Intent(this, SpendActivity.class));
        spendingTab.setIndicator("", getResources().getDrawable(R.drawable.spending_icon));
        maintabHost.addTab(spendingTab);

        TabHost.TabSpec mypageTab = maintabHost.newTabSpec("마이페이지");
        mypageTab.setContent(new Intent(this, MyPageActivity.class));
        mypageTab.setIndicator("", getResources().getDrawable(R.drawable.mypage_icon));
        maintabHost.addTab(mypageTab);

<<<<<<< HEAD
=======
        mainToolBar.setTitle("메인화면");
        maintabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mainToolBar.setTitle(tabId);
            }
        });

>>>>>>> 71fb32a1873559086ed304a6f2e927c79ae087d2
    }
}
