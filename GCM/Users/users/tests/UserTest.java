package users.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import users.User;

class UserTest {
	static final int DEFAULT_USER_ID = 1;

	private User defaultUser;

	@BeforeEach
	void setUp() throws Exception {
		defaultUser = new User("moshe@gmail.com", "050000000");
	}

	@Test
	void getIdReturnsCorrectId() {
		assertEquals("moshe@gmail.com", defaultUser.getEmail());
		assertEquals("050000000", defaultUser.getPhoneNumber());

	}

}
