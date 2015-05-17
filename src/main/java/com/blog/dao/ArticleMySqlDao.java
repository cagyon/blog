package com.blog.dao;

import java.sql.*;
import java.util.ArrayList;

import com.blog.Article;
 
public class ArticleMySqlDao<T extends Article> implements ArticleDBService<T>, CommonDBService {
    // PostgreSQL connection to the database
    private Connection conn;
    // A raw SQL query used without parameters
    private Statement stmt;
 
    public ArticleMySqlDao() {
        
        String createArticleTableQuery =
                "CREATE TABLE IF NOT EXISTS article(" +
                        "id         INT           PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                        "title      VARCHAR(64)   NOT NULL," +
                        "content    VARCHAR(1024) NOT NULL," +
                        "summary    VARCHAR(64)   NOT NULL," +
                        "deleted    BOOLEAN       DEFAULT FALSE," +
                        "createdAt  DATE          NOT NULL," +
                        "views      INT           DEFAULT 0," +
                        "author     VARCHAR(16)   NOT NULL," +
                        "FOREIGN KEY fk_author(author) REFERENCES user(login) ON UPDATE CASCADE ON DELETE CASCADE" +
                        ");";

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(uri, user, passwd);
            stmt = conn.createStatement();
            stmt.execute(createArticleTableQuery);
            System.out.println("ArticleMySqlDao: connecting to mySQL database");
        } 
        catch(Exception e) {
            System.out.println("ArticleMySqlDao: "+e.getMessage());
 
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
            	System.out.println("ArticleMySqlDao: database error, exiting");
            	System.exit(-1);
            }
        }
    }
 
    
    public boolean create(T article) {
        try {
        	// PreparedStatement to insert an article, values will be extracted from the article parameter
            String insertQuery = "INSERT INTO article(title, content, summary, createdAt, author) VALUES(?, ?, ?, ?, ?);";
 
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);// preparedStatement = c'est une requete sql 
            //ds laquelle il manque les valeurs concretes.
 
            // JDBC binds every prepared statement argument to a Java Class such as Integer and or String
            pstmt.setString(1, article.getTitle());
            pstmt.setString(2, article.getContent());
            pstmt.setString(3, article.getSummary());
 
            java.sql.Date sqlNow = new Date(new java.util.Date().getTime());
            pstmt.setDate(4, sqlNow);
             
            pstmt.setString(5, article.getAuthor());

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
    public T readOne(int id) {
        try {
            String selectQuery = "SELECT * FROM article where id = ?";
 
            PreparedStatement pstmt = conn.prepareStatement(selectQuery);
            pstmt.setInt(1, id);
 
            // A ResultSet is Class which represents a table returned by a SQL query
            ResultSet resultSet = pstmt.executeQuery();
 
            if(resultSet.next()) {
            	
            	int views = resultSet.getInt("views")+1;
                Article a = new Article(
                        // You must know both the column name and the type to extract the row
                        resultSet.getString("title"),
                        resultSet.getString("summary"),
                        resultSet.getString("content"),
                        resultSet.getInt("id"),
                        resultSet.getDate("createdat"),
                        resultSet.getBoolean("deleted"),
                        views,
                        resultSet.getString("author")
                );
 
                // Update article views in database
                String updateViewsQuery = "UPDATE article SET views = ? WHERE id = ?";
                pstmt = conn.prepareStatement(updateViewsQuery);
                pstmt.setInt(1, views);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                
                pstmt.close();
 
                return (T) a;
            }
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
 
    @SuppressWarnings("unchecked") //Tells the compiler to ignore unchecked type casts
    public ArrayList<T> readAll() {
        // Type cast the generic T into an Article
        ArrayList<Article> results = (ArrayList<Article>) new ArrayList<T>();
 
        try {
            String query = "SELECT * FROM article;";
 
            stmt.execute(query); 
            ResultSet resultSet = stmt.getResultSet();
 
            while(resultSet.next()) {
                Article entity = new Article(
                        resultSet.getString("title"),
                        resultSet.getString("summary"),
                        resultSet.getString("content"),
                        resultSet.getInt("id"),
                        resultSet.getDate("createdat"),
                        resultSet.getBoolean("deleted"),
                        resultSet.getInt("views"),
                        resultSet.getString("author")
                );
 
                results.add(entity);
            }
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
 
        // The interface ArticleDbService relies upon the generic type T so we cast it back
        return (ArrayList<T>) results;
    }
 
    
    public boolean update(int id, String title, String summary, String content) {
        try {
            String updateQuery =
                "UPDATE article SET title = ?, summary = ?, content = ? WHERE id = ?;";
 
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            
            pstmt.setString(1, title);
            pstmt.setString(2, summary);
            pstmt.setString(3, content);
            pstmt.setInt(4, id);
 
            pstmt.executeUpdate();
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
 
        return true;
    }
 
    public boolean delete(int id) {
        try {
            String deleteQuery = "DELETE FROM article WHERE id = ?";
 
            PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setInt(1, id);
 
            pstmt.executeUpdate();
        } catch (Exception e) {
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
 
        return true;
    }
    
    public int count() {

    	try {
            String countQuery = "SELECT COUNT(*) FROM article;";
            int articleCount;

            PreparedStatement pstmt = conn.prepareStatement(countQuery);
 
            // A ResultSet is Class which represents a table returned by a SQL query
            ResultSet resultSet = pstmt.executeQuery();
 
            if(resultSet.next()) {
            	articleCount = resultSet.getInt("count(*)"); 
                pstmt.close();
                return articleCount;
            }
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
        return 0;

    }
    
}