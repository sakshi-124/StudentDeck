package com.StudentDeck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.QueriesTemplate;
import com.baseDesignPatterns.ResponseState;
import com.database.UserQueries;
import com.model.ErrorResponse;
import com.model.LandlordNotification;
import com.model.SuccessResponse;
import com.model.user.Student;

public class StudentOperations {

	public ResponseState sendInquiryToLandlord(String title, String landlordId, Student student,
			QueriesTemplate landlordNotificationQueries, List<String> messages, long sourceId) {

		LandlordNotification landlordNotification = new LandlordNotification();
		landlordNotification.setSourceId(sourceId);
		landlordNotification.setLandlordId(Integer.parseInt(landlordId));
		landlordNotification.setNotification("Rental Inquiry by " + student.getFirstName() + " " + student.getLastName() +
				" from " + student.getCountry() + " \nStudying " + student.getCourse() + " at " + student.getUniversity() +
				" in the intake " + student.getIntake() + " \nPlease contact at: " + student.getEmail() + " for further communication");
		landlordNotification.setSource(Constants.student); 

		ResponseState response = null;

		landlordNotificationQueries.writeDb(landlordNotification, messages);

		if (!messages.isEmpty()) {
			response = new ErrorResponse();
			response.setMessages(messages);
			return response;
		}

		response = new SuccessResponse();
		return response;

	}

	public ResponseState getStudentById(int id, QueriesTemplate userQueries, List<String> messages) {
		
		return userQueries.fetchById(id, messages);
	}

	private boolean checkNullOrEmpty(String attribute) {
		if(attribute == null || attribute.trim().isEmpty()) {
			return false;
		}
		return true;
	}

	private boolean checkCity(String city) {
		if(!checkNullOrEmpty(city)) {
			return false;
		}
		return true;
	}

    public ResponseState getAllListingsByCity(String city, String studentId, QueriesTemplate userQueries) {
		if(checkCity(city)) {
			List<Student> students = new ArrayList<Student>();
			List<String> messages = userQueries.fetchByCity((List<Object>)(Object) students, city + " " + studentId);
			ResponseState response;

			if(!messages.isEmpty()) {
				response = new ErrorResponse();
				response.setMessages(messages);
				return response;
			}

			response = new SuccessResponse();
			response.setResponseObject(students);
			return response;
		}
		ResponseState response = new ErrorResponse();
		response.setMessages(Arrays.asList("Please enter valid data in city"));
		return response;
    }
}
