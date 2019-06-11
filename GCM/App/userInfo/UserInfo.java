package userInfo;

import users.User;

public interface UserInfo {

	boolean login(String username, String password);
	
	boolean register(String username, String password,User user);

}
