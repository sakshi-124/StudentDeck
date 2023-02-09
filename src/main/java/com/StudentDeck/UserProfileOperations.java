package com.StudentDeck;

import com.StudentDeck.Utils.FormVerifier;
import com.StudentDeck.Utils.PasswordUtil;
import com.baseDesignPatterns.ResponseState;
import com.database.UserQueries;
import com.database.authentication.*;
import com.model.ErrorResponse;
import com.model.SuccessResponse;
import com.model.user.AdminUser;
import com.model.user.LandlordUser;
import com.model.user.Student;
import com.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserProfileOperations {
    public ResponseState fetchByStudentId(int id) {
        UserQueries userQueries = new UserQueries();
        return userQueries.fetchStudent(id);
    }

    public ResponseState fetchByLandlordId(int id) {
        UserQueries userQueries = new UserQueries();
        return userQueries.fetchLandlord(id);
    }

    public ResponseState fetchByAdminId(int id) {
        UserQueries userQueries = new UserQueries();
        return userQueries.fetchAdmin(id);
    }

    public ResponseState updateUser(Object user, String userType) {
        FormVerifier formVerifier = new FormVerifier();
        UserQueries userQueries = new UserQueries();
        List<String> messages;
        PasswordUtil passwordUtil = PasswordUtil.getInstance();

        if (userType.equals("STUDENT")) {
            messages = formVerifier.verifyStudentFields(user);
            if(messages.isEmpty()) {
                Student student = (Student) user;
                student.setPassword(passwordUtil.encrypt(student.getPassword()));
                return userQueries.updateStudent(user, new ArrayList<>());
            } else {
                return new ErrorResponse();
            }
        }
        if (userType.equals("LANDLORD")) {
            messages = formVerifier.verifyLandlordFields(user);
            if(messages.isEmpty()) {
                LandlordUser landlordUser = (LandlordUser) user;
                landlordUser.setPassword(passwordUtil.encrypt(landlordUser.getPassword()));
                return userQueries.updateLandLord(user, new ArrayList<>());
            } else {
                return new ErrorResponse();
            }
        } else if (userType.equals("ADMIN")) {
            messages = formVerifier.verifyAdminFields(user);
            if(messages.isEmpty()) {
                AdminUser adminUser = (AdminUser) user;
                String encryptPassword = passwordUtil.encrypt(adminUser.getPassword());
                adminUser.setPassword(encryptPassword);
                return userQueries.updateAdmin(adminUser, new ArrayList<>());
            } else {
                return new ErrorResponse();
            }
        }
        return new ErrorResponse();
    }

    public ResponseState deactivateUser(Object user, String securityAnswer, String userType) {
        List<String> messages = new ArrayList<>();
        ResponseState responseState = null;
        if(isAnswerValid(user, securityAnswer)) {
            responseState = new SuccessResponse();
            if (userType.equals("STUDENT")){
                Student student = (Student) user;
                AuthenticationContext.getInstance().setAuthStrategy(new StudentAuthStrategy());
                AuthenticationContext.getInstance().deactivateAccount(student.getId());
                responseState.setResponseObject(student);
            } else if (userType.equals("LANDLORD")){
                LandlordUser landlordUser = (LandlordUser) user;
                AuthenticationContext.getInstance().setAuthStrategy(new LandlordAuthStrategy());
                AuthenticationContext.getInstance().deactivateAccount(landlordUser.getId());
                responseState.setResponseObject(landlordUser);
            } else {
                AdminUser admin = (AdminUser) user;
                AuthenticationContext.getInstance().setAuthStrategy(new AdminAuthStrategy());
                AuthenticationContext.getInstance().deactivateAccount(admin.getId());
                responseState.setResponseObject(admin);
            }
            responseState.setMessages(messages);
            return responseState;
        }
        responseState = new ErrorResponse();
        messages.add("The response is incorrect, try again!");
        responseState.setMessages(messages);
        responseState.setResponseObject(user);
        return responseState;
    }

    public ResponseState updatePassword(int id, String newPassword, String userType) {
        ResponseState responseState = null;
        if (isValidPassword(newPassword)) {
            if (userType.equals("STUDENT")){
                AuthenticationContext.getInstance().setAuthStrategy(new StudentAuthStrategy());
                AuthenticationContext.getInstance().updatePassword(id, newPassword);
            } else if (userType.equals("LANDLORD")){
                AuthenticationContext.getInstance().setAuthStrategy(new LandlordAuthStrategy());
                AuthenticationContext.getInstance().updatePassword(id, newPassword);
            } else {
                AuthenticationContext.getInstance().setAuthStrategy(new AdminAuthStrategy());
                AuthenticationContext.getInstance().updatePassword(id, newPassword);
            }
            responseState = new SuccessResponse();
            return responseState;
        } else {
            responseState = new ErrorResponse();
            responseState.setMessages(List.of("Password does not match criteria"));
            return responseState;
        }
    }

    private boolean isAnswerValid(Object user, String formResponse) {
        User u = (User) user;
        if (u.getSecurityAnswer().trim().equals(formResponse.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isValidPassword(String password)
    {
        /* https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a */
        String passwordRegx = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{10,}$";
        if(password.matches(passwordRegx))
            return true;
        else
            return false;
    }
}
