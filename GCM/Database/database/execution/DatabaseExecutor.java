package database.execution;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import database.objectParse.Status;

import java.sql.Statement;

/**
 * @author amit
 *
 */
public class DatabaseExecutor implements IExecuteQueries {
	private Connection dbConnection;
	// private String dbName;
	/**
	 * global database access synchronization.
	 */
	static private ReentrantLock dbAccess = new ReentrantLock();

	public DatabaseExecutor(Connection connection/* , String dbName */) {
		dbConnection = connection;
		// this.dbName = dbName;
	}

	@Override
	public int insertAndGenerateId(String tableName, List<Object> objects) throws SQLException {
		if (objects.size() > 0) {
			String sqlquery = "INSERT " + tableName + " VALUES (";
			for (int i = 0; i < objects.size() - 1; i++) {
				sqlquery = sqlquery.concat("?, ");
			}
			sqlquery = sqlquery.concat("?);");
			System.out.println(sqlquery);
			PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlquery);
			int id = generateId(tableName);
			preparedStatement.setObject(1, id);
			for (int i = 1; i < objects.size(); i++) {
				preparedStatement.setObject(i + 1, objects.get(i)); // start from second '?' and value.
			}
			synchronized (dbAccess) {
				preparedStatement.execute();
			}
			return id;
		}
		return -1;
	}

	@Override
	public void insertToTable(String tableName, List<Object> objects) throws SQLException {
		if (objects.size() > 0) {
			String sqlquery = "INSERT " + tableName + " VALUES (";
			for (int i = 0; i < objects.size() - 1; i++) {
				sqlquery = sqlquery.concat("?, ");
			}
			sqlquery = sqlquery.concat("?);");

			System.out.println(sqlquery);

			PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlquery);
			for (int i = 0; i < objects.size(); i++) {
				preparedStatement.setObject(i + 1, objects.get(i)); // start from second '?'
			}
			synchronized (dbAccess) {
				preparedStatement.execute();
			}
		}
	}

	private int generateId(String tableName) throws SQLException {
		Statement s2 = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		int id = -1;
		synchronized (dbAccess) {
			ResultSet rset = s2.executeQuery("select * from " + tableName);
			if (rset.next()) {
				rset.afterLast();
				rset.previous();
				id = rset.getInt(1);
			}
		}
		return id > 0 ? id + 1 : 1;
	}

	@Override
	public void deleteValueFromTable(String tableName, String objectName, Object object) throws SQLException {
		String sqlquery = "DELETE from " + tableName + " WHERE " + objectName + " = ?;";
		System.out.println(sqlquery);
		PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlquery);
		preparedStatement.setObject(1, object);
		synchronized (dbAccess) {
			preparedStatement.execute();
		}
	}

	@Override
	public List<List<Object>> selectColumnsByValue(String tableName, String objectName, Object object,
			String columnsToSelect) throws SQLException {
		String sqlquery = "Select " + columnsToSelect + " from " + tableName + " WHERE " + objectName + " = ? ;";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlquery);
		preparedStatement.setObject(1, object);
		List<List<Object>> fields = new ArrayList<>();
		synchronized (dbAccess) {
			fields = toList(preparedStatement.executeQuery());
		}
		return fields;
	}

	@Override
	public List<List<Object>> selectColumnsByValues(String tableName, List<String> objectNames,
			List<Object> objectsValues, String columnsToSelect) throws SQLException {
		if (objectNames.size() > 0) {

			String sqlquery = "Select " + columnsToSelect + " from " + tableName + " WHERE ";
			sqlquery = concatConditionalsSymbols(sqlquery, objectNames);
			PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlquery);
			putValuesInSymbols(preparedStatement, objectsValues);
			List<List<Object>> fields = new ArrayList<>();
			synchronized (dbAccess) {
				fields = toList(preparedStatement.executeQuery());
			}
			return fields;
		} else
			return null;

	}

	String concatConditionalsSymbols(String baseString, List<String> objectNames) {
		if (objectNames.size() > 0) {
			for (int i = 0; i < objectNames.size() - 1; i++) {
				baseString = baseString.concat(objectNames.get(i) + " = ? AND ");
			}
			baseString = baseString.concat(objectNames.get(objectNames.size() - 1) + " = ?;");
		}
		return baseString;
	}

	void putValuesInSymbols(PreparedStatement preparedStatement, List<Object> objects) throws SQLException {
		for (int i = 0; i < objects.size(); i++) {
			preparedStatement.setObject(i + 1, objects.get(i));
		}
	}

	private List<List<Object>> toList(ResultSet resultSet) throws SQLException {
		List<List<Object>> rowsData = new ArrayList<>();
		List<String> columnNames = new ArrayList<>();
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
			columnNames.add(resultSetMetaData.getColumnLabel(i));
		}
		while (resultSet.next()) {
			List<Object> rowData = new ArrayList<>();
			for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
				rowData.add(resultSet.getObject(i));
			}
			rowsData.add(rowData);
		}
		return rowsData;
	}

	@Override
	public void deleteValuesFromTable(String tableName, List<String> objectNames, List<Object> objects)
			throws SQLException {
		String sqlquery = "DELETE from " + tableName + " WHERE ";
		System.out.println(sqlquery);
		concatConditionalsSymbols(sqlquery, objectNames);
		PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlquery);
		putValuesInSymbols(preparedStatement, objects);
		synchronized (dbAccess) {
			preparedStatement.execute();
		}
	}

	@Override
	public List<List<Object>> selectColumnsByPartialValue(String tableName, String objectName, Object object,
			String columnsToSelect) throws SQLException {
		String sqlquery = "Select " + columnsToSelect + " from " + tableName + " WHERE " + objectName + " like ? ;";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlquery);
		preparedStatement.setString(1, "%" + (String) object + "%");
		List<List<Object>> fields = new ArrayList<>();
		synchronized (dbAccess) {
			fields = toList(preparedStatement.executeQuery());
		}
		return fields;
	}

	@Override
	public void insertToTable(String tableName, List<Object> objects, Status status) throws SQLException {
		objects.add(getStatus(status));
		insertToTable(tableName, objects);
	}

	@Override
	public int insertAndGenerateId(String tableName, List<Object> objects, Status status) throws SQLException {
		int id = generateId(tableName);
		System.out.println("id received from server: " + id);
		objects.set(0, id);
		insertToTable(tableName, objects, status);
		return id;
	}

	private int getStatus(Status status) {
		switch (status) {
		case published:
			return 0;
		case toAdd:
			return 1;
		case toUpdate:
			return 2;
		case toDelete:
			return 3;
		default:
			System.err.println("bad status value");
			return -1;
		}
	}

	@SuppressWarnings("serial")
	@Override
	public List<List<Object>> selectColumnsByValue(String tableName, String objectName, Object object,
			String columnsToSelect, Status status) throws SQLException {
		List<Object> objectsValues = new ArrayList<Object>() {
			{
				add(object);
				add(getStatus(status));
			}

		};
		List<String> objectNames = new ArrayList<String>() {
			{
				add(objectName);
				add("status");
			}
		};
		return selectColumnsByValues(tableName, objectNames, objectsValues, columnsToSelect);
	}

	@Override
	public List<List<Object>> selectColumnsByPartialValue(String tableName, String objectName, Object object,
			String columnsToSelect, Status status) throws SQLException {
		String sqlquery = "Select " + columnsToSelect + " from " + tableName + " WHERE " + objectName
				+ " like ? AND status = " + getStatus(status) + ";";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlquery);
		preparedStatement.setString(1, "%" + (String) object + "%");
		List<List<Object>> fields = new ArrayList<>();
		synchronized (dbAccess) {
			fields = toList(preparedStatement.executeQuery());
		}
		return fields;
	}

	@Override
	public List<List<Object>> selectColumnsByValues(String tableName, List<String> objectNames,
			List<Object> objectsValues, String columnsToSelect, Status status) throws SQLException {
		objectNames.add("status");
		objectsValues.add(getStatus(status));
		return selectColumnsByValues(tableName, objectNames, objectsValues, columnsToSelect);
	}
}
