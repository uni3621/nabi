package com.example.user.myapplication.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.user.myapplication.activity.SpendActivity;
import com.example.user.myapplication.util.Constants;

import java.util.Map;

/**
 * Created by Administrator on 2017-08-16.
 */

public class SpendGoalTask extends AsyncTask<Map<String, String>, Integer, String> {

    Context context;
    public SpendGoalTask(Context context){
        this.context =context;
    }

    @Override
    protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

// Http 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", Constants.nabiAddr+"spend/insertGoal");

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
        Log.i("목표삽입결과", s);
        ((SpendActivity)context).refreshList();
    }

}
