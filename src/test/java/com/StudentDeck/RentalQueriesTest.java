package com.StudentDeck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.database.RentalQueries;
import com.model.RentalList;
import com.repositoryMock.RentalQueriesMock;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@WebMvcTest(RentalQueries.class)
public class RentalQueriesTest {
	
	RentalQueriesMock rentalQueriesMock = new RentalQueriesMock();

	@Test
	public void writeDbTest() {
		List<String> msg = new ArrayList<String>();
		
		rentalQueriesMock.writeDb(creatDemoRental(), msg);
        assertEquals(msg, new ArrayList<String>());
	}
	
	@Test
	public void updateTest() {
		List<String> msg = new ArrayList<String>();
		
		rentalQueriesMock.update(creatDemoRental(), msg);
        assertEquals(msg, new ArrayList<String>());		
	}
	
	private RentalList creatDemoRental(){
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
		return rental;
		
	}


}
