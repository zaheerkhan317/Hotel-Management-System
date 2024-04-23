package com.UserLogin;

public class UserLoginDAOException extends Exception {
	private String errorCode;

    public UserLoginDAOException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
