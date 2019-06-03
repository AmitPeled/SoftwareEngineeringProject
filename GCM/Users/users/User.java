
package users;

import java.io.Serializable;

/**
 * @author amit
 *
 */
public final class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public User(String email, String phoneNumber) {
		this.email = email;
		this.phoneNumber = phoneNumber;

	}
	public String getEmail() {
		return email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	private String email;
	private String phoneNumber;
}

// Membership membership - membership type, purchase date
