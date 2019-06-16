package userInfo;

import users.User;

public interface UserInfo {

	/**
	 * Executes a log-in request. If successful, it will update the UserInfo object
	 * with the logged in user's details
	 * 
	 * @return true if the log-in was successful, false otherwise
	 */
	boolean login(String username, String password);

	/**
	 * Executes a registration request.
	 * 
	 * @return true if user was registered successfully, false otherwise
	 */
	boolean register(String username, String password, User user);

	User getUserDetailes();

}
