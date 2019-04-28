package com.dan.Coupons.enums;

public enum ErrorTypes {
	GENERAL_ERROR("General error"), 
	FAIL_TO_GENERATE_ID("Couldn't generate an ID"),
	LOGIN_FAILED("Login failed. Please try again."),
	NAME_ALREADY_EXISTS("The name you chose already exist. Enter another name"), 
	ID_DONT_EXIST("This ID don't exist"),
	NON_REPLACEABLE_NAME("Cannot change the name"),
	INNCORECT_PASSWORD("Password must contain 6-10 charaters, at least one letter, at least one digit"),
	NOT_ENOUGH_COUPONS_LEFT("Not enough coupons left to purchase the amount requested"),
	COUPON_EXPIERED("The coupon is expiered"),
	COUPON_TITLE_EXIST_FOR_COMPANY("The company already have a coupon with the same title, please change the title"),
	CANNOT_UPDATE_COMPANY_ID("Coupons company ID cannot be changed"), 
	INVALID_PRICE("Coupon price must be more than 0"),
	INVALID_AMOUNT("Coupon amount must be more than 0"), 
	INVALID_DATES("The dates you've entered are wrong");

	private String errorMessage;

	private ErrorTypes(String internalMessage) {
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
