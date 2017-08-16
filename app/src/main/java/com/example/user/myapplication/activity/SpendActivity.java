package com.example.user.myapplication.activity;

import android.app.ActivityGroup;
import android.os.Bundle;
import android.widget.ListView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.adapter.SpendListAdapter;
import com.example.user.myapplication.item.SpendListItem;

public class SpendActivity extends ActivityGroup {
    ListView spendListView;
    SpendListAdapter spendListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend);
        spendListView = (ListView)findViewById(R.id.spend_list_view);
        spendListAdapter = new SpendListAdapter();

        spendListView.setAdapter(spendListAdapter);
        spendListAdapter.addItem("13", "2017.08", "일요일", 25710);
        SpendListItem listItem = (SpendListItem)spendListAdapter.getItem(0);


        ListView subListView = listItem.getListView();
//        SpendSubListAdapter spendSubListAdapter = new SpendSubListAdapter();
//        subListView.setAdapter(spendSubListAdapter);
//        spendSubListAdapter.addItem("수원인계동", "닭갈비맛있게 먹었음", 19200);

//        Log.i("정보", ((SpendListItem) spendListAdapter.getItem(0)).getListView().getAdapter().getCount() + "개");
//        spendSubListAdapter.notifyDataSetChanged();;
//        spendListAdapter.notifyDataSetChanged();

    }
}
