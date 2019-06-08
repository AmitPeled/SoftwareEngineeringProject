package gcmDataAccess.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import dataAccess.users.UserDAO;
import gcmDataAccess.GcmDAO;
import queries.RequestState;
import users.User;

class usersAccessTest {

	static UserDAO userAccess;

	@BeforeAll
	static void setAll() {
		userAccess = new GcmDAO();
	}

	@Test
	void test() {
		assertEquals(RequestState.wrongDetails, userAccess.login("abcd", "abcd"));
		userAccess.register("abcda", "abvdc", new User("first", "last", "email", "05"));
		assertEquals(RequestState.customer, userAccess.login("abcda", "abvdc"));
		assertEquals(RequestState.usernameAlreadyExists,
				userAccess.register("abcda", "aadabcd", new User("", "", "", "")));
	}
}
