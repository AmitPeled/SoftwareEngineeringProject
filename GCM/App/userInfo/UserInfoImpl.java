package userInfo;

import dataAccess.customer.CustomerDAO;
import dataAccess.users.UserDAO;
import queries.RequestState;
import users.User;

public class UserInfoImpl implements UserInfo {

	private UserDAO userDao;
	private CustomerDAO customerDAO;
	private User user;
	private RequestState state;

	public UserInfoImpl(UserDAO userDao, CustomerDAO customerDAO) {
		this.userDao = userDao;
		this.customerDAO = customerDAO;
	}

	@Override
	public boolean login(String username, String password) {
		this.state = userDao.login(username, password);
		if (state == RequestState.wrongDetails) {
			return false;
		}

		return true;
	}

	@Override
	public boolean register(String username, String password, User user) {
		if (userDao.register(username, password, user) == RequestState.wrongDetails) {
			return false;
		}
		return true;
	}

	

	public RequestState getState() {
		return this.state;

	}

	// update the user and send him back
	@Override
	public User getUserDetailes() {

		if (customerDAO.getUserDetails() == null) {
			System.out.println("need to get user but get null");
			return null;
		} else {
			user = customerDAO.getUserDetails();
			System.out.println(customerDAO.getUserDetails().getUsername());
		}
		return user;

	}

}
