package com.example.user.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.user.myapplication.R;
import com.example.user.myapplication.network.LoginTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-07-19.
 */

public class SplashActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {



                SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", 0);
                String loginString = sharedPreferences.getString("email", null);

                //자동로그인 기능
                if(loginString != null){

                    Map<String, String> params = new HashMap<>();
                    params.put("email", loginString);
                    params.put("password", sharedPreferences.getString("password", null));
                    LoginTask loginTask = new LoginTask(SplashActivity.this, params);

                    loginTask.execute(params);
                }else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        }, 2000);
    }
}
