package hr.codiraona.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import hr.codiraona.model.Company;
import hr.codiraona.model.Location;
import hr.codiraona.model.Role;

public class UserDTO {

	private int id;

	private String email;

	private Timestamp expiresAt;

	private String firstName;

	private String lastName;

	private String mobileNumber;

	private String phoneNumber;

	private String token;

	private String username;

	private Role role;

	private Location location;

	/**
	 * get user id
	 * @return
	 */
	public int getId() {
		return id;
	}


/**
 * get users email
 * @return
 */
	public String getEmail() {
		return email;
	}

	/**
	 * sets user email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * gets timestamp when token will expire
	 * @return
	 */
	public Timestamp getExpiresAt() {
		return expiresAt;
	}

	/*sets token for expiration
	 * 
	 */
	public void setExpiresAt(Timestamp expiresAt) {
		this.expiresAt = expiresAt;
	}

	
	/**
	 * gets user first name
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * sets user first name
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * gets users last name
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * sets users last name
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * gets users mobile number
	 * @return
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * sets user mobile number
	 * @param mobileNumber
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * users phone number
	 * @return
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * sets users phone number
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * return registration token
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
	 * sets registration token
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Returns username
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * sets username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * gets role
	 * @return
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * sets role
	 * @param role
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * gets location
	 * @return
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * sets location
	 * @param location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * gets company
	 * @return
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * sets company
	 * @param company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	private Company company;
}
