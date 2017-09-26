package com.example.user.myapplication.dto;

public class SpendDTO {
	private String email;
	private String spendDay;
	private String location;
	private int spending;
	private String month;
	private String day;
	private String year;


	public SpendDTO(String email, String spendDay, String location, int spending, String month, String day, String year) {
		this.email = email;
		this.spendDay = spendDay;
		this.location = location;
		this.spending = spending;
		this.month = month;
		this.day = day;
		this.year = year;
	}

	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSpendDay() {
		return spendDay;
	}
	public void setSpendDay(String spendDay) {
		this.spendDay = spendDay;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getSpending() {
		return spending;
	}
	public void setSpending(int spending) {
		this.spending = spending;
	}
	@Override
	public String toString() {
		return "SpendDTO [" + (email != null ? "email=" + email + ", " : "")
				+ (spendDay != null ? "spendDay=" + spendDay + ", " : "")
				+ (location != null ? "location=" + location + ", " : "") + "spending=" + spending + ", "
				+ (month != null ? "month=" + month + ", " : "") + (day != null ? "day=" + day + ", " : "")
				+ (year != null ? "year=" + year : "") + "]";
	}
	
}
