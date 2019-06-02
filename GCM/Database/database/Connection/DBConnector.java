package database.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
	static final String host = "remotemysql.com";
	static final String DBName = "X6SgPM1fb2";
	static final String username = "X6SgPM1fb2";
	static final String password = "AUTfbYZpT5";
	static Connection conn = null;
	static Statement connStmt = null;

	public static Connection connect(String host, String DBName, String username, String password) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + DBName
					+ "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", username, password); // For MySQL
		} catch (SQLException e) {
			System.err.println("unable to connect to DB");
			System.err.println(e.getMessage());
			System.exit(1);
		}
		return conn;
	}

	public static Statement getStatement() {
		return connStmt;
	}

	public static Connection connect() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + DBName
						+ "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", username, password);
				connStmt = conn.createStatement();
				
			} catch (SQLException e) {
				System.err.println("unable to connect to DB");
				System.err.println(e.getMessage());
			}
		}
		return conn;
	}

	public static void closeConnection() {
		try {
			if (!conn.isClosed()) {
				conn.close();
			}
			if (!connStmt.isClosed()) {
				connStmt.close();
			}
		} catch (SQLException e) {
			System.err.println("Error in closing connection");
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		try {
			Connection conn = connect();
			Statement stmt = conn.createStatement();
			String strSelect = "select * from usersDetails";
			System.out.println("The SQL query is: " + strSelect); // Echo For debugging
			System.out.println();

			ResultSet rset = stmt.executeQuery(strSelect);
			System.out.println("The details selected are:");
			int rowCount = 0;
			while (rset.next()) { // Move the cursor to the next row, return false if no more row
				String username = rset.getString("username");
				String password = rset.getString("numPurchase");
				System.out.println(username + ", " + password);
				++rowCount;
			}
			System.out.println("Total number of users = " + rowCount);

			stmt.close();
			rset.close();
			conn.close();
		} catch (Exception ex) {
			System.err.println("unable to read from DB");
			System.err.println(ex.getMessage());
		}
	}
}
