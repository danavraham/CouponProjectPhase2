package com.dan.Coupons.beans;

public class Purchase {
	// --------------Properties-----------------
	private long purchaseID;
	private long customerID;
	private long couponID;
	private int amount;

	// -----------------Ctor---------------------

	public Purchase(long purchaseID, long customerID, long couponID, int amount) {
		this(customerID, couponID, amount);
		this.purchaseID = purchaseID;
	}

	public Purchase(long customerID, long couponID, int amount) {
		super();
		this.customerID = customerID;
		this.couponID = couponID;
		this.amount = amount;
	}

	public Purchase() {
	}

	// -----------------G&S---------------------
	public long getPurchaseID() {
		return purchaseID;
	}

	public void setPurchaseID(long purchaseID) {
		this.purchaseID = purchaseID;
	}

	public long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public long getCouponID() {
		return couponID;
	}

	public void setCouponID(long couponID) {
		this.couponID = couponID;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	// -----------------Methods---------------------
	@Override
	public String toString() {
		return "Purchase [purchaseID=" + purchaseID + ", customerID=" + customerID + ", couponID=" + couponID
				+ ", amount=" + amount + "]";
	}

}
