package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.SessionManagement.SessionManager;
import com.StudentDeck.AdminOperations;
import com.StudentDeck.LandlordRentalOperations;
import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.ResponseState;
import com.database.LandlordNotificationQueries;
import com.database.RentalQueries;

@RestController
public class AdminController {

	LandlordRentalOperations rentalOperations = new LandlordRentalOperations();
	AdminOperations adminOperations = new AdminOperations();

	@GetMapping(Constants.rentalApprovalUrl)
	public ModelAndView rentalToApproved(HttpServletRequest request) {
		SessionManager sessionManager = new SessionManager();
		if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.rentalApprovalPage);
		ResponseState response = adminOperations.getListingsToBeApproved(new RentalQueries());

		if (response.getStatusCode() == Constants.status404) {
			modelAndView.addObject("message", response.getMessages());
		} else {
			modelAndView.addObject("message", response.getMessages());
			modelAndView.addObject("rentals", response.getResponseObject());
		}
		return modelAndView;
	}

	@RequestMapping(value = Constants.approveListingUrl + "/{id}/{title}/{landlordId}")
	public ModelAndView approveListing(@PathVariable("id") String id, @PathVariable("title") String title,
			@PathVariable("landlordId") String landlordId, HttpServletRequest request) {

		SessionManager sessionManager = new SessionManager();
		if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}

		sessionManager.getLoggedUserId(request.getSession());
		int adminId = Integer.parseInt(sessionManager.getLoggedUserId(request.getSession()));

		ResponseState response = adminOperations.approveListing(id, landlordId, title, new RentalQueries(),
				new LandlordNotificationQueries(), adminId);
		if (response.getStatusCode() == Constants.status404) {
			return new ModelAndView(Constants.redirect + Constants.rentalApprovalUrl);
		} else {
			return new ModelAndView(Constants.redirect + Constants.rentalApprovalUrl);
			// TODO - add error messages
		}
	}

	@RequestMapping(value = Constants.rejectListingUrl + "/{id}/{title}/{landlordId}")
	public ModelAndView rejectListing(@PathVariable("id") String id, @PathVariable("title") String title,
			@PathVariable("landlordId") String landlordId, HttpServletRequest request) {

		SessionManager sessionManager = new SessionManager();
		if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}

		sessionManager.getLoggedUserId(request.getSession());
		int adminId = Integer.parseInt(sessionManager.getLoggedUserId(request.getSession()));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.rentalApprovalPage);
		ResponseState response = adminOperations.rejectListing(landlordId, title, new LandlordNotificationQueries(), adminId);
		if (response.getStatusCode() == Constants.status200) {
			response = rentalOperations.deleteRental(id, new RentalQueries());
			if (response.getStatusCode() == Constants.status200) {
				return new ModelAndView(Constants.redirect + Constants.rentalApprovalUrl);
			}
		}
		return new ModelAndView(Constants.redirect + Constants.rentalApprovalUrl);
		// TODO - add error messages

	}

}
