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
		defaultUser = new User(1);
	}

	@Test
	void getIdReturnsCorrectId() {
		assertEquals(1, defaultUser.getId());
	}

}
