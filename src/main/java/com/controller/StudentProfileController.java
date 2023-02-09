package com.controller;


import java.util.List;

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
import com.model.ErrorResponse;
import com.model.user.Student;

@RestController
public class StudentProfileController {
    UserProfileOperations userProfileOperations = new UserProfileOperations();
    Student studentUser = new Student();

    @GetMapping(Constants.userProfileUrl) @RequestMapping(Constants.userProfileUrl)
    public ModelAndView showCurrentStudent(HttpServletRequest request) {
    	SessionManager sessionManager = new SessionManager();
        if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
        ModelAndView modelAndView = new ModelAndView(Constants.userProfilePage);

        String userID = sessionManager.getLoggedUserId(request.getSession());
        ResponseState responseState = userProfileOperations.fetchByStudentId(Integer.parseInt(userID));
        studentUser = (Student) responseState.getResponseObject();
        modelAndView.addObject("student", responseState.getResponseObject());
        modelAndView.addObject("editMode", "False");
        return modelAndView;
    }

    @RequestMapping(value = Constants.editUserProfileUrl)
    public ModelAndView editUserProfile(@RequestParam("email") String email, @RequestParam("firstName") String firstName,
                                   @RequestParam("lastName") String lastName, @RequestParam("university") String university,
                                   @RequestParam("course") String course, @RequestParam("intake") String intake,
                                   @RequestParam("phone") String phone, @RequestParam("countryCode")String countryCode,
                                   @RequestParam("country") String country, @RequestParam("studyCountry") String studyCountry,
                                   HttpServletRequest request) {
    	SessionManager sessionManager = new SessionManager();
        if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
        
        ModelAndView modelAndView = new ModelAndView(Constants.userProfilePage);
        studentUser.setEmail(email);
        studentUser.setFirstName(firstName);
        studentUser.setLastName(lastName);
        studentUser.setUniversity(university);
        studentUser.setCourse(course);
        studentUser.setIntake(intake);
        studentUser.setPhone(phone);
        studentUser.setCountryCode(countryCode);
        studentUser.setCountry(country);
        studentUser.setStudyCountry(studyCountry);
        ResponseState responseState = userProfileOperations.updateUser(studentUser, "STUDENT");
        modelAndView.addObject("student", responseState.getResponseObject());
        modelAndView.addObject("editMode", "True");
        return modelAndView;
    }

    @GetMapping(Constants.deactivateStudentUrl) @RequestMapping(Constants.deactivateStudentUrl)
    public ModelAndView deactivateStudent(@RequestParam("securityAnswer") String securityAnswer,  HttpServletRequest request) {
    	SessionManager sessionManager = new SessionManager();
        if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
        ModelAndView modelAndView = new ModelAndView(Constants.userProfilePage);
        ResponseState responseState = userProfileOperations.deactivateUser(studentUser, securityAnswer, Constants.student);
        if(responseState.getMessages().isEmpty()){
            return new ModelAndView(Constants.redirect + Constants.signInUrl);
        }
        modelAndView.addObject("student", responseState.getResponseObject());
        modelAndView.addObject("message", responseState.getMessages());
        return modelAndView;
    }

    @RequestMapping(Constants.changeStudentPasswordUrl)
    public ModelAndView changeStudentPassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword,  HttpServletRequest request){
    	SessionManager sessionManager = new SessionManager();
        if (!sessionManager.checkIfValidSession(request.getSession())) {
			return new ModelAndView(Constants.redirect + Constants.signInUrl);
		}
        
        ModelAndView modelAndView = new ModelAndView(Constants.userProfilePage);
        boolean isPasswordMatched = studentUser.getPassword().equals(oldPassword);
        ResponseState responseState;
        if(isPasswordMatched) {
            responseState = userProfileOperations.updatePassword(studentUser.getId(), newPassword, Constants.student);
            responseState.setResponseObject(studentUser);
        } else {
            responseState = new ErrorResponse();
            responseState.setMessages(List.of("Incorrect previous password, please try again!", "Failure"));
            modelAndView.addObject("message", responseState.getMessages());
            return modelAndView;
        }
        return modelAndView;
    }
}
