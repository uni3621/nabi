package com.example.user.myapplication.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.user.myapplication.R;
import com.example.user.myapplication.network.SpendEnrollTask;
import com.example.user.myapplication.user.UserInfo;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-07-22.
 */

public class SpendingInputDialog extends Dialog implements View.OnClickListener{
    TimePickerDialog.OnTimeSetListener listener;
    EditText spendTime, spendLoc, spendMoney, spendDay;
    TextView spendMonth;
    Button spendOk;
    String time, loc, money, day;
    int month, year;
    Context spendContext;
    public SpendingInputDialog(Context context, int month, int year){
        super(context);
        spendContext = context;
        this.month = month;
        this.year = year;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.spending_input);

        findViewById(R.id.spendCancle).setOnClickListener(this);
        spendTime = (EditText) findViewById(R.id.spendTime);
        spendLoc = (EditText) findViewById(R.id.spendLoc);
        spendMoney = (EditText)findViewById(R.id.spendMoney);
        spendDay =  (EditText)findViewById(R.id.spendDay);
        spendMonth = (TextView)findViewById(R.id.spendMonth);

        spendMonth.setText(month + "월");
        spendOk = (Button)findViewById(R.id.spendOk);
        spendTime.setFocusable(false);
        spendTime.setClickable(false);
        spendTime.setOnClickListener(this);
        spendOk.setOnClickListener(this);

        listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String minuteString = String.format("%02d", minute);
                String hourString = String.format("%02d", hourOfDay);
                spendTime.setText(hourString+ ":" + minuteString);
            }
        };

    }

    public boolean checkValid(){
        time = spendTime.getText().toString().trim();
        loc = spendLoc.getText().toString().trim();
        money = spendMoney.getText().toString().trim();
        day= spendDay.getText().toString().trim();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DATE, 1);
        int lastDay =  calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Log.i("lastDay", lastDay + "");
        if(day.equals("")){
            Toast.makeText(getContext(), "일을 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        int intDay = Integer.parseInt(day);
        if(!(intDay > 0 && intDay <= lastDay)){
            Toast.makeText(getContext(), "올바르지 않은 일입니다", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(time.equals("")){
            Toast.makeText(getContext(), "시간을 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(loc.equals("")){
            Toast.makeText(getContext(), "장소를 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(money.equals("")){
            Toast.makeText(getContext(), "금액을 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.spendOk:
                if(checkValid()) {
                    SpendEnrollTask spendEnrollTask = new SpendEnrollTask(this, spendOk, spendContext);
                    Map<String, String> params = new HashMap<>();
                    params.put("monthNum", month + "");
                    params.put("email", UserInfo.email);
                    params.put("spendDay", time);
                    params.put("location", loc);
                    params.put("spending", money);
                    params.put("day",day);
                    params.put("year", year +"");
                    spendEnrollTask.execute(params);
                    spendOk.setClickable(false);
                }
                break;
            case R.id.spendCancle:
                dismiss();
                break;
            case R.id.spendTime:
                TimePickerDialog dialog = new TimePickerDialog(getContext(), listener, 12, 00, true);
                dialog.show();
                break;
        }
    }
}
