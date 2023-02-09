package com.StudentDeck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.ResponseState;
import com.model.user.IUserFactory;
import com.model.user.Student;
import com.model.user.UserFactory;
import com.model.user.UserType;

@ExtendWith({SpringExtension.class})
@WebMvcTest()
public class UserProfileOperationsTest {
    UserProfileOperations userProfileOperations = new UserProfileOperations();

    @Test
    void fetchByStudentId() {
        ResponseState responseState = userProfileOperations.fetchByStudentId(23);
        assertEquals(responseState.getStatusCode(), Constants.status200);
        assertEquals(responseState.getMessages().size(), 0);
    }

    @Test
    void fetchByLandlordId() {
        ResponseState responseState = userProfileOperations.fetchByLandlordId(9);
        assertEquals(responseState.getStatusCode(), Constants.status200);
        assertEquals(responseState.getMessages().size(), 0);
    }

    @Test
    void updateUserTest()
    {
        Student student = (Student) getTestStudent();
        ResponseState response = userProfileOperations.updateUser(student, Constants.student);
        assertEquals(response.getStatusCode(), Constants.status200);
        assertEquals(response.getMessages().size(), 0);
    }

    @Test
    void invalidDeactivateUserTest() {
        Student student = (Student) getTestStudent();
        ResponseState response = userProfileOperations.deactivateUser(student,"What color is the sky", Constants.student);
        assertEquals(response.getStatusCode(), 404);
        assertEquals(response.getMessages().size(), 1);
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
        student.setId(14);
        return student;
    }
}
