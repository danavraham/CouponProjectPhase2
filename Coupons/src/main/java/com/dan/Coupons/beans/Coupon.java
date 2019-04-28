package com.dan.Coupons.beans;

import com.dan.Coupons.enums.Category;

public class Coupon {

	// --------------Properties-----------------
	private long couponID;
	private long companyID;
	private Category category;
	private String couponTitle;
	private String description;
	private String startDate;
	private String endDate;
	private int amount;
	private double price;
	private String image;

	// -----------------Ctor---------------------
	public Coupon(long couponID, long companyID, Category category, String couponTitle, String description,
			String startDate, String endDate, int amount, double price, String image) {
		this(companyID, category, couponTitle, description, startDate, endDate, amount, price, image);
		this.couponID = couponID;
	}

	public Coupon(long companyID, Category category, String couponTitle, String description, String startDate,
			String endDate, int amount, double price, String image) {
		super();
		this.companyID = companyID;
		this.category = category;
		this.couponTitle = couponTitle;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	public Coupon() {
	}

	// -----------------G&S---------------------
	public long getCouponID() {
		return couponID;
	}

	public void setCouponID(long couponID) {
		this.couponID = couponID;
	}

	public long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(long companyID) {
		this.companyID = companyID;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getCouponTitle() {
		return couponTitle;
	}

	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	// -----------------Methods---------------------
	@Override
	public String toString() {
		return "Coupon details [couponID=" + getCouponID() + ", companyID=" + getCompanyID() + ", category="
				+ getCategory() + ", couponTitle=" + getCouponTitle() + ", description=" + getDescription()
				+ ", startDate=" + getStartDate() + ", endDate=" + getEndDate() + ", amount=" + getAmount() + ", price="
				+ getPrice() + ", image=" + getImage() + "]";
	}

}
