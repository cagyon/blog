package com.blog.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.blog.Comment;

public class CommentMySqlDao<T extends Comment> implements CommentDBService<T>, CommonDBService {

	// PostgreSQL connection to the database
    private Connection conn;
    // A raw SQL query used without parameters
    private Statement stmt;
 
    public CommentMySqlDao() {
        
        String createCommentTableQuery = 
        		"CREATE TABLE IF NOT EXISTS comment(" +
        				"id INT PRIMARY KEY NOT NULL AUTO_INCREMENT," + 
        				"articleId INT NOT NULL," + 
        				"FOREIGN KEY fk_article(articleId) REFERENCES article(id) ON UPDATE CASCADE ON DELETE CASCADE," +
        				"content VARCHAR(255) NOT NULL," +
        				"createdAt DATE NOT NULL" +
        				");";
        			

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(uri, user, passwd);
            stmt = conn.createStatement();
            stmt.execute(createCommentTableQuery);

            System.out.println("CommentMySqlDao(): connecting to mySQL database");
        } 
        catch(Exception e) {
            System.out.println(e.getMessage());
 
            try {
                if(null != stmt) {
                    stmt.close();
                }
                if(null != conn) {
                    conn.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            finally {
            	System.out.println("CommentMySqlDao: database error, exiting");
            	System.exit(-1);
            }
        }
        
    }
    
	public boolean create(T comment) {
		try {
        	// PreparedStatement to insert an article, values will be extracted from the article parameter
            String insertQuery = "INSERT INTO comment(articleId, content, createdAt) VALUES(?, ?, ?);";
 
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
 
            // JDBC binds every prepared statement argument to a Java Class such as Integer and or String
            pstmt.setInt(1, comment.getArticleId());
            pstmt.setString(2, comment.getContent());
 
            java.sql.Date sqlNow = new Date(new java.util.Date().getTime());
            pstmt.setDate(3, sqlNow);
             
            pstmt.executeUpdate();
 
            pstmt.close();
 
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
 
            try {
                if(null != stmt) {
                    stmt.close();
                }
                if(null != conn) {
                    conn.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
 
            return false;
        }
	}

	@SuppressWarnings("unchecked")
	public ArrayList<T> readAll(int articleId) {

		ArrayList<Comment> comments = ((ArrayList<Comment>) new ArrayList<T>());
 
        try {
            String selectQuery = "SELECT * FROM comment WHERE articleId = ?;";

        	PreparedStatement pstmt = conn.prepareStatement(selectQuery);
            pstmt.setInt(1, articleId);
            pstmt.execute();

            ResultSet resultSet = pstmt.getResultSet();
 
            while(resultSet.next()) {
                Comment c = new Comment(
                        resultSet.getInt("id"),
                        resultSet.getInt("articleId"),
                        resultSet.getString("content"),
                        resultSet.getDate("createdAt")
                );
 
                comments.add(c);
            }
            pstmt.close();
            
        } catch(Exception e) {
            System.out.println("CommentMySqlDao: readAll() : "+e.getMessage());
 
            try {
                if(null != stmt) {
                    stmt.close();
                }
                if(null != conn) {
                    conn.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        
        return ((ArrayList<T>)comments);
	}

}
