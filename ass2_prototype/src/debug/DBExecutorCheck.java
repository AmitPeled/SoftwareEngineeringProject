package debug;

import java.awt.Checkbox;

import database.DBConnector;
import database.DBExecutor;
import database.IDBExecute;

public class DBExecutorCheck {
	static String username;
	static String password;

	public static void main(String[] args) {
		username = password = "bla";
		DBConnector.connect();
		IDBExecute executer = new DBExecutor(username, password);
		check(executer);
		DBConnector.closeConnection();

	}

	private static void check(IDBExecute executor) {
		System.out.println("addUser check:");
		if (executor.addUser()) {
			System.out.println("\tuser details: (" + username + ", " + password + ") are added to the system.\n");
		} else {
			System.out.println("\tusername " + username + " already exists.\n");
		}

		System.out.println("detailsExists check:");
		if (executor.detailsExist()) {
			System.out.println("\tdetails: (" + username + ", " + password + ") exist\n");
		} else {
			System.out.println("\tdetails: (" + username + ", " + password + ") doesn't exist\n");
		}
	}
}