package gcmDataAccess.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dataAccess.customer.CustomerDAO;
import dataAccess.users.UserDAO;
import gcmDataAccess.GcmDAO;
import users.User;

class CustomerTest {
	
	static CustomerDAO customerDAO;
	@BeforeAll
	static void setAll() {
		GcmDAO gcmDAO = new GcmDAO();
		UserDAO userDAO = gcmDAO;
		userDAO.register("user12", "password", new User("aa", "ab", "ac", "ad"));
		customerDAO = gcmDAO;
	}
	@Test
	void getUserTest() {
		User user = customerDAO.getUserDetails();
		assertEquals("aa", user.getFirstName());
		assertEquals("ab", user.getLastName());
		assertEquals("ac", user.getEmail());
		assertEquals("ad", user.getPhoneNumber());

	}

}
