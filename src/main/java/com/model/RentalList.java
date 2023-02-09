package com.model;

public class RentalList {
    private long id;
	private long landlordId;
	private String title;
	private String listingType;
	private String rent;
	private String availability;
	private String maxOccupancy;
	private String description;;
	private String city;
	private String address;
	private String country;
	private int adminId;
	private int status;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getLandlordId() {
		return landlordId;
	}
	public void setLandlordId(long landlordId) {
		this.landlordId = landlordId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getListingType() {
		return listingType;
	}
	public void setListingType(String listingType) {
		this.listingType = listingType;
	}
	public String getRent() {
		return rent;
	}
	public void setRent(String rent2) {
		this.rent = rent2;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public String getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(String maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
