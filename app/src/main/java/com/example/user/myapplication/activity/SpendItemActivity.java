package com.example.user.myapplication.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.user.myapplication.MainActivity;
import com.example.user.myapplication.R;
import com.example.user.myapplication.dialog.SpendingInputDialog;

public class SpendItemActivity extends AppCompatActivity{
    FloatingActionButton addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend_item);
        addBtn = (FloatingActionButton)findViewById(R.id.spendingAddBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new SpendingInputDialog(MainActivity.context);
                dialog.show();
            }
        });
    }
}
