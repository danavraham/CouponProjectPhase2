package com.dan.Coupons.beans;


public class LoginData {

	// --------------Properties-----------------
	private String userName;
	private String password;

		// -----------------Ctor---------------------
		
	public LoginData(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

		// -----------------G&S---------------------

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}


	// -----------------Methods---------------------
	@Override
	public String toString() {
		return "LoginData [userName=" + userName + ", password=" + password + "]";
	}


}
