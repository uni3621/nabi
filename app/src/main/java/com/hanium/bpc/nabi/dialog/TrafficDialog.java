package com.hanium.bpc.nabi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hanium.bpc.nabi.R;

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
                dismiss();
        }
    }
}
