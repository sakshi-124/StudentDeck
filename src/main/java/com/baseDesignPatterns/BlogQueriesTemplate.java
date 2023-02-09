package com.baseDesignPatterns;

import java.util.List;

import com.model.Blog;

public abstract class  BlogQueriesTemplate {


    public abstract String insUserBlog(Object obj);

    public abstract List<String> fetchBlogs(List<Blog> blogs, String userId, String blogId);

    public abstract String insUserComment(Object obj);
    
    public abstract List<String> fetchBlogComments(List<Blog> blogs, String blogId);}
