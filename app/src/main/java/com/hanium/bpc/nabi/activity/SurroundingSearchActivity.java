package com.hanium.bpc.nabi.activity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.adapter.SearchTrafficListAdapter;
import com.hanium.bpc.nabi.dto.StationDTO;
import com.hanium.bpc.nabi.network.StationSelectTask;
import com.hanium.bpc.nabi.network.SurrondSearchTask;
import com.hanium.bpc.nabi.user.UserInfo;
import com.hanium.bpc.nabi.util.Constants;
import com.hanium.bpc.nabi.util.GpsInfo;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by user on 2017-08-16.
 */

public class SurroundingSearchActivity extends AppCompatActivity implements MapView.MapViewEventListener{
    String currentLocationAddress = "";
    TextView surroundAddress;
    GpsInfo gpsInfo;
    ListView surroundListView;
    SearchTrafficListAdapter adapter;
    MapView mapView;
    ViewGroup mapViewContainer;
    int markerIndex = 0;
    public static ProgressBar surroundProgressBar;
    private static final long MIN_CLICK_INTERVAL=600;
    private long mLastClickTime;
    @Override
    protected void onResume() {
        super.onResume();
        mapView = new MapView(this);
        mapView.setDaumMapApiKey(Constants.MAP_KEY);
        mapViewContainer = (ViewGroup) findViewById(R.id.surMap);
        mapViewContainer.addView(mapView);
        gpsInfo = new GpsInfo(this);
        if(!gpsInfo.isGetLocation()){
            Toast.makeText(this, "네트워크/GPS 상태를 확인해주세요", Toast.LENGTH_SHORT).show();
        }else{

            if(gpsInfo.getLatitude() == 0 || gpsInfo.getLatitude() == 0) {
                Toast.makeText(this, "위치정보를 받아 올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }else{
                surroundAddress.setText(findAddress(this,gpsInfo.getLatitude(), gpsInfo.getLongitude()));
                SurrondSearchTask surrondSearchTask = new SurrondSearchTask(this, adapter);
                Map<String, String> params = new HashMap<>();
                params.put("lat", gpsInfo.getLatitude()+"");
                params.put("lng",gpsInfo.getLongitude()+"");
                surrondSearchTask.execute(params);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("핸들러 실행",(gpsInfo.getLongitude()+":"+gpsInfo.getLatitude()) );
                        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(gpsInfo.getLatitude(),gpsInfo.getLongitude());
                        mapView.setMapCenterPoint(mapPoint, true);
                        MapPOIItem marker = new MapPOIItem();
                        marker.setItemName("현재 내 위치");
                        marker.setTag(100);
                        marker.setMapPoint(mapPoint);
                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                        mapView.addPOIItem(marker);
                    }
                }, 2000);


            }
        }
    }
    public void addMarkerToMap(List<StationDTO> stationList){
        for(int i=0; i<stationList.size(); i++){
            StationDTO station = stationList.get(i);

            MapPOIItem marker = new MapPOIItem();
            marker.setItemName(station.getStationName());
            marker.setTag(markerIndex++);
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(station.getLongitude()),Double.parseDouble(station.getLatitude())));
            marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setCustomImageResourceId(R.drawable.busballoon);
            marker.setCustomImageAutoscale(false);
            marker.setCustomImageAnchor(0.5f, 1.0f);
            mapView.addPOIItem(marker);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.removeItem();
        mapViewContainer.removeView(mapView);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surroundingsearh);
        surroundAddress = (TextView)findViewById(R.id.surrondAddress);
        surroundListView = (ListView)findViewById(R.id.surrondListView);
        surroundProgressBar = (ProgressBar)findViewById(R.id.surroundProgress);
        adapter = new SearchTrafficListAdapter();
        surroundListView.setAdapter(adapter);
        surroundListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);

                long currentClickTime= SystemClock.uptimeMillis();
                long elapsedTime=currentClickTime-mLastClickTime;
                mLastClickTime=currentClickTime;

                // 중복 클릭인 경우
                if(elapsedTime<=MIN_CLICK_INTERVAL){
                    return;
                }

                StationDTO dto = (StationDTO)parent.getItemAtPosition(position);
                int click = dto.getClick();
                if(click == 0) {
                    List<StationDTO> stationList = adapter.getStationList();
                    for(StationDTO station : stationList){
                            station.setClick(0);
                    }
                    MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(dto.getLongitude()), Double.parseDouble(dto.getLatitude()));
                    mapView.setMapCenterPoint(mapPoint, true);
                    dto.setClick(1);
                }else if(click == 1){
                    StationSelectTask stationSelectTask = new StationSelectTask(SurroundingSearchActivity.this, dto);
                    Map<String, String> params = new HashMap<>();
                    params.put("stationId", dto.getStationId());
                    params.put("email", UserInfo.email);
                    surroundProgressBar.bringToFront();;
                    surroundProgressBar.setVisibility(View.VISIBLE);
                    stationSelectTask.execute(params);
                }
            }
        });


    }

    private String findAddress(Context context, double lat, double lng) {
        StringBuffer bf = new StringBuffer();
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                // 세번째 인수는 최대결과값인데 하나만 리턴받도록 설정했다
                address = geocoder.getFromLocation(lat, lng, 1);
                // 설정한 데이터로 주소가 리턴된 데이터가 있으면
                if (address != null && address.size() > 0) {
                    // 주소
                    currentLocationAddress = address.get(0).getAddressLine(0).toString();

                    // 전송할 주소 데이터 (위도/경도 포함 편집)
                    bf.append(currentLocationAddress).append("#");
                    bf.append(lat).append("#");
                    bf.append(lng);
                }
            }

        } catch (IOException e) {
            Toast.makeText(context, "위치 정보를 받아 올 수 없습니다."
                    , Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
        return bf.toString();
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
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

