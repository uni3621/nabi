package com.example.user.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.myapplication.MainActivity;
import com.example.user.myapplication.R;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn, signBtn;
    public static boolean signState = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        signBtn = (Button)findViewById(R.id.signBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
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
}
