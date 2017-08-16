package com.example.user.myapplication.item;

/**
 * Created by YTK on 2017-08-13.
 */

public class SpendSubListItem {
    private String location; // 장소
    private String content; // 상세 내용
    private int spend; // 지출 내역

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSpend() {
        return spend;
    }

    public void setSpend(int spend) {
        this.spend = spend;
    }
}
