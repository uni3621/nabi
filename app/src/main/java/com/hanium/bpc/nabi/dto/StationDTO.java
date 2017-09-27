package com.hanium.bpc.nabi.dto;

import java.io.Serializable;

/**
 * Created by YTK on 2017-09-22.
 */

public class StationDTO implements Serializable{
    private String stationId;
    private String stationName;
    private String mobileNo;
    private String regionName;
    private String latitude;
    private String longitude;

    public StationDTO(){}


    @Override
    public String toString() {
        return "StationDTO{" +
                "stationId='" + stationId + '\'' +
                ", stationName='" + stationName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", regionName='" + regionName + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
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
