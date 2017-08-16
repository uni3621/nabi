package com.example.user.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.item.SpendListItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.user.myapplication.MainActivity.context;

/**
 * Created by YTK on 2017-08-13.
 */

public class SpendListAdapter extends BaseAdapter {

    private ArrayList<SpendListItem> listViewItemList = new ArrayList<SpendListItem>() ;

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spend_list, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView spendDayText = (TextView) convertView.findViewById(R.id.spend_day_text) ;
        TextView spendDateText = (TextView) convertView.findViewById(R.id.spend_date_text) ;
        TextView spendYoilText = (TextView) convertView.findViewById(R.id.spend_yoil_text);
        TextView spendText = (TextView) convertView.findViewById(R.id.spend_text);
        ListView addView = (ListView)convertView.findViewById(R.id.spend_list_item);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        SpendListItem spendListItem = listViewItemList.get(position);

        spendListItem.setListView(addView);
        SpendSubListAdapter spendSubListAdapter = new SpendSubListAdapter();
        addView.setAdapter(spendSubListAdapter);
        spendSubListAdapter.addItem("수원인계동", "닭갈비맛있게 먹었음", 19200);

        DecimalFormat df = new DecimalFormat("#,##0");
        // 아이템 내 각 위젯에 데이터 반영
        spendDayText.setText(spendListItem.getDay());
        spendYoilText.setText(spendListItem.getYoil());
        spendText.setText(df.format(spendListItem.getSpending()) + "원");
        spendDateText.setText(spendListItem.getDate());

        return convertView;


    }

    /**
     * 리스트에 아이템 추가
     * @param day 일
     * @param date 날짜(년/월)
     * @param yoil 요일
     * @param spending 지출액
     */
    public void addItem(String day, String date, String yoil, int spending) {
        SpendListItem item = new SpendListItem();

        item.setDay(day);
        item.setDate(date);
        item.setYoil(yoil);
        item.setSpending(spending);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View addView= inflater.inflate(R.layout.spend_list, null);
        item.setListView((ListView)addView.findViewById(R.id.spend_list_item));
        listViewItemList.add(item);
    }

}
