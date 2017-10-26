package com.hanium.bpc.nabi.dto;

import java.io.Serializable;

/**
 * Created by YTK on 2017-09-22.
 */

public class StationDTO implements Serializable{
    private String stationId;
    private String stationName;
    private String plateNo;
    private String routeId;
    private int order;
    boolean isBus;
    private String mobileNo;
    private String regionName;
    private String latitude;
    private String longitude;
    private int click;
    public StationDTO(){}


    @Override
    public String toString() {
        return "StationDTO{" +
                "stationId='" + stationId + '\'' +
                ", stationName='" + stationName + '\'' +
                ", plateNo='" + plateNo + '\'' +
                ", routeId='" + routeId + '\'' +
                ", order=" + order +
                ", isBus=" + isBus +
                ", mobileNo='" + mobileNo + '\'' +
                ", regionName='" + regionName + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", click=" + click +
                '}';
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isBus() {
        return isBus;
    }

    public void setBus(boolean bus) {
        isBus = bus;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
