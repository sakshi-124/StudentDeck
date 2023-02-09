package com.StudentDeck;

import java.util.ArrayList;
import java.util.List;

import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.QueriesTemplate;
import com.baseDesignPatterns.ResponseState;
import com.model.ErrorResponse;
import com.model.LandlordNotification;
import com.model.RentalList;
import com.model.SuccessResponse;

public class AdminOperations {

	public ResponseState getListingsToBeApproved(QueriesTemplate rentalQueries) {
		List<RentalList> rentals = new ArrayList<RentalList>();
		List<String> messages = rentalQueries.fetchRentalsToBeApproved((List<Object>)(Object)rentals);
		ResponseState response;

		if (!messages.isEmpty()) {
			response = new ErrorResponse();
			response.setMessages(messages);
			return response;
		}

		response = new SuccessResponse();
		response.setResponseObject(rentals);
		return response;
	}

	public ResponseState approveListing(String id, String landlordId, String title, QueriesTemplate rentalQueries,
			QueriesTemplate landlordNotificationQueries, long adminId) {
		ResponseState response = null;
		List<String> messages = new ArrayList<String>();
		rentalQueries.approve(Integer.parseInt(id), messages);

		LandlordNotification landlordNotification = new LandlordNotification();
		landlordNotification.setSourceId(adminId);
		landlordNotification.setLandlordId(Integer.parseInt(landlordId));
		landlordNotification.setNotification("Rental Posting approved by Admin.	Title: " + title);
		landlordNotification.setSource(Constants.admin);
		landlordNotificationQueries.writeDb(landlordNotification, messages);

		if (!messages.isEmpty()) {
			response = new ErrorResponse();
			response.setMessages(messages);
			return response;
		}

		response = new SuccessResponse();
		return response;
	}

	public ResponseState rejectListing(String landlordId, String title, QueriesTemplate landlordNotificationQueries,
			long adminId) {
		LandlordNotification landlordNotification = new LandlordNotification();
		landlordNotification.setSourceId(adminId);
		landlordNotification.setLandlordId(Integer.parseInt(landlordId));
		landlordNotification.setNotification("Rental Posting rejected by Admin.	Title: " + title);
		landlordNotification.setSource(Constants.admin); 

		ResponseState response = null;

		List<String> messages = new ArrayList<String>();

		landlordNotificationQueries.writeDb(landlordNotification, messages);

		if (!messages.isEmpty()) {
			response = new ErrorResponse();
			response.setMessages(messages);
			return response;
		}

		response = new SuccessResponse();
		return response;
	}

}
