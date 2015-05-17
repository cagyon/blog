package com.blog.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.blog.User;

public class TestUser {
	
	User u;

	@Test
	public void testGetLogin() {
		u = new User("julien", "password", true);
		assertEquals(u.getLogin(), "julien");
		u = new User("julien", "password", false);
		assertEquals(u.getLogin(), "julien");
	}

	@Test
	public void testIsAdmin() {
		u = new User("julien", "password", true);
		assertTrue(u.isAdmin());
		u = new User("julien", "password", false);
		assertFalse(u.isAdmin());
	}

}
