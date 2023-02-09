package com.StudentDeck.Utils;

import com.database.UserQueries;
import com.model.user.AdminUser;
import com.model.user.LandlordUser;
import com.model.user.Student;
import com.model.user.UserType;

import java.util.ArrayList;
import java.util.List;

public class FormVerifier {
    public List<String> verifyStudentFields(Object user) {
        List<String> errorMessages = new ArrayList<>();
        Student student = (Student) user;
        UserQueries userQueries = new UserQueries();

        Integer numUsersWithEmail = userQueries.userEmailCheck(student.getEmail(), UserType.STUDENT);

        if (numUsersWithEmail > 0) {
            errorMessages.add("This user already exists!");
        }
        if (!student.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{10,}$")) {
            errorMessages.add("This password does not follow the criteria");
        }
        if (!student.getEmail().matches("^(.+)@(\\S+)$")) {
            errorMessages.add("Please enter a valid email address");
        }
        if (student.getEmail().trim().isEmpty()) {
            errorMessages.add("Email field cannot be empty");
        }
        if (student.getPassword().trim().isEmpty()) {
            errorMessages.add("Password field cannot be empty");
        }
        if (student.getFirstName().trim().isEmpty()) {
            errorMessages.add("First name cannot be empty");
        }
        if (student.getLastName().trim().isEmpty()) {
            errorMessages.add("Last name cannot be empty");
        }
        if (student.getDateOfBirth().trim().isEmpty()) {
            errorMessages.add("Birth date cannot be empty");
        }
        if (student.getPhone().trim().isEmpty()) {
            errorMessages.add("Phone cannot be empty");
        }
        if (student.getPassword().trim().isEmpty()) {
            errorMessages.add("Password cannot be empty");
        }
        if (student.getSecurityQuestion().trim().isEmpty()) {
            errorMessages.add("Security question cannot be empty");
        }
        if (student.getSecurityAnswer().trim().isEmpty()) {
            errorMessages.add("Security answer cannot be empty");
        }
        if (student.getCity().trim().isEmpty()) {
            errorMessages.add("City cannot be empty.");
        }
        return errorMessages;
    }

    public List<String> verifyLandlordFields(Object user) {
        List<String> errorMessages = new ArrayList<>();
        LandlordUser landlordUser = (LandlordUser) user;
        UserQueries userQueries = new UserQueries();

        Integer numUsersWithEmail = userQueries.userEmailCheck(landlordUser.getEmail(), UserType.LANDLORD);

        if (numUsersWithEmail > 0) {
            errorMessages.add("This user already exists!");
        }
        if (!landlordUser.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{10,}$")) {
            errorMessages.add("This password does not follow the criteria");
        }
        if (!landlordUser.getEmail().matches("^(.+)@(\\S+)$")) {
            errorMessages.add("Please enter a valid email address");
        }
        if (landlordUser.getEmail().trim().isEmpty()) {
            errorMessages.add("Email field cannot be empty");
        }
        if (landlordUser.getPassword().trim().isEmpty()) {
            errorMessages.add("Password field cannot be empty");
        }
        if (landlordUser.getFirstName().trim().isEmpty()) {
            errorMessages.add("First name cannot be empty");
        }
        if (landlordUser.getLastName().trim().isEmpty()) {
            errorMessages.add("Last name cannot be empty");
        }
        if (landlordUser.getDateOfBirth().trim().isEmpty()) {
            errorMessages.add("Birth date cannot be empty");
        }
        if (landlordUser.getPhone().trim().isEmpty()) {
            errorMessages.add("Phone cannot be empty");
        }
        if (landlordUser.getPassword().trim().isEmpty()) {
            errorMessages.add("Password cannot be empty");
        }
        if (landlordUser.getSecurityQuestion().trim().isEmpty()) {
            errorMessages.add("Security question cannot be empty");
        }
        if (landlordUser.getSecurityAnswer().trim().isEmpty()) {
            errorMessages.add("Security answer cannot be empty");
        }
        return errorMessages;
    }

    public List<String> verifyAdminFields(Object user) {
        List<String> errorMessages = new ArrayList<>();
        AdminUser adminUser = (AdminUser) user;
        UserQueries userQueries = new UserQueries();

        Integer numUsersWithEmail = userQueries.userEmailCheck(adminUser.getEmail(), UserType.ADMIN);

        if (numUsersWithEmail > 0) {
            errorMessages.add("This user already exists!");
        }
        if (!adminUser.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{10,}$")) {
            errorMessages.add("This password does not follow the criteria");
        }
        if (!adminUser.getEmail().matches("^(.+)@(\\S+)$")) {
            errorMessages.add("Please enter a valid email address");
        }
        if (adminUser.getEmail().trim().isEmpty()) {
            errorMessages.add("Email field cannot be empty");
        }
        if (adminUser.getPassword().trim().isEmpty()) {
            errorMessages.add("Password field cannot be empty");
        }
        if (adminUser.getFirstName().trim().isEmpty()) {
            errorMessages.add("First name cannot be empty");
        }
        if (adminUser.getLastName().trim().isEmpty()) {
            errorMessages.add("Last name cannot be empty");
        }
        if (adminUser.getDateOfBirth().trim().isEmpty()) {
            errorMessages.add("Birth date cannot be empty");
        }
        if (adminUser.getPassword().trim().isEmpty()) {
            errorMessages.add("Password cannot be empty");
        }
        if (adminUser.getSecurityQuestion().trim().isEmpty()) {
            errorMessages.add("Security question cannot be empty");
        }
        if (adminUser.getSecurityAnswer().trim().isEmpty()) {
            errorMessages.add("Security answer cannot be empty");
        }
        return errorMessages;
    }

}
