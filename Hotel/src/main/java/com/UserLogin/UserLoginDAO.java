package com.UserLogin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.UserRegistration.*;
import com.main.Main;



public class UserLoginDAO {

	private static Session session;
	
	public UserLoginDAO(Session session) {
		this.session = session;
	}
	
	public void validateLogin(UserLogin Ulog, Session session) throws UserLoginDAOException{
		try {
		    session.beginTransaction();
		    String query = "FROM UserRegistration where Email = :email AND Password = :password";
		    UserRegistration UReg = (UserRegistration) session.createQuery(query)
		            .setParameter("email", Ulog.getEmail())
		            .setParameter("password", Ulog.getPassword())
		            .uniqueResult();
		    
		    session.getTransaction().commit();
		    if (UReg != null) {
		        System.out.println("");
		        System.out.println("=====================");
		        System.out.println("Login Successful!!!!!");
		        System.out.println("=====================");
		        System.out.println("");
		        
		        
		        userLoginInterface();
		    }
		        else {
		            // User does not exist or credentials are incorrect
		            System.out.println("");
		            System.out.println("=========================");
		            System.out.println("Email/Password wrong!!!!!");
		            System.out.println("=========================");
		            System.out.println("");
		        }
		        
		}
		    catch (HibernateException ex) {
			    if (session.getTransaction() != null) {
			        session.getTransaction().rollback();
			    }
			    ex.printStackTrace(); 
		} catch (Exception e) {
		    e.printStackTrace(); 
		}
	}
		
		
	public static void userLoginInterface() {
		com.main.Main.userlogin();
		Scanner sc = new Scanner(System.in);
	      
        int op = sc.nextInt();
        switch(op) {
	      case 1:
	    	  Main.Update(session);
	          break;
	      
	      case 2:
	    	  Main.getEmptyRooms(session);
	          break;
	          
	      case 0:
			try {
				System.out.println("Logging out........");
	            System.out.println("Logged Out Successfully!!!!");
	            System.out.println("");
				System.exit(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      default:
	          System.out.println("Invalid option!");
	          break;
        }
        
        
	}
	
	public void UpdateUser(UserRegistration user, Session session) throws UserLoginDAOException{
		
		Transaction tx = session.beginTransaction();
		try {
				
			UserRegistration query = session.createQuery("FROM UserRegistration WHERE Phone = :phone", UserRegistration.class)
                    .setParameter("phone", user.getPhone())
                    .uniqueResult();
		    
		    if (query == null) {
	            throw new UserRegistrationDAOException("User not found with the phone: " + user.getPhone(), "USER_NOT_FOUND_ERROR");
	        }
		    
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
            userLoginInterface();
		}
		catch (UserRegistrationDAOException e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        System.out.println("Error code: " + e.getErrorCode());
	        System.out.println("Error message: " + e.getMessage());
	        com.main.Main.userlogin();
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
	
	
	public void getEmptyRooms(Main ur, Session session) {
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
	        userLoginInterface();
	    } else if (Main.getreturnRoomType().equals("Double Room")) {
	        System.out.println("Number of " + Main.getreturnRoomType() + " rooms left: " + (doubleRoom - roomCount));
	        userLoginInterface();
	    } else if (Main.getreturnRoomType().equals("Triple Room")) {
	        System.out.println("Number of " + Main.getreturnRoomType() + " rooms left: " + (tripleRoom - roomCount));
	        userLoginInterface();
	    } else if (Main.getreturnRoomType().equals("Deluxe Room")) {
	        System.out.println("Number of " + Main.getreturnRoomType() + " rooms left: " + (deluxeRoom - roomCount));
	        userLoginInterface();
	    } else if (Main.getreturnRoomType().equals("Suite Room")){
	        System.out.println("Number of " + Main.getreturnRoomType() + " rooms left: " + (suiteRoom - roomCount));
	        userLoginInterface();
	    }
	}
}
		
