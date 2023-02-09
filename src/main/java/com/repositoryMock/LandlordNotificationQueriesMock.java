package com.repositoryMock;

import java.util.ArrayList;
import java.util.List;

import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.QueriesTemplate;
import com.model.LandlordNotification;

public class LandlordNotificationQueriesMock extends QueriesTemplate{
	
	public List<String> fetchByLandlordId(List<Object> notifications, int landlordId) {
		return new ArrayList<String>();
	}
	
	private LandlordNotification createDemoObject() {
		LandlordNotification landlordNotification = new LandlordNotification();
		landlordNotification.setLandlordId(123);
		landlordNotification.setNotification("notification message");
		landlordNotification.setSource(Constants.admin);
		landlordNotification.setSourceId(1);
		return landlordNotification;
	}
}
