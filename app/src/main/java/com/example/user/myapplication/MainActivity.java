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

        TabHost.TabSpec trafficTab = tabHost.newTabSpec("Traffic tab");
        trafficTab.setContent(R.id.traffic_tab);
        trafficTab.setIndicator("TRAFFIC");
        tabHost.addTab(trafficTab);

    }
}
