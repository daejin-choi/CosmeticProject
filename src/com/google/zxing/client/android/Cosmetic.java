package com.google.zxing.client.android;

import java.util.Date;

public class Cosmetic {
	private int id;
	private String name;
	private String brand;
	private int expiredDate;
	private Date endDate;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public int getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(int expiredDate) {
		this.expiredDate = expiredDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date date) {
		this.endDate = date;
	}
	
}
