package com.dan.Coupons.exceptions;

import com.dan.Coupons.enums.ErrorTypes;

public class ApplicationException extends Exception {

	// --------------Properties-----------------
	private static final long serialVersionUID = 1L;
	private ErrorTypes errorType;

	// --------------Ctor-----------------
	public ApplicationException(Exception e, ErrorTypes errorType, String message) {
		super(message, e);
		this.errorType = errorType;
	}

	public ApplicationException(ErrorTypes errorType, String message) {
		super(message);
		this.errorType = errorType;
	}

	// --------------Methods-----------------
	public ErrorTypes getErrorType() {
		return errorType;
	}

}
