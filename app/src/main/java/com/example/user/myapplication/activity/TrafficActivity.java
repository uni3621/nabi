package com.example.user.myapplication.activity;

import android.app.ActivityGroup;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.TabHost;

import com.example.user.myapplication.R;
import com.example.user.myapplication.dialog.TrafficDialog;

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

    public void searchList(View v) {
        Dialog dialog = new TrafficDialog(this);
        dialog.show();

        // Dialog 사이즈 조절 하기
        WindowManager.LayoutParams params =dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

}
