package model;

import com.StudentDeck.Utils.Constants;
import com.model.user.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserFactoryTest {

    Object getTestStudent() {
        IUserFactory userFactory = UserFactory.getInstance();
        Student student = (Student) userFactory.makeUser(UserType.STUDENT);
        student.setUserCredentials(userFactory.makeUserCredentials("kjparke@gmail.com", "Password@123"));
        student.setBasicInfo((userFactory.makeBasicUser("Kevin", "Parke", "Male", "1994-03-31")));
        student.setContactDetail(userFactory.makeContactDetails("1", "9034449999", "Grenada"));
        student.setUniversityDetail(userFactory.makeUniversityDetails("MACS", "Fall 2022", "Dalhousie University"));
        student.setStudyLocation(userFactory.makeStudentLocation("Canada", "Halifax"));
        student.setSecurityDetails(userFactory.makeSecurityDetails("What color is the sky?", "Blue"));
        return student;
    }

    @Test
    void makeUserTest() {
        IUserFactory userFactory = UserFactory.getInstance();
        assertTrue((Student) userFactory.makeUser(UserType.STUDENT) instanceof Student);
        assertTrue((LandlordUser) userFactory.makeUser(UserType.LANDLORD) instanceof LandlordUser);
        assertTrue((AdminUser) userFactory.makeUser(UserType.ADMIN) instanceof AdminUser);
    }

    @Test
    void makeCredentialsTest() {
        Student student = (Student) getTestStudent();
        assertEquals(student.getEmail(), "kjparke@gmail.com");
        assertEquals(student.getPassword(), "Password@123");
    }

    @Test
    void makeBasicInfoTest() {
        Student student = (Student) getTestStudent();
        assertEquals(student.getFirstName(), "Kevin");
        assertEquals(student.getLastName(), "Parke");
        assertEquals(student.getGender(), "Male");
        assertEquals(student.getDateOfBirth(), "1994-03-31");
    }

    @Test
    void makeContactDetailsTest() {
        Student student = (Student) getTestStudent();
        assertEquals(student.getCountryCode(), "1");
        assertEquals(student.getPhone(), "9034449999");
        assertEquals(student.getCountry(), "Grenada");
    }

    @Test
    void makeUniversityDetailTest() {
        Student student = (Student) getTestStudent();
        assertEquals(student.getCourse(),"MACS");
        assertEquals(student.getIntake(),"Fall 2022");
        assertEquals(student.getUniversity(),"Dalhousie University");
    }

    @Test
    void makeStudyLocationTest() {
        Student student = (Student) getTestStudent();
        assertEquals(student.getStudyCountry(), "Canada");
        assertEquals(student.getCity(), "Halifax");
    }

    @Test
    void makeSecurityDetailsTest() {
        Student student = (Student) getTestStudent();
        assertEquals(student.getSecurityQuestion(), "What color is the sky?");
        assertEquals(student.getSecurityAnswer(), "Blue");
    }
}
