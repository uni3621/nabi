package com.example.user.myapplication.network;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.myapplication.adapter.SpendListAdapter;
import com.example.user.myapplication.dto.SpendDTO;
import com.example.user.myapplication.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-08-16.
 */

public class SpendSelectTask extends AsyncTask<Map<String, String>, Integer, String> {

    SpendListAdapter spendListAdapter;
    int month;
    int year;
    TextView spendGoalText;
    ProgressBar goalBar;
    public SpendSelectTask(SpendListAdapter spendListAdapter, int month, int year, TextView spendGoalText, ProgressBar goalBar){
        this.month = month;
        this.year = year;
        this.spendListAdapter = spendListAdapter;
        this.spendGoalText = spendGoalText;
        this.goalBar = goalBar;
    }

    @Override
    protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

// Http 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", Constants.nabiAddr+"spend/select");

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
        try {
            JSONObject jsonObject = new JSONObject(s);
            int monthSum = 0;
            List<SpendDTO>[] list = new ArrayList[31];
            spendListAdapter.removeItem();
            JSONArray jsonArray = jsonObject.getJSONArray("spendList");
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                String day = obj.getString("day");
                int dayInt = Integer.parseInt(day);
                if(list[dayInt-1] == null){
                    list[dayInt-1] = new ArrayList<SpendDTO>();
                }
                String spendDayObj = obj.getString("spendDay");
                String dayObj = obj.getString("day");
                String locObj = obj.getString("location");
                int spendingObj = obj.getInt("spending");
                list[dayInt-1].add(new SpendDTO(null, spendDayObj, locObj, spendingObj, month+"", dayObj, null));
            }
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month-1);
            for(int i=0; i<31; i++){
                if(list[i] != null){
                    SpendDTO spendDTO = list[i].get(0);
                    calendar.set(Calendar.DATE, Integer.parseInt(spendDTO.getDay()));
                    int sum = 0;
                    for(int j=0; j<list[i].size(); j++){
                        sum += list[i].get(j).getSpending();
                    }
                    monthSum += sum;
                    spendListAdapter.addItem(spendDTO.getDay(), year+"."+month, Constants.yoil[calendar.get(Calendar.DAY_OF_WEEK) -1], sum, list[i]);
                }
            }

            spendListAdapter.notifyDataSetChanged();

            if(jsonObject.isNull("spendGoal")){
                spendGoalText.setText("지출 달성도(등록되지 않음)");
                goalBar.setProgress(0);
            }else{
                JSONObject spendGoal = jsonObject.getJSONObject("spendGoal");
                int goalSpending = spendGoal.getInt("spending");
                spendGoalText.setText("지출달성도("+monthSum+"/"+goalSpending+")");
                goalBar.setProgress((int)((monthSum/(double)goalSpending) * 100));
            }
        }catch(Exception e){
            Log.e("E", e.getMessage());
        }
    }

}
