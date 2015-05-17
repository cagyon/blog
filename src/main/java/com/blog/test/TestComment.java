package com.blog.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.blog.Comment;

public class TestComment {

	Comment c1;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testGetId() {
		c1 = new Comment(666, 42, "My Comment", new Date(new java.util.Date().getTime()));
		assertEquals(c1.getId(), 666);
	}

	@Test
	public void testGetArticleId() {
		c1 = new Comment(666, 42, "My Comment", new Date(new java.util.Date().getTime()));
		assertEquals(c1.getArticleId(), 42);	
	}

	@Test
	public void testGetContent() {
		c1 = new Comment(666, 42, "My Comment", new Date(new java.util.Date().getTime()));
		assertEquals(c1.getContent(), "My Comment");		
	}

	@Test
	public void testGetCreatedAt() {
		Date date = new Date(new java.util.Date().getTime());
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		c1 = new Comment(666, 42, "My Comment", date);
		assertEquals(c1.getCreatedAt(), dateFormat.format(date));
	}

}
