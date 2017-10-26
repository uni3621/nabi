package com.hanium.bpc.nabi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.adapter.BusInfoListAdapter;
import com.hanium.bpc.nabi.dto.StationDTO;

import java.util.ArrayList;
import java.util.List;

public class BookInfoActivity extends AppCompatActivity {
    ListView stationListView;
    TextView routeName;
    BusInfoListAdapter busInfoListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_station_activity);
        routeName = (TextView)findViewById(R.id.busStationName);
        stationListView = (ListView)findViewById(R.id.stationListView);

        Intent intent = getIntent();
        String routeIdString = intent.getStringExtra("routeName");
        List<StationDTO>  busList = (ArrayList<StationDTO>)intent.getSerializableExtra("stationList");
        routeName.setText(routeIdString);

        busInfoListAdapter = new BusInfoListAdapter();
        stationListView.setAdapter(busInfoListAdapter);
        for(int i=0; i<busList.size(); i++){
            busInfoListAdapter.addItem(busList.get(i));
        }
        busInfoListAdapter.notifyDataSetChanged();


    }
}
