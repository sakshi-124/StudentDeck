package com.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.baseDesignPatterns.BlogQueriesTemplate;
import com.model.Blog;

public class BlogQueries extends BlogQueriesTemplate {

    static Connection conn;
    String query, output;

    public BlogQueries() {
        conn = Database.getConnection();
    }

    public String insUserBlog(Object obj) {
        try {
            Blog blog = (Blog) obj;

            query = "{Call prc_insert_blog(?,?,?,?)}";

            CallableStatement stmt;
            stmt = conn.prepareCall(query);
            stmt.setString(1, blog.getTitle());
            stmt.setString(2, blog.getBlogDescription());
            stmt.setInt(3, blog.getLoggedUserId());

            stmt.registerOutParameter(4, Types.VARCHAR);

            stmt.execute();
            output = stmt.getString(4);

            return output;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "N";
            //closeConnections(con);
        }
    }

    public List<String> fetchBlogs(List<Blog> blogs, String userId, String blogId) {
        List<String> messages = new ArrayList<String>();
        try {

            query = "{Call prc_get_blogs(?,?)}";

            CallableStatement stmt;
            stmt = conn.prepareCall(query);
            stmt.setString(1, blogId.isEmpty() == true ? null : blogId);
            stmt.setString(2, userId.isEmpty() == true ? null : userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setTitle(rs.getString("title"));
                blog.setDescription(rs.getString("blog_description"));
                blog.setBlogId(rs.getInt("idBlog"));
                blog.setUserId(rs.getInt("user_id"));
                blog.setUserName(rs.getString("userName"));
                blogs.add(blog);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            messages.add("Error!");
        }
        return messages;
    }

    public String insUserComment(Object obj) {
        try {
            Blog blog = (Blog) obj;

            query = "{Call prc_ins_comments(?,?,?,?)}";

            CallableStatement stmt;
            stmt = conn.prepareCall(query);
            stmt.setInt(1, blog.getBlogId());
            stmt.setInt(2, blog.getLoggedUserId());
            stmt.setString(3, blog.getBlogComments());

            stmt.registerOutParameter(4, Types.VARCHAR);

            stmt.execute();
            output = stmt.getString(4);

            return output;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "N";
            //closeConnections(con);
        }
    }

    public List<String> fetchBlogComments(List<Blog> blogs, String blogId) {
        List<String> messages = new ArrayList<String>();
        try {

            query = "{Call prc_get_blog_with_cmnt(?)}";

            CallableStatement stmt;
            stmt = conn.prepareCall(query);
            stmt.setString(1, blogId.isEmpty() == true ? null : blogId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setBlogId(rs.getInt("idBlog"));
                blog.setTitle("title");
                blog.setDescription("blog_description");
                blog.setUserName(rs.getString("first_name"));
                blog.setBlogComments(rs.getString("user_comment"));
                blogs.add(blog);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            messages.add("Error!");
        }
        return messages;
    }
}
