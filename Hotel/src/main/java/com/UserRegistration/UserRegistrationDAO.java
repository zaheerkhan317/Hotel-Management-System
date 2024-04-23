package com.UserRegistration;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.main.Main; 

public class UserRegistrationDAO {

	private Session session;
	
	public UserRegistrationDAO(Session session) {
		this.session = session;
	}
	
	public void insertUser(UserRegistration UReg) throws UserRegistrationDAOException {
		Transaction tx =session.beginTransaction();
		try {
			
			if (UReg.getFirstname().trim().isEmpty() || 
				    UReg.getLastname().trim().isEmpty() ||
				    UReg.getEmail() == null || 
				    UReg.getEmail().trim().isEmpty() ||
				    UReg.getPhone() == 0 ||  // Assuming 0 indicates an invalid phone number
				    UReg.getPassword() == null || 
				    UReg.getPassword().trim().isEmpty() ||
				    UReg.getCheckIn() == null || 
				    UReg.getCheckOut().trim().isEmpty() ||
				    UReg.getRoomType() == null || 
				    UReg.getRoomType().trim().isEmpty()) {
	                throw new UserRegistrationDAOException("One or more required fields are null or empty.", "INVALID_USER_DATA_ERROR");
	                
	            }
					
            List<UserRegistration> queryByPhone = session.createQuery("FROM UserRegistration WHERE Phone = :phone", UserRegistration.class).setParameter("phone", UReg.getPhone()).getResultList();
   
            if (!queryByPhone.isEmpty()) {
                throw new UserRegistrationDAOException("User with the same email or phone already exists.", "DUPLICATE_USER_ERROR");
            }

            session.save(UReg);
            session.getTransaction().commit();
            System.out.println("");
        	System.out.println("==========================");
        	System.out.println("Registration successful!!!");
        	System.out.println("==========================");
        	System.out.println("");
        	
        	Main.main(null);
		}
		catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
	    }
	}
	
}
