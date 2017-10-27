package com.hanium.bpc.nabi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.adapter.BookBusListAdapter;
import com.hanium.bpc.nabi.network.BusBookSelectTask;
import com.hanium.bpc.nabi.user.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017-08-16.
 */

public class BookmarkActivity extends AppCompatActivity {
    BookBusListAdapter bookBusListAdapter;
    ListView busBookListView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        busBookListView = (ListView)findViewById(R.id.busBookList);
        bookBusListAdapter = new BookBusListAdapter();
        busBookListView.setAdapter(bookBusListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookBusListAdapter.removeItem();
        Map<String, String> params = new HashMap<>();
        params.put("email", UserInfo.email);
        BusBookSelectTask task = new BusBookSelectTask(this, bookBusListAdapter);
        task.execute(params);
    }
}
