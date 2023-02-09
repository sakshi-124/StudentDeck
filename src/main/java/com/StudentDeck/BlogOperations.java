package com.StudentDeck;


import java.util.ArrayList;
import java.util.List;

import com.baseDesignPatterns.BlogQueriesTemplate;
import com.baseDesignPatterns.ResponseState;
import com.database.BlogQueries;
import com.model.Blog;
import com.model.ErrorResponse;
import com.model.SuccessResponse;

public class BlogOperations {
    String isInserted;

    public ResponseState insBlog(String title, String blogDescription, int userId) {
        ResponseState response = null;

        List<String> messages = new ArrayList<String>();


        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setDescription(blogDescription);
        blog.setUserId(userId);

        verifyFields(blog, messages,"BLOG");

        if (!messages.isEmpty()) {
            response = new ErrorResponse();
            response.setMessages(messages);
            response.setResponseObject(blog);
            return response;
        }

        BlogQueries blogQueries = new BlogQueries();
            isInserted = blogQueries.insUserBlog(blog);

        if (isInserted.equals("Y")) {
            response = new SuccessResponse();
            response.setMessages(List.of("Blog added..!!", "Sucess"));
            response.setResponseObject(blog);
            return response;
        } else {
            response = new ErrorResponse();
            response.setMessages(List.of("Problem in Insert..", "Failed"));
            response.setResponseObject(blog);
            return response;
        }
    }

    public static boolean verifyFields(Blog blog, List<String> errorMessages, String insType) {
        if (insType == "BLOG") {
            if (blog.getTitle().trim().isEmpty() == true)
                errorMessages.add("Blog-Title is empty!");
            if (blog.getBlogDescription().trim().isEmpty() == true)
                errorMessages.add("Blog-Description is empty");
        } else if (insType == "COMMENT") {
            if (blog.getBlogComments().trim().isEmpty() == true)
                errorMessages.add("Blog-Comment is empty");
        }
        if (errorMessages.isEmpty() == true)
            return true;
        else
            return false;
    }

    public ResponseState getUserBlogs(BlogQueriesTemplate blogQueries, String userID, String blogId) {

        List<Blog> blogs = new ArrayList<Blog>();
        List<String> messages = blogQueries.fetchBlogs(blogs, userID, blogId);
        ResponseState response;

        if (!messages.isEmpty()) {
            response = new ErrorResponse();
            response.setMessages(messages);
            return response;
        }

        response = new SuccessResponse();
        response.setResponseObject(blogs);
        return response;

    }


    public ResponseState insBlogComment(String blogComments, int blogId, int userId) {
        ResponseState response = null;

        List<String> messages = new ArrayList<String>();

        Blog blog = new Blog();
        blog.setBlogComments(blogComments);
        blog.setBlogId(blogId);
        blog.setUserId(userId);

        verifyFields(blog, messages,"COMMENT");

        if (!messages.isEmpty()) {
            response = new ErrorResponse();
            response.setMessages(messages);
            response.setResponseObject(blog);
            return response;
        }

        BlogQueries blogQueries = new BlogQueries();
        isInserted = blogQueries.insUserComment(blog);

        if (isInserted.equals("Y")) {
            response = new SuccessResponse();
            response.setMessages(List.of("Comment added..!!", "Sucess"));
            response.setResponseObject(blog);
            return response;
        } else {
            response = new ErrorResponse();
            response.setMessages(List.of("Problem in Insert..", "Failed"));
            response.setResponseObject(blog);
            return response;
        }
    }

    public ResponseState getUserComments(String blogId) {

        BlogQueries blogQueries = new BlogQueries();
        List<Blog> blogs = new ArrayList<Blog>();
        List<String> messages = blogQueries.fetchBlogComments(blogs, blogId);
        ResponseState response;

        if (!messages.isEmpty()) {
            response = new ErrorResponse();
            response.setMessages(messages);
            return response;
        }

        response = new SuccessResponse();
        response.setResponseObject(blogs);
        return response;

    }

}
