package com.hanium.bpc.nabi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.adapter.SearchBusListAdapter;
import com.hanium.bpc.nabi.dto.BusDTO;
import com.hanium.bpc.nabi.util.Constants;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public class StationDetailActivity extends AppCompatActivity  implements MapView.MapViewEventListener{
    MapView mapView;
    SearchBusListAdapter adapter;
    ListView busListView;
    TextView detailStationName, detailRegionName, detailMobileNum;
    List<BusDTO> busList;
    String lat, lng;
    int markerIndex = 0;
    ViewGroup mapViewContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.station_detail);
        mapView = new MapView(this);
        mapView.setDaumMapApiKey(Constants.MAP_KEY);
        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        detailStationName = (TextView)findViewById(R.id.detailStationName);
        detailMobileNum = (TextView)findViewById(R.id.detailStationNum);
        detailRegionName = (TextView)findViewById(R.id.detailRegionNum);
        busListView = (ListView)findViewById(R.id.busListView);
        adapter = new SearchBusListAdapter();
        busListView.setAdapter(adapter);
        mapViewContainer.addView(mapView);

        Intent intent = getIntent();
        busList = (ArrayList<BusDTO>)intent.getSerializableExtra("busList");
        detailStationName.setText(intent.getStringExtra("stationName"));
        detailMobileNum.setText(intent.getStringExtra("mobileNo"));
        detailRegionName.setText(intent.getStringExtra("regionName"));
        lat = intent.getStringExtra("lat");
        lng = intent.getStringExtra("lng");
        mapView.setMapViewEventListener(this);

        for(int i=0; i<busList.size(); i++){
            adapter.addItem(busList.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapViewContainer.removeView(mapView);
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(lng),Double.parseDouble(lat)), true);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("정류장 위치");
        marker.setTag(markerIndex++);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(lng),Double.parseDouble(lat)));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }
}
