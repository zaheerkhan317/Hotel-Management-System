package com.main;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.Admin.Admin;
import com.Admin.AdminDAO;
import com.Admin.AdminDAOException;
import com.UserLogin.UserLogin;
import com.UserLogin.UserLoginDAO;
import com.UserLogin.UserLoginDAOException;
import com.UserRegistration.*;


public class Main {
	
	
	
	public static void Login(Session session) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter your email : ");
		String email = sc.nextLine();
		
		System.out.print("Enter your password : ");
		String password = sc.nextLine();
		
		UserLogin ULog = new UserLogin(email, password);
		UserLoginDAO UDao = new UserLoginDAO(session);
		try {
			UDao.validateLogin(ULog, session);
		} catch (UserLoginDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void addUser(Session session) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the FirstName of User : ");
        String fname = sc.nextLine();
        
        System.out.print("Enter the LastName of User : ");
        String lname = sc.nextLine();

        System.out.print("Enter the Email of User : ");
        String email = sc.nextLine();
        
        System.out.print("Enter the Password of User: ");
        String password = sc.nextLine();

        System.out.print("Enter the Number of User : ");
        long phone = sc.nextLong();
        sc.nextLine();

        System.out.print("Enter the CheckIn (YYYY-MM-DD): ");
        String checkin= sc.nextLine();

        System.out.print("Enter the CheckOut (YYYY-MM-DD): ");
        String checkout = sc.nextLine();
        
        RoomTypes(); // Display room types

        System.out.print("Enter the Room Type : ");
        int roomType = sc.nextInt();
        sc.nextLine(); // Consume newline

        String roomTypeName;
        switch (roomType) {
            case 1:
                roomTypeName = "Single Room";
                break;
            case 2:
                roomTypeName = "Double Room";
                break;
            case 3:
                roomTypeName = "Triple Room";
                break;
            case 4:
                roomTypeName = "Deluxe Room";
                break;
            case 5:
                roomTypeName = "Suite Room";
                break;
            default:
                roomTypeName = "Invalid Room Type";
                break;
        }
        
        UserRegistration UReg = new UserRegistration(fname, lname, email, password, phone, checkin, checkout, roomTypeName);
        AdminDAO adDao = new AdminDAO(session);
        try {
			adDao.addUserByAdmin(UReg, session);
		} catch (AdminDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        sc.close();;
	}
	
	public static void RoomTypes() {
		
		System.out.println("");
		System.out.println("1. Single Room");
		System.out.println("2. Double Room");
		System.out.println("3. Triple Room");
		System.out.println("4. Deluxe Room");
		System.out.println("5. Suite Room");
		System.out.println("");
		
	}
	
	
	public static void Update(Session session) {
		
		Scanner sc = new Scanner(System.in);
		
        System.out.print("Enter your Number : ");
        long phone = sc.nextLong();
        sc.nextLine();

        System.out.print("Enter the FirstName of User : ");
        String fname = sc.nextLine();
        
        System.out.print("Enter the LastName of User : ");
        String lname = sc.nextLine();

        System.out.print("Enter the Email of User : ");
        String email = sc.nextLine();
        
        System.out.print("Enter the Password of User: ");
        String password = sc.nextLine();

        System.out.print("Enter the CheckIn (YYYY-MM-DD): ");
        String checkin= sc.nextLine();

        System.out.print("Enter the CheckOut (YYYY-MM-DD): ");
        String checkout = sc.nextLine();
        
        RoomTypes();
        
        System.out.print("Select Room Type: ");
        int roomType = sc.nextInt();
        sc.nextLine();
        
        String roomTypeName;
        switch (roomType) {
        case 1:
            roomTypeName = "Single Room";
            break;
        case 2:
            roomTypeName = "Double Room";
            break;
        case 3:
            roomTypeName = "Triple Room";
            break;
        case 4:
            roomTypeName = "Deluxe Room";
            break;
        case 5:
            roomTypeName = "Suite Room";
            break;
        default:
            roomTypeName = "Invalid Room Type";
            break;
    }

        UserRegistration UReg = new UserRegistration(fname, lname, email, password, phone, checkin, checkout, roomTypeName);
        AdminDAO admDao = new AdminDAO(session);
		
		try {
			admDao.UpdateUser(UReg, session);
		} catch (AdminDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        sc.close();
	}
	
	public static void Delete(Session session) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter your Number : ");
        long phone = sc.nextLong();
        sc.nextLine();
        
        UserRegistration UReg = new UserRegistration(phone);
        AdminDAO admDao = new AdminDAO(null);
        try {
			admDao.DeleteUserByAdmin(UReg, session);
		} catch (AdminDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        sc.close();
		
	}
	
	public static void getAllUsers(Session session) {
		UserRegistration UReg = new UserRegistration();
		AdminDAO admDao = new AdminDAO(session);
		try {
			admDao.ViewAllUsers(UReg, session);
		} catch (AdminDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void getUserByID(Session session) {
		Scanner sc = new Scanner(System.in);
		
        System.out.print("Enter the userID : ");
        int userId = sc.nextInt();
        sc.nextLine();
        
		UserRegistration UReg = new UserRegistration(userId);
        AdminDAO admDao = new AdminDAO(session);
        try {
			admDao.GetUsersByIdByAdmin(UReg, session);
		} catch (AdminDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        sc.close();
	}
	
	public static void getEmptyRooms(Session session) {
		        
		
        Scanner scanner = new Scanner(System.in);
        RoomTypes();
        int op = scanner.nextInt();
        enterRoom(scanner, op);
        Main UReg = new Main(Main.getreturnRoomType());
        UserLoginDAO UDao = new UserLoginDAO(session);
        UDao.getEmptyRooms(UReg, session);
        
	}
	
	public static void getEmptyRoomsByAdmin(Session session) {
		        
		
        Scanner scanner = new Scanner(System.in);
        RoomTypes();
        int op = scanner.nextInt();
        enterRoom(scanner, op);
        Main UReg = new Main(Main.getreturnRoomType());
        AdminDAO admDao = new AdminDAO(session);
        admDao.getEmptyRoomsByAdmin(UReg, session);
        
	}
	
	public static void UserRegistration(Session session) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter your FirstName : ");
        String fname = sc.nextLine();
        
        System.out.print("Enter your LastName : ");
        String lname = sc.nextLine();

        System.out.print("Enter your Email : ");
        String email = sc.nextLine();
        
        System.out.print("Enter your Password : ");
        String password = sc.nextLine();

        System.out.print("Enter your Phone Number : ");
        long phone = sc.nextLong();
        sc.nextLine();

        System.out.print("Enter your CheckIn (YYYY-MM-DD): ");
        String checkin= sc.nextLine();

        System.out.print("Enter your CheckOut (YYYY-MM-DD): ");
        String checkout = sc.nextLine();
        
        RoomTypes(); // Display room types
        System.out.print("Enter your Room Type : ");
        int roomType = sc.nextInt();
        sc.nextLine();
        enterRoom(sc, roomType);
        UserRegistration UReg = new UserRegistration(fname, lname, email, password, phone, checkin, checkout, Main.getreturnRoomType());
        UserRegistrationDAO UDao = new UserRegistrationDAO(session);
        try {
			UDao.insertUser(UReg);
		} catch (UserRegistrationDAOException e) {
			e.printStackTrace();
		}
        sc.close();
		
		
	}
	private static String roomTypeName;
	
	public Main(String roomTypeName) {
		Main.roomTypeName = roomTypeName;
	}
	public static void setreturnRoomType(String roomTypeName) {
		Main.roomTypeName = roomTypeName;
	}
	
	public static String getreturnRoomType() {
		return roomTypeName;
	}
	
	public static void enterRoom(Scanner sc,  int roomType) {
		  // Consume newline

	        String roomTypeName;
	        switch (roomType) {
	            case 1:
	                roomTypeName = "Single Room";
	                break;
	            case 2:
	                roomTypeName = "Double Room";
	                break;
	            case 3:
	                roomTypeName = "Triple Room";
	                break;
	            case 4:
	                roomTypeName = "Deluxe Room";
	                break;
	            case 5:
	                roomTypeName = "Suite Room";
	                break;
	            default:
	                roomTypeName = "Invalid Room Type";
	                break;
	        }
	        setreturnRoomType(roomTypeName);
	}
	
	public static void Three() {
		
		System.out.println("");
		System.out.println("1. User Login");
		System.out.println("2. Admin Login");
		System.out.println("3. User Registration");
		System.out.println("0. Exit");
		System.out.println("");
		
	}
	
	
	public static void MainMenu(Session session) {
		
		System.out.println("");
		System.out.println("1. Book a Room");
		System.out.println("2. Update Details of User By Phone Number");
		System.out.println("3. Delete a User By Phone Number");
		System.out.println("4. Fetch All Users");
		System.out.println("5. Fetch User By ID");
		System.out.println("6. Fetch Empty Rooms");
		System.out.println("0. Exit");
		System.out.println("");
		
	    Scanner sc = new Scanner(System.in);
	    
	    int option = sc.nextInt();
	    
	    switch(option) {
		    case 1:
		    	Main.addUser(session);
		        System.out.println("Room booked successfully!");
		        break;
		    
		    case 2:
		    	Main.Update(session);
		        System.out.println("Details updated successfully!");
		        break;
		    
		    case 3:
		    	Main.Delete(session);
		        System.out.println("User deleted successfully!");
		        break;
		    
		    case 4:
		    	System.out.println("Fetching all users...");
		    	Main.getAllUsers(session);
		        break;
		        
		    case 5:
		        System.out.println("Fetching User");
		        Main.getUserByID(session);
		        break;
		    
		    case 6:
		        System.out.println("Fetching empty rooms...");
		        Main.getEmptyRoomsByAdmin(session);
		        break;
		    
		    case 0:
		    	Main.main(null);
		    default:
		        System.out.println("Invalid option!");
		        break;
	}
	    sc.close();
		
	}
	
	private static void AdminLogin(Scanner sc, Session session) {
		Admin ad = new Admin(1,"admin","admin@gmail.com","admin123");
		AdminDAO adDAO = new AdminDAO(session);
		try {
			adDAO.insertAdmin(ad, session);
			Scanner scc = new Scanner(System.in);
			
			System.out.print("Enter admin mailID : ");
			String email = scc.nextLine();
			
			System.out.print("Enter admin password : ");
			String password = scc.nextLine();
			Admin ad1 = new Admin(email, password);
			AdminDAO adDAO1 = new AdminDAO(session);
			adDAO1.adminValidateLogin(ad1, email, password);
		} catch (AdminDAOException e) {
			System.out.println("Error code: " + e.getErrorCode());
            System.out.println("Error message: " + e.getMessage());
		}
        
		
	}
	
	
	public static void adminLogin(Session session) {
		MainMenu(session);
		
	}
	
	public static void main(String[] args) {
		SessionFactory sf = new Configuration().configure("Hibernate.cfg.xml").buildSessionFactory();
        Session session = sf.openSession();
		System.out.println("\n");
		System.out.println("          ::::::::::::::::::::::::::          ");
		System.out.println("          :     WELCOME TO OUR     :          ");
		System.out.println("          :        HOTEL!!!!       :          ");
		System.out.println("          ::::::::::::::::::::::::::          ");
		System.out.println("\n");
		
        Three();
        Scanner sc = new Scanner(System.in);
      
        int op = sc.nextInt();
        
        switch(op) {
	      case 1:
	      	  Login(session);
	          break;
	      case 2:
	      	  AdminLogin(sc, session);
	          break;
	      case 3:
	    	  UserRegistration(session);
	      case 0:
	      		System.exit(0);
	      default:
	          System.out.println("Invalid option!");
	          break;
        }
       
		sc.close();
	}

	public static void userlogin() {
		System.out.println("");
 		System.out.println("1. Update Details");
 		System.out.println("2. Fetch empty Rooms");
 		System.out.println("0. Exit");
 		System.out.println("");
		
	}

	

}
