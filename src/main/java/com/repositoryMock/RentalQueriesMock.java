package com.repositoryMock;

import java.util.ArrayList;
import java.util.List;

import com.baseDesignPatterns.QueriesTemplate;
import com.baseDesignPatterns.ResponseState;
import com.model.RentalList;
import com.model.SuccessResponse;

public class RentalQueriesMock extends QueriesTemplate{
	
	public List<String> fetchRentalsToBeApproved(List<Object> rentals) {
		RentalList rental = new RentalList();
		rental.setTitle("Title");
		rental.setListingType("listing type");
		rental.setRent("450");
		rental.setAvailability("2020/10/10");
		rental.setMaxOccupancy("4");
		rental.setDescription("description");
		rental.setCity("city");
		rental.setAddress("address");
		rental.setCountry("country");
		rental.setLandlordId(123);
		rentals.add(rental);
		return new ArrayList<String>();
	}

	@Override
	public List<String> fetchByLandlordId(List<Object> objects, int landlordId) {
		if(landlordId == 123) {
			RentalList rental = new RentalList();
			rental.setTitle("Title");
			rental.setListingType("listing type");
			rental.setRent("450");
			rental.setAvailability("2020/10/10");
			rental.setMaxOccupancy("4");
			rental.setDescription("description");
			rental.setCity("city");
			rental.setAddress("address");
			rental.setCountry("country");
			rental.setLandlordId(123);
			objects.add(rental);
		}
		return new ArrayList<String>();
	}

	public void delete(Object id, List<String> messages) {
	}
	
	public ResponseState fetchById(Object obj, List<String> messages) {
		return new SuccessResponse();
	}
	
	public List<String> fetchByCity(List<Object> rentals, String city) {
		return new ArrayList<String>();
	}

	
}
