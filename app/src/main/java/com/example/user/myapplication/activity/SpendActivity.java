package com.example.user.myapplication.activity;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.adapter.SpendListAdapter;
import com.example.user.myapplication.dialog.SpendingInputDialog;
import com.example.user.myapplication.network.SpendGoalTask;
import com.example.user.myapplication.network.SpendSelectTask;
import com.example.user.myapplication.user.UserInfo;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SpendActivity extends ActivityGroup implements View.OnClickListener{
    ListView spendListView;
    SpendListAdapter spendListAdapter;
    TextView dateText, spendGoal;
    ImageView spendLeft, spendRight;
    Calendar calendar;
    FloatingActionButton fab;
    final String[] items = {"지출목표 설정", "지출내역 추가"};
    AlertDialog alertDialog;
    AlertDialog.Builder goalDialogBulder;
    AlertDialog goalDialog;
    ProgressBar goalBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend);

        setup();
        setupGoalDialog();
        spendListAdapter = new SpendListAdapter(this, calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH)+1));
        spendListView.setAdapter(spendListAdapter);

//        spendListAdapter.addItem("13", "2017.08", "일요일", 25710);
//        SpendListItem listItem = (SpendListItem)spendListAdapter.getItem(0);
//

//        ListView subListView = listItem.getListView();
//        SpendSubListAdapter spendSubListAdapter = new SpendSubListAdapter();
//        subListView.setAdapter(spendSubListAdapter);
//        spendSubListAdapter.addItem("수원인계동", "닭갈비맛있게 먹었음", 19200);

//        Log.i("정보", ((SpendListItem) spendListAdapter.getItem(0)).getListView().getAdapter().getCount() + "개");
//        spendSubListAdapter.notifyDataSetChanged();;
//        spendListAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    public void setup(){
        calendar = Calendar.getInstance();

        spendListView = (ListView)findViewById(R.id.spend_list_view);
        dateText = (TextView)findViewById(R.id.dateText);
        spendGoal = (TextView)findViewById(R.id.spendGoal);
        spendLeft = (ImageView)findViewById(R.id.spendLeft);
        spendRight  = (ImageView)findViewById(R.id.spendRight);
        fab = (FloatingActionButton)findViewById(R.id.spendFab);
        goalBar = (ProgressBar)findViewById(R.id.spendGoalBar);


        calendar.set(Calendar.DATE, 1);
        dateText.setText(calendar.get(Calendar.YEAR) +"년 " + (calendar.get(Calendar.MONTH)+1) +"월");

        spendLeft.setOnClickListener(this);
        spendRight.setOnClickListener(this);
        fab.setOnClickListener(this);

        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setTitle("원하는 명령을 선택하세요");
        alBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        goalDialog.show();
                        break;
                    case 1:
                        SpendingInputDialog spendingInputDialog = new SpendingInputDialog(SpendActivity.this, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
                        spendingInputDialog.show();
                        break;
                }
            }
        });
        alertDialog = alBuilder.create();
    }

    /**
     * 지출 목표 설정
     */
    public void setupGoalDialog(){
        goalDialogBulder = new AlertDialog.Builder(this);

        goalDialogBulder.setTitle((calendar.get(Calendar.MONTH) + 1) + "월 지출 목표를 설정해주세요");
        final EditText et = new EditText(this);
        et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        goalDialogBulder.setView(et);

// 확인 버튼 설정
        goalDialogBulder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String money = et.getText().toString();
                if(money.trim().equals("")){
                    dialog.dismiss();
                }else{
                    SpendGoalTask spendGoalTask = new SpendGoalTask(SpendActivity.this);
                    Map<String, String> params = new HashMap<>();
                    params.put("email", UserInfo.email);
                    params.put("year", calendar.get(Calendar.YEAR) + "");
                    params.put("monthNum", (calendar.get(Calendar.MONTH)+1) + "");
                    params.put("spending", money);
                    spendGoalTask.execute(params);
                    dialog.dismiss();
                }

            }
        });
// 취소 버튼 설정
        goalDialogBulder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();     //닫기
                // Event
            }
        });
        goalDialog = goalDialogBulder.create();

    }

    /**
     * 지출 목록 갱신(서버와 통신)
     */
    public void refreshList(){
        SpendSelectTask spendSelectTask = new SpendSelectTask(spendListAdapter, calendar.get(Calendar.MONTH)+1,  calendar.get(Calendar.YEAR), spendGoal, goalBar);
        Map<String, String> params = new HashMap<String, String>();
        params.put("year", calendar.get(Calendar.YEAR) + "");
        params.put("monthNum", (calendar.get(Calendar.MONTH)+1) + "");
        params.put("email", UserInfo.email);
        spendSelectTask.execute(params);
    }


    /**
     * 클릭리스너
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.spendLeft:
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
                dateText.setText(calendar.get(Calendar.YEAR) +"년 " + (calendar.get(Calendar.MONTH)+1) +"월");
                refreshList();
                break;
            case R.id.spendRight:
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
                dateText.setText(calendar.get(Calendar.YEAR) +"년 " + (calendar.get(Calendar.MONTH)+1) +"월");
                refreshList();
                break;
            case R.id.spendFab:
                alertDialog.show();
                break;
        }
    }
}
