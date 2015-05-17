package com.blog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {

		private int id;
		private int articleId;
		private String content;
		private Date createdAt;

		public Comment(int articleId, String content) {
			this.articleId	= articleId;
			this.content = content;
		}
		
	    public Comment(int id, int articleId, String content, Date createdAt) {
	    	this(articleId, content);
			this.id 		= id;
			this.createdAt 	= createdAt;
		}
	    
		public int getId() {
			return id;
		}
		
		public int getArticleId() {
			return articleId;
		}
		
		public String getContent() {
			return content;
		}
		
		public String getCreatedAt() {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        return dateFormat.format(this.createdAt);		
	   }
}
