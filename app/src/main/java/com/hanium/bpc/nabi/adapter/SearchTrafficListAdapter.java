package com.hanium.bpc.nabi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.dto.StationDTO;

import java.util.ArrayList;

/**
 * Created by YTK on 2017-08-13.
 */

public class SearchTrafficListAdapter extends BaseAdapter {

    private ArrayList<StationDTO> listViewItemList = new ArrayList<>() ;

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
            convertView = inflater.inflate(R.layout.station_item, parent, false);


        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView stationName = (TextView) convertView.findViewById(R.id.stationName);
        TextView regionName= (TextView) convertView.findViewById(R.id.regionName) ;
        TextView mobileNo = (TextView) convertView.findViewById(R.id.mobileNo);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        StationDTO stationDTO = listViewItemList.get(position);

        stationName.setText(stationDTO.getStationName());
        regionName.setText(stationDTO.getRegionName());
        mobileNo.setText(stationDTO.getMobileNo());


        return convertView;


    }
    public void removeItem(){
        listViewItemList.clear();
    }
    public void addItem(StationDTO dto) {

       listViewItemList.add(dto);
    }

}
