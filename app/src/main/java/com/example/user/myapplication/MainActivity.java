package com.example.user.myapplication;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.widget.HeaderViewListAdapter;
        import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = (TabHost)findViewById(R.id.tabHost1);
        tabHost.setup();

        TabHost.TabSpec homeTab = tabHost.newTabSpec("Home Tab");
        homeTab.setContent(R.id.home_tab);
        homeTab.setIndicator("", getResources().getDrawable(R.drawable.home_icon));
        tabHost.addTab(homeTab);

        TabHost.TabSpec trafficTab = tabHost.newTabSpec("Traffic Tab");
        trafficTab.setContent(R.id.traffic_tab);
        trafficTab.setIndicator("", getResources().getDrawable(R.drawable.traffic_icon));
        tabHost.addTab(trafficTab);

        TabHost.TabSpec weatherTab = tabHost.newTabSpec("Weather Tab");
        weatherTab.setContent(R.id.weather_tab);
        weatherTab.setIndicator("", getResources().getDrawable(R.drawable.weather_icon));
        tabHost.addTab(weatherTab);

        TabHost.TabSpec spendingTab = tabHost.newTabSpec("Spending Tab");
        spendingTab.setContent(R.id.spending_tab);
        spendingTab.setIndicator("", getResources().getDrawable(R.drawable.spending_icon));
        tabHost.addTab(spendingTab);

        TabHost.TabSpec mypageTab = tabHost.newTabSpec("Mypage Tab");
        mypageTab.setContent(R.id.mypage_tab);
        mypageTab.setIndicator("", getResources().getDrawable(R.drawable.mypage_icon));
        tabHost.addTab(mypageTab);

    }
}
