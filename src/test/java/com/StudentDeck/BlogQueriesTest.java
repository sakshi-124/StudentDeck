package com.StudentDeck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.model.Blog;
import com.repositoryMock.BlogQueriesMock;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@WebMvcTest(LandlordRentalOperations.class)
public class BlogQueriesTest {

    BlogQueriesMock blogQueriesMock = new BlogQueriesMock();

    @Test
    public void fetchBlogsByBlogId()
    {
        List<String> msg = new ArrayList<String>();
        List<Blog> blogs = new ArrayList<Blog>();
        blogs.add(demoBlog());
        blogQueriesMock.fetchBlogs(blogs,"","1");
        assertEquals(msg, new ArrayList<String>());
    }
    @Test
    public void fetchBlogsByUserId()
    {
        List<String> msg = new ArrayList<String>();
        List<Blog> blogs = new ArrayList<Blog>();
        blogs.add(demoBlog());
        blogQueriesMock.fetchBlogs(blogs,"1","");
        assertEquals(msg, new ArrayList<String>());
    }

    @Test
    public void insUserBlog()
    {
        List<String> msg =  new ArrayList<String>();
        blogQueriesMock.insUserBlog((Object) demoBlog());
        assertEquals(msg,new ArrayList<String>());
    }

    @Test
    public void fetchAllBlogs()
    {
        List<String> msg = new ArrayList<String>();
        List<Blog> blogs = new ArrayList<Blog>();
        blogs.add(demoBlog());
        blogQueriesMock.fetchBlogs(blogs,"","");
        assertEquals(msg, new ArrayList<String>());
    }
    @Test
    public void insUserComment()
    {
        List<String> msg =  new ArrayList<String>();
        blogQueriesMock.insUserComment((Object) demoBlog());
        assertEquals(msg,new ArrayList<String>());
    }
    @Test
    public void fetchBlogComments()
    {
        List<String> msg = new ArrayList<String>();
        List<Blog> blogs = new ArrayList<Blog>();
        blogQueriesMock.fetchBlogComments(blogs,"1");
        assertEquals(msg, new ArrayList<String>());
    }
    private Blog demoBlog()
    {
        Blog blog = new Blog();
        blog.setTitle("Blog Title Test");
        blog.setDescription("Blog Description Test");
        blog.setBlogComments("Blog Comment Test");
        blog.setBlogId(1);
        blog.setUserId(1);
        return blog;
    }

}
