package com.example.user.myapplication.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

import com.example.user.myapplication.R;

/**
 * Created by user on 2017-07-19.
 */

public class TrafficActivity extends ActivityGroup {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);

        TabHost trafficTabHost = (TabHost)findViewById(R.id.trafficTabHost);
        trafficTabHost.setup(getLocalActivityManager());

        TabHost.TabSpec bookmarkTab = trafficTabHost.newTabSpec("즐겨찾기");
        bookmarkTab.setContent(new Intent(this, TrafficItemActivity.class));
        bookmarkTab.setIndicator("즐겨찾기");
        trafficTabHost.addTab(bookmarkTab);

        TabHost.TabSpec searchTab= trafficTabHost.newTabSpec("최근 검색");
        searchTab.setContent(new Intent(this, TrafficItemActivity.class));
        searchTab.setIndicator("최근검색");
        trafficTabHost.addTab(searchTab);

    }
}
