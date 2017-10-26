package com.hanium.bpc.nabi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.dto.StationDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YTK on 2017-09-24.
 */

public class SearchTrafficActivity extends AppCompatActivity{
    List<StationDTO> stationList;

    Toolbar toolbar;
    public SearchTrafficActivity(){
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_traffic);

        toolbar = (Toolbar)findViewById(R.id.trafficSearchToolBar);

        stationList = (ArrayList<StationDTO>)(getIntent().getSerializableExtra("stationList"));
        toolbar.setTitle("총 " + stationList.size() + "개가 검색되었습니다.");
        Log.i("전달받은 arrayList", stationList.toString());

    }
}
