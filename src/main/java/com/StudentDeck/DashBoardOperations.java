package com.StudentDeck;

import java.util.ArrayList;
import java.util.List;

import com.baseDesignPatterns.QueriesTemplate;
import com.baseDesignPatterns.ResponseState;
import com.database.DashBoardQueries;
import com.model.ErrorResponse;
import com.model.FriendRequest;
import com.model.SuccessResponse;

public class DashBoardOperations {

    public ResponseState sendRequest(int senderStudentId, int recieverStudentId, QueriesTemplate dashboardQueries)
    {
        ResponseState response = null;

        List<String> messages = new ArrayList<String>();

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setUserSenderId(senderStudentId);
        friendRequest.setUserReceiverId(recieverStudentId);

        dashboardQueries.writeDb(friendRequest, messages);

        if (!messages.isEmpty()) {
            response = new ErrorResponse();
            response.setMessages(messages);
            response.setResponseObject(friendRequest);
            return response;
        }

        response = new SuccessResponse();
        response.setMessages(List.of("Friend request sent successfully"));
        return response;
    }

    public ResponseState getMyFriendRequests(int studentId, QueriesTemplate dashBoardQueries) {
        List<String> messages = new ArrayList<String>();
        messages.add("0");
        return dashBoardQueries.fetchById(studentId, messages);
    }

    public ResponseState changeRequest(int id, int receiverId, String action, QueriesTemplate dashBoardQueries) {
        ResponseState response = null;
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setUserSenderId(id);
        friendRequest.setUserReceiverId(receiverId);
        friendRequest.setAction(action);

        List<String> messages = new ArrayList<String>();
        dashBoardQueries.update(friendRequest, messages);
        if (!messages.isEmpty()) {
            response = new ErrorResponse();
            response.setMessages(messages);
            return response;
        }

        response = new SuccessResponse();
        if (action.equalsIgnoreCase("accept")){
            response.setMessages(List.of("Friend request accepted"));
        }else   if (action.equalsIgnoreCase("reject")){
            response.setMessages(List.of("Friend request rejected"));
        }

        return response;
    }

	public ResponseState getMyFriends(int studentId, DashBoardQueries dashBoardQueries) {
        List<String> messages = new ArrayList<String>();
        messages.add("1");
        return dashBoardQueries.fetchById(studentId, messages);
	}

}
