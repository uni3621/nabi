package com.example.user.myapplication.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.user.myapplication.R;

public class SpendActivity extends ActivityGroup {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending);

        TabHost spendTabHost = (TabHost)findViewById(R.id.spendingTabHost);
        spendTabHost.setup(getLocalActivityManager());

        TabHost.TabSpec dayTab = spendTabHost.newTabSpec("일별 지출");
        dayTab.setContent(new Intent(this, SpendItemActivity.class));
        dayTab.setIndicator("일별");
        spendTabHost.addTab(dayTab);

        TabHost.TabSpec weekTab= spendTabHost.newTabSpec("주별 지출");
        weekTab.setContent(new Intent(this, SpendItemActivity.class));
        weekTab.setIndicator("주별");
        spendTabHost.addTab(weekTab);

        TabHost.TabSpec monthTab = spendTabHost.newTabSpec("달별 지출");
        monthTab.setContent(new Intent(this, SpendItemActivity.class));
        monthTab.setIndicator("달별");
        spendTabHost.addTab(monthTab);
    }
}
