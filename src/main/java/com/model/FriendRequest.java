package com.model;

public class FriendRequest
{
    private int id;
    private int userSenderId;
    private int userReceiverId;
    private int requestStatus;

    private String action;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserSenderId() {
        return userSenderId;
    }

    public void setUserSenderId(int userSenderId) {
        this.userSenderId = userSenderId;
    }

    public int getUserReceiverId() {
        return userReceiverId;
    }

    public void setUserReceiverId(int userReceiverId) {
        this.userReceiverId = userReceiverId;
    }

    public int getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
