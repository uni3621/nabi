package com.example.user.myapplication;


import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.user.myapplication.activity.HomeActivity;
import com.example.user.myapplication.activity.MyPageActivity;
import com.example.user.myapplication.activity.SpendActivity;
import com.example.user.myapplication.activity.TrafficActivity;
import com.example.user.myapplication.activity.WeatherActivity;
import com.example.user.myapplication.listener.AnimatedTabHostListener;


public class MainActivity extends ActivityGroup {
    public static Context context;
    final static String HOME = "메인화면";
    final static String TRRAFIC = "교통정보";
    final static String WEATHER = "날씨정보";
    final static String SPENDING = "지출관리";
    final static String MYPAGE = "마이페이지";
    TabHost mainTabHost;
    Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        typeface = Typeface.createFromAsset(getAssets(), "fonts/BM-HANNA.ttf");
        mainTabHost = (TabHost)findViewById(R.id.mainTabHost);
        mainTabHost.setup(getLocalActivityManager());
        setupTab(new TextView(this), HOME);
        setupTab(new TextView(this), TRRAFIC);
        setupTab(new TextView(this), WEATHER);
        setupTab(new TextView(this), SPENDING);
        setupTab(new TextView(this), MYPAGE);
        mainTabHost.setCurrentTab(0);
        mainTabHost.setOnTabChangedListener(new AnimatedTabHostListener(mainTabHost));

    }

    private void setupTab(final View view, final String tag)
    {
        View tabview = createTabView(mainTabHost.getContext(), tag);

        // TabSpec은 공개된 생성자가 없으므로 직접 생성할 수 없으며, TabHost의 newTabSpec메서드로 생성
        TabHost.TabSpec setContent = mainTabHost.newTabSpec(tag).setIndicator(tabview);

        if(tag.equals(HOME))
            setContent.setContent(new Intent(this, HomeActivity.class));
        else if(tag.equals(TRRAFIC))
            setContent.setContent(new Intent(this, TrafficActivity.class));
        else if(tag.equals(WEATHER))
            setContent.setContent(new Intent(this, WeatherActivity.class));
        else if(tag.equals(SPENDING))
            setContent.setContent(new Intent(this, SpendActivity.class));
        else if(tag.equals(MYPAGE))
            setContent.setContent(new Intent(this, MyPageActivity.class));

        mainTabHost.addTab(setContent);

    }
    // Tab에 나타날 View를 구성
    private View createTabView(final Context context, final String text)
    {
        // layoutinflater를 이용해 xml 리소스를 읽어옴
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_widget, null);
        ImageView img = (ImageView)view.findViewById(R.id.tabs_image);

        if(text.equals(HOME)){
            img.setImageResource(R.drawable.home_selector);
        }else if(text.equals(TRRAFIC)){
            img.setImageResource(R.drawable.traffic_selector);
        }else if(text.equals(WEATHER)){
            img.setImageResource(R.drawable.weather_selector);
        }else if(text.equals(SPENDING)){
            img.setImageResource(R.drawable.spend_selector);
        }else if(text.equals(MYPAGE)){
            img.setImageResource(R.drawable.my_selector);
        }


        TextView tv = (TextView) view.findViewById(R.id.tabs_text);
        tv.setTypeface(typeface);
        tv.setText(text);
        return view;
    }


}
