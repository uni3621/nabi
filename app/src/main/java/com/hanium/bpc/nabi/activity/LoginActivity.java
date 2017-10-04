package com.hanium.bpc.nabi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.network.LoginTask;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn, signBtn;
    EditText loginId, loginPw;
    String email, password;
    public static boolean signState = true;
    public static ProgressBar loginProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        signBtn = (Button)findViewById(R.id.signBtn);
        loginId = (EditText)findViewById(R.id.loginId);
        loginPw = (EditText)findViewById(R.id.loginPw);
        loginProgress = (ProgressBar)findViewById(R.id.loginProgress);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValid()) {
                    LoginActivity.loginProgress.bringToFront();
                    LoginActivity.loginProgress.setVisibility(View.VISIBLE);
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
                    LoginTask loginTask = new LoginTask(LoginActivity.this, params);

                    loginTask.execute(params);
                }
            }
        });
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null && signState) {
            String finish = bundle.getString("FINISH"); // 회원가입 완료 메세지
            if (finish != null) {
                Toast.makeText(this, "이메일을 인증을 완료하세요!", Toast.LENGTH_LONG).show();
                signState = false;
            }
        }
    }

    /**
     * 로그인 유효성 검사
     * @return
     */
    private boolean checkValid(){
        email =  loginId.getText().toString();
        password = loginPw.getText().toString();
        if(email == null || email.trim().equals("")){
            Toast.makeText(this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
            loginId.requestFocus();
            return false;
        }
        if(password == null || password.trim().equals("")){
            Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            loginPw.requestFocus();
            return false;
        }
        return true;
    }
}
