package com.blog.dao;

public interface UserDBService<T> {

		public T authenticate(String login, String password); // Authenticate user
		
}
