package database.execution;

import java.sql.SQLException;
import java.util.List;

/**
 * @author amit
 *
 */
public interface IExecuteQueries {
	void insertToTable(String tableName, List<Object> objects) throws SQLException;

	int insertAndGenerateId(String tableName, List<Object> objects) throws SQLException;

	void deleteValueFromTable(String tableName, String objectName, Object object) throws SQLException;

	List<List<Object>> selectColumnsByValue(String tableName, String objectName, Object object, String columnsToSelect)
			throws SQLException;

	List<List<Object>> selectColumnsByValues(String tableName, List<String> objectNames, List<Object> objectsValues,
			String columnsToSelect) throws SQLException;
}
