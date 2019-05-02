package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

import hash.sha1;

public class DBExecutor implements IDBExecute {
	String username;
	String password;

	public DBExecutor(String username, String password) {
		this.username = username;
		this.password = sha1.applyHash(password);
	}

	public boolean detailsExist() {
		String sqlquery = "SELECT username FROM " + usersdetailsTable + " WHERE username = ? AND password = ?;";
		boolean success = false;
		try {
			PreparedStatement pstmt = DBConnector.conn.prepareStatement(sqlquery);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			synchronized (dbAccess) {
				success = pstmt.executeQuery().next();
			}
		} catch (SQLException e) {
			System.err.println("Error in verifying user");
			System.err.println(e.getMessage());
		}
		return success;
	}

	public boolean userExists() {
		String sqlquery = "SELECT username FROM " + usersdetailsTable + " WHERE username = ?;";
		boolean success = false;
		try {
			PreparedStatement pstmt = DBConnector.conn.prepareStatement(sqlquery);
			pstmt.setString(1, username);
			synchronized (dbAccess) {
				success = pstmt.executeQuery().next();
			}
		} catch (SQLException e) {
			System.err.println("Error in verifying user");
			System.err.println(e.getMessage());
		}
		return success;
	}

	public boolean addUser() {
		int numPurchases = 0;
		String sqlquery = "INSERT INTO " + usersdetailsTable + " values (?, ?, ?);";
		try {
			PreparedStatement pstmt = DBConnector.conn.prepareStatement(sqlquery);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setInt(3, numPurchases);
			if (!detailsExist()) {
				synchronized (dbAccess) {
					pstmt.executeUpdate();
				}
				return true;
			}
		} catch (SQLException e) {
			System.err.println("Error in adding user");
			System.err.println(e.getMessage());
		}
		return false;
	}

	public void addPurchase() {
		String sqlquery = "UPDATE " + usersdetailsTable + " SET numPurchases = numPurchases + 1 WHERE username = ? AND "
				+ "password = ?;";
		try {
			PreparedStatement pstmt = DBConnector.conn.prepareStatement(sqlquery);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			synchronized (dbAccess) {
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.err.println("Error in inserting new purchase");
			System.err.println(e.getMessage());
		}
	}

	public int getNumPurchases() {
		String sqlquery = "SELECT numPurchases FROM " + usersdetailsTable + " WHERE username = ? AND password = ?;";
		int numPurchases = -1;
		try {
			PreparedStatement pstmt = DBConnector.conn.prepareStatement(sqlquery);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			synchronized (dbAccess) {
				ResultSet rs = pstmt.executeQuery();
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

	public ResultSet getPurchases() {
		// TODO Auto-generated method stub
		return null;
	}

	public void cleanTables() {
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
}
