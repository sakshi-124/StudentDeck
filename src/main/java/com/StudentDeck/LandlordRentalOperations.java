package com.StudentDeck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baseDesignPatterns.QueriesTemplate;
import com.baseDesignPatterns.ResponseState;
import com.database.RentalQueries;
import com.model.ErrorResponse;
import com.model.RentalList;
import com.model.SuccessResponse;

public class LandlordRentalOperations {

	public ResponseState addRental(String title, String listingType, String rent, String availability, String maxOccupancy,
			String description, String city, String country, String address, QueriesTemplate rentalQueries, long landlordId) {
		
		ResponseState response = null;

		List<String> messages = new ArrayList<String>();
		
		RentalList rental = new RentalList();
		rental.setTitle(title);
		rental.setListingType(listingType);
		rental.setRent(rent);
		rental.setAvailability(availability);
		rental.setMaxOccupancy(maxOccupancy);
		rental.setDescription(description);
		rental.setCity(city);
		rental.setCountry(country);
		rental.setAddress(address);
		rental.setLandlordId(landlordId);
		verifyFields(rental, messages);
		
		if(!messages.isEmpty()) {			
			response = new ErrorResponse();
			response.setMessages(messages);
			response.setResponseObject(rental);
			return response;
		}
		
		rentalQueries.writeDb(rental, messages);
		
		if(!messages.isEmpty()) {			
			response = new ErrorResponse();
			response.setMessages(messages);
			response.setResponseObject(rental);
			return response;
		}
							
		response = new SuccessResponse();
		response.setMessages(List.of("Rental Posting addded successfully", "Posting awaiting admin approval"));
		return response;
	}

	public ResponseState getAllListingsByLandLordId(int landlordId, QueriesTemplate rentalQueries) {
		List<RentalList> rentals = new ArrayList<RentalList>();
		List<String> messages = rentalQueries.fetchByLandlordId((List<Object>)(Object) rentals, landlordId);
		ResponseState response;
		
		if(!messages.isEmpty()) {			
			response = new ErrorResponse();
			response.setMessages(messages);
			return response;
		}
		
		response = new SuccessResponse();
		response.setResponseObject(rentals);
		return response;
		
	}

	public ResponseState deleteRental(String id, QueriesTemplate rentalQueries) {
		ResponseState response = null;

		List<String> messages = new ArrayList<String>();

		rentalQueries.delete(Integer.parseInt(id), messages);
		
		if(!messages.isEmpty()) {			
			response = new ErrorResponse();
			response.setMessages(messages);
			return response;
		}
		
		response = new SuccessResponse();
		return response;
		
	}

	public ResponseState fetchRentalById(String id, QueriesTemplate rentalQueries) {
		ResponseState response = null;
		List<String> messages = new ArrayList<String>();
		response = rentalQueries.fetchById(Integer.parseInt(id), messages);
		return response;
	}

	public ResponseState updateRentalDetails(String id, String title, String listingType, String rent, String availability,
			String maxOccupancy, String description, String city, String country, String address,
			QueriesTemplate rentalQueries, long landlordId) {
		ResponseState response = null;

		List<String> messages = new ArrayList<String>();
		
		RentalList rental = new RentalList();
		rental.setId(Long.parseLong(id));
		rental.setTitle(title);
		rental.setListingType(listingType);
		rental.setRent(rent);
		rental.setAvailability(availability);
		rental.setMaxOccupancy(maxOccupancy);
		rental.setDescription(description);
		rental.setCity(city);
		rental.setCountry(country);
		rental.setAddress(address);
		rental.setLandlordId(landlordId);
		
		verifyFields(rental, messages);
		
		if(!messages.isEmpty()) {			
			response = new ErrorResponse();
			response.setMessages(messages);
			response.setResponseObject(rental);
			return response;
		}
		
		rentalQueries.update(rental, messages);
		
		if(!messages.isEmpty()) {			
			response = new ErrorResponse();
			response.setMessages(messages);
			response.setResponseObject(rental);
			return response;
		}
							
		response = new SuccessResponse();
		response.setMessages(List.of("Rental Posting updated successfully"));
		return response;
	}
	
	private ResponseState getAllListings(String city, QueriesTemplate rentalQueries) {
		List<RentalList> rentals = new ArrayList<RentalList>();
		List<String> messages = rentalQueries.fetchByCity((List<Object>)(Object) rentals, city);
		ResponseState response;
		
		if(!messages.isEmpty()) {			
			response = new ErrorResponse();
			response.setMessages(messages);
			return response;
		}
		
		response = new SuccessResponse();
		response.setResponseObject(rentals);
		return response;
		
	}
	
	public ResponseState getAllListingsByCity(String city, QueriesTemplate rentalQueries) {
		if(checkCity(city) && isAlpha(city)) {
			return getAllListings(city, rentalQueries);
		}
		ResponseState response = new ErrorResponse();
		response.setMessages(Arrays.asList("Please enter valid data in city"));
		return response;
	}

	public boolean verifyFields(RentalList rental, List<String> errorMessages) {
		
		if(!checkNullOrEmpty(rental.getTitle())) {
			errorMessages.add("Title cannot be empty");
		}
		
		if(!checkNullOrEmpty(rental.getListingType())) {
			errorMessages.add("Listing type cannot be empty");
		}
		
		if(!checkNullOrEmpty(rental.getDescription())) {
			errorMessages.add("Description cannot be empty");
		}
		
		if(!checkNullOrEmpty(rental.getCity())) {
			errorMessages.add("City cannot be empty");
		}else if(!isAlpha(rental.getCity())){
			errorMessages.add("City cannot contain numeric value");
		}
		
		if(!checkNullOrEmpty(rental.getCountry())) {
			errorMessages.add("Country cannot be empty");
		}else if(!isAlpha(rental.getCountry())){
			errorMessages.add("Country cannot contain numeric value");
		}
		
		if(!checkNullOrEmpty(rental.getAddress())) {
			errorMessages.add("Address cannot be empty");
		}
		
		if(!checkNullOrEmpty(rental.getAddress())) {
			errorMessages.add("Address cannot be empty");
		}
		
		if(!checkNullOrEmpty(rental.getAvailability())) {
			errorMessages.add("Availability cannot be empty");
		}
		
		if(!checkNullOrEmpty(rental.getRent())) {
			errorMessages.add("Rent cannot be empty");
		}else if(!verifyRent(rental.getRent())) {
			errorMessages.add("Rent should be numberic");
		}
		
		if(!checkNullOrEmpty(rental.getMaxOccupancy())) {
			errorMessages.add("Max Occupancy cannot be empty");
		}else if(!verifyMaxOccupancy(rental.getMaxOccupancy())) {
			errorMessages.add("Max Occupancy should numeric");
		}
		
		if(!errorMessages.isEmpty()) {
			return false;
		}	
		return true;
	}

	private boolean isAlpha(String name) {
	    return name.matches("[a-zA-Z]+");
	}
	
	private boolean verifyMaxOccupancy(String maxOccupancy) {
		try {
	       Integer.parseInt(maxOccupancy);
		}catch(Exception e) {
			return false;
		}
		return true;	
	}

	private boolean verifyRent(String rent) {
		try {
	        Double.parseDouble(rent);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	private boolean checkNullOrEmpty(String attribute) {
		if(attribute == null || attribute.trim().isEmpty()) {
			return false;
		}
		return true;
	}
	
	private boolean checkCity(String city) {
		if(checkNullOrEmpty(city)) {
			return true;
		}
		return false;
	}
}
