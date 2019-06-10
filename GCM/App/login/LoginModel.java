package login;

import dataAccess.users.UserDAO;
import queries.RequestState;
import users.User;

public class LoginModel implements UserDAO {

	@Override
	public RequestState register(String username, String password, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestState login(String username, String password) {
		if (username.equals("user") && password.equals("pass")) {
			return RequestState.customer;
		} else
			return RequestState.wrongDetails;
	}

}
