package database.tests;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import database.connection.DBConnector;
import database.execution.DatabaseExecutor;
import database.execution.IExecuteQueries;
import database.metadata.databaseMetaData;
import database.metadata.databaseMetaData.Tables;

class DbExecutorTest {
	IExecuteQueries dbExecutor = new DatabaseExecutor(DBConnector.connect(), databaseMetaData.getDbName());;
	String tableName = databaseMetaData.getTableName(Tables.users);

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
		List<List<Object>> rowsData = dbExecutor.selectColumnsByValue(tableName, "username", username, "password, userDetails");
		// insertion + selection test
		Assert.assertEquals(password, rowsData.get(0).get(0));
		Assert.assertEquals(10, rowsData.get(0).get(1));

		dbExecutor.deleteValueFromTable(tableName, "username", username);
		List<List<Object>> rowsDataAfterDeletion = dbExecutor.selectColumnsByValue(tableName, "username", username, "password, userDetails");
		// delete test
		Assert.assertTrue(rowsDataAfterDeletion.isEmpty());
	}
}
