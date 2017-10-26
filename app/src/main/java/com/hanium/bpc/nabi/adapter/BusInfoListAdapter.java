package com.hanium.bpc.nabi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.dto.StationDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YTK on 2017-10-25.
 */

public class BusInfoListAdapter extends BaseAdapter {

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

    public List<StationDTO> getStationList() {return listViewItemList;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.bus_info_item, parent, false);


        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView busInfoStationName = (TextView) convertView.findViewById(R.id.busInfoStationName);
        TextView busInfoPlateNo = (TextView) convertView.findViewById(R.id.busInfoPlateNo) ;
        ImageView busInfoImg = (ImageView) convertView.findViewById(R.id.busInfoImg);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        StationDTO stationDTO = listViewItemList.get(position);
        boolean isBus = stationDTO.isBus();
        busInfoStationName.setText(stationDTO.getStationName());
        if(isBus){
            busInfoImg.setImageResource(R.drawable.busstationselected);
            busInfoPlateNo.setBackgroundResource(R.drawable.tab_bg);
        }else{
            busInfoImg.setImageResource(R.drawable.busstationdown);

        }
        busInfoPlateNo.setText(stationDTO.getPlateNo());

        return convertView;


    }
    public void removeItem(){
        listViewItemList.clear();
    }
    public void addItem(StationDTO dto) {

        listViewItemList.add(dto);
    }

}
