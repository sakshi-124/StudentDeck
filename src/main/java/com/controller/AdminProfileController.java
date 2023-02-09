package com.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.SessionManagement.SessionManager;
import com.StudentDeck.UserProfileOperations;
import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.ResponseState;
import com.model.user.AdminUser;

@RestController
public class AdminProfileController {
    private UserProfileOperations userProfileOperations = new UserProfileOperations();
    private AdminUser adminUser = new AdminUser();

    @GetMapping(Constants.adminProfileUrl) @RequestMapping(Constants.adminProfileUrl)
    public ModelAndView showCurrentAdmin(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("admin-profile");
        SessionManager sessionManager = new SessionManager();
        if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
        String userID = sessionManager.getLoggedUserId(request.getSession());

        ResponseState responseState = userProfileOperations.fetchByAdminId(Integer.parseInt(userID));
        adminUser = (AdminUser) responseState.getResponseObject();
        modelAndView.addObject("admin", responseState.getResponseObject());
        modelAndView.addObject("editMode", "False");
        return modelAndView;
    }

    @RequestMapping(value = Constants.editAdminUserProfileUrl)
    public ModelAndView editRental(@RequestParam("email") String email,
                                   @RequestParam("firstName") String firstName,
                                   @RequestParam("lastName") String lastName,
                                   HttpServletRequest request){

    	 SessionManager sessionManager = new SessionManager();
         if (!sessionManager.checkIfValidSession(request.getSession())) {
 			return new ModelAndView(Constants.redirect + Constants.signInUrl);
 		}
         
        ModelAndView modelAndView = new ModelAndView(Constants.adminProfilePage);
        adminUser.setEmail(email);
        adminUser.setFirstName(firstName);
        adminUser.setLastName(lastName);

        ResponseState responseState = userProfileOperations.updateUser(adminUser, Constants.admin);
        modelAndView.addObject("admin", responseState.getResponseObject());
        modelAndView.addObject("editMode", "True");
        return modelAndView;
    }

    @GetMapping(Constants.deactivateAdminUrl) @RequestMapping(Constants.deactivateAdminUrl)
    public ModelAndView deactivateAdmin(@RequestParam("securityAnswer") String securityAnswer, HttpServletRequest request) {
    	 SessionManager sessionManager = new SessionManager();
         if (!sessionManager.checkIfValidSession(request.getSession())) {
 			return new ModelAndView(Constants.redirect + Constants.signInUrl);
 		}
         
        ModelAndView modelAndView = new ModelAndView("admin-profile");
        ResponseState responseState = userProfileOperations.deactivateUser(adminUser, securityAnswer, Constants.admin);
        if(responseState.getMessages().isEmpty()){
            return new ModelAndView(Constants.redirect + Constants.signInUrl);
        }
        modelAndView.addObject("admin", responseState.getResponseObject());
        modelAndView.addObject("message", responseState.getMessages());
        return modelAndView;
    }

    @RequestMapping(Constants.changeAdminPasswordUrl)
    public ModelAndView changeAdminPassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword,
    		HttpServletRequest request){
    	 SessionManager sessionManager = new SessionManager();
         if (!sessionManager.checkIfValidSession(request.getSession())) {
 			return new ModelAndView(Constants.redirect + Constants.signInUrl);
 		}
         
        ModelAndView modelAndView = new ModelAndView(Constants.adminProfilePage);
        boolean isPasswordMatched = adminUser.getPassword().equals(oldPassword);
        if(isPasswordMatched) {
            userProfileOperations.updatePassword(adminUser.getId(), newPassword, Constants.student);
        }
        return modelAndView.addObject("admin", adminUser);
    }
}
