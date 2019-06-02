package database.Execution;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import database.Connection.DBConnector;

public class DatabaseExecutor implements IExecuteQueries {
	private Connection dbConnection;

	public DatabaseExecutor(Connection connection) {
		dbConnection = connection;
	}

	@Override
	public int insertAndGenerateId(String tableName, List<Object> objects) throws SQLException {
		String sqlquery = "INSERT " + tableName + " VALUES (";
		if (objects.size() > 0) {
			for (int i = 0; i <= objects.size() - 1; i++) {
				sqlquery = sqlquery.concat("?, ");
			}
			sqlquery = sqlquery.concat("?)");
		}
		System.out.println(sqlquery);
		PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlquery);
		int id = generateId(tableName);
		preparedStatement.setObject(1, id);
		for (int i = 0; i < objects.size(); i++) {
			preparedStatement.setObject(i + 2, objects.get(i)); // start from second '?'
		}
		preparedStatement.execute();
		return id;
	}

	@Override
	public void insertToTable(String tableName, List<Object> objects) throws SQLException {
		String sqlquery = "INSERT " + tableName + " VALUES (";
		if (objects.size() > 0) {
			for (int i = 0; i < objects.size() - 1; i++) {
				sqlquery = sqlquery.concat("?, ");
			}
			sqlquery = sqlquery.concat("?)");
		}
		System.out.println(sqlquery);

		PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlquery);
		for (int i = 0; i < objects.size(); i++) {
			preparedStatement.setObject(i + 1, objects.get(i)); // start from second '?'
		}
		preparedStatement.execute();
	}

	private int generateId(String tableName) {
		// TODO Auto-generated method stub
		return 120;
	}

	@Override
	public void deleteValueFromTable(String tableName, String objectName, Object object) throws SQLException {
		String sqlquery = "DELETE from " + tableName + " WHERE " + objectName + " = ? ;";
		System.out.println(sqlquery);

		PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlquery);
		preparedStatement.setObject(1, object);
		preparedStatement.execute();

	}

	@Override
	public ResultSet selectColumnsByValue(String tableName, String objectName, Object object, String columnsToSelect)
			throws SQLException {
		String sqlquery = "Select " + columnsToSelect + " from " + tableName + " WHERE " + objectName + " = ? ;";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlquery);
		preparedStatement.setObject(1, object);
		return preparedStatement.executeQuery();
	}

}
