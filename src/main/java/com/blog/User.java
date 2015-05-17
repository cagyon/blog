package com.blog;

public class User {
	
	private String login;
	@SuppressWarnings("unused")
	private String password;
	private boolean isAdmin;
	
	public User(String login, String password, boolean isAdmin) {
		this.login = login;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public String getLogin() {
		return login;
	}
		
	public boolean isAdmin() {
		return isAdmin;
	}
		
}
