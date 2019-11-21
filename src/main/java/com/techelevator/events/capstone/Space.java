package com.techelevator.events.capstone;

import java.time.LocalDate;

public class Space {
	
	private long id;
	private String name;
	private long openMonth;
	private long closeMonth;
	private long maxOccupancy;
	private boolean isAccessible;
	private long dailyRate;
	private long venueId;
	
	
	public long getVenueId() {
		return venueId;
	}
	public void setVenueId(long venueId) {
		this.venueId = venueId;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public long getOpenMonth() {
		return openMonth;
	}
	public long getCloseMonth() {
		return closeMonth;
	}
	public long getMaxOccupancy() {
		return maxOccupancy;
	}
	public boolean isAccessible() {
		return isAccessible;
	}
	public long getDailyRate() {
		return dailyRate;
	}
	public String toString() {
		return "ID: " + this.id + ", " +
	           "NAME: " + this.name + "," +
			   "OPEN MONTH: " + this.openMonth;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setOpenMonth(long openMonth) {
		this.openMonth = openMonth;
	}
	public void setCloseMonth(long closeMonth) {
		this.closeMonth = closeMonth;
	}
	public void setMaxOccupancy(long maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public void setAccessible(boolean isAccessible) {
		this.isAccessible = isAccessible;
	}
	public void setDailyRate(long dailyRate) {
		this.dailyRate = dailyRate;
	}
}
