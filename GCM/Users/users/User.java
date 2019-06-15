
package users;

import java.io.Serializable;
import java.util.Date;

/**
 * @author amit
 *
 */
public final class User implements Serializable {
	private static final long serialVersionUID = 1L;

	public User(String firstName, String lastName, String email, String phoneNumber) {
		this(firstName, lastName, email, phoneNumber, null);

	}

	public User(String username, String firstName, String lastName, String email, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.username = username;
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

	public String getUsername() {
		return username;
	}

	public String setUsername(String username) {
		return this.username = username;
	}

	private String username;
	private String email;
	private String phoneNumber;
	private String firstName;
	private String lastName;
}
