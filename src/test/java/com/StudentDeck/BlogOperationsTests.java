package com.StudentDeck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.StudentDeck.Utils.Constants;
import com.baseDesignPatterns.ResponseState;
import com.model.Blog;
import com.repositoryMock.BlogQueriesMock;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@WebMvcTest(LandlordRentalOperations.class)
public class BlogOperationsTests {
    BlogOperations blogOperations = new BlogOperations();

    @Test
    void verifyEmptyTitle()
    {
        List<String> msg = new ArrayList<String>();
        Blog blog= new Blog();
        blog = demoBlog();
        blog.setTitle("");
        blogOperations.verifyFields(blog,msg,"BLOG");
        assertEquals("Blog-Title is empty!", msg.get(0));
    }

    @Test
    void verifyEmptyDescription()
    {
        List<String> msg = new ArrayList<String>();
        Blog blog= new Blog();
        blog = demoBlog();
        blog.setDescription("");
        blogOperations.verifyFields(blog, msg,"BLOG");
        assertEquals("Blog-Description is empty", msg.get(0));
    }

    public Blog demoBlog()
    {
        Blog blog = new Blog();
        blog.setTitle("blog title test");
        blog.setDescription("blog description test");
        blog.setBlogComments("blog comment test");
        blog.setBlogId(1);
        blog.setUserId(1);
        return blog;
    }
    @Test
    public void getUserBlogByUserId()
    {
        ResponseState response = blogOperations.getUserBlogs(new BlogQueriesMock(),"1" ,"");
        assertEquals(response.getStatusCode(), Constants.status200);
    }
    @Test
    public void getUserBlogByBlogId()
    {
        ResponseState response = blogOperations.getUserBlogs(new BlogQueriesMock(),"" ,"1");
        assertEquals(response.getStatusCode(), Constants.status200);
    }

    @Test
    public void getUserComments()
    {
        ResponseState response = blogOperations.getUserComments("1");
        assertEquals(response.getStatusCode(), Constants.status200);
    }
}
