package com.hanium.bpc.nabi.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.network.SignTask;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignActivity extends AppCompatActivity {
    Toolbar signToolbar; // 회원가입 툴바
    Button signOkBtn; // 확인버튼
    //이메일, 비밀번호, 비밀번호 확인, 이름 입력
    EditText emailEdit, pwEdit, rePwEdit, nameEdit;
    String email, pw, rePw, name;
    private static final long MIN_CLICK_INTERVAL=600;

    private long mLastClickTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        signToolbar = (Toolbar)findViewById(R.id.signtool);
        signOkBtn = (Button)findViewById(R.id.signOkBtn);
        emailEdit = (EditText)findViewById(R.id.signEmail);
        pwEdit = (EditText)findViewById(R.id.signPw);
        rePwEdit = (EditText)findViewById(R.id.signRePw);
        nameEdit = (EditText)findViewById(R.id.signName);
        setSupportActionBar(signToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        /**
         * 확인버튼 이벤트처리
         */
        signOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValid()){
                    long currentClickTime= SystemClock.uptimeMillis();
                    long elapsedTime=currentClickTime-mLastClickTime;
                    mLastClickTime=currentClickTime;

                    // 중복 클릭인 경우
                    if(elapsedTime<=MIN_CLICK_INTERVAL){
                        return;
                    }
                    SignTask signTask = new SignTask(SignActivity.this);
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", pw);
                    params.put("name", name);

                    signTask.execute(params);
                }
            }
        });
    }

    /**
     * 회원가입 유효성 검사
     * @return 올바름 여부
     */
    private boolean checkValid(){
        email = emailEdit.getText().toString();
        pw = pwEdit.getText().toString();
        rePw = rePwEdit.getText().toString();
        name = nameEdit.getText().toString();

        if(email == null || email.trim().equals("")){
            Toast.makeText(SignActivity.this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
            emailEdit.requestFocus();
            return false;
        }
        if(pw == null || pw.trim().equals("")){
            Toast.makeText(SignActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            pwEdit.requestFocus();
            return false;
        }
        if(rePw == null || rePw.trim().equals("")){
            Toast.makeText(SignActivity.this, "비밀번호 확인을 입력하세요", Toast.LENGTH_SHORT).show();
            rePwEdit.requestFocus();
            return false;
        }
        if(name == null || name.trim().equals("")){
            Toast.makeText(SignActivity.this, "닉네임을 입력하세요", Toast.LENGTH_SHORT).show();
            nameEdit.requestFocus();
            return false;
        }
        if(!Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+",email.trim())){
            Toast.makeText(SignActivity.this, "이메일 형식이 맞지 않습니다", Toast.LENGTH_SHORT).show();
            emailEdit.requestFocus();
            return false;
        }
        if(!pw.equals(rePw)){
            Toast.makeText(SignActivity.this, "비밀번호가 일치해야 합니다", Toast.LENGTH_SHORT).show();
            rePwEdit.requestFocus();
            return false;
        }
        return true;
    }
    /**
     * 툴바 뒤로가기 셋팅
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
