package com.StudentDeck;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.SessionManagement.SessionManager;
import com.StudentDeck.Utils.PasswordUtil;
import com.baseDesignPatterns.ResponseState;
import com.database.authentication.AdminAuthStrategy;
import com.database.authentication.AuthenticationContext;
import com.database.authentication.LandlordAuthStrategy;
import com.database.authentication.StudentAuthStrategy;
import com.model.ErrorResponse;
import com.model.SuccessResponse;
import com.model.user.AdminUser;
import com.model.user.IUserFactory;
import com.model.user.LandlordUser;
import com.model.user.Student;
import com.model.user.User;
import com.model.user.UserFactory;
import com.model.user.UserType;

public class SigninOperations {

    static PasswordUtil passwordUtil = PasswordUtil.getInstance();
    public ResponseState studentSignIn(String email, String password,HttpServletRequest request) {
        ResponseState response = null;

        List<String> messages = new ArrayList<String>();
        String isValidUser;
        int userId;
        String userCity;
        IUserFactory factory = UserFactory.getInstance();
        Student student = (Student) factory.makeUser(UserType.STUDENT);

        student.setEmail(email);
        student.setPassword(passwordUtil.encrypt(password));

        verifyFields(student, messages);

        if (!messages.isEmpty()) {
            response = new ErrorResponse();
            response.setMessages(messages);
            response.setResponseObject(student);
            return response;
        }

        AuthenticationContext.getInstance().setAuthStrategy(new StudentAuthStrategy());
        isValidUser = AuthenticationContext.getInstance().signIn(student);

        String[] userDet = isValidUser.split("-");
        userId = Integer.parseInt(userDet[0]);
        userCity = userDet[1];

        if (userId != 0) {

            student.setId(userId);
            SessionManager sessionManager = new SessionManager();

            sessionManager.setSession(request.getSession(), student.getId().toString(), "STUDENT", userCity.toUpperCase());

            response = new SuccessResponse();
            response.setMessages(List.of("Login is Successful", "Success"));
            response.setResponseObject(student);

            return response;
        } else {
            response = new ErrorResponse();
            response.setMessages(List.of("Please Check Credentials", "Failed"));
            response.setResponseObject(student);
            student.setId(userId); // will set id as 0 so failed
            return response;
        }
    }

    public ResponseState adminSignIn(String email, String password, /* String userType, */ HttpServletRequest request) {
        ResponseState response = null;

        List<String> messages = new ArrayList<String>();
        String isValidUser;
        int userId;
        String userCity;
        IUserFactory factory = UserFactory.getInstance();

        AdminUser adminUser = (AdminUser) factory.makeUser(UserType.ADMIN);

        adminUser.setEmail(email);
        adminUser.setPassword(passwordUtil.encrypt(password));

        verifyFields(adminUser, messages);

        if (!messages.isEmpty()) {
            response = new ErrorResponse();
            response.setMessages(messages);
            response.setResponseObject(adminUser);
            return response;
        }

        AuthenticationContext.getInstance().setAuthStrategy(new AdminAuthStrategy());
        isValidUser = AuthenticationContext.getInstance().signIn(adminUser);

        String[] userDet = isValidUser.split("-");
        userId = Integer.parseInt(userDet[0]);
        userCity = userDet[1];

        if (userId != 0) {
            adminUser.setId(userId);
            SessionManager sessionManager = new SessionManager();

            sessionManager.setSession(request.getSession(), adminUser.getId().toString(), "ADMIN", userCity.toUpperCase());

            response = new SuccessResponse();
            response.setMessages(List.of("Login is Successful", "Success"));
            response.setResponseObject(adminUser);

        } else {
            response = new ErrorResponse();
            response.setMessages(List.of("Please Check Credentials", "Failed"));
            response.setResponseObject(adminUser);
            adminUser.setId(userId); // will set id as 0 so failed
        }
        return response;
    }

    public ResponseState landlordSignIn(String email, String password, /* String userType, */ HttpServletRequest request) {
        ResponseState response = null;

        List<String> messages = new ArrayList<String>();
        String isValidUser;
        int userId;
        String userCity;
        IUserFactory factory = UserFactory.getInstance();

        LandlordUser landlordUser = (LandlordUser) factory.makeUser(UserType.LANDLORD);

        landlordUser.setEmail(email);
        landlordUser.setPassword(passwordUtil.encrypt(password));

        verifyFields(landlordUser, messages);

        if (!messages.isEmpty()) {
            response = new ErrorResponse();
            response.setMessages(messages);
            response.setResponseObject(landlordUser);
            return response;
        }

        AuthenticationContext.getInstance().setAuthStrategy(new LandlordAuthStrategy());
        isValidUser = AuthenticationContext.getInstance().signIn(landlordUser);

        String[] userDet = isValidUser.split("-");
        userId = Integer.parseInt(userDet[0]);
        userCity = userDet[1];

        if (userId != 0) {

            landlordUser.setId(userId);
            SessionManager sessionManager = new SessionManager();

            sessionManager.setSession(request.getSession(), landlordUser.getId().toString(),"LANDLORD", userCity.toUpperCase());

            response = new SuccessResponse();
            response.setMessages(List.of("Login is Successful", "Success"));
            response.setResponseObject(landlordUser);

            return response;
        } else {
            response = new ErrorResponse();
            response.setMessages(List.of("Please Check Credentials", "Failed"));
            response.setResponseObject(landlordUser);
            landlordUser.setId(userId); // will set id as 0 so failed
            return response;
        }
    }


    public static boolean verifyFields(User _objUser, List<String> errorMessages) {
        if (_objUser.getEmail().trim().isEmpty() == true) errorMessages.add("Email is empty");
        else if (!isValidEmail(_objUser.getEmail()) == true) errorMessages.add("Please Enter Correct Email");

        if (passwordUtil.decrypt( _objUser.getPassword()).trim().isEmpty() == true) errorMessages.add("Password is empty");
        else if (!isValidPassword(passwordUtil.decrypt(_objUser.getPassword())) == true)
            errorMessages.add("Password must follow criteria");

        if (errorMessages.isEmpty() == true) return true;
        else return false;
    }

    public static boolean isValidEmail(String email) {
        /* https://mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/#:~:text=Email%20Regex%20%E2%80%93%20Simple%20Validation.&text=%2B)%40(%5CS%2B)%24,then%20a%20non%20whitespace%20character.&text=1.1%20A%20Java%20example%20using%20the%20above%20regex%20for%20email%20validation.&text=1.2%20Below%20is%20a%20JUnit,some%20valid%20and%20invalid%20emails. */
        String emailRegx = "^(.+)@(\\S+)$";

        if (email.matches(emailRegx)) return true;
        else return false;
    }

    public static boolean isValidPassword(String password) {
        /* https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a */
        String passwordRegx = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{10,}$";
        if (password.matches(passwordRegx)) return true;
        else return false;
    }
}

