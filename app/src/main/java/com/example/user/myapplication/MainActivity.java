package com.example.user.myapplication;

        import android.app.ActivityGroup;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.HeaderViewListAdapter;
        import android.widget.TabHost;

        import com.example.user.myapplication.activity.TrafficActivity;

public class MainActivity extends ActivityGroup {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost maintabHost = (TabHost)findViewById(R.id.mainTabHost);
        maintabHost.setup(getLocalActivityManager());

        TabHost.TabSpec homeTab = maintabHost.newTabSpec("Home Tab");
        homeTab.setContent(R.id.home_tab);
        homeTab.setIndicator("", getResources().getDrawable(R.drawable.home_icon));
        maintabHost.addTab(homeTab);

        TabHost.TabSpec trafficTab = maintabHost.newTabSpec("Traffic Tab");
        trafficTab.setContent(new Intent(this, TrafficActivity.class));
        trafficTab.setIndicator("", getResources().getDrawable(R.drawable.traffic_icon));
        maintabHost.addTab(trafficTab);

        TabHost.TabSpec weatherTab = maintabHost.newTabSpec("Weather Tab");
        weatherTab.setContent(R.id.weather_tab);
        weatherTab.setIndicator("", getResources().getDrawable(R.drawable.weather_icon));
        maintabHost.addTab(weatherTab);

        TabHost.TabSpec spendingTab = maintabHost.newTabSpec("Spending Tab");
        spendingTab.setContent(R.id.spending_tab);
        spendingTab.setIndicator("", getResources().getDrawable(R.drawable.spending_icon));
        maintabHost.addTab(spendingTab);

        TabHost.TabSpec mypageTab = maintabHost.newTabSpec("Mypage Tab");
        mypageTab.setContent(R.id.mypage_tab);
        mypageTab.setIndicator("", getResources().getDrawable(R.drawable.mypage_icon));
        maintabHost.addTab(mypageTab);

    }
}
