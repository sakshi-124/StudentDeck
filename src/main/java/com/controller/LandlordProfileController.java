package com.controller;

import com.SessionManagement.SessionManager;
import com.StudentDeck.UserProfileOperations;
import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.ResponseState;
import com.model.ErrorResponse;
import com.model.user.LandlordUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class LandlordProfileController {
    private UserProfileOperations userProfileOperations = new UserProfileOperations();
    private LandlordUser landlordUser = new LandlordUser();

    @GetMapping(Constants.landlordProfileUrl) @RequestMapping(Constants.landlordProfileUrl)
    public ModelAndView showCurrentLandlord(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(Constants.landlordProfilePage);
        SessionManager sessionManager = new SessionManager();
        if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}

        String userID = sessionManager.getLoggedUserId(request.getSession());
        ResponseState responseState = userProfileOperations.fetchByLandlordId(Integer.parseInt(userID));
        landlordUser = (LandlordUser) responseState.getResponseObject();

        modelAndView.addObject("landlord", responseState.getResponseObject());
        modelAndView.addObject("editMode", "False");
        return modelAndView;
    }

    @RequestMapping(value = Constants.editLandlordProfileUrl)
    public ModelAndView editLandlordProfile(@RequestParam("email") String email, @RequestParam("firstName") String firstName,
                                            @RequestParam("lastName") String lastName, @RequestParam("phone") String phone,
                                            @RequestParam("countryCode")String countryCode, @RequestParam("country") String country,
                                            HttpServletRequest request) {

    	SessionManager sessionManager = new SessionManager();
        if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}

        ModelAndView modelAndView = new ModelAndView(Constants.landlordProfilePage);
        landlordUser.setEmail(email);
        landlordUser.setFirstName(firstName);
        landlordUser.setLastName(lastName);
        landlordUser.setPhone(phone);
        landlordUser.setCountryCode(countryCode);
        landlordUser.setCountry(country);
        ResponseState responseState = userProfileOperations.updateUser(landlordUser, "LANDLORD");
        modelAndView.addObject("landlord", responseState.getResponseObject());
        modelAndView.addObject("editMode", "True");
        return modelAndView;
    }

    @GetMapping(Constants.deactivateLandlordUrl) @RequestMapping(Constants.deactivateLandlordUrl)
    public ModelAndView deactivateLandlord(@RequestParam("securityAnswer") String securityAnswer,  HttpServletRequest request) {

    	SessionManager sessionManager = new SessionManager();
        if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}

        ModelAndView modelAndView = new ModelAndView(Constants.landlordProfilePage);
        ResponseState responseState = userProfileOperations.deactivateUser(landlordUser, securityAnswer, Constants.student);
        if(responseState.getMessages().isEmpty()){
            return new ModelAndView(Constants.redirect + Constants.signInUrl);
        }
        modelAndView.addObject("landlord", responseState.getResponseObject());
        modelAndView.addObject("message", responseState.getMessages());
        return modelAndView;
    }

    @RequestMapping(Constants.changeLandlordPasswordUrl)
    public ModelAndView changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword
    		, HttpServletRequest request){
    	
    	SessionManager sessionManager = new SessionManager();
        if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
        
        ModelAndView modelAndView = new ModelAndView(Constants.landlordProfilePage);
        boolean isPasswordMatched = landlordUser.getPassword().equals(oldPassword);
        ResponseState responseState;
        if(isPasswordMatched) {
            responseState = userProfileOperations.updatePassword(landlordUser.getId(), newPassword, Constants.landlord);
            responseState.setResponseObject(landlordUser);
        } else {
            responseState = new ErrorResponse();
            responseState.setMessages(List.of("Incorrect previous password, please try again!", "Failure"));
            modelAndView.addObject("message", responseState.getMessages());
            return modelAndView;
        }
        return modelAndView;
    }
}
