package com.hanium.bpc.nabi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.adapter.BusInfoListAdapter;
import com.hanium.bpc.nabi.dto.BusDTO;
import com.hanium.bpc.nabi.dto.StationDTO;

import java.util.ArrayList;
import java.util.List;

public class BookInfoActivity extends AppCompatActivity {
    ListView stationListView;
    TextView routeName, upFirstTime, upLastTime, downFirstTime, downLastTime, allocTime, pAllocTime;
    BusInfoListAdapter busInfoListAdapter;
    BusDTO busDTO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_station_activity);
        routeName = (TextView)findViewById(R.id.busStationName);
        stationListView = (ListView)findViewById(R.id.stationListView);
        upFirstTime = (TextView)findViewById(R.id.upFirstTime);
        upLastTime = (TextView)findViewById(R.id.upLastTime);
        downFirstTime = (TextView)findViewById(R.id.downFirstTime);
        downLastTime = (TextView)findViewById(R.id.downLastTime);
        allocTime = (TextView)findViewById(R.id.allocTime);
        pAllocTime = (TextView)findViewById(R.id.pAllocTime);

        Intent intent = getIntent();
        busDTO = (BusDTO)intent.getSerializableExtra("busDTO");
setBusText();

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
    public void setBusText(){
        upFirstTime.setText(busDTO.getUpFirstTime());
        upLastTime.setText(busDTO.getUpLastTime());
        downFirstTime.setText(busDTO.getDownFirstTime());;
        downLastTime.setText(busDTO.getDownLastTime());
        allocTime.setText(busDTO.getPeekAlloc() + "분");
        pAllocTime.setText(busDTO.getnPeekAlloc() + "분");
    }
}
