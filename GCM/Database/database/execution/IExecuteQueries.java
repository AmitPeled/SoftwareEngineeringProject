package database.execution;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import table.Table;

public interface IExecuteQueries {
//	boolean updateObjectsById(String table, String idFieldName,int id, String obj//	boolean addUser(String username, String password, Object userDetails);
//	boolean verifyUser(String username, String password); // select.next
//	int addObjectByFieldName(String table, String objectName, Object object); // returns object identifier
//	List<Object> getObjectsById(String table, String idFieldName, int id);
//	boolean deleteObjectsById(String table,StringectFieldName, Object newObject);
	void insertToTable(String tableName, List<Object> objects) throws SQLException;

	int insertAndGenerateId(String tableName, List<Object> objects) throws SQLException;

	void deleteValueFromTable(String tableName, String objectName, Object object) throws SQLException;

	ResultSet selectColumnsByValue(String tableName, String objectName, Object object, String columnsToSelect)
			throws SQLException;

}
