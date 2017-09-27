package com.hanium.bpc.nabi.activity;


import android.app.ActivityGroup;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.dialog.TrafficDialog;
import com.hanium.bpc.nabi.listener.AnimatedTabHostListener;
/**
 * Created by user on 2017-07-19.
 */

public class TrafficActivity extends ActivityGroup {


    public static Context context;
    final static String BOOKMARK = "즐겨찾기";
    final static String SURROUNDINGSEARCH = "주변검색";
    final static String SEARCH = "검색";
    TabHost trafficTabHost;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);
        context = this;
        typeface = Typeface.createFromAsset(getAssets(), "fonts/BM-HANNA.ttf");
        trafficTabHost = (TabHost)findViewById(R.id.trafficTabHost);
        trafficTabHost.setup(getLocalActivityManager());
        setupTab(new TextView(this), BOOKMARK);
        setupTab(new TextView(this), SEARCH);
        setupTab(new TextView(this), SURROUNDINGSEARCH);


        trafficTabHost.setCurrentTab(0);
        trafficTabHost.setOnTabChangedListener(new AnimatedTabHostListener(trafficTabHost));

    }

    private void setupTab(final View view, final String tag)
    {
    private void setupTab(final View view, final String tag) {
        View tabview = createTabView(trafficTabHost.getContext(), tag);

        // TabSpec은 공개된 생성자가 없으므로 직접 생성할 수 없으며, TabHost의 newTabSpec메서드로 생성
        TabHost.TabSpec setContent = trafficTabHost.newTabSpec(tag).setIndicator(tabview);

        if (tag.equals(BOOKMARK))
            setContent.setContent(new Intent(this, BookmarkActivity.class));
        else if (tag.equals(SEARCH))
            setContent.setContent(new Intent(this, SearchActivity.class));
        else if (tag.equals(SURROUNDINGSEARCH))
            setContent.setContent(new Intent(this, SurroundingSearchActivity.class));

        trafficTabHost.addTab(setContent);}
    public void searchList(View v) {
        Dialog dialog = new TrafficDialog(this);
        dialog.show();
        trafficTabHost.addTab(setContent);
    }
    public void searchList(View v) {
        Dialog dialog = new TrafficDialog(this);
        dialog.show();

    }




    // Tab에 나타날 View를 구성
    private View createTabView(final Context context, final String text)
    {
        // layoutinflater를 이용해 xml 리소스를 읽어옴
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_widget, null);
        ImageView img = (ImageView)view.findViewById(R.id.tabs_image);

        if(text.equals(BOOKMARK)){
            img.setImageResource(R.drawable.bookmark_selector);
        }else if(text.equals(SEARCH)) {
            img.setImageResource(R.drawable.search_selector);
        }else if(text.equals(SURROUNDINGSEARCH)){
            img.setImageResource(R.drawable.surroundingsearch_selector);

        }


        TextView tv = (TextView) view.findViewById(R.id.tabs_text);
        tv.setTypeface(typeface);
        tv.setText(text);
        return view;
    }


}
