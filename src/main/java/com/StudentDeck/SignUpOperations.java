package com.StudentDeck;

import com.StudentDeck.Utils.FormVerifier;
import com.StudentDeck.Utils.PasswordUtil;
import com.baseDesignPatterns.ResponseState;
import com.database.authentication.AdminAuthStrategy;
import com.database.authentication.AuthenticationContext;
import com.database.authentication.LandlordAuthStrategy;
import com.database.authentication.StudentAuthStrategy;
import com.model.ErrorResponse;
import com.model.SuccessResponse;
import com.model.user.AdminUser;
import com.model.user.LandlordUser;
import com.model.user.Student;

import java.util.List;

public class SignUpOperations {
    public ResponseState signUpStudent(Object user, String confirmPassword) {
        Student student = (Student) user;

        ResponseState response;
        FormVerifier formVerifier = new FormVerifier();

        List<String> messages = formVerifier.verifyStudentFields(student);

        if (student.getPassword().equals(confirmPassword)) {
            PasswordUtil passwordUtil = PasswordUtil.getInstance();
            student.setPassword(passwordUtil.encrypt(student.getPassword()));
        } else {
            messages.add("Passwords do not match");
        }

        if (!messages.isEmpty()) {
            response = new ErrorResponse();
            response.setMessages(messages);
            response.setResponseObject(student);
            return response;
        }

        AuthenticationContext.getInstance().setAuthStrategy(new StudentAuthStrategy());
        AuthenticationContext.getInstance().signUp(student);

        response = new SuccessResponse();
        response.setMessages(List.of("Sign in successful", "Welcome to StudentDeck!"));
        return response;
    }

    public ResponseState signUpLandlord(Object user, String confirmPassword) {
        LandlordUser landlordUser = (LandlordUser) user;

        ResponseState response;
        FormVerifier formVerifier = new FormVerifier();

        List<String> messages = formVerifier.verifyLandlordFields(landlordUser);

        if (landlordUser.getPassword().equals(confirmPassword)) {
            PasswordUtil passwordUtil = PasswordUtil.getInstance();
            landlordUser.setPassword(passwordUtil.encrypt(landlordUser.getPassword()));
        } else {
            messages.add("Passwords do not match");
        }

        if (!messages.isEmpty()) {
            response = new ErrorResponse();
            response.setMessages(messages);
            response.setResponseObject(landlordUser);
            return response;
        }

        AuthenticationContext.getInstance().setAuthStrategy(new LandlordAuthStrategy());
        AuthenticationContext.getInstance().signUp(landlordUser);

        response = new SuccessResponse();
        response.setMessages(List.of("Sign in successful", "Welcome to StudentDeck!"));
        return response;
    }
    public ResponseState signUpAdmin(Object user, String confirmPassword) {
        AdminUser adminUser = (AdminUser) user;

        ResponseState response;
        FormVerifier formVerifier = new FormVerifier();
        List<String> messages = formVerifier.verifyAdminFields(adminUser);


        if (adminUser.getPassword().equals(confirmPassword)) {
            PasswordUtil passwordUtil = PasswordUtil.getInstance();
            adminUser.setPassword(passwordUtil.encrypt(adminUser.getPassword()));
        } else {
            messages.add("Passwords do not match");
        }

        if (!messages.isEmpty()) {
            response = new ErrorResponse();
            response.setMessages(messages);
            response.setResponseObject(adminUser);
            return response;
        }

        AuthenticationContext.getInstance().setAuthStrategy(new AdminAuthStrategy());
        AuthenticationContext.getInstance().signUp(adminUser);

        response = new SuccessResponse();
        response.setMessages(List.of("Sign in successful", "Welcome to StudentDeck!"));
        return response;
    }
}
