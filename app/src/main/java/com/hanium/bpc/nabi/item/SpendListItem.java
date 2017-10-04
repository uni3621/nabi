package com.hanium.bpc.nabi.item;

/**
 * Created by YTK on 2017-08-13.
 */

import android.widget.ListView;

import com.hanium.bpc.nabi.dto.SpendDTO;

import java.util.List;

/**
 * 지출내역에서 월별 지출내역 리스트의 아이템
 */
public class SpendListItem {
    private String date; // 년,월
    private String day; // 일
    private String yoil; // 요일
    private int spending; // 지출 금액
    private ListView listView; // 하위 리스트뷰
    private List<SpendDTO> spendList;

    public List<SpendDTO> getSpendList() {
        return spendList;
    }

    public void setSpendList(List<SpendDTO> spendList) {
        this.spendList = spendList;
    }

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYoil() {
        return yoil;
    }

    public void setYoil(String yoil) {
        this.yoil = yoil;
    }

    public int getSpending() {
        return spending;
    }

    public void setSpending(int spending) {
        this.spending = spending;
    }
}
