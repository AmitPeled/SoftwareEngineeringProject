package database.Tests;

import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import database.connection.DBConnector;
import database.execution.DatabaseExecutor;
import database.execution.IExecuteQueries;
import database.metadata.DatabaseMetaData;
import database.metadata.DatabaseMetaData.Tables;

/**
 * @author amit
 *
 */
class DatabaseExecutorTest {
	static IExecuteQueries dbExecutor;
	static String tableName;

	@BeforeAll
	static void setAll() {
		dbExecutor = new DatabaseExecutor(DBConnector.connect());
		tableName = DatabaseMetaData.getTableName(Tables.users);
	}

	@AfterAll
	static void closeConnection() {
		DBConnector.closeConnection();
	}

	/**
	 * @throws SQLException
	 */
	@Test
	void test() throws SQLException {
		Assert.assertTrue(true);
		String username = "user1", password = "pass1", email = "aa", phone = "052";
		@SuppressWarnings("serial")

		List<Object> arrayList = new ArrayList<Object>() {
			{
				add(username);
				add(password);
				add(email);
				add(phone);
			}
		};
		try {
			dbExecutor.insertToTable(tableName, arrayList);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			fail();
		}
		/** insertion and selection test */
		try {
			List<List<Object>> rowsData = dbExecutor.selectColumnsByValue(tableName, "username", username,
					"password, email");
			Assert.assertEquals(password, rowsData.get(0).get(0));
			Assert.assertEquals(email, rowsData.get(0).get(1));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			fail();
		}
		dbExecutor.deleteValueFromTable(tableName, "username", username);
		List<List<Object>> rowsDataAfterDeletion = dbExecutor.selectColumnsByValue(tableName, "username", username,
				"password, email");
		/** deletion test */
		Assert.assertTrue(rowsDataAfterDeletion.isEmpty());

		/** insert and generate id */
		float height = 12.1f, witdh = 10.7f, x_offset_coordinate = 87.7f, y_offset_coordinate = 12.64f;
		@SuppressWarnings("serial")
		List<Object> mapDetailsFieldsList = new ArrayList<Object>() {
			{
				add(-1);
				add(height);
				add(witdh);
				add(x_offset_coordinate);
				add(y_offset_coordinate);
			}
		};
		int mapId = 0;
		try {
			mapId = dbExecutor.insertAndGenerateId(DatabaseMetaData.getTableName(Tables.mapsMetaDetails),
					mapDetailsFieldsList);
            List<List<Object>> rowsData = dbExecutor
                    .selectColumnsByValue(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), "mapId", mapId, "*");
            Assert.assertEquals(height, rowsData.get(0).get(1));
            Assert.assertEquals(witdh, rowsData.get(0).get(2));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			fail();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		} finally {
			dbExecutor.deleteValueFromTable(DatabaseMetaData.getTableName(Tables.mapsMetaDetails), "mapId", mapId);
		}
	}
}