package com.repositoryMock;

import java.util.List;
import com.baseDesignPatterns.QueriesTemplate;
import com.baseDesignPatterns.ResponseState;
import com.model.SuccessResponse;

public class DashBoardQueriesMock extends QueriesTemplate
{
    public void writeDb(Object obj, List<String> messages) {
    }

    public ResponseState fetchById(Object obj, List<String> messages) {
        return new SuccessResponse();
    }

    public void update(Object obj, List<String> messages) {
    }

    public List<String> fetchMyFriends(List<Object> users, int id) {
        return null;
    }
}
