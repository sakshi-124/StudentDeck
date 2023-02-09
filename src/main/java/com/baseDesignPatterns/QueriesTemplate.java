package com.baseDesignPatterns;

import java.util.List;

public abstract class QueriesTemplate {

	public void writeDb(Object obj, List<String> messages2){
	}
	
	public void update(Object obj, List<String> messages){
		System.out.println("Updates database");
	}

	public void delete(Object obj, List<String> messages) {
		System.out.println("Deletes/Deactivates a entry in database");
	}
	
	public ResponseState fetchById(Object obj, List<String> messages) {
		System.out.println("fetches object by primary key");
		return null;
	}

	public List<String> fetchByLandlordId(List<Object> objects, int landlordId){
		return null;
	}

	public List<String> fetchRentalsToBeApproved(List<Object> rentals){
		return null;
	}

	public List<String> fetchByCity(List<Object> list, String city) {
		return null;
	}

	public List<String> fetchMyFriends(List<Object> list, int id) {
		return null;
	}

	public void approve(int parseInt, List<String> messages) {}
}
