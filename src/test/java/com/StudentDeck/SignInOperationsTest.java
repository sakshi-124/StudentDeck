package com.StudentDeck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.StudentDeck.Utils.Constants;
import com.StudentDeck.Utils.PasswordUtil;
import com.baseDesignPatterns.ResponseState;
import com.model.user.AdminUser;
import com.model.user.IUserFactory;
import com.model.user.LandlordUser;
import com.model.user.Student;
import com.model.user.User;
import com.model.user.UserFactory;
import com.model.user.UserType;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@WebMvcTest(LandlordRentalOperations.class)

public class SignInOperationsTest {

    SigninOperations signinOperations = new SigninOperations();
    MockHttpServletRequest request = new MockHttpServletRequest();

    static PasswordUtil passwordUtil = PasswordUtil.getInstance();
    @Test
    void verifyEmptyEmail() throws Exception {
        List<String> msg = new ArrayList<String>();
        User studentUser = new Student();
        studentUser = demoUser();
        studentUser.setEmail("");
        SigninOperations.verifyFields(studentUser, msg);
        assertEquals("Email is empty", msg.get(0));
    }

    @Test
    void verifyEmptyPassword() throws Exception {
        List<String> msg = new ArrayList<String>();
        User studentUser = new Student();
        studentUser = demoUser();
        studentUser.setPassword("");
        SigninOperations.verifyFields(studentUser, msg);
        assertEquals("Password is empty", msg.get(0));
    }

    @Test
    void verifyValidEmail()
    {
        List<String> msg = new ArrayList<String>();
        User studentUser = new Student();
        studentUser = demoUser();
        studentUser.setEmail("sakshigmail.com");
        SigninOperations.verifyFields(studentUser, msg);
        assertEquals("Please Enter Correct Email", msg.get(0));
    }

    @Test
    void verifyValidPassword()
    {
        List<String> msg = new ArrayList<String>();
        User studentUser = new Student();
        studentUser = demoUser();
        studentUser.setPassword(passwordUtil.encrypt("passwordTest"));
        SigninOperations.verifyFields(studentUser, msg);
        assertEquals("Password must follow criteria", msg.get(0));
    }

    @Test
    void  studentValidSignIn()
    {
        IUserFactory factory = UserFactory.getInstance();
        Student studentUser = (Student) factory.makeUser(UserType.STUDENT);

        studentUser.setEmail("sakshi@gmail.com");
        studentUser.setPassword(("Sakshi@123456"));
        ResponseState response = signinOperations.studentSignIn(studentUser.getEmail(),studentUser.getPassword(),request);
        assertEquals(response.getStatusCode(), Constants.status200);
    }
    @Test
    void  studentInvalidSignIn()
    {
        IUserFactory factory = UserFactory.getInstance();
        Student studentUser = (Student) factory.makeUser(UserType.STUDENT);

        studentUser.setEmail("test@user.com");
        studentUser.setPassword("Password@12345");
        ResponseState response = signinOperations.studentSignIn(studentUser.getEmail(),studentUser.getPassword(),request);
        assertEquals(response.getStatusCode(), Constants.status404);
    }

    @Test
    void adminValidSignIn()
    {
        IUserFactory factory = UserFactory.getInstance();
        AdminUser adminUser = (AdminUser) factory.makeUser(UserType.ADMIN);

        adminUser.setEmail("admin@studentdeck.ca");
        adminUser.setPassword("Admin@12345678");
        ResponseState response = signinOperations.adminSignIn(adminUser.getEmail(),adminUser.getPassword(),request);
        assertEquals(response.getStatusCode(), Constants.status200);
    }

    @Test
    void adminInvalidSignIn()
    {
        IUserFactory factory = UserFactory.getInstance();
        AdminUser adminUser = (AdminUser) factory.makeUser(UserType.ADMIN);

        adminUser.setEmail("admin@gmail.com");
        adminUser.setPassword("Admin@1234");
        ResponseState response = signinOperations.adminSignIn(adminUser.getEmail(),adminUser.getPassword(),request);
        assertEquals(response.getStatusCode(), Constants.status404);
    }

    @Test
    void landlordValidSignIn()
    {
        IUserFactory factory = UserFactory.getInstance();
        LandlordUser landlordUser = (LandlordUser) factory.makeUser(UserType.LANDLORD);

        landlordUser.setEmail("universal@gmail.com");
        landlordUser.setPassword("Landlord@12345678");
        ResponseState response = signinOperations.landlordSignIn(landlordUser.getEmail(),landlordUser.getPassword(),request);
        assertEquals(response.getStatusCode(), Constants.status200);
    }

    @Test
    void landlordInvalidSignIn()
    {
        IUserFactory factory = UserFactory.getInstance();
        LandlordUser landlordUser = (LandlordUser) factory.makeUser(UserType.LANDLORD);

        landlordUser.setEmail("landlord@gmail.com");
        landlordUser.setPassword("Landlord@12345");
        ResponseState response = signinOperations.landlordSignIn(landlordUser.getEmail(),landlordUser.getPassword(),request);
        assertEquals(response.getStatusCode(), Constants.status404);
    }


    private User demoUser()
    {
        User user = new Student();
        user.setEmail("sk790317@dal.ca");
        user.setPassword(passwordUtil.encrypt("Test@12345"));
        return user;
    }
}
