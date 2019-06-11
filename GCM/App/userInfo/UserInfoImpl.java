package userInfo;

import dataAccess.customer.CustomerDAO;
import dataAccess.users.UserDAO;
import queries.RequestState;
import users.User;

public class UserInfoImpl implements UserInfo {

	private UserDAO userDao;
	private CustomerDAO customerDAO;
	private User user;

	public UserInfoImpl(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean login(String username, String password) {
		if (userDao.login(username, password) == RequestState.wrongDetails) {
			return false;
		}
		// update the user
		// user = customerDAO.getUserDetails();
		return true;
	}
}
