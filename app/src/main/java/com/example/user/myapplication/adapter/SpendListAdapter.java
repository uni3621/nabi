package com.example.user.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.dto.SpendDTO;
import com.example.user.myapplication.item.SpendListItem;
import com.example.user.myapplication.item.SpendSubListItem;
import com.example.user.myapplication.network.SpendDeleteTask;
import com.example.user.myapplication.user.UserInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YTK on 2017-08-13.
 */

public class SpendListAdapter extends BaseAdapter {

    private ArrayList<SpendListItem> listViewItemList = new ArrayList<SpendListItem>() ;
    Context context;
    int year, month;
    AlertDialog alertDialog;
    public SpendListAdapter(Context context, int year, int month){
        this.context = context;
        this.year = year;
        this.month = month;
    }
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
            convertView = inflater.inflate(R.layout.spend_list, parent, false);


        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView spendDayText = (TextView) convertView.findViewById(R.id.spend_day_text) ;
        TextView spendDateText = (TextView) convertView.findViewById(R.id.spend_date_text) ;
        TextView spendYoilText = (TextView) convertView.findViewById(R.id.spend_yoil_text);
        TextView spendText = (TextView) convertView.findViewById(R.id.spend_text);
        ListView addView = (ListView)convertView.findViewById(R.id.spend_list_item);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
         final SpendListItem spendListItem = listViewItemList.get(position);
        List<SpendDTO> spendList = spendListItem.getSpendList();
        spendListItem.setListView(addView);

        SpendSubListAdapter spendSubListAdapter = new SpendSubListAdapter();
        addView.setAdapter(spendSubListAdapter);
        Log.i("SPEND_SIZE", spendList.size() +"");
        for(int i=0; i<spendList.size(); i++){
            SpendDTO dto = spendList.get(i);
            Log.i("SPEND_INFO", dto + "");
            spendSubListAdapter.addItem(dto.getLocation(), dto.getSpendDay(), dto.getSpending());
        }
        setListViewHeightBasedOnChildren(addView);



        addView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("CLICKED", position + "번");
                view.setSelected(true);
                final SpendSubListItem spendSubListItem = (SpendSubListItem)parent.getItemAtPosition(position);
                AlertDialog.Builder ab = new AlertDialog.Builder(context);
                ab.setTitle("삭제하시겠습니까?");
                ab.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] splitDate = spendListItem.getDate().split("\\.");
                        Log.i("여기서?", spendListItem.getDate());
                        SpendDeleteTask spendDeleteTask = new SpendDeleteTask(context);
                        Map<String, String> params = new HashMap<>();
                        params.put("spendDay", spendSubListItem.getContent());
                        params.put("spending", spendSubListItem.getSpend() + "");
                        params.put("location", spendSubListItem.getLocation());
                        params.put("email", UserInfo.email);
                        params.put("year", splitDate[0]);
                        params.put("monthNum", splitDate[1]);
                        params.put("day", spendListItem.getDay());
                        spendDeleteTask.execute(params);
                        dialog.dismiss();
                    }
                });
                ab.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog = ab.create();
                alertDialog.show();
            }
        });
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
    public void addItem(String day, String date, String yoil, int spending, List<SpendDTO> spendList) {
        SpendListItem item = new SpendListItem();

        item.setDay(day);
        item.setDate(date);
        item.setYoil(yoil);
        item.setSpending(spending);
        item.setSpendList(spendList);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View addView= inflater.inflate(R.layout.spend_list, null);
        item.setListView((ListView)addView.findViewById(R.id.spend_list_item));
        listViewItemList.add(item);
    }
    /**
     * list의 모든 아이템을 제거하는 메소드.
     */
    public void removeItem(){
        listViewItemList.clear();
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //listItem.measure(0, 0);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight() - 150;
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;
        listView.setLayoutParams(params);

        listView.requestLayout();
    }

}
