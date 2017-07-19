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

        TabHost maintabHost = (TabHost)findViewById(R.id.mainTabHost);
        maintabHost.setup();

        TabHost.TabSpec homeTab = maintabHost.newTabSpec("Home Tab");
        homeTab.setContent(R.id.home_tab);
        homeTab.setIndicator("", getResources().getDrawable(R.drawable.home_icon));
        maintabHost.addTab(homeTab);

        TabHost.TabSpec trafficTab = maintabHost.newTabSpec("Traffic Tab");
        trafficTab.setContent(R.id.traffic_tab);
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

        TabHost spendingtabHost = (TabHost)findViewById(R.id.spendingtabHost);
        spendingtabHost.setup();

        TabHost.TabSpec monthtab = spendingtabHost.newTabSpec("Month Tab");
        monthtab.setContent(R.id.month_Tab);
        monthtab.setIndicator("월별");
        spendingtabHost.addTab(monthtab);

        TabHost.TabSpec weektab = spendingtabHost.newTabSpec("Week Tab");
        weektab.setContent(R.id.week_Tab);
        weektab.setIndicator("주별");
        spendingtabHost.addTab(weektab);

        TabHost.TabSpec daytab = spendingtabHost.newTabSpec("Day Tab");
        daytab.setContent(R.id.day_Tab);
        daytab.setIndicator("일별");
        spendingtabHost.addTab(daytab);

    }
}
