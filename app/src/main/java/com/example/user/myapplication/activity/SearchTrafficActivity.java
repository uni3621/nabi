package com.example.user.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.adapter.SearchTrafficListAdapter;
import com.example.user.myapplication.dto.StationDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YTK on 2017-09-24.
 */

public class SearchTrafficActivity extends AppCompatActivity{
    List<StationDTO> stationList;
    ListView stationListView;
    SearchTrafficListAdapter adapter;
    Toolbar toolbar;
    public SearchTrafficActivity(){
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_traffic);

        toolbar = (Toolbar)findViewById(R.id.trafficSearchToolBar);
        stationListView= (ListView)findViewById(R.id.searchTrafficResultList);
        stationList = (ArrayList<StationDTO>)(getIntent().getSerializableExtra("stationList"));
        toolbar.setTitle("총 " + stationList.size() + "개가 검색되었습니다.");
        Log.i("전달받은 arrayList", stationList.toString());
        adapter = new SearchTrafficListAdapter();

        stationListView.setAdapter(adapter);

        for(int i=0; i<stationList.size(); i++){
            adapter.addItem(stationList.get(i));
        }
        adapter.notifyDataSetChanged();
    }
}
