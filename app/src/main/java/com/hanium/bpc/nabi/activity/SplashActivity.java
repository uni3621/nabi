package com.hanium.bpc.nabi.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.network.LoginTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-07-19.
 */

public class SplashActivity extends AppCompatActivity{
    int checkstate = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PermissionListener permissionListener = new PermissionListener() {
            /**
             * 권한 허용시
             */
            @Override
            public void onPermissionGranted() {
                Log.i("퍼미션", "OK!");
                checkstate++;
                if (checkstate == 1) {
                    goPage();
                }
            }
            /**
             * 권한 거부시
             *
             * @param : deniedPermissions
             */
            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                checkstate++;
                if (checkstate == 1) {
                    goPage();

                }
            }


        };
        TedPermission tedPermission = new TedPermission();
        tedPermission.with(this).setPermissionListener(permissionListener)
                .setDeniedMessage("권한 거부시 이용이 원할하지 않습니다 [설정]->[권한]에서 허용해주세요")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();


    }
    public void goPage(){
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
