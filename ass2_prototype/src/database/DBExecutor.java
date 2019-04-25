package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

public class DBExecutor {
	static final String usersdetailsTable = "usersDetails";
	static final String userspurchasesTable = "usersPurchases";
	static ReentrantLock dbAccess = new ReentrantLock();

	public static void cleanTables() {
		try {
			synchronized (dbAccess) {
				DBConnector.getStatement().execute("truncate table " + usersdetailsTable + ";");
				DBConnector.getStatement().execute("truncate table " + userspurchasesTable + ";");
			}
		} catch (SQLException e) {
			System.err.println("Error in erasing tables data");
			System.err.println(e.getMessage());
		}
	}

	public static boolean userExists(String username, String password) {
		String sqlquery = "SELECT username FROM " + usersdetailsTable + " WHERE username = '" + username
				+ "' AND password = '" + password + "';";
		boolean success = false;
		try {
			synchronized (dbAccess) {
				success = DBConnector.connStmt.executeQuery(sqlquery).next();
			}
		} catch (SQLException e) {
			System.err.println("Error in verifying user");
			System.err.println(e.getMessage());
		}
		return success;
	}

	public static boolean userExists(String username) {
		String sqlquery = "SELECT username FROM " + usersdetailsTable + " WHERE username = '" + username + "';";
		boolean success = false;
		try {
			synchronized (dbAccess) {
				success = DBConnector.connStmt.executeQuery(sqlquery).next();
			}
		} catch (SQLException e) {
			System.err.println("Error in verifying user");
			System.err.println(e.getMessage());
		}
		return success;
	}

	public static boolean addUser(String username, String password) {
		int numPurchases = 0;
		boolean success = false;
		String sqlquery = "INSERT INTO " + usersdetailsTable + " values ('" + username + "', '" + password + "', "
				+ numPurchases + ");";
		try {
			if (!userExists(username)) {
				synchronized (dbAccess) {
					success = DBConnector.connStmt.execute(sqlquery);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error in adding user");
			System.err.println(e.getMessage());
		}
		return success;
	}

	public static void addPurchase(String username, String password) {
		String sqlquery = "UPDATE " + usersdetailsTable + " SET numPurchases = numPurchases + 1 WHERE username = '"
				+ username + "' AND password = '" + password + "';";
		try {
			synchronized (dbAccess) {
				DBConnector.connStmt.execute(sqlquery);
			}
		} catch (SQLException e) {
			System.err.println("Error in inserting new purchase");
			System.err.println(e.getMessage());
		}
	}

	static public int getNumPurchases(String username, String password) {
		String sqlquery = "SELECT numPurchases FROM " + usersdetailsTable + " WHERE username = '" + username
				+ "' AND password = '" + password + "';";
		int numPurchases = -1;
		try {
			synchronized (dbAccess) {
				ResultSet rs = DBConnector.connStmt.executeQuery(sqlquery);
				rs.next();
				numPurchases = rs.getInt("numPurchases");
				rs.close();
			}
		} catch (SQLException e) {
			System.err.println("Error in selecting purchases");
			System.err.println(e.getMessage());
		}
		return numPurchases;
	}

	public static ResultSet getPurchases(String un, String ps) {
		// TODO Auto-generated method stub
		return null;
	}
}
