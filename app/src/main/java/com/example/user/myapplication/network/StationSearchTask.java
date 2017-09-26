package com.example.user.myapplication.network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.user.myapplication.activity.SearchTrafficActivity;
import com.example.user.myapplication.dto.StationDTO;
import com.example.user.myapplication.util.Constants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by YTK on 2017-09-22.
 */

public class StationSearchTask extends AsyncTask<String, Void, ArrayList<StationDTO>> {
    Context context;
    public StationSearchTask(Context context){
        this.context = context;
    }
    @Override
    protected ArrayList<StationDTO> doInBackground(String... params) {
        String param = null;
        try {
            param = URLEncoder.encode(params[0], "UTF-8");
        }catch(Exception e){
            param = params[0];
        }
        String url = "http://openapi.gbis.go.kr/ws/rest/busstationservice?serviceKey="+ Constants.busKey+"&keyword="+param;
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
                        if(resultStartTag.equals("busStationList")){
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
                        if(resultEndTag.equals("busStationList")){
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
        Intent intent = new Intent(context, SearchTrafficActivity.class);
        intent.putExtra("stationList", stationDTO);
        context.startActivity(intent);
    }
}
