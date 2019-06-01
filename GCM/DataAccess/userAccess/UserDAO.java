package userAccess;

import users.User;

public interface UserDAO {

	/**
	 * Adds a user to the system
	 * 
	 * @param user The user to add
	 * @return True if the user added successfully to the system and False if
	 *         username already exists in the system or if details are Invalid
	 */
	public boolean register(User user); // adds user to the system (privileged as regular costumer)

	/**
	 * Verifies whether a user exists in the system
	 * 
	 * @param username The username string of the user
	 * @param password The password string of the user
	 * @return True if the user exists in the system and False if the details are
	 *         wrong
	 */
	public boolean login(String username, String password); // verifies details correctness
}
