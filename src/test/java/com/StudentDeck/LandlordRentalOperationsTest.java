package com.StudentDeck;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.model.RentalList;
import com.repositoryMock.RentalQueriesMock;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@WebMvcTest(LandlordRentalOperations.class)

public class LandlordRentalOperationsTest {
	
	LandlordRentalOperations landlordRentalOperations = new LandlordRentalOperations();
	
	@Test
    void verifyFieldsEmptyTitle() throws Exception {
		List<String> msg = new ArrayList<String>();
		RentalList rental = creatDemoRental();
		rental.setTitle("");
		landlordRentalOperations.verifyFields(rental, msg);
        assertEquals("Title cannot be empty", msg.get(0));
    }
	
	@Test
    void verifyFieldsEmptyListingType() throws Exception {
		List<String> msg = new ArrayList<String>();
		RentalList rental = creatDemoRental();
		rental.setListingType("");
		landlordRentalOperations.verifyFields(rental, msg);
        assertEquals("Listing type cannot be empty", msg.get(0));
    }
	
	@Test
    void verifyFieldsEmptyDescription() throws Exception {
		List<String> msg = new ArrayList<String>();
		RentalList rental = creatDemoRental();
		rental.setDescription("");
		landlordRentalOperations.verifyFields(rental, msg);
        assertEquals("Description cannot be empty", msg.get(0));
    }	
	
	@Test
    void verifyFieldsEmptyCity() throws Exception {
		List<String> msg = new ArrayList<String>();
		RentalList rental = creatDemoRental();
		rental.setCity("");
		landlordRentalOperations.verifyFields(rental, msg);
        assertEquals("City cannot be empty", msg.get(0));
    }
	
	@Test
    void verifyFieldsNumericCity() throws Exception {
		List<String> msg = new ArrayList<String>();
		RentalList rental = creatDemoRental();
		rental.setCity("122");
		landlordRentalOperations.verifyFields(rental, msg);
        assertEquals("City cannot contain numeric value", msg.get(0));
    }
	
	@Test
    void verifyFieldsSpecialCharactersCity() throws Exception {
		List<String> msg = new ArrayList<String>();
		RentalList rental = creatDemoRental();
		rental.setCity("arizona@");
		landlordRentalOperations.verifyFields(rental, msg);
        assertEquals("City cannot contain numeric value", msg.get(0));
    }

	@Test
    void verifyFieldsEmptyCountry() throws Exception {
		List<String> msg = new ArrayList<String>();
		RentalList rental = creatDemoRental();
		rental.setCountry("");
		landlordRentalOperations.verifyFields(rental, msg);
        assertEquals("Country cannot be empty", msg.get(0));
    }
	
	@Test
	void verifyFieldsNumericCountry() throws Exception {
		List<String> msg = new ArrayList<String>();
		RentalList rental = creatDemoRental();
		rental.setCountry("122");
		landlordRentalOperations.verifyFields(rental, msg);
		assertEquals("Country cannot contain numeric value", msg.get(0));
	}

	@Test
	void verifyFieldsSpecialCharactersCountry() throws Exception {
		List<String> msg = new ArrayList<String>();
		RentalList rental = creatDemoRental();
		rental.setCountry("arizona@");
		landlordRentalOperations.verifyFields(rental, msg);
		assertEquals("Country cannot contain numeric value", msg.get(0));
	}
	
	@Test
    void verifyFieldsEmptyAddress() throws Exception {
		List<String> msg = new ArrayList<String>();
		RentalList rental = creatDemoRental();
		rental.setAddress("");
		landlordRentalOperations.verifyFields(rental, msg);
        assertEquals("Address cannot be empty", msg.get(0));
    }

	@Test
    void verifyFieldsEmptyAvailability() throws Exception {
		List<String> msg = new ArrayList<String>();
		RentalList rental = creatDemoRental();
		rental.setAvailability("");
		landlordRentalOperations.verifyFields(rental, msg);
        assertEquals("Availability cannot be empty", msg.get(0));
    }
	
	@Test
    void verifyFieldsEmptyRent() throws Exception {
		List<String> msg = new ArrayList<String>();
		RentalList rental = creatDemoRental();
		rental.setRent("");
		landlordRentalOperations.verifyFields(rental, msg);
        assertEquals("Rent cannot be empty", msg.get(0));
    }
	
	@Test
    void verifyFieldsNotNumberRent() throws Exception {
		List<String> msg = new ArrayList<String>();
		RentalList rental = creatDemoRental();
		rental.setRent("neha");
		landlordRentalOperations.verifyFields(rental, msg);
        assertEquals("Rent should be numberic", msg.get(0));
    }
	
	@Test
    void verifyFieldsEmptyMaxOccupancy() throws Exception {
		List<String> msg = new ArrayList<String>();
		RentalList rental = creatDemoRental();
		rental.setMaxOccupancy("");
		landlordRentalOperations.verifyFields(rental, msg);
        assertEquals("Max Occupancy cannot be empty", msg.get(0));
    }
	
	@Test
    void verifyFieldsNotNumberMaxOccupancy() throws Exception {
		List<String> msg = new ArrayList<String>();
		RentalList rental = creatDemoRental();
		rental.setMaxOccupancy("fsf");
		landlordRentalOperations.verifyFields(rental, msg);
        assertEquals("Max Occupancy should numeric", msg.get(0));
    }

	@Test
	void addRentalIncorrectInput() {
		ResponseState response  = landlordRentalOperations.addRental("", "listing type", "7j", "2020/10/10", "4", "description"
				, "city", "address", "country", new RentalQueriesMock(), 123);
        assertEquals(List.of("Title cannot be empty", "Rent should be numberic"),
        		response.getMessages());

	}
	
	@Test
	void addRentalCorrectInput() {
		ResponseState response  = landlordRentalOperations.addRental("Title", "listing type", "400", "2020/10/10", "4", "description"
				, "city", "address", "country", new RentalQueriesMock(), 123);
        assertEquals(List.of("Rental Posting addded successfully", "Posting awaiting admin approval"),
        		response.getMessages());

	}
	
	@Test
	void getAllListingsByLandLordIdCorrectTest() {
		RentalList rental = creatDemoRental();
		ResponseState response  = landlordRentalOperations.getAllListingsByLandLordId(123, new RentalQueriesMock());
		List<RentalList> rentals = (List<RentalList>) response.getResponseObject();
		assertEquals(response.getStatusCode(), Constants.status200);
		assertThat(rental).isEqualToComparingFieldByField(rentals.get(0));
	}
	
	@Test
	void getAllListingsByLandLordIdIncorrectTest() {
		RentalList rental = creatDemoRental();
		ResponseState response  = landlordRentalOperations.getAllListingsByLandLordId(10, new RentalQueriesMock());
		List<RentalList> rentals = (List<RentalList>) response.getResponseObject();
		assertEquals(response.getStatusCode(), Constants.status200);
		assertEquals(rentals.size(), 0);
	}
	
	@Test
	void deleteRentalTest() {
		ResponseState response  = landlordRentalOperations.deleteRental("10", new RentalQueriesMock());
		assertEquals(response.getStatusCode(), Constants.status200);
	}
	
	@Test
	void fetchRentalByIdTest() {
		ResponseState response = landlordRentalOperations.fetchRentalById("1", new RentalQueriesMock());
		assertEquals(response.getStatusCode(), Constants.status200);
	}
	
	@Test
	void updateRentalDetailsTest() {
		ResponseState response = landlordRentalOperations.updateRentalDetails("1", "Title", "listing type", "400", "2020/10/10", "4", "description"
				, "city", "address", "country", new RentalQueriesMock(), (long) 123);
		assertEquals(response.getStatusCode(), Constants.status200);
	}
	
	@Test
	void getAllListingsByCityEmptyCityTest() {
		ResponseState response = landlordRentalOperations.getAllListingsByCity("", new RentalQueriesMock());
		assertEquals(response.getMessages(), Arrays.asList("Please enter valid data in city"));
	}
	
	@Test
	void getAllListingsByCityInvalidCityTest() {
		ResponseState response = landlordRentalOperations.getAllListingsByCity("!ds", new RentalQueriesMock());
		assertEquals(response.getMessages(), Arrays.asList("Please enter valid data in city"));
	}
	
	@Test
	void getAllListingsByCityNumericCityTest() {
		ResponseState response = landlordRentalOperations.getAllListingsByCity("78", new RentalQueriesMock());
		assertEquals(response.getMessages(), Arrays.asList("Please enter valid data in city"));
	}
	
	@Test
	void getAllListingsByCityCorrectTest() {
		ResponseState response = landlordRentalOperations.getAllListingsByCity("halifax", new RentalQueriesMock());
		assertEquals(response.getStatusCode(), Constants.status200);
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
		rental.setLandlordId(123);
		return rental;
		
	}
}
