package com.dan.Coupons.beans;

import com.dan.Coupons.enums.ClientType;

public class User {
	// --------------Properties-----------------
	private long userID;
	private String userName;
	private String password;
	private Long companyId;
	private ClientType type;

	// -----------------Ctor---------------------
	public User(long userID, String userName, String password, Long companyId, ClientType type) {
		this(userName, password, companyId, type);
		this.userID = userID;
	}

	public User(String userName, String password, Long companyId, ClientType type) {
		this(userName, password, type);
		this.companyId = companyId;
	}

	public User(String userName, String password, ClientType type) {
		super();
		this.userName = userName;
		this.password = password;
		this.type = type;
	}

	public User(long userID, String userName, String password) {
		this.userID = userID;
		this.userName = userName;
		this.password = password;
	}

	public User() {
	}

	// -----------------G&S---------------------
	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public ClientType getType() {
		return type;
	}

	public void setType(ClientType type) {
		this.type = type;
	}

	// -----------------Methods---------------------
	@Override
	public String toString() {

		return "User details [userID= " + getUserID() + ", Name= " + getUserName() + ", Password=  " + getPassword()
				+ ", Company Id= " + getCompanyId() + ", Client type= " + getType() + "]";
	}
}
