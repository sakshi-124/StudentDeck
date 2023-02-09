package com.model;

public class LandlordNotification {
	private long id;
	private long sourceId;
	private long landlordId;
	private String notification;
	private String source;

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
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public long getSourceId() {
		return sourceId;
	}
	public void setSourceId(long adminId) {
		this.sourceId = adminId;
	}
}
