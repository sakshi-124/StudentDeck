package com.model;

public class Blog {
    private int blogId;
    private int userId;
    private String title;
    private String blogDescription;
    private String blogComments;

    private String userName;

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBlogDescription() {
        return blogDescription;
    }

    public void setDescription(String blogDescription) {
        this.blogDescription = blogDescription;
    }

    public int getLoggedUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBlogComments() {
        return blogComments;
    }

    public void setBlogComments(String blogComments) {
        this.blogComments = blogComments;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

