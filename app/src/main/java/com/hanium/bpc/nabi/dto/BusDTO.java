package com.hanium.bpc.nabi.dto;

import java.io.Serializable;

public class BusDTO implements Serializable{
	private String locationNoOne;
	private String locationNoTwo;
	private String plateNoOne;
	private String plateNoTwo;
	private String predictTimeOne;
	private String predictTimeTwo;
	private String routeId;
	private String staOrder;
	private String stationId;
	private String busName;
	private String busType;
	public BusDTO(){}
	public BusDTO(String locationNoOne, String locationNoTwo, String plateNoOne, String plateNoTwo, String predictTimeOne, String predictTimeTwo, String routeId, String staOrder, String stationId, String busName, String busType) {
		this.locationNoOne = locationNoOne;
		this.locationNoTwo = locationNoTwo;
		this.plateNoOne = plateNoOne;
		this.plateNoTwo = plateNoTwo;
		this.predictTimeOne = predictTimeOne;
		this.predictTimeTwo = predictTimeTwo;
		this.routeId = routeId;
		this.staOrder = staOrder;
		this.stationId = stationId;
		this.busName = busName;
		this.busType = busType;
	}

	@Override
	public String toString() {
		return "BusDTO [" + (locationNoOne != null ? "locationNoOne=" + locationNoOne + ", " : "")
				+ (locationNoTwo != null ? "locationNoTwo=" + locationNoTwo + ", " : "")
				+ (plateNoOne != null ? "plateNoOne=" + plateNoOne + ", " : "")
				+ (plateNoTwo != null ? "plateNoTwo=" + plateNoTwo + ", " : "")
				+ (predictTimeOne != null ? "predictTimeOne=" + predictTimeOne + ", " : "")
				+ (predictTimeTwo != null ? "predictTimeTwo=" + predictTimeTwo + ", " : "")
				+ (routeId != null ? "routeId=" + routeId + ", " : "")
				+ (staOrder != null ? "staOrder=" + staOrder + ", " : "")
				+ (stationId != null ? "stationId=" + stationId + ", " : "")
				+ (busName != null ? "busName=" + busName + ", " : "") + (busType != null ? "busType=" + busType : "")
				+ "]";
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getBusName() {
		return busName;
	}
	public void setBusName(String busName) {
		this.busName = busName;
	}
	public String getPlateNoOne() {
		return plateNoOne;
	}
	public void setPlateNoOne(String plateNoOne) {
		this.plateNoOne = plateNoOne;
	}
	public String getPlateNoTwo() {
		return plateNoTwo;
	}
	public void setPlateNoTwo(String plateNoTwo) {
		this.plateNoTwo = plateNoTwo;
	}
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}String getStationId() {
		return stationId;
	}
	public String getLocationNoOne() {
		return locationNoOne;
	}
	public void setLocationNoOne(String locationNoOne) {
		this.locationNoOne = locationNoOne;
	}
	public String getLocationNoTwo() {
		return locationNoTwo;
	}
	public void setLocationNoTwo(String locationNoTwo) {
		this.locationNoTwo = locationNoTwo;
	}
	public String getPredictTimeOne() {
		return predictTimeOne;
	}
	public void setPredictTimeOne(String predictTimeOne) {
		this.predictTimeOne = predictTimeOne;
	}
	public String getPredictTimeTwo() {
		return predictTimeTwo;
	}
	public void setPredictTimeTwo(String predictTimeTwo) {
		this.predictTimeTwo = predictTimeTwo;
	}
	public String getStaOrder() {
		return staOrder;
	}
	public void setStaOrder(String staOrder) {
		this.staOrder = staOrder;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	
	

}
