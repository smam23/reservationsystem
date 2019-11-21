package com.techelevator.events.capstone;

public class Venue {
	private long id;
	private String name;
	private String cityName;
	private long cityId;
	private String state;
	private String description;
	
	public void setId(long id) {
		this.id = id;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private String category;
	
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCityName() {
		return cityName;
	}
	public String getState() {
		return state;
	}
	public String getDescription() {
		return description;
	}
	public String getCategory() {
		return category;
	}
	public String toString() {
		return this.name;
	}
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

}
