package com.UserRegistration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")

public class UserRegistration {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
	private int UserId;
	
	@NotNull
    @Column(name = "FIRST_NAME", columnDefinition = "VARCHAR(50) NOT NULL")
	private String Firstname;
	
	@NotNull
    @Column(name = "LAST_NAME", columnDefinition = "VARCHAR(50) NOT NULL")
	private String Lastname;
	
	@NotNull
    @Column(name = "EMAIL", columnDefinition = "VARCHAR(50) NOT NULL")
	private String Email;
	
	@NotNull
    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(50) NOT NULL")
	private String password;
	
	@NotNull
    @Column(name = "PHONE", columnDefinition = "BIGINT NOT NULL" , unique = true) // assuming phone number is a long integer
    private long Phone;

    @Column(name = "CHECK_IN")
    private String CheckIn;

    @Column(name = "CHECK_OUT")
    private String CheckOut;

    @Column(name = "ROOM_TYPE_NAME")
    private String RoomTypeName;
	
	public String getFirstname() {
		return Firstname;
	}
	public void setFirstname(String firstname) {
		Firstname = firstname;
	}
	public String getLastname() {
		return Lastname;
	}
	public void setLastname(String lastname) {
		Lastname = lastname;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getPhone() {
		return Phone;
	}
	public void setPhone(long phone) {
		Phone = phone;
	}
	public String getCheckIn() {
		return CheckIn;
	}
	public void setCheckIn(String checkIn) {
		CheckIn = checkIn;
	}
	public String getCheckOut() {
		return CheckOut;
	}
	public void setCheckOut(String checkOut) {
		CheckOut = checkOut;
	}
	
	public String getRoomType() {
		return RoomTypeName;
	}
	
	public void setRoomType(String RoomTypename) {
		this.RoomTypeName = RoomTypename;
	}
	
	public int getUserId() {
		return UserId;
	}
	
	public void getUserId(int UserId) {
		this.UserId = UserId;
	}
	
	public UserRegistration(String firstname, String lastname, String email, String password, long phone, String checkin, String checkout, String RoomTypename) {
		super();
		this.Firstname = firstname;
		this.Lastname = lastname;
		this.Email = email;
		this.password = password;
		this.Phone = phone;
		this.CheckIn = checkin;
		this.CheckOut = checkout;
		this.RoomTypeName = RoomTypename;
	}
	
	public UserRegistration( long phone, String checkin, String checkout, String RoomTypeName) {
		super();
		this.Phone = phone;
		this.CheckIn = checkin;
		this.CheckOut = checkout;
		this.RoomTypeName = RoomTypeName;
	}
	
	public UserRegistration(String email, String password) {
		this.Email = email;
		this.password = password;
	}
	
	
	public UserRegistration( String email) {
		super();
		
		this.Email = email;
		
	}
	
	public UserRegistration(long phone) {
		this.Phone = phone;
	}
	
	public UserRegistration( int UserId) {
		super();
		
		this.UserId = UserId;
		
	}
	
	public UserRegistration( ) {
		
		
	}
	@Override
	public String toString() {
		return "UserRegistration [UserId=" + UserId + ", Firstname=" + Firstname + ", Lastname=" + Lastname + ", Email="
				+ Email + ", password=" + password + ", Phone=" + Phone + ", CheckIn=" + CheckIn + ", CheckOut="
				+ CheckOut + ", RoomTypeName=" + RoomTypeName + "]";
	}
	
	
}
