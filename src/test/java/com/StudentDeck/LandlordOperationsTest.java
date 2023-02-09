package com.StudentDeck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.ResponseState;
import com.repositoryMock.LandlordNotificationQueriesMock;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@WebMvcTest(LandlordOperations.class)
public class LandlordOperationsTest {
	
	LandlordOperations landlordOperations = new LandlordOperations();
	
	@Test
	public void fetchNotificationsByAdminTest() {
		ResponseState response = landlordOperations.fetchNotificationsByAdmin(123, new LandlordNotificationQueriesMock());
		assertEquals(response.getStatusCode(), Constants.status200);
		assertEquals(response.getMessages().size(), 0);		
	}
	
	@Test
	public void fetchNotificationsByStudentTest() {
		ResponseState response = landlordOperations.fetchNotificationsByStudent(123, new LandlordNotificationQueriesMock());
		assertEquals(response.getStatusCode(), Constants.status200);
		assertEquals(response.getMessages().size(), 0);		
	}

}
