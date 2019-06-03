package gcmDataAccess.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import accessObject.GcmDAO;
import dataAccess.maps.MapDAO;
import dataAccess.users.UserDAO;
import queries.RequestState;
import users.User;

class usersAccessTest {

	static UserDAO userAccess;

	@BeforeAll
	static void setAll() {
		userAccess = new GcmDAO("", "");
	}

	@Test
	void test() {
		userAccess.login("abcd", "abcd");
		assertEquals(RequestState.wrongDetails, userAccess.login("abcd", "abcd"));
		userAccess.register("abcda", "abvdc", new User("a", "05"));
		assertEquals(RequestState.success, userAccess.login("abcda", "abvdc"));
		assertEquals(RequestState.usernameAlreadyExists, userAccess.register("abcda", "aadabcd", new User("", "")));
	}
}
