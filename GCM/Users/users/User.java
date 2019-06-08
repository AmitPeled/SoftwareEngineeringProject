
package users;

import java.io.Serializable;

//import dataAccess.users.Membership;

/**
 * @author amit
 *
 */
public final class User implements Serializable {
	private static final long serialVersionUID = 1L;

	public User(String firstName, String lastName, String email, String phoneNumber) {
		this.email = email;
		this.phoneNumber = phoneNumber;

	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	private String email;
	private String phoneNumber;
	private String firstName;
	private String lastName;
//	private Membership membershipType;
//	private int numPurchases;
	
}


// Membership membership - membership type, purchase date
