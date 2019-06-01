package database.execution;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IExecuteQueries {

	void insertToTable(String tableName, List<Object> objects) throws SQLException;

	int insertAndGenerateId(String tableName, List<Object> objects) throws SQLException;

	void deleteValueFromTable(String tableName, String objectName, Object object) throws SQLException;

	ResultSet selectColumnsByValue(String tableName, String objectName, Object object, String columnsToSelect)
			throws SQLException;

}
