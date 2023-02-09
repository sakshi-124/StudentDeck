package com.StudentDeck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.ResponseState;
import com.model.user.Student;
import com.repositoryMock.LandlordNotificationQueriesMock;
import com.repositoryMock.UserQueriesMock;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@WebMvcTest(StudentOperations.class)
public class StudentOperationsTest {
	StudentOperations studentOperations = new StudentOperations();
	
	@Test
	public void sendInquiryToLandlordTest() {
		List<String> messages = new ArrayList<String>();
		ResponseState response = studentOperations.sendInquiryToLandlord("title", "123", new Student(), new LandlordNotificationQueriesMock(), messages, (long) 1);
		assertEquals(response.getStatusCode(), Constants.status200);
		assertEquals(response.getMessages().size(), 0);		
	}
	
	@Test
	public void getStudentByIdTest() {
		List<String> messages = new ArrayList<String>();
		ResponseState response = studentOperations.getStudentById(0, new UserQueriesMock(), messages);
		assertEquals(response.getStatusCode(), Constants.status200);
		assertEquals(response.getMessages().size(), 0);		
	}
	
	@Test
	public void getAllListingsByCityCorrectTest() {
		List<String> messages = new ArrayList<String>();
		ResponseState response = studentOperations.getAllListingsByCity("hello", "1", new UserQueriesMock());
		assertEquals(response.getStatusCode(), Constants.status200);
		assertEquals(response.getMessages().size(), 0);		
	}
	
	@Test
	public void getAllListingsByCityEmptyCityTest() {
		List<String> messages = new ArrayList<String>();
		ResponseState response = studentOperations.getAllListingsByCity("", "1", new UserQueriesMock());
		assertEquals(response.getStatusCode(), Constants.status404);
		assertEquals(response.getMessages(), Arrays.asList("Please enter valid data in city"));		
	}

}
