package com.Admin;

import org.hibernate.Session;

import com.sun.tools.javac.Main;


public class AdminDAOException extends Exception {
	private String errorCode;
	
	private Session session;
	
    public AdminDAOException(String message, String errorCode, Session session) {
        super(message);
        this.errorCode = errorCode;
        this.session = session;
        try {
			Main.main(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public String getErrorCode() {
        return errorCode;
    }
}
