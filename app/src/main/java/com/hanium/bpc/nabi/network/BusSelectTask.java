package com.hanium.bpc.nabi.network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hanium.bpc.nabi.activity.BookInfoActivity;
import com.hanium.bpc.nabi.activity.StationDetailActivity;
import com.hanium.bpc.nabi.dto.StationDTO;
import com.hanium.bpc.nabi.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by YTK on 2017-10-26.
 */

public class BusSelectTask extends AsyncTask<Map<String, String>, Integer, String> {

    Context context;
    String routeName;
    public BusSelectTask(Context context, String routeName){
        this.context = context;
        this.routeName = routeName;
    }

    @Override
    protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

// Http 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", Constants.nabiAddr+"traffic/busInfo");

// Parameter 를 전송한다.
        http.addAllParameters(maps[0]);

        Log.i("파라미터", maps[0] +"");
//Http 요청 전송
        HttpClient post = http.create();
        post.request();

// 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode();

// 응답 본문 가져오기
        String body = post.getBody();

        return body;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.i("결과", s+"");
        List<StationDTO> stationList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(s);
            if(jsonArray == null || jsonArray.length() == 0){
                Toast.makeText(context, "버스 정보가 없습니다.", Toast.LENGTH_SHORT).show();
            }else{
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    StationDTO stationDTO = new StationDTO();
                    stationDTO.setPlateNo(obj.getString("plateNo"));

                    if(obj.getBoolean("bus")){
                        stationDTO.setBus(true);
                    }else{
                        stationDTO.setBus(false);
                    }
                    stationDTO.setRouteId(obj.getString("routeId"));
                    stationDTO.setStationId(obj.getString("stationId"));
                    stationDTO.setStationName(obj.getString("stationName"));
                    stationDTO.setOrder(Integer.parseInt(obj.getString("order")));
                    stationList.add(stationDTO);
                }
            }
            Intent intent = new Intent(context, BookInfoActivity.class);
            intent.putExtra("routeName", routeName);
            intent.putExtra("stationList", (ArrayList<StationDTO>)stationList);
            if(StationDetailActivity.busInfoProgress != null) StationDetailActivity.busInfoProgress.setVisibility(View.GONE);
            context.startActivity(intent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

