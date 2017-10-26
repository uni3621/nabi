package com.hanium.bpc.nabi.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.adapter.SearchTrafficListAdapter;
import com.hanium.bpc.nabi.dto.StationDTO;
import com.hanium.bpc.nabi.network.StationSearchTask;
import com.hanium.bpc.nabi.network.StationSelectTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017-08-16.
 */

public class SearchActivity extends AppCompatActivity  implements View.OnClickListener{
    private static final long MIN_CLICK_INTERVAL=600;

    private long mLastClickTime;

    ListView stationListView;
    SearchTrafficListAdapter adapter;
    TextView searchTrafficBtn;
    EditText inputTrafficText;
    public static ProgressBar searchProgress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        stationListView= (ListView)findViewById(R.id.searchTrafficResultList);
        searchTrafficBtn = (TextView)findViewById(R.id.searchTrafficBtn);
        inputTrafficText = (EditText)findViewById(R.id.inputTrafficText);
        searchProgress = (ProgressBar)findViewById(R.id.searchProgress);

        searchTrafficBtn.setOnClickListener(this);
        adapter = new SearchTrafficListAdapter();
        stationListView.setAdapter(adapter);
        stationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                view.setSelected(true);
                long currentClickTime= SystemClock.uptimeMillis();
                long elapsedTime=currentClickTime-mLastClickTime;
                mLastClickTime=currentClickTime;

                // 중복 클릭인 경우
                if(elapsedTime<=MIN_CLICK_INTERVAL){
                    return;
                }
                StationDTO dto = (StationDTO)parent.getItemAtPosition(position);
                StationSelectTask selectTask = new StationSelectTask(SearchActivity.this, dto);
                Map<String, String> params = new HashMap<>();
                params.put("stationId", dto.getStationId());

//                view.setClickable(false);

                searchProgress.bringToFront();
                searchProgress.setVisibility(View.VISIBLE);
                selectTask.execute(params);

                //startActivity(new Intent(SearchActivity.this, StationDetailActivity.class));
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.searchTrafficBtn:
                StationSearchTask stationSearchTask = new StationSearchTask(this, adapter);
                searchProgress.bringToFront();
                searchProgress.setVisibility(View.VISIBLE);
                adapter.removeItem();
                stationSearchTask.execute(inputTrafficText.getText().toString());
        }
    }
}
