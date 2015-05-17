package com.blog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
 
public class Article {
	
    private String title;
    private String content;
    private String summary;
    private String author;
    private Date createdAt;
    private int id;
    private boolean deleted;
    private int views;
 
    public Article(String title, String summary, String content, String author) { 
    
    	this(title, summary, content, 0, new Date(), false , 0, author);
    }
    
    public Article(String title, String summary, String content, int id, Date createdAt, boolean deleted, int views, String author) {
    	
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.id = id;
        this.createdAt = createdAt;
        this.deleted = deleted;
        this.views = views;
        this.author = author;
    }
 
    public String getTitle() {
        return title;
    }
 
    public String getContent() {
        return content;
    }
 
    public String getSummary() {
        return summary;
    }
 
    public int getViews() {
    	return views;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
 
    public void setContent(String content) {
        this.content = content;
    }
 
    public void setSummary(String summary) {
        this.summary = summary;
    }
 
    public Integer getId() {
        return id;
    }
 
    public String getAuthor() {
        return author;
    }
    
    public void delete() {
        this.deleted = true;
    }
 
    public Boolean readable() {
        return (!this.deleted);
    }
 
    public String getCreatedAt() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(this.createdAt);
    }
 
    public String getEditLink() {
        return "<a href='/article/update/" + this.id + "'>Edit</a>";
    }
 
    public String getDeleteLink() {
        return "<a href='/article/delete/" + this.id + "'>Delete</a>";
    }
 
    public String getSummaryLink() {
        return "<a href='/article/read/" + this.id + "'>" + this.summary + "</a>";
    }
}
