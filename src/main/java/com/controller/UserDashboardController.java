package com.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.SessionManagement.SessionManager;
import com.StudentDeck.DashBoardOperations;
import com.StudentDeck.LandlordRentalOperations;
import com.StudentDeck.StudentOperations;
import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.ResponseState;
import com.database.DashBoardQueries;
import com.database.LandlordNotificationQueries;
import com.database.RentalQueries;
import com.database.UserQueries;
import com.model.user.Student;

@RestController
public class UserDashboardController {

	StudentOperations studentOperations = new StudentOperations();
	LandlordRentalOperations landlordRentalOperations = new LandlordRentalOperations();

	DashBoardOperations dashBoardOperations = new DashBoardOperations();

	@GetMapping("/userDashboard")
	@RequestMapping("/userDashboard")
	public ModelAndView userDashboard(HttpServletRequest request) {
		SessionManager sessionManager = new SessionManager();
		if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
		sessionManager.getLoggedUserId(request.getSession());
		String studentId = sessionManager.getLoggedUserId(request.getSession());
		String city = sessionManager.getUserCity(request.getSession());

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user-dashboard");

		ResponseState response = studentOperations.getAllListingsByCity(city, studentId, new UserQueries());

		if (response.getStatusCode() == Constants.status404) {
			modelAndView.addObject("message", response.getMessages());
		} else {
			modelAndView.addObject("message", response.getMessages());
			modelAndView.addObject("users", response.getResponseObject());
		}
		return modelAndView;
	}

	@RequestMapping(Constants.sendInquiryUrl + "/{landlordId}/{title}")
	public ModelAndView sendRentalInquiry(@PathVariable("title") String title,
										  @PathVariable("landlordId") String landlordId, HttpServletRequest request) {

		SessionManager sessionManager = new SessionManager();
		if(!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}

		sessionManager.getLoggedUserId(request.getSession());	
		int studentId = Integer.parseInt(sessionManager.getLoggedUserId(request.getSession()));

		List<String> messages = new ArrayList<String>();

		ResponseState response = studentOperations.getStudentById(studentId, new UserQueries(), messages);
		if (response.getStatusCode() == Constants.status200) {
			Student student = (Student) response.getResponseObject();

			response = studentOperations.sendInquiryToLandlord(title, landlordId, student,
					new LandlordNotificationQueries(), messages, studentId);
		}
		if(response.getStatusCode() == Constants.status200)	{
			response.setMessages(Arrays.asList("Inquiry Notification sent to admin.\nInquired about " + title));
		}
		ModelAndView model =  new ModelAndView("redirect:/viewRentalListings");
		model.addObject("message", response.getMessages());
		return model;
	}
	
	@RequestMapping(value=Constants.viewRentalListingsUrl, method = RequestMethod.GET)
	public ModelAndView viewRentalListings(@RequestParam("city") Optional<String> optionalCity,
										   HttpServletRequest request) {

		SessionManager sessionManager = new SessionManager();
		if(!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
		
		sessionManager.getLoggedUserId(request.getSession());	

		String city = null;
		if (optionalCity.isPresent()) {
			city = optionalCity.get();
		} else {
			city = sessionManager.getUserCity(request.getSession());
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(Constants.viewRentalListingsPage); 

		ResponseState response = landlordRentalOperations.getAllListingsByCity(city, new RentalQueries());

		if (response.getStatusCode() == Constants.status404) {
			modelAndView.addObject("message", response.getMessages());
		} else {
			modelAndView.addObject("message", response.getMessages());
			modelAndView.addObject("rentals", response.getResponseObject());
		}

		return modelAndView;
	}

	@RequestMapping(value = "/sendRequest/{id}")
	public ModelAndView sendRequest(@PathVariable("id") int id, HttpServletRequest request)
	{
		SessionManager sessionManager = new SessionManager();
        if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
        
		sessionManager.getLoggedUserId(request.getSession());
		int senderStudentId = Integer.parseInt(sessionManager.getLoggedUserId(request.getSession()));
		ResponseState response = dashBoardOperations.sendRequest(senderStudentId, id, new DashBoardQueries());
		ModelAndView modelAndView = new ModelAndView("redirect:/userDashboard");
		modelAndView.addObject("message",response.getMessages());
		return modelAndView;
	}

	@RequestMapping(value = "/viewRequests")
	public ModelAndView viewRequests(HttpServletRequest request)
	{
		SessionManager sessionManager = new SessionManager();
        if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
        
		sessionManager.getLoggedUserId(request.getSession());
		int studentId = Integer.parseInt(sessionManager.getLoggedUserId(request.getSession()));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("request-page");

		ResponseState response = dashBoardOperations.getMyFriendRequests(studentId, new DashBoardQueries());

		if (response.getStatusCode() == Constants.status404) {
			modelAndView.addObject("message", response.getMessages());
		} else {
			modelAndView.addObject("message", response.getMessages());
			modelAndView.addObject("users", response.getResponseObject());
		}

		return modelAndView;
	}

	@RequestMapping(value = "/changeRequest/{action}/{id}")
	public ModelAndView changeRequest(@PathVariable("id") int id, @PathVariable("action") String action, HttpServletRequest request) {
		SessionManager sessionManager = new SessionManager();
		sessionManager.getLoggedUserId(request.getSession());
		int receiverId = Integer.parseInt(sessionManager.getLoggedUserId(request.getSession()));
		ResponseState response = dashBoardOperations.changeRequest(id, receiverId, action, new DashBoardQueries());
		ModelAndView modelAndView = new ModelAndView("redirect:/viewRequests");
		modelAndView.addObject("message",response.getMessages());
		return modelAndView;
	}

	@RequestMapping(value = "/viewMyFriends")
	public ModelAndView viewMyFriends(HttpServletRequest request) {
		SessionManager sessionManager = new SessionManager();
        if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
        
		sessionManager.getLoggedUserId(request.getSession());
		int studentId = Integer.parseInt(sessionManager.getLoggedUserId(request.getSession()));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("view-my-friends");

		ResponseState response = dashBoardOperations.getMyFriends(studentId, new DashBoardQueries());

		if (response.getStatusCode() == Constants.status404) {
			modelAndView.addObject("message", response.getMessages());
		} else {
			modelAndView.addObject("message", response.getMessages());
			modelAndView.addObject("users", response.getResponseObject());
		}

		return modelAndView;
	}
}
