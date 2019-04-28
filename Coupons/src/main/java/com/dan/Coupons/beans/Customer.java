package com.dan.Coupons.beans;

public class Customer {

	// --------------Properties-----------------
	private long customerID;
	private User user;
	private String customerFirstName;
	private String customerLastName;

	// -----------------Ctor---------------------

	public Customer(long customerID, User user, String customerFirstName, String customerLastName) {
		this(user, customerFirstName, customerLastName);
		this.customerID = customerID;
	}

	public Customer(User user, String customerFirstName, String customerLastName) {
		super();
		this.user = user;
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
	}

	public Customer() {
	}

	// -----------------G&S---------------------

	public long getcustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	// -----------------Methods---------------------

	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", user=" + user + ", customerFirstName=" + customerFirstName
				+ ", customerLastName=" + customerLastName + "]";
	}

}