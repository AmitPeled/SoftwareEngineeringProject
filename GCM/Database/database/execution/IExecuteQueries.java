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

	void deleteValuesFromTable(String tableName, List<String> objectNames, List<Object> objects) throws SQLException;

	List<List<Object>> selectColumnsByValue(String tableName, String objectName, Object object, String columnsToSelect)
			throws SQLException;
	
	List<List<Object>> selectColumnsByPartialValue(String tableName, String objectName, Object object, String columnsToSelect)
			throws SQLException;

	List<List<Object>> selectColumnsByValues(String tableName, List<String> objectNames, List<Object> objectsValues,
			String columnsToSelect) throws SQLException;
	
	/**
	 *  this funtion update 1 value in 1 column in the data base
	 *  
	 *  Query = Update tableName set columnToUpdate = valueToUpdate Where columnCondition = conditonValue;
	 *  note: inside the function i get only int object value!
	 *  
	 * @param tableName
	 * @param columnToUpdate
	 * @param valueToUpdate
	 * @param columnCondition
	 * @param conditonValue
	 * @throws SQLException
	 */
	void updateTableColumn(String tableName, String columnToUpdate, Object valueToUpdate ,String columnCondition , Object conditonValue) throws SQLException;
	
}
