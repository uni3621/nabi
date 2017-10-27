package com.hanium.bpc.nabi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanium.bpc.nabi.R;
import com.hanium.bpc.nabi.dto.BusDTO;
import com.hanium.bpc.nabi.network.BusBookIDeleteTask;
import com.hanium.bpc.nabi.network.BusBookInsertTask;
import com.hanium.bpc.nabi.user.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YTK on 2017-08-13.
 */

public class SearchBusListAdapter extends BaseAdapter {

    String stationId;
    public SearchBusListAdapter(String stationid){
        this.stationId = stationid;
    }
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
        TextView firstSt = (TextView) convertView.findViewById(R.id.firstSt);
        TextView lastSt = (TextView) convertView.findViewById(R.id.lastSt);
        final CheckBox bookBox = (CheckBox)convertView.findViewById(R.id.busBook);
        ImageView busImg = (ImageView)convertView.findViewById(R.id.stationBusImg);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final BusDTO busDTO = listViewItemList.get(position);
        if(busDTO.isBook()){
            bookBox.setChecked(true);
        }else{
            bookBox.setChecked(false);
        }
        bookBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                params.put("email", UserInfo.email);
                params.put("routeId", busDTO.getRouteId());
                params.put("stationId", stationId);
                params.put("isBook", "0");
                if(bookBox.isChecked()){
                    BusBookInsertTask busBookInsertTask = new BusBookInsertTask();
                    busBookInsertTask.execute(params);
                }else{
                    BusBookIDeleteTask busBookIDeleteTask = new BusBookIDeleteTask();
                    busBookIDeleteTask.execute(params);
                }
            }
        });
        busNum.setText(busDTO.getBusName());
        String predictOneString = busDTO.getPredictTimeOne();
        String predictTwoString = busDTO.getPredictTimeTwo();
        if(!predictOneString.equals("")) predictOne.setText(busDTO.getPredictTimeOne()+"분 전 도착예정");
        if(!predictTwoString.equals("")) predictTwo.setText(busDTO.getPredictTimeTwo()+"분 전 도착예정");
        firstSt.setText(busDTO.getFirstStation());
        lastSt.setText(busDTO.getLastStation());
        switch(busDTO.getBusType()){
            case "R":
                busImg.setImageResource(R.drawable.rbus);
                break;
            case "B":
                busImg.setImageResource(R.drawable.bbus);
                break;
            case "G":
                busImg.setImageResource(R.drawable.gbus);
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
