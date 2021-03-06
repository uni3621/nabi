package com.hanium.bpc.nabi.network;

import android.os.AsyncTask;
import android.util.Log;

import com.hanium.bpc.nabi.util.Constants;

import java.util.Map;

/**
 * Created by YTK on 2017-10-26.
 */

public class BusBookIDeleteTask extends AsyncTask<Map<String, String>, Integer, String> {

    public BusBookIDeleteTask(){
    }

    @Override
    protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

// Http 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", Constants.nabiAddr+"traffic/deleteBusBook");

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
    }

}

