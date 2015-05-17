package com.blog.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.blog.Article;

public class TestArticle {

	Article a1;
	
	@BeforeClass//
	public static void setUpBeforeClass() throws Exception {
	}

	@Test//
	public void testGetTitle() {
		Date date = new Date(new java.util.Date().getTime());//
		a1 = new Article("My title", "My summary", "My content", 666, date, false, 42, "My author");
		assertEquals(a1.getTitle(), "My title");
	}

	@Test
	public void testGetContent() {
		Date date = new Date(new java.util.Date().getTime());
		a1 = new Article("My title", "My summary", "My content", 666, date, false, 42, "My author");
		assertEquals(a1.getContent(), "My content");
	}

	@Test
	public void testGetSummary() {
		Date date = new Date(new java.util.Date().getTime());
		a1 = new Article("My title", "My summary", "My content", 666, date, false, 42, "My author");
		assertEquals(a1.getSummary(), "My summary");
	}

	@Test
	public void testGetViews() {
		Date date = new Date(new java.util.Date().getTime());
		a1 = new Article("My title", "My summary", "My content", 666, date, false, 42, "My author");
		assertEquals(a1.getViews(), 42);	
	}

	@Test
	public void testSetTitle() {
		Date date = new Date(new java.util.Date().getTime());
		a1 = new Article("My title", "My summary", "My content", 666, date, false, 42, "My author");
		a1.setTitle("New title");
		assertEquals(a1.getTitle(), "New title");
	}

	@Test
	public void testSetContent() {
		Date date = new Date(new java.util.Date().getTime());
		a1 = new Article("My title", "My summary", "My content", 666, date, false, 42, "My author");
		a1.setContent("New content");
		assertEquals(a1.getContent(), "New content");
	}

	@Test
	public void testSetSummary() {
		Date date = new Date(new java.util.Date().getTime());
		a1 = new Article("My title", "My summary", "My content", 666, date, false, 42, "My author");
		a1.setSummary("New summary");
		assertEquals(a1.getSummary(), "New summary");
	}

	@Test
	public void testGetId() {
		Date date = new Date(new java.util.Date().getTime());
		a1 = new Article("My title", "My summary", "My content", 666, date, false, 42, "My author");
		assertTrue(a1.getId() == 666);
	}

	@Test
	public void testGetAuthor() {
		Date date = new Date(new java.util.Date().getTime());
		a1 = new Article("My title", "My summary", "My content", 666, date, false, 42, "My author");
		assertEquals(a1.getAuthor(), "My author");
	}

	@Test
	public void testReadableDelete() {
		Date date = new Date(new java.util.Date().getTime());
		a1 = new Article("My title", "My summary", "My content", 666, date, false, 42, "My author");
		assertTrue(a1.readable());
		a1.delete();
		assertFalse(a1.readable());
	}

	@Test
	public void testGetCreatedAt() {
		Date date = new Date(new java.util.Date().getTime());
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		a1 = new Article("My title", "My summary", "My content", 666, date, false, 42, "My author");
		assertEquals(a1.getCreatedAt(), dateFormat.format(date));
	}

	@Test
	public void testGetEditLink() {
		Date date = new Date(new java.util.Date().getTime());
		a1 = new Article("My title", "My summary", "My content", 666, date, false, 42, "My author");
		assertTrue(a1.getEditLink().equals("<a href='/article/update/" + a1.getId() + "'>Edit</a>"));
	}
	
	@Test
	public void testGetSummaryLink() {
		Date date = new Date(new java.util.Date().getTime());
		a1 = new Article("My title", "My summary", "My content", 666, date, false, 42, "My author");
		assertTrue(a1.getSummaryLink().equals("<a href='/article/read/" + a1.getId() + "'>"+a1.getSummary()+"</a>"));
	}

	@Test
	public void testGetDeleteLink() {
		Date date = new Date(new java.util.Date().getTime());
		a1 = new Article("My title", "My summary", "My content", 666, date, false, 42, "My author");
		assertTrue(a1.getDeleteLink().equals("<a href='/article/delete/" + a1.getId() + "'>Delete</a>"));
	}

}
