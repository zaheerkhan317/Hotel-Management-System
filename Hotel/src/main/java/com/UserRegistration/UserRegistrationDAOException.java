package com.UserRegistration;

public class UserRegistrationDAOException extends Exception {
	    private String errorCode;

	    public UserRegistrationDAOException(String message, String errorCode) {
	        super(message);
	        this.errorCode = errorCode;
	    }

	    public String getErrorCode() {
	        return errorCode;
	    }
	
}
