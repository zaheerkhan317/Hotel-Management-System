package com.UserLogin;

public class UserLogin {
	
	private String Email;
	private String password;
	
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserLogin(String email, String password) {
		super();
		Email = email;
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserLogin [Email=" + Email + ", password=" + password + "]";
	}
	
	
	
}
