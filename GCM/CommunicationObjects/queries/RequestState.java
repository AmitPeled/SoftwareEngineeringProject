package queries;

public enum RequestState {
	/**
	 * when a request executes properly
	 */
	success,

	/**
	 * when login details are wrong
	 */
	wrongDetails,

	/**
	 * when register username already exists in the system
	 */
	usernameAlreadyExists,

	/**
	 * when a user's details format is not valid
	 */
	longPassword, shortPassword, longUsername, shortUsername
}
