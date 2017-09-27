package com.hanium.bpc.nabi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.dto.BusDTO;

import java.util.ArrayList;

/**
 * Created by YTK on 2017-08-13.
 */

public class SearchBusListAdapter extends BaseAdapter {

    private ArrayList<BusDTO> listViewItemList = new ArrayList<>() ;

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
            convertView = inflater.inflate(R.layout.bus_item, parent, false);


        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView busNum = (TextView) convertView.findViewById(R.id.busNum);
        TextView predictOne= (TextView) convertView.findViewById(R.id.predictOne) ;
        TextView predictTwo = (TextView) convertView.findViewById(R.id.predictTwo);
        ImageView busImg = (ImageView)convertView.findViewById(R.id.stationBusImg);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        BusDTO busDTO = listViewItemList.get(position);

        busNum.setText(busDTO.getBusName());
        predictOne.setText(busDTO.getPredictTimeOne()+"분 전");
        predictTwo.setText(busDTO.getPredictTimeTwo()+"분 전");
        switch(busDTO.getBusType()){
            case "R":
                busImg.setImageResource(R.drawable.redbus);
                break;
            case "B":
                busImg.setImageResource(R.drawable.bluebus);
                break;
            case "G":
                busImg.setImageResource(R.drawable.greenbus);
                break;
        }
        return convertView;


    }
    public void removeItem(){
        listViewItemList.clear();
    }
    public void addItem(BusDTO dto) {
       listViewItemList.add(dto);
    }

}
