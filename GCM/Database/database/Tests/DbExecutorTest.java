package database.Tests;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import database.Connection.DBConnector;
import database.Execution.DatabaseExecutor;
import database.Execution.IExecuteQueries;

class DbExecutorTest {
	IExecuteQueries dbExecutor = new DatabaseExecutor(DBConnector.connect());;
	String tableName = "usersDetails";

	@AfterAll
	void closeConnection() {
		DBConnector.closeConnection();
	}

	@Test
	void test() throws SQLException {
		String username = "user1", password = "pass1";
		@SuppressWarnings("serial")
		List<Object> arrayList = new ArrayList<Object>() {
			{
				add(username);
				add(password);
				add(10);
			}
		};
		dbExecutor.insertToTable(tableName, arrayList);
		ResultSet rSet = dbExecutor.selectColumnsByValue(tableName, "username", username, "password, userDetails");
		rSet.next();
		// insertion + selection test
		Assert.assertEquals(password, rSet.getObject(1));
		Assert.assertEquals(10, rSet.getObject(2));

		dbExecutor.deleteValueFromTable(tableName, "username", username);
		rSet = dbExecutor.selectColumnsByValue(tableName, "username", username, "password");
		// delete test
		Assert.assertFalse(rSet.next());
	}
}
