package userManagement;

import java.util.HashSet;
import java.util.Set;

public class UserManager {
	private static Set<User> userPool = new HashSet<>();

	static public boolean addUser(String username, String password) {
		User user = new UserManager.User(username, password);
		synchronized (userPool) {
			if (!userPool.contains(user)) {
				userPool.add(user);
				return true;

			} else
				return false;
		}
	}

	public static void removeUser(String username, String password) {
		User user = new UserManager.User(username, password);
		synchronized (userPool) {
			userPool.remove(user);
		}
	}

	public static class User {
		private String username, password;

		public User(String username, String password) {
			this.setUsername(username);
			this.setPassword(password);
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public boolean equals(User other) {
			return other.getPassword() == password && other.getUsername() == username;
		}
	}
}
