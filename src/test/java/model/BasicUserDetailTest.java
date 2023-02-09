package model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.model.user.BasicUserDetails;
import org.junit.jupiter.api.Test;

public class BasicUserDetailTest {
    private BasicUserDetails basicUserDetails = new BasicUserDetails("john",
            "doe",
            "male",
            "1999-01-01");
    @Test
    void getFirstNameTest() {
        String firstName = basicUserDetails.getFirstName();
        assertEquals("john", firstName);
    }

    @Test
    void getLastNameTest() {
        String lastName = basicUserDetails.getLastName();
        assertEquals("doe", lastName);
    }
}
