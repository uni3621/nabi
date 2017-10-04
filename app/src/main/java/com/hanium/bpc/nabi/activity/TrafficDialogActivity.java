package com.hanium.bpc.nabi.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.hanium.bpc.nabi.R;

/**
 * Created by user on 2017-07-22.
 */

public class TrafficDialogActivity extends Dialog  {

    public TrafficDialogActivity(Context context) {
        super(context);
        this.setContentView(R.layout.activity_traffic_dialog);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_dialog);

        Button backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                TrafficDialogActivity.this.dismiss();
            }

        });

    }

}
