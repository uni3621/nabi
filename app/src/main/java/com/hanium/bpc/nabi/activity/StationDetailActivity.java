package com.hanium.bpc.nabi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.adapter.SearchBusListAdapter;
import com.hanium.bpc.nabi.dto.BusDTO;
import com.hanium.bpc.nabi.network.BusSelectTask;
import com.hanium.bpc.nabi.util.Constants;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationDetailActivity extends AppCompatActivity  implements MapView.MapViewEventListener{
    private static final long MIN_CLICK_INTERVAL=600;
    private long mLastClickTime;

    MapView mapView;
    SearchBusListAdapter adapter;
    ListView busListView;
    TextView detailStationName, detailRegionName, detailMobileNum;
    List<BusDTO> busList;
    String lat, lng;
    int markerIndex = 0;
    ViewGroup mapViewContainer;
    public static ProgressBar busInfoProgress;

    @Override
    protected void onResume() {
        super.onResume();
        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapView = new MapView(this);
        mapView.setDaumMapApiKey(Constants.MAP_KEY);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);

        for(int i=0; i<busList.size(); i++){
            adapter.addItem(busList.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("시작", "띠로리!!!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.station_detail);

        Intent intent = getIntent();
        detailStationName = (TextView)findViewById(R.id.detailStationName);
        detailMobileNum = (TextView)findViewById(R.id.detailStationNum);
        detailRegionName = (TextView)findViewById(R.id.detailRegionNum);
        busListView = (ListView)findViewById(R.id.busListView);
        busInfoProgress = (ProgressBar)findViewById(R.id.busInfoProgress);

        String mobileNo = intent.getStringExtra("mobileNo");
        adapter = new SearchBusListAdapter(intent.getStringExtra("stationId"));
        busListView.setAdapter(adapter);




        //전달받은 액티비티값 받아서 활용하기
        busList = (ArrayList<BusDTO>)intent.getSerializableExtra("busList");
        detailStationName.setText(intent.getStringExtra("stationName"));
        detailMobileNum.setText(mobileNo);
        detailRegionName.setText(intent.getStringExtra("regionName"));

        lat = intent.getStringExtra("lat");
        lng = intent.getStringExtra("lng");



        busListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                long currentClickTime= SystemClock.uptimeMillis();
                long elapsedTime=currentClickTime-mLastClickTime;
                mLastClickTime=currentClickTime;

                // 중복 클릭인 경우
                if(elapsedTime<=MIN_CLICK_INTERVAL){
                    return;
                }
                BusDTO busDTO = (BusDTO)parent.getItemAtPosition(position);
                String routeId = busDTO.getRouteId();
                String routeName = busDTO.getBusName();

                Log.i("BUSNAME", routeName + "");
                BusSelectTask selectTask = new BusSelectTask(StationDetailActivity.this, routeName, busDTO);
                Map<String, String> params = new HashMap<>();
                params.put("routeId", routeId);

//                view.setClickable(false);

                busInfoProgress.bringToFront();
                busInfoProgress.setVisibility(View.VISIBLE);
                selectTask.execute(params);
            }
        });
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
