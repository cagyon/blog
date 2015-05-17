package com.blog.dao;

public interface CommonDBService {
	
	final static String user = "root";
    final static String passwd = "root";
    final static String dbName = "blog";
    // DB connection on localhost via JDBC
    final static String uri = "jdbc:mysql://localhost:8889/" + dbName;
    final static String driver = "com.mysql.jdbc.Driver";

}
