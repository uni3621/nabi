package com.example.user.myapplication.network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.user.myapplication.activity.LoginActivity;
import com.example.user.myapplication.activity.SignActivity;
import com.example.user.myapplication.util.Constants;

import java.util.Map;

/**
 * Created by Administrator on 2017-08-16.
 */

public class SignTask extends AsyncTask<Map<String, String>, Integer, String> {

    Context context;
    public SignTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

// Http 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", Constants.nabiAddr+"member/insert");

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
        int result = Integer.parseInt(s);

        if(result > 0) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("FINISH", "FINISH");
            context.startActivity(intent);
            ((SignActivity) context).finish();
            LoginActivity.signState = true;
        }else if(result == -1){
            Toast.makeText(context, "이메일이 중복입니다.", Toast.LENGTH_LONG).show();
        }
    }

}
