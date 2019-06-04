package login;

import userAccess.UserDAO;
import users.User;

public class LoginModel implements UserDAO {

	@Override
	public boolean register(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		if(username.equals("user")&&password.equals("pass")) {
			return true;
		}
		return false;
	}

}
