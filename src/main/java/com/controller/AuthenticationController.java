package com.controller;

import javax.servlet.http.HttpServletRequest;

import com.StudentDeck.SignUpOperations;
import com.model.user.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.SessionManagement.SessionManager;
import com.StudentDeck.SigninOperations;
import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.ResponseState;

@RestController
public class AuthenticationController {
    SigninOperations signinOperations = new SigninOperations();
    SignUpOperations signupOperations = new SignUpOperations();


    @GetMapping(value = Constants.signUpUrl)
    public ModelAndView getSignUp(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(Constants.signUpPage);
        return modelAndView;
    }

    @GetMapping(value = Constants.signInUrl)
    public ModelAndView getSignIn(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(Constants.signInPage);
        return modelAndView;
    }

    @RequestMapping(value = Constants.signInUrl, params = "Student")
    public ModelAndView StudentSignIn(@RequestParam("email") String email, @RequestParam("password") String password,
                                      HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(Constants.signInPage);

        ResponseState response = signinOperations.studentSignIn(email, password, request);

        if (response.getStatusCode() == Constants.status404) {
            modelAndView.addObject("studentMsg", response.getMessages());
            modelAndView.addObject("user", response.getResponseObject());
            return modelAndView;
        } else {
            modelAndView.addObject("studentMsg", response.getMessages());
            return new ModelAndView(Constants.redirect + Constants.userDashboardUrl);

        }
    }

    @RequestMapping(value = Constants.signInUrl, params = "Landlord")
    public ModelAndView LandlordSignIn(@RequestParam("email") String email, @RequestParam("password") String password,
                                       HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(Constants.signInPage);

        ResponseState response = signinOperations.landlordSignIn(email, password, request);

        if (response.getStatusCode() == Constants.status404) {
            modelAndView.addObject("landlordMsg", response.getMessages());
            modelAndView.addObject("user", response.getResponseObject());
            return modelAndView;
        } else {
            return new ModelAndView(Constants.redirect + Constants.rentalDetailsUrl);
        }
    }


    @RequestMapping(value = Constants.signInUrl, params = "Admin")
    public ModelAndView AdminSignIn(@RequestParam("email") String email, @RequestParam("password") String password,
                                    HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(Constants.signInPage);

        ResponseState response = signinOperations.adminSignIn(email, password, request);

        if (response.getStatusCode() == Constants.status404) {
            modelAndView.addObject("adminMsg", response.getMessages());
            modelAndView.addObject("user", response.getResponseObject());
            return modelAndView;
        } else {
            return new ModelAndView(Constants.redirect + Constants.rentalApprovalUrl);
        }
    }

    @RequestMapping(value = Constants.signUpUrl, params = "Student")
    public ModelAndView StudentSignUp(@RequestParam("email") String email, @RequestParam("password") String password,
                                      @RequestParam(value = "confirmPassword") String confirmPassword, @RequestParam("firstName") String firstName,
                                      @RequestParam("lastName") String lastName, @RequestParam("gender") String gender,
                                      @RequestParam("dateOfBirth") String dateOfBirth, @RequestParam("securityQuestion") String securityQuestion,
                                      @RequestParam("securityAnswer") String securityAnswer, @RequestParam("university") String university,
                                      @RequestParam("course") String course, @RequestParam(value = "year") String year,
                                      @RequestParam(value = "semester") String semester, @RequestParam("phone") String phone,
                                      @RequestParam("countryCode") String countryCode, @RequestParam("city") String city,
                                      @RequestParam("country") String country, @RequestParam("studyCountry") String studyCountry) {

        IUserFactory userFactory = UserFactory.getInstance();
        Student student = (Student) userFactory.makeUser(UserType.STUDENT);
        student.setUserCredentials(userFactory.makeUserCredentials(email, password));
        student.setBasicInfo(userFactory.makeBasicUser(firstName, lastName, gender, dateOfBirth));
        student.setContactDetail(userFactory.makeContactDetails(countryCode, phone, country));
        student.setUniversityDetail(userFactory.makeUniversityDetails(course, semester + " " + year, university));
        student.setStudyLocation(userFactory.makeStudentLocation(studyCountry, city));
        student.setSecurityDetails(userFactory.makeSecurityDetails(securityQuestion, securityAnswer));

        ResponseState responseState = signupOperations.signUpStudent(student, confirmPassword);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(Constants.signUpPage);

        if (responseState.getStatusCode() == Constants.status404) {
            modelAndView.addObject("studentMessage", responseState.getMessages());
            modelAndView.addObject("student", responseState.getResponseObject());
            return modelAndView;
        }
        return new ModelAndView(Constants.redirect + Constants.signInUrl);
    }


    @RequestMapping(value = "/signUp", params = "Landlord")
    public ModelAndView LandLordSignUp(@RequestParam("email") String email,
                                       @RequestParam("password") String password,
                                       @RequestParam(value = "confirmPassword") String confirmPassword,
                                       @RequestParam("firstName") String firstName,
                                       @RequestParam("lastName") String lastName,
                                       @RequestParam("gender") String gender,
                                       @RequestParam("dateOfBirth") String dateOfBirth,
                                       @RequestParam("securityQuestion") String securityQuestion,
                                       @RequestParam("securityAnswer") String securityAnswer,
                                       @RequestParam("phone") String phone,
                                       @RequestParam("countryCode") String countryCode,
                                       @RequestParam("country") String country) {

        IUserFactory userFactory = UserFactory.getInstance();
        LandlordUser landlordUser = (LandlordUser) userFactory.makeUser(UserType.LANDLORD);
        landlordUser.setUserCredentials(userFactory.makeUserCredentials(email, password));
        landlordUser.setBasicInfo(userFactory.makeBasicUser(firstName, lastName, gender, dateOfBirth));
        landlordUser.setContactDetails(userFactory.makeContactDetails(countryCode, phone, country));
        landlordUser.setSecurityDetails(userFactory.makeSecurityDetails(securityQuestion, securityAnswer));

        ResponseState responseState = signupOperations.signUpLandlord(landlordUser, confirmPassword);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(Constants.signUpPage);

        if (responseState.getStatusCode() == Constants.status404) {
            modelAndView.addObject("landlordMessage", responseState.getMessages());
            modelAndView.addObject("landlord", responseState.getResponseObject());
            return modelAndView;
        } else {
            return new ModelAndView(Constants.redirect + Constants.signInUrl);
        }
    }

    @RequestMapping(value = Constants.signUpUrl, params = "Admin")
    public ModelAndView AdminSignUp(@RequestParam("email") String email,
                                    @RequestParam("password") String password,
                                    @RequestParam(value = "confirmPassword") String confirmPassword,
                                    @RequestParam("firstName") String firstName,
                                    @RequestParam("lastName") String lastName,
                                    @RequestParam("securityQuestion") String securityQuestion,
                                    @RequestParam("securityAnswer") String securityAnswer) {

        IUserFactory userFactory = UserFactory.getInstance();
        AdminUser adminUser = (AdminUser) userFactory.makeUser(UserType.ADMIN);
        adminUser.setUserCredentials(userFactory.makeUserCredentials(email, password));
        adminUser.setBasicInfo(userFactory.makeBasicUser(firstName, lastName, "N/A", "N/A"));
        adminUser.setSecurityDetails(userFactory.makeSecurityDetails(securityQuestion, securityAnswer));

        ResponseState responseState = signupOperations.signUpAdmin(adminUser, confirmPassword);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(Constants.signUpPage);

        if (responseState.getStatusCode() == Constants.status404) {
            modelAndView.addObject("adminMessage", responseState.getMessages());
            modelAndView.addObject("adminUser", responseState.getResponseObject());
            return modelAndView;
        } else {
            return new ModelAndView(Constants.redirect + Constants.signInUrl);
        }
    }

    @GetMapping(Constants.logoutUrl)
    public ModelAndView logout(HttpServletRequest request) {
        SessionManager sessionManager = new SessionManager();
        if (!sessionManager.checkIfValidSession(request.getSession())) {
            return new ModelAndView(Constants.redirect + Constants.signInUrl);
        }
        sessionManager.destroySession(request.getSession());
        return new ModelAndView(Constants.redirect + Constants.signInUrl);
    }
}
