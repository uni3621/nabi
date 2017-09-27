package com.hanium.bpc.nabi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.item.SpendSubListItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by YTK on 2017-08-13.
 */

public class SpendSubListAdapter extends BaseAdapter {

    private ArrayList<SpendSubListItem> listViewItemList = new ArrayList<SpendSubListItem>() ;

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


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spend_list_item, parent, false);


        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView spendLocationText = (TextView) convertView.findViewById(R.id.spend_item_location);
        TextView spendContentText = (TextView) convertView.findViewById(R.id.spend_time) ;
        TextView spendPriceText = (TextView) convertView.findViewById(R.id.spend_sub_price);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        SpendSubListItem spendSubListItem = listViewItemList.get(position);
        DecimalFormat df = new DecimalFormat("#,##0");
        spendLocationText.setText(spendSubListItem.getLocation());
        spendContentText.setText(spendSubListItem.getContent());
        spendPriceText.setText(df.format(spendSubListItem.getSpend()) + "원");


        return convertView;


    }

    /**
     * 리스트에 아이템 추가
     * @param location 지출 장소
     * @param content 지출 내용
     * @param price 지출 가격
     */
    public void addItem(String location, String content, int price) {
        SpendSubListItem item = new SpendSubListItem();
        item.setLocation(location);
        item.setContent(content);
        item.setSpend(price);
        listViewItemList.add(item);
    }

}
