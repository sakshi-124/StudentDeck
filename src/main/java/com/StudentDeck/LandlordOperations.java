package com.StudentDeck;

import java.util.ArrayList;
import java.util.List;

import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.QueriesTemplate;
import com.baseDesignPatterns.ResponseState;
import com.model.ErrorResponse;
import com.model.LandlordNotification;
import com.model.SuccessResponse;

public class LandlordOperations {

	private ResponseState fetchNotifications(int landlordId, QueriesTemplate landlordNotificationQueries) {
		
		List<LandlordNotification> notifications = new ArrayList<LandlordNotification>();
		List<String> messages = landlordNotificationQueries.fetchByLandlordId((List<Object>)(Object)notifications, landlordId);
		ResponseState response;
		
		if(!messages.isEmpty()) {			
			response = new ErrorResponse();
			response.setMessages(messages);
			return response;
		}
		
		response = new SuccessResponse();
		response.setResponseObject(notifications);
		return response;
	}

	public ResponseState fetchNotificationsByAdmin(int landlordId, QueriesTemplate landlordNotificationQueries) {
		ResponseState response = fetchNotifications(landlordId, landlordNotificationQueries);
		if(response.getStatusCode() == Constants.status200) {
			List<LandlordNotification> notifications = (List<LandlordNotification>) response.getResponseObject();
			notifications.removeIf(n -> !n.getSource().equals("ADMIN")); //TODO - use Constants
			response.setResponseObject(notifications);
		}
		return response;
	}

	public ResponseState fetchNotificationsByStudent(int landlordId, QueriesTemplate landlordNotificationQueries) {
		ResponseState response = fetchNotifications(landlordId, landlordNotificationQueries);
		if(response.getStatusCode() == Constants.status200) {
			List<LandlordNotification> notifications = (List<LandlordNotification>) response.getResponseObject();
			notifications.removeIf(n -> ! n.getSource().equals("STUDENT")); //TODO - use Constants
			response.setResponseObject(notifications);
		}
		return response;
	}

}
