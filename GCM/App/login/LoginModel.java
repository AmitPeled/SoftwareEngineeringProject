package login;

import dataAccess.users.UserDAO;
import gcmDataAccess.GcmDAO;
import queries.RequestState;


public class LoginModel  {
	UserDAO userDAO = new GcmDAO();


	public RequestState login(String username, String password) {
		return userDAO.login(username, password);
	}

}
