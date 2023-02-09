package com.StudentDeck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baseDesignPatterns.ResponseState;
import com.model.user.AdminUser;
import com.model.user.IUserFactory;
import com.model.user.LandlordUser;
import com.model.user.Student;
import com.model.user.UserFactory;
import com.model.user.UserType;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@WebMvcTest()
class SignupOperationsTest {
    SignUpOperations signUpOperations = new SignUpOperations();

    @Test
    void invalidPasswordTest() {
        Student student = (Student) getTestStudent();
        LandlordUser landlordUser = (LandlordUser) getTestLandLord();
        AdminUser adminUser = (AdminUser) getTestAdmin();
        student.setPassword("Password");
        landlordUser.setPassword("Password");
        adminUser.setPassword("Password");
        ResponseState responseState = signUpOperations.signUpStudent(student, "Password");
        assertEquals("This password does not follow the criteria", responseState.getMessages().get(0));
        responseState = signUpOperations.signUpLandlord(landlordUser, "Password");
        assertEquals("This password does not follow the criteria", responseState.getMessages().get(0));
        responseState = signUpOperations.signUpAdmin(adminUser, "Password");
        assertEquals("This password does not follow the criteria", responseState.getMessages().get(0));
        student.setPassword("Password@1234");
        landlordUser.setPassword("Landlord@1234");
        adminUser.setPassword("Admin@12345");
    }

    @Test
    void passwordsDoNotMatchTest() {
        Student student = (Student) getTestStudent();
        LandlordUser landlordUser = (LandlordUser) getTestLandLord();
        AdminUser adminUser = (AdminUser) getTestAdmin();
        student.setPassword("Password@1234");
        landlordUser.setPassword("Landlord@1234");
        adminUser.setPassword("Admin@12345");
        ResponseState responseState = signUpOperations.signUpStudent(student, "Password@123");
        assertEquals("Passwords do not match", responseState.getMessages().get(0));
        responseState = signUpOperations.signUpLandlord(landlordUser, "Landlord@123");
        assertEquals("Passwords do not match", responseState.getMessages().get(0));
        responseState = signUpOperations.signUpAdmin(adminUser, "Admin@1234");
        assertEquals("Passwords do not match", responseState.getMessages().get(0));
        student.setPassword("Password@1234");
        landlordUser.setPassword("Landlord@1234");
        adminUser.setPassword("Admin@12345");
    }

    @Test
    void invalidEmailAddressTest() {
        Student student = (Student) getTestStudent();
        LandlordUser landlordUser = (LandlordUser) getTestLandLord();
        AdminUser adminUser = (AdminUser) getTestAdmin();
        student.setEmail("kparkegmail.com");
        landlordUser.setEmail("gaillandlord.com");
        adminUser.setEmail("Adminadmin.com");
        ResponseState responseState = signUpOperations.signUpStudent(student, "Password@1234");
        assertEquals("Please enter a valid email address", responseState.getMessages().get(0));
        responseState = signUpOperations.signUpLandlord(landlordUser, "Landlord@1234");
        assertEquals("Please enter a valid email address", responseState.getMessages().get(0));
        responseState = signUpOperations.signUpAdmin(adminUser, "Admin@12345");
        assertEquals("Please enter a valid email address", responseState.getMessages().get(0));
        student.setEmail("kjparke@yahoo.com");
        landlordUser.setEmail("gail@landlord.com");
        adminUser.setEmail("Admin@admin.com");
    }

    @Test
    void emptyPasswordFieldTest() {
        Student student = (Student) getTestStudent();
        LandlordUser landlordUser = (LandlordUser) getTestLandLord();
        student.setPassword("");
        landlordUser.setPassword("");
        ResponseState responseState = signUpOperations.signUpStudent(student, "");
        assertEquals("Password field cannot be empty", responseState.getMessages().get(1));
        responseState = signUpOperations.signUpLandlord(landlordUser, "");
        assertEquals("Password field cannot be empty", responseState.getMessages().get(1));
        student.setPassword("Password@1234");
        landlordUser.setPassword("Landlord@1234");
    }

    @Test
    void emptyFirstNameTest() {
        Student student = (Student) getTestStudent();
        LandlordUser landlordUser = (LandlordUser) getTestLandLord();
        AdminUser adminUser = (AdminUser) getTestAdmin();
        student.setFirstName("");
        landlordUser.setFirstName("");
        adminUser.setFirstName("");
        ResponseState responseState = signUpOperations.signUpStudent(student, "Password@1234");
        assertEquals("First name cannot be empty", responseState.getMessages().get(0));
        responseState = signUpOperations.signUpLandlord(landlordUser, "Landlord@1234");
        assertEquals("First name cannot be empty", responseState.getMessages().get(0));
        responseState = signUpOperations.signUpAdmin(adminUser, "Admin@12345");
        assertEquals("First name cannot be empty", responseState.getMessages().get(0));
        student.setFirstName("Kevin");
        landlordUser.setFirstName("Gail");
        adminUser.setFirstName("Admin");
    }

    @Test
    void emptyLastNameTest() {
        Student student = (Student) getTestStudent();
        LandlordUser landlordUser = (LandlordUser) getTestLandLord();
        AdminUser adminUser = (AdminUser) getTestAdmin();
        student.setLastName("");
        landlordUser.setLastName("");
        adminUser.setLastName("");
        ResponseState responseState = signUpOperations.signUpStudent(student, "Password@1234");
        assertEquals("Last name cannot be empty", responseState.getMessages().get(0));
        responseState = signUpOperations.signUpLandlord(landlordUser, "Landlord@1234");
        assertEquals("Last name cannot be empty", responseState.getMessages().get(0));
        responseState = signUpOperations.signUpAdmin(adminUser, "Admin@12345");
        assertEquals("Last name cannot be empty", responseState.getMessages().get(0));
        student.setLastName("Parke");
        landlordUser.setLastName("Jones");
        adminUser.setLastName("User");
    }

    @Test
    void emptyBirthDateTest() {
        Student student = (Student) getTestStudent();
        LandlordUser landlordUser = (LandlordUser) getTestLandLord();
        student.setDateOfBirth("");
        landlordUser.setDateOfBirth("");
        ResponseState responseState = signUpOperations.signUpStudent(student, "Password@1234");
        assertEquals("Birth date cannot be empty", responseState.getMessages().get(0));
        responseState = signUpOperations.signUpLandlord(landlordUser, "Landlord@1234");
        assertEquals("Birth date cannot be empty", responseState.getMessages().get(0));
        student.setLastName("1994-03-31");
        landlordUser.setDateOfBirth("1974-01-01");
    }

    @Test
    void emptyPhoneTest() {
        Student student = (Student) getTestStudent();
        LandlordUser landlordUser = (LandlordUser) getTestLandLord();
        student.setPhone("");
        landlordUser.setPhone("");
        ResponseState responseState = signUpOperations.signUpStudent(student, "Password@1234");
        assertEquals("Phone cannot be empty", responseState.getMessages().get(0));
        responseState = signUpOperations.signUpLandlord(landlordUser, "Landlord@1234");
        assertEquals("Phone cannot be empty", responseState.getMessages().get(0));
        student.setPhone("9023456789");
        landlordUser.setPhone("9023456789");
    }

    @Test
    void emptySecurityQuestionTest() {
        Student student = (Student) getTestStudent();
        LandlordUser landlordUser = (LandlordUser) getTestLandLord();
        AdminUser adminUser = (AdminUser) getTestAdmin();
        student.setSecurityQuestion("");
        landlordUser.setSecurityQuestion("");
        adminUser.setSecurityQuestion("");
        ResponseState responseState = signUpOperations.signUpStudent(student, "Password@1234");
        assertEquals("Security question cannot be empty", responseState.getMessages().get(0));
        responseState = signUpOperations.signUpLandlord(landlordUser, "Landlord@1234");
        assertEquals("Security question cannot be empty", responseState.getMessages().get(0));
        responseState = signUpOperations.signUpAdmin(adminUser, "Admin@12345");
        assertEquals("Security question cannot be empty", responseState.getMessages().get(0));
        student.setSecurityQuestion("What color is the sky?");
        landlordUser.setSecurityQuestion("How many colors are on the canadian flag");
        adminUser.setSecurityQuestion("Who is the admin?");
    }

    @Test
    void emptySecurityAnswerTest() {
        Student student = (Student) getTestStudent();
        LandlordUser landlordUser = (LandlordUser) getTestLandLord();
        AdminUser adminUser = (AdminUser) getTestAdmin();
        student.setSecurityAnswer("");
        landlordUser.setSecurityAnswer("");
        adminUser.setSecurityAnswer("");
        ResponseState responseState = signUpOperations.signUpStudent(student, "Password@1234");
        assertEquals("Security answer cannot be empty", responseState.getMessages().get(0));
        responseState = signUpOperations.signUpLandlord(landlordUser, "Landlord@1234");
        assertEquals("Security answer cannot be empty", responseState.getMessages().get(0));
        responseState = signUpOperations.signUpAdmin(adminUser, "Admin@12345");
        assertEquals("Security answer cannot be empty", responseState.getMessages().get(0));
        student.setSecurityAnswer("Blue");
        landlordUser.setSecurityAnswer("2");
        adminUser.setSecurityAnswer("I am");
    }

    @Test
    void emptyCityTest() {
        Student student = (Student) getTestStudent();
        student.setCity("");
        ResponseState responseState = signUpOperations.signUpStudent(student, "Password@1234");
        assertEquals("City cannot be empty.", responseState.getMessages().get(0));
        student.setCity("Halifax");
    }

    @Test
    void studentSignUpTest() {
        Student student = (Student) getTestStudent();
        student.setCity("");
        ResponseState responseState = signUpOperations.signUpStudent(student, "Password@1234");
        assertEquals("City cannot be empty.", responseState.getMessages().get(0));
        student.setCity("Halifax");
    }

    Object getTestStudent() {
        IUserFactory userFactory = UserFactory.getInstance();
        Student student = (Student) userFactory.makeUser(UserType.STUDENT);
        student.setUserCredentials(userFactory.makeUserCredentials("kjparke@yahoo.com", "Password@1234"));
        student.setBasicInfo((userFactory.makeBasicUser("Kevin", "Parke", "Male", "1994-03-31")));
        student.setContactDetail(userFactory.makeContactDetails("1", "9034449999", "Grenada"));
        student.setUniversityDetail(userFactory.makeUniversityDetails("MACS", "Fall 2022", "Dalhousie University"));
        student.setStudyLocation(userFactory.makeStudentLocation("Canada", "Halifax"));
        student.setSecurityDetails(userFactory.makeSecurityDetails("What color is the sky?", "Blue"));
        return student;
    }

    Object getTestLandLord(){
        IUserFactory userFactory = UserFactory.getInstance();
        LandlordUser landlordUser = (LandlordUser) userFactory.makeUser(UserType.LANDLORD);
        landlordUser.setUserCredentials(userFactory.makeUserCredentials("gail@landlord.com", "Landlord@1234"));
        landlordUser.setBasicInfo(userFactory.makeBasicUser("Gail", "Jones", "Female", "1974-01-01"));
        landlordUser.setContactDetails(userFactory.makeContactDetails("1", "9023456789", "Georgia"));
        landlordUser.setSecurityDetails(userFactory.makeSecurityDetails("How many colors are on the canadian flag?", "2"));
        return landlordUser;
    }

    Object getTestAdmin() {
        IUserFactory userFactory = UserFactory.getInstance();
        AdminUser adminUser = (AdminUser) userFactory.makeUser(UserType.ADMIN);
        adminUser.setUserCredentials(userFactory.makeUserCredentials("Admin@admin.com", "Admin@12345"));
        adminUser.setBasicInfo(userFactory.makeBasicUser("Admin", "User", "Female", "2000-01-01"));
        adminUser.setSecurityDetails(userFactory.makeSecurityDetails("Who is the admin?", "I am"));
        return adminUser;
    }
}