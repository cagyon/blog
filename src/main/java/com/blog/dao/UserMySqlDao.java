package com.blog.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.blog.User;

public class UserMySqlDao<T extends User> implements UserDBService<T>, CommonDBService {

	// PostgreSQL connection to the database
    private Connection conn;
    // A raw SQL query used without parameters
    private Statement stmt;
 
    public UserMySqlDao() {
        
        String createUserTableQuery = 
        		"CREATE TABLE IF NOT EXISTS user(" +
        				"login VARCHAR(16) NOT NULL PRIMARY KEY," + 
        				"password VARCHAR(16) NOT NULL," +
        				"isAdmin BOOLEAN" +
        				");";
        			

        // Create the article table and close resources if an exception is thrown
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(uri, user, passwd);
            stmt = conn.createStatement();
            stmt.execute(createUserTableQuery);

            System.out.println("UserMySqlDao() connecting to mySQL database");
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
            } 
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            finally {
            	System.out.println("UserMySqlDao: database error, exiting");
            	System.exit(-1);
            }
        }
        
    }
    
	@SuppressWarnings("unchecked")
	public T authenticate(String login, String password) {
		
		try {
            String selectUserQuery = "SELECT * FROM user WHERE login = ? AND password = ? LIMIT 1;";
 
            PreparedStatement pstmt = conn.prepareStatement(selectUserQuery);
            pstmt.setString(1, login);
            pstmt.setString(2, password);

            // A ResultSet is Class which represents a table returned by a SQL query
            ResultSet resultSet = pstmt.executeQuery();
 
            if (resultSet.next()) {
            	User u = new User(resultSet.getString("login"), null, resultSet.getBoolean("isAdmin"));
            	return (T)u;
            }
            
            return null;
            
        } catch(Exception e) {
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
        }
 
        return null;
    }
}
