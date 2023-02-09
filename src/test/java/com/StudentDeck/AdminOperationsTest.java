package com.StudentDeck;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.ResponseState;
import com.model.RentalList;
import com.repositoryMock.LandlordNotificationQueriesMock;
import com.repositoryMock.RentalQueriesMock;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@WebMvcTest(AdminOperations.class)
public class AdminOperationsTest {
	
	AdminOperations adminOperations = new AdminOperations();
	
	@Test
	public void getListingsToBeApprovedTest() {
		ResponseState response = adminOperations.getListingsToBeApproved(new RentalQueriesMock());
		assertEquals(response.getStatusCode(), Constants.status200);
		assertEquals(response.getMessages().size(), 0);		
		
		RentalList rental = createDemoRental();
		List<RentalList> rentals = (List<RentalList>) response.getResponseObject();
		assertThat(rental).isEqualToComparingFieldByField(rentals.get(0));
	}
	
	@Test
	public void approveListingTest() {
		ResponseState response = adminOperations.approveListing("1","123", "hello", new RentalQueriesMock(), new LandlordNotificationQueriesMock(), 0);
		assertEquals(response.getStatusCode(), Constants.status200);
		assertEquals(response.getMessages().size(), 0);		
	}

	@Test
	public void rejectListingTest() {
		ResponseState response = adminOperations.rejectListing("1","123", new LandlordNotificationQueriesMock(), 0);
		assertEquals(response.getStatusCode(), Constants.status200);
		assertEquals(response.getMessages().size(), 0);		
	}
	
	private RentalList createDemoRental(){
		RentalList rental = new RentalList();
		rental.setTitle("Title");
		rental.setListingType("listing type");
		rental.setRent("450");
		rental.setAvailability("2020/10/10");
		rental.setMaxOccupancy("4");
		rental.setDescription("description");
		rental.setCity("city");
		rental.setAddress("address");
		rental.setCountry("country");
		rental.setLandlordId(123);
		return rental;
		
	}
}
