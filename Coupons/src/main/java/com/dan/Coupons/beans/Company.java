package com.dan.Coupons.beans;

public class Company {

	// --------------Properties-----------------
	private long companyID;
	private String companyName;
	private String email;
	private String website;

	// -----------------Ctor---------------------
	public Company(long companyID, String companyName, String email, String website) {
		this(companyName, email, website);
		this.companyID = companyID;
	}

	public Company(String companyName, String email, String website) {
		super();
		this.companyName = companyName;
		this.email = email;
		this.website = website;
	}

	public Company() {
	}

	// -----------------G&S---------------------
	public long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(long companyID) {
		this.companyID = companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	// -----------------Methods---------------------
	@Override
	public String toString() {
		return "Company details [companyID=" + getCompanyID() + ", companyName=" + getCompanyName() + ", email="
				+ getEmail() + ", website=" + getWebsite() + "]";
	}

}
