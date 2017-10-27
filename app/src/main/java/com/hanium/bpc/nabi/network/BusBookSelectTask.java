package com.hanium.bpc.nabi.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hanium.bpc.nabi.adapter.BookBusListAdapter;
import com.hanium.bpc.nabi.dto.BusDTO;
import com.hanium.bpc.nabi.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by YTK on 2017-10-26.
 */

public class BusBookSelectTask extends AsyncTask<Map<String, String>, Integer, String> {

    Context context;
    BookBusListAdapter bookBusListAdapter;
    public BusBookSelectTask(Context context, BookBusListAdapter bookBusListAdapter){
        this.bookBusListAdapter = bookBusListAdapter;
    }

    @Override
    protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

// Http 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", Constants.nabiAddr+"traffic/selectBookBus");

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
//                Toast.makeText(context, "버스 정보가 없습니다.", Toast.LENGTH_SHORT).show();
            }else{
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    busList.add(new BusDTO(null, null, null,
                            null, obj.getString("predictTimeOne"), obj.getString("predictTimeTwo")
                            ,obj.getString("routeId") ,null, obj.getString("stationId"), obj.getString("busName"), obj.getString("busType"),
                            obj.getString("firstStation"), obj.getString("lastStation"), obj.getString("upFirstTime"),
                            obj.getString("upLastTime"), obj.getString("downFirstTime"), obj.getString("downLastTime"),
                            obj.getString("peekAlloc"), obj.getString("nPeekAlloc"), obj.getBoolean("book"), obj.getString("stationName")));

                }

            }
            for(int i=0; i<busList.size(); i++){
                bookBusListAdapter.addItem(busList.get(i));
            }
            bookBusListAdapter.notifyDataSetChanged();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

