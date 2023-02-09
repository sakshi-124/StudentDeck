package com.StudentDeck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.ResponseState;
import com.database.DashBoardQueries;
import com.repositoryMock.DashBoardQueriesMock;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@WebMvcTest(LandlordRentalOperations.class)
public class DashBoardOperationTest {

    DashBoardOperations dashBoardOperations = new DashBoardOperations();

    @Test
    public void sendRequestTest()
    {
        ResponseState response = dashBoardOperations.sendRequest(1,2,new DashBoardQueriesMock());
        assertEquals(response.getStatusCode(), Constants.status200);
    }

    @Test
    public void getMyFriendRequestsTest()
    {
        ResponseState response = dashBoardOperations.getMyFriendRequests(1,new DashBoardQueriesMock());
        assertEquals(response.getStatusCode(), Constants.status200);
        assertEquals(response.getMessages().size(), 0);
    }

    @Test
    public void changeRequestAcceptTest()
    {
        ResponseState response = dashBoardOperations.changeRequest(1,2,"accept",new DashBoardQueriesMock());
        assertEquals(response.getStatusCode(), Constants.status200);
        assertEquals(response.getMessages(), List.of("Friend request accepted"));
    }

    @Test
    public void changeRequestRejecttTest()
    {
        ResponseState response = dashBoardOperations.changeRequest(1,2,"reject",new DashBoardQueriesMock());
        assertEquals(response.getStatusCode(), Constants.status200);
        assertEquals(response.getMessages(), List.of("Friend request rejected"));
    }

    @Test
    public void getMyFriendsTest()
    {
        ResponseState response = dashBoardOperations.getMyFriends(1,new DashBoardQueries());
        assertEquals(response.getStatusCode(), Constants.status200);
        assertEquals(response.getMessages().size(), 0);
    }
}