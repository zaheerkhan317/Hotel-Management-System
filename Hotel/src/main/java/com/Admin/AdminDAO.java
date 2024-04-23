package com.Admin;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.UserLogin.UserLoginDAO;
import com.UserRegistration.UserRegistration;
import com.UserRegistration.UserRegistrationDAOException;
import com.main.Main;


public class AdminDAO {

	private Session session;
	
	public AdminDAO(Session session) {
		this.session = session;
	}
	
	public void insertAdmin(Admin ad, Session session) throws AdminDAOException {
		
		Transaction tr = session.beginTransaction();
		
		try {
			boolean ordersTableExists = session.getMetamodel().getEntities().stream()
                    .anyMatch(entityType -> "Admin".equals(entityType.getName()));
			if (ordersTableExists) { 
			    Admin existingAdmin = session.get(Admin.class, ad.getAdminId());
			    if (existingAdmin != null) {
			        existingAdmin.setAdminName(ad.getAdminName());
			        existingAdmin.setAdminEmail(ad.getAdminEmail());
			        existingAdmin.setAdminPassword(ad.getAdminPassword());
			        session.update(existingAdmin);
			    } else {
			        session.save(ad);
			    }
			}
			session.getTransaction().commit();
		}
		catch (ConstraintViolationException e) {
            tr.rollback();
            throw new AdminDAOException("Error inserting admin details: Constraint violation occurred", "CONSTRAINT_VIOLATION",session);
        } catch (Exception e) {
            tr.rollback();
            throw new AdminDAOException("Error inserting admin details", "GENERAL_ERROR",session);
        }
	}
	
	
	public void adminValidateLogin(Admin admin, String email, String password)throws AdminDAOException {
		
		try {
		    session.beginTransaction();
		    String query = "FROM Admin where adminEmail = :adminEmail AND adminPassword = :adminPassword";
		    Admin adm = (Admin) session.createQuery(query)
		            .setParameter("adminEmail", email)
		            .setParameter("adminPassword", password)
		            .uniqueResult();
		    System.out.println(email + password);
		    session.getTransaction().commit();
		    if (adm != null) {
		        System.out.println("");
		        System.out.println("===========================");
		        System.out.println("Admin Login Successful!!!!!");
		        System.out.println("===========================");
		        System.out.println("");
		        
		        Main.adminLogin(session);
		        
		    } else {
		    	
		        System.out.println("");
		        System.out.println("=========================");
		        System.out.println("Email/Password wrong!!!!!");
		        System.out.println("=========================");
		        System.out.println("");
		        throw new AdminDAOException("Email/Password wrong!!!!!", "ADMIN_DAO_LOGIN_ERROR", session);
		        
		    }
		} catch (HibernateException ex) {
		    if (session.getTransaction() != null) {
		        session.getTransaction().rollback();
		    }
		    ex.printStackTrace(); 
		} catch (AdminDAOException e) {
		    System.out.println("Error code: " + e.getErrorCode());
		    System.out.println("Error message: " + e.getMessage());
		} catch (Exception e) {
		    e.printStackTrace(); 
		}

		
	}
	
	
	public void addUserByAdmin(UserRegistration UReg, Session session) throws AdminDAOException {
		
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
        	
        	Main.MainMenu(session);
		}
		catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
	    }
		
	}
	
public void UpdateUser(UserRegistration user, Session session) throws AdminDAOException{
		
		Transaction tx = session.beginTransaction();
		try {
				
			UserRegistration query = session.createQuery("FROM UserRegistration WHERE Phone = :phone", UserRegistration.class)
                    .setParameter("phone", user.getPhone())
                    .uniqueResult();
		    
		    if (query == null) {
	            throw new UserRegistrationDAOException("User not found with the phone: " + user.getPhone(), "USER_NOT_FOUND_ERROR");
	        }
		    
		    query.setFirstname(user.getFirstname());
		    query.setLastname(user.getLastname());
		    query.setEmail(user.getEmail());
		    query.setPassword(user.getPassword());
		    query.setCheckIn(user.getCheckIn());
		    query.setCheckOut(user.getCheckOut());
		    query.setRoomType(user.getRoomType());
		    
		    session.update(query);
            session.getTransaction().commit();
        	
            System.out.println("");
            System.out.println("============================");
            System.out.println("User Updated successfully!!!");
            System.out.println("============================");
            System.out.println("");
            
            UserLoginDAO.userLoginInterface();
		}
		catch (UserRegistrationDAOException e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        System.out.println("Error code: " + e.getErrorCode());
	        System.out.println("Error message: " + e.getMessage());
	        UserLoginDAO.userLoginInterface();
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	        UserLoginDAO.userLoginInterface();
	    } finally {
	        session.close();
	    }
		
	}
	
	public void UpdateUserByAdmin(UserRegistration user, Session session) throws AdminDAOException{
		
		Transaction tx = session.beginTransaction();
		try {
				
			UserRegistration query = session.createQuery("FROM UserRegistration WHERE Phone = :phone", UserRegistration.class)
                    .setParameter("phone", user.getPhone())
                    .uniqueResult();
		    
		    if (query == null) {
	            throw new UserRegistrationDAOException("User not found with the phone: " + user.getPhone(), "USER_NOT_FOUND_ERROR");
	        }
		    
		    query.setFirstname(user.getFirstname());
		    query.setLastname(user.getLastname());
		    query.setEmail(user.getEmail());
		    query.setPassword(user.getPassword());
		    query.setCheckIn(user.getCheckIn());
		    query.setCheckOut(user.getCheckOut());
		    query.setRoomType(user.getRoomType());
		    
		    session.update(query);
            session.getTransaction().commit();
        	
            System.out.println("");
            System.out.println("============================");
            System.out.println("User Updated successfully!!!");
            System.out.println("============================");
            System.out.println("");
            
            Main.MainMenu(session);
		}
		catch (UserRegistrationDAOException e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        System.out.println("Error code: " + e.getErrorCode());
	        System.out.println("Error message: " + e.getMessage());
	        Main.MainMenu(session);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	        com.main.Main.userlogin();
	    } finally {
	        session.close();
	    }
		
	}
	
	public void DeleteUserByAdmin(UserRegistration user, Session session) throws AdminDAOException{
		
		Transaction tx = session.beginTransaction();
		try {
				
			UserRegistration query = session.createQuery("FROM UserRegistration WHERE Phone = :phone", UserRegistration.class)
                    .setParameter("phone", user.getPhone())
                    .uniqueResult();
		    
		    if (query == null) {
	            throw new UserRegistrationDAOException("User not found with phone number: " + user.getPhone(), "USER_NOT_FOUND_ERROR");
	        }
		    
		    session.delete(query);
            session.getTransaction().commit();
        	
            System.out.println("");
            System.out.println("============================");
            System.out.println("User deleted successfully!!!");
            System.out.println("============================");
            System.out.println("");
            Main.MainMenu(session);
        	
		}
		catch (UserRegistrationDAOException e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        System.out.println("Error code: " + e.getErrorCode());
	        System.out.println("Error message: " + e.getMessage());
	        Main.MainMenu(session);
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	        Main.MainMenu(session);
	    } finally {
	        session.close();
	    }
		
	}
	
	
	public void ViewAllUsers(UserRegistration user, Session session) throws AdminDAOException{
		
		 try {
		        List<UserRegistration> usersList = session.createQuery("FROM UserRegistration", UserRegistration.class).getResultList();
		        if (usersList.isEmpty()) {
		            System.out.println("No orders found.");
		        } else {
		            for (UserRegistration user1 : usersList) {
		                // Print or process each order as needed
		                System.out.print("User ID: " + user1.getUserId() + " FIRST_NAME: " + user1.getFirstname() + " LAST_NAME: " 
		                + user1.getLastname() + " EMAIL: " + user1.getEmail() + " PASSWORD: " + user1.getPassword() + " PHONE: " 
		                + user1.getPhone() + " CHECK_IN: " + user1.getCheckIn() + " CHECK_OUT: " + user1.getCheckOut() + " ROOM_TYPE: "
		                + user1.getRoomType());
		                System.out.println();
		            }
		        }
		        Main.MainMenu(session);
		    } catch (Exception e) {
		        throw new AdminDAOException("Error retrieving orders.", "GET_ORDERS_ERROR", session);
		    }
		
	}
	
	public void GetUsersByIdByAdmin(UserRegistration user, Session session) throws AdminDAOException {
		try {
			List<UserRegistration> usersList = session.createQuery("FROM UserRegistration WHERE UserId = :UserId", UserRegistration.class)
					.setParameter("UserId", user.getUserId())
					.getResultList();
	        if (usersList.isEmpty()) {
	            System.out.println("No users found.");
	        } else {
	            for (UserRegistration user1 : usersList) {
	            	System.out.print("User ID: " + user1.getUserId() + " FIRST_NAME: " + user1.getFirstname() + " LAST_NAME: " 
			                + user1.getLastname() + " EMAIL: " + user1.getEmail() + " PASSWORD: " + user1.getPassword() + " PHONE: " 
			                + user1.getPhone() + " CHECK_IN: " + user1.getCheckIn() + " CHECK_OUT: " + user1.getCheckOut() + " ROOM_TYPE: "
			                + user1.getRoomType());
			                System.out.println();
	                System.out.println();
	            }
	        }
	        Main.MainMenu(session);
	    } catch (Exception e) {
	        throw new AdminDAOException("Error retrieving orders.", "GET_ORDERS_ERROR", session);
	    }
	}
	
	public void getEmptyRoomsByAdmin(Main ur, Session session) {
	    int singleRoom = 20;
	    int doubleRoom = 10;
	    int tripleRoom = 5;
	    int deluxeRoom = 10;
	    int suiteRoom = 20;
	    System.out.println(Main.getreturnRoomType());
	    Query<Long> query = session.createQuery("SELECT COUNT(*) FROM UserRegistration WHERE RoomTypeName = :RoomTypename", Long.class);
	    query.setParameter("RoomTypename", Main.getreturnRoomType());
	    
	    long roomCount = query.uniqueResult();

	    if (Main.getreturnRoomType().equals("Single Room")) {
	        System.out.println("Number of " + Main.getreturnRoomType() + " rooms left: " + (singleRoom - roomCount));
	        Main.MainMenu(session);
	    } else if (Main.getreturnRoomType().equals("Double Room")) {
	        System.out.println("Number of " + Main.getreturnRoomType() + " rooms left: " + (doubleRoom - roomCount));
	        Main.MainMenu(session);
	    } else if (Main.getreturnRoomType().equals("Triple Room")) {
	        System.out.println("Number of " + Main.getreturnRoomType() + " rooms left: " + (tripleRoom - roomCount));
	        Main.MainMenu(session);
	    } else if (Main.getreturnRoomType().equals("Deluxe Room")) {
	        System.out.println("Number of " + Main.getreturnRoomType() + " rooms left: " + (deluxeRoom - roomCount));
	        Main.MainMenu(session);
	    } else if (Main.getreturnRoomType().equals("Suite Room")){
	        System.out.println("Number of " + Main.getreturnRoomType() + " rooms left: " + (suiteRoom - roomCount));
	        Main.MainMenu(session);
	    }
	}
	
}
