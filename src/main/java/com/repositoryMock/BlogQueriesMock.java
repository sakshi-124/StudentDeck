package com.repositoryMock;

import java.util.ArrayList;
import java.util.List;
import com.baseDesignPatterns.BlogQueriesTemplate;
import com.model.Blog;

public class BlogQueriesMock extends BlogQueriesTemplate {

    @Override
    public String insUserBlog(Object obj) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> fetchBlogs(List<Blog> blogs, String userId, String blogId) {
        if (userId == "1" || blogId == "1") {
            Blog blog = new Blog();
            blog.setTitle("blog title test");
            blog.setDescription("blog description test");
            blog.setBlogComments("blog comment test");
            blogs.add(blog);
        }
        else
        {
            for (int i=0;i<3;i++)
            {
                Blog blog = new Blog();
                blog.setTitle("blog title test " + i);
                blog.setDescription("blog description test" + i);
                blog.setBlogComments("blog comment test" + i);
                blogs.add(blog);
            }
        }
        return new ArrayList<String>();
    }

    @Override
    public String insUserComment(Object obj) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> fetchBlogComments(List<Blog> blogs, String blogId) {
        if (blogId == "1") {
            Blog blog = new Blog();
            blog.setTitle("blog title test");
            blog.setDescription("blog description test");
            blog.setBlogComments("blog comment test");
            blogs.add(blog);
        }
        return new ArrayList<String>();
    }

}
