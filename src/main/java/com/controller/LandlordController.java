package com.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.SessionManagement.SessionManager;
import com.StudentDeck.LandlordOperations;
import com.StudentDeck.LandlordRentalOperations;
import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.ResponseState;
import com.database.LandlordNotificationQueries;
import com.database.RentalQueries;

@RestController
public class LandlordController {

	LandlordRentalOperations rentalOperations = new LandlordRentalOperations();
	LandlordOperations landlordOperations = new LandlordOperations();

	@RequestMapping(Constants.rentalDetailsUrl)
	public ModelAndView rentalAdded(@RequestParam("title") String title,
			@RequestParam("listingType") String listingType, @RequestParam("rent") String rent,
			@RequestParam("availability") String availability, @RequestParam("maxOccupancy") String maxOccupancy,
			@RequestParam("description") String description, @RequestParam("city") String city,
			@RequestParam("country") String country, @RequestParam("address") String address,
			HttpServletRequest request) {
		
		SessionManager sessionManager = new SessionManager();
		if(!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
		
		sessionManager.getLoggedUserId(request.getSession());	
		long landlordId = Integer.parseInt(sessionManager.getLoggedUserId(request.getSession()));

		ResponseState response = rentalOperations.addRental(title, listingType, rent, availability, maxOccupancy,
				description, city, country, address, new RentalQueries(), landlordId);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.rentalDetailsPage);

		if (response.getStatusCode() == Constants.status404) {
			modelAndView.addObject("message", response.getMessages());
			modelAndView.addObject("rental", response.getResponseObject());
		} else {
			modelAndView.addObject("message", response.getMessages());
		}
		return modelAndView;
	}

	@GetMapping(Constants.rentalDetailsUrl)
	public ModelAndView rentalToAdd(HttpServletRequest request) {
		SessionManager sessionManager = new SessionManager();
		if(!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.rentalDetailsPage);
		return modelAndView;
	}

	@GetMapping(Constants.viewMyListingsUrl)
	public ModelAndView viewMyListings(HttpServletRequest request) {
		SessionManager sessionManager = new SessionManager();
		if(!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
		
		sessionManager.getLoggedUserId(request.getSession());	
		int landlordId = Integer.parseInt(sessionManager.getLoggedUserId(request.getSession()));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.viewMyListingsPage);
		ResponseState response = rentalOperations.getAllListingsByLandLordId(landlordId, new RentalQueries());

		if (response.getStatusCode() == Constants.status404) {
			modelAndView.addObject("message", response.getMessages());
		} else {
			modelAndView.addObject("message", response.getMessages());
			modelAndView.addObject("rentals", response.getResponseObject());
		}
		return modelAndView;
	}

	@RequestMapping(value = Constants.deleteRentalUrl + "/{id}")
	public ModelAndView deleteRental(@PathVariable("id") String id, HttpServletRequest request) {
		SessionManager sessionManager = new SessionManager();
		if(!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
		
		ResponseState response = rentalOperations.deleteRental(id, new RentalQueries());
		if (response.getStatusCode() == Constants.status404) {
			ModelAndView modelAndView = viewMyListings(request);
			modelAndView.addObject("errorMessage", response.getMessages());
			return modelAndView;
		} else {
			return viewMyListings(request);
		}
	}

	@RequestMapping(value = Constants.editRentalUrl + "/{id}")
	public ModelAndView editRental(@PathVariable("id") String id, HttpServletRequest request) {
		SessionManager sessionManager = new SessionManager();
		if(!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.rentalDetailsPage);
		ResponseState response = rentalOperations.fetchRentalById(id, new RentalQueries());
		System.out.println(response.getStatusCode());
		if (response.getStatusCode() == Constants.status404) {
			ModelAndView redirected = new ModelAndView(Constants.redirect +  Constants.viewMyListingsUrl);
			redirected.addObject("message", Arrays.asList("Error Opening the Edit Mode","Please try again later."));
			return redirected;
		} else {
			modelAndView.addObject("rental", response.getResponseObject());
			modelAndView.addObject("editMode", "True");
			return modelAndView;
		}
	}

	@RequestMapping(Constants.editRentalUrl)
	public ModelAndView editRental(@RequestParam("id") String id, @RequestParam("title") String title,
			@RequestParam("listingType") String listingType, @RequestParam("rent") String rent,
			@RequestParam("availability") String availability, @RequestParam("maxOccupancy") String maxOccupancy,
			@RequestParam("description") String description, @RequestParam("city") String city,
			@RequestParam("country") String country, @RequestParam("address") String address,
			HttpServletRequest request) {
		
		SessionManager sessionManager = new SessionManager();
		if(!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
		
		sessionManager.getLoggedUserId(request.getSession());	
		long landlordId = Integer.parseInt(sessionManager.getLoggedUserId(request.getSession()));

		ResponseState response = rentalOperations.updateRentalDetails(id, title, listingType, rent, availability,
				maxOccupancy, description, city, country, address, new RentalQueries(), landlordId);

		ModelAndView modelAndView = new ModelAndView();

		if (response.getStatusCode() == Constants.status404) {
			modelAndView.setViewName(Constants.rentalDetailsPage);
			modelAndView.addObject("message", response.getMessages());
			modelAndView.addObject("rental", response.getResponseObject());
		} else {
			modelAndView = new ModelAndView(Constants.redirect + Constants.viewMyListingsUrl);
		}
		return modelAndView;
	}

	@RequestMapping(Constants.landlordNotificationUrl)
	public ModelAndView landlordNotifications(HttpServletRequest request) {
		SessionManager sessionManager = new SessionManager();
		if(!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
		
		sessionManager.getLoggedUserId(request.getSession());	
		int landlordId = Integer.parseInt(sessionManager.getLoggedUserId(request.getSession()));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.landlordNotificationPage);
		ResponseState response = landlordOperations.fetchNotificationsByAdmin(landlordId, new LandlordNotificationQueries());

		if (response.getStatusCode() == Constants.status404) {
			modelAndView.addObject("errorMessages", response.getMessages());

		} else {
			modelAndView.addObject("notifications", response.getResponseObject());
		}
		return modelAndView;
	}
	
	@RequestMapping(Constants.rentalInquiryNotificationsUrl)
	public ModelAndView viewRentalInquiry(HttpServletRequest request)
	{
		SessionManager sessionManager = new SessionManager();
		if(!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
		
		sessionManager.getLoggedUserId(request.getSession());	
		int landlordId = Integer.parseInt(sessionManager.getLoggedUserId(request.getSession()));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.rentalInquiryNotificationsPage);

		ResponseState response = landlordOperations.fetchNotificationsByStudent(landlordId, new LandlordNotificationQueries());
		
		if (response.getStatusCode() == Constants.status404) {
			modelAndView.addObject("errorMessages", response.getMessages());

		} else {
			modelAndView.addObject("notifications", response.getResponseObject());
		}
		return modelAndView;
	}
}
