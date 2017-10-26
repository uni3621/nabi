package com.hanium.bpc.nabi.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.hanium.bpc.nabi.activity.SurroundingSearchActivity;
import com.hanium.bpc.nabi.adapter.SearchTrafficListAdapter;
import com.hanium.bpc.nabi.dto.StationDTO;
import com.hanium.bpc.nabi.util.Constants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by YTK on 2017-09-22.
 */

public class SurrondSearchTask extends AsyncTask<Map<String, String>, Void, ArrayList<StationDTO>> {
    Context context;
    SearchTrafficListAdapter adapter;

    public SurrondSearchTask(Context context, SearchTrafficListAdapter adapter){
        this.context = context;
        this.adapter = adapter;
    }
    @Override
    protected ArrayList<StationDTO> doInBackground(Map<String, String>... params) {
        String lat = params[0].get("lat");
        String lng = params[0].get("lng");
        String url = "http://openapi.gbis.go.kr/ws/rest/busstationservice/searcharound?serviceKey="+ Constants.busKey+"&x="+lng+"&y="+lat;
        Log.i("요청URL", url);
        XmlPullParserFactory factory;
        XmlPullParser parser;
        URL xmlUrl;
        ArrayList<StationDTO> stationList = new ArrayList<>();
        StationDTO returnResult = null;
        try {

            boolean [] stationFlag = new boolean[6];
            boolean oneBus = false;
            String[] stationAttribute = {"stationId", "stationName", "mobileNo", "regionName", "x", "y"};
            xmlUrl = new URL(url);
            xmlUrl.openConnection().getInputStream();
            factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
            parser.setInput(xmlUrl.openStream(), "utf-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String resultStartTag = parser.getName();
                        Log.i("현재 태그", resultStartTag);
                        if(resultStartTag.equals("busStationAroundList")){
                            oneBus = true;
                            returnResult = new StationDTO();
                        }else {
                            for (int i = 0; i < stationAttribute.length; i++) {
                                if (stationAttribute[i].equals(resultStartTag)) {
                                    stationFlag[i] = true;
                                    break;
                                }
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        String resultEndTag = parser.getName();
                        if(resultEndTag.equals("busStationAroundList")){
                            oneBus = false;
                            stationList.add(returnResult);
                            returnResult = null;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        String resultText = parser.getText();
                        Log.i("현재 메세지", resultText);
                        int resultIndex = -1;
                        for(int i=0; i<stationFlag.length; i++){
                            if(stationFlag[i]){
                                stationFlag[i] = false;
                                resultIndex = i;
                                break;
                            }
                        }
                        switch(resultIndex){
                            case 0: returnResult.setStationId(resultText);break;
                            case 1: returnResult.setStationName(resultText);break;
                            case 2: returnResult.setMobileNo(resultText);break;
                            case 3: returnResult.setRegionName(resultText);break;
                            case 4: returnResult.setLatitude(resultText);break;
                            case 5: returnResult.setLongitude(resultText);break;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            Log.e("STATION_TASK", e.getMessage());
        }

        return stationList;
    }

    @Override
    protected void onPostExecute(ArrayList<StationDTO> stationDTO) {
        Log.i("STATION_RESULT", stationDTO.toString());
        for(int i=0; i<stationDTO.size(); i++){
            adapter.addItem(stationDTO.get(i));
        }
        adapter.notifyDataSetChanged();
        Toast.makeText(context, "총 " + stationDTO.size() + "건이 검색되었습니다.", Toast.LENGTH_SHORT).show();
        ((SurroundingSearchActivity)context).addMarkerToMap(stationDTO);
    }
}
