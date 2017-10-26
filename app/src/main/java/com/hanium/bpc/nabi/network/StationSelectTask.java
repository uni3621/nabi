package com.hanium.bpc.nabi.network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hanium.bpc.nabi.activity.SearchActivity;
import com.hanium.bpc.nabi.activity.StationDetailActivity;
import com.hanium.bpc.nabi.activity.SurroundingSearchActivity;
import com.hanium.bpc.nabi.dto.BusDTO;
import com.hanium.bpc.nabi.dto.StationDTO;
import com.hanium.bpc.nabi.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-08-16.
 */

public class StationSelectTask extends AsyncTask<Map<String, String>, Integer, String> {
    StationDTO dto;
    Context context;
    public StationSelectTask(Context context, StationDTO dto){
        this.context = context;
        this.dto = dto;
    }

    @Override
    protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

// Http 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", Constants.nabiAddr+"traffic/searchStation");

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
        List<BusDTO> busList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(s);
            if(jsonArray == null || jsonArray.length() == 0){
                Toast.makeText(context, "해당 정류장 정보가 없습니다", Toast.LENGTH_SHORT).show();
            }else{
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    busList.add(new BusDTO(obj.getString("locationNoOne"), obj.getString("locationNoTwo"), obj.getString("plateNoOne"),
                            obj.getString("plateNoTwo"), obj.getString("predictTimeOne"), obj.getString("predictTimeTwo")
                    ,obj.getString("routeId") ,obj.getString("staOrder"), null, obj.getString("busName"), obj.getString("busType"),
                            obj.getString("firstStation"), obj.getString("lastStation")));
                }
            }
            Intent intent = new Intent(context, StationDetailActivity.class);
            intent.putExtra("busList", (ArrayList<BusDTO>)busList);
            intent.putExtra("stationName", dto.getStationName());
            intent.putExtra("mobileNo", dto.getMobileNo());
            intent.putExtra("regionName", dto.getRegionName());
            intent.putExtra("lat", dto.getLatitude());
            intent.putExtra("lng", dto.getLongitude());

            if(SearchActivity.searchProgress != null) SearchActivity.searchProgress.setVisibility(View.GONE);
            if(SurroundingSearchActivity.surroundProgressBar != null) SurroundingSearchActivity.surroundProgressBar.setVisibility(View.GONE);
            context.startActivity(intent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
