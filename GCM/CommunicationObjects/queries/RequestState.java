package queries;

public enum RequestState {
	/**
	 * when a log-in executes properly, it returns the user's account type.
	 */
	customer, editor, manager,

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
	longPassword, shortPassword, longUsername, shortUsername,

	/**
	 * when a user tries to operate a function out of its privilege
	 */

	somethingWrongHappend

}
