package com.blog.dao;

import java.util.ArrayList;

public interface CommentDBService<T> {
	public boolean create(T comment); // Add a comment to an article
    	public ArrayList<T> readAll(int articleId); // Read all comments for a given article
}
