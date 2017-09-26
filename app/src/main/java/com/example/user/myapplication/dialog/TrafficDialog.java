package com.example.user.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.myapplication.R;
import com.example.user.myapplication.network.StationSearchTask;

/**
 * Created by user on 2017-07-22.
 */

public class TrafficDialog extends Dialog implements View.OnClickListener{
    EditText trafficEditText;
    Button trraficSearchBtn;
    Context context;

    public TrafficDialog(Context context) {
        super(context);
        this.context = context;
        this.setContentView(R.layout.activity_traffic_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trafficEditText = (EditText)findViewById(R.id.trafficEditText);
        trraficSearchBtn = (Button)findViewById(R.id.trafficSerachBtn);
        trraficSearchBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.trafficSerachBtn:
                StationSearchTask stationSearchTask = new StationSearchTask(context);
                stationSearchTask.execute(trafficEditText.getText().toString());
                dismiss();
        }
    }
}
