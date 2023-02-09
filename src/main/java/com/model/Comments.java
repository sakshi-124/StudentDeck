package com.model;

public class Comments {
    public int blogId;
    public int userId;
    public String blogComments;

    public String userName;


    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }
    public String getBlogComment() {
        return blogComments;
    }

    public void setBlogComment(String blogComments) {
        this.blogComments = blogComments;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLoggedUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
