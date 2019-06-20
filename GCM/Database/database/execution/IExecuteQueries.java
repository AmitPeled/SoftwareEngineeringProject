package database.execution;

import java.sql.SQLException;
import java.util.List;

import database.objectParse.Status;

/**
 * @author amit
 *
 */
public interface IExecuteQueries {
	void insertToTable(String tableName, List<Object> objects, Status status) throws SQLException;

	int insertAndGenerateId(String tableName, List<Object> objects, Status status) throws SQLException;

	void insertToTable(String tableName, List<Object> objects) throws SQLException;

	int insertAndGenerateId(String tableName, List<Object> objects) throws SQLException;

	void deleteValueFromTable(String tableName, String objectName, Object object) throws SQLException;

	void deleteValuesFromTable(String tableName, List<String> objectNames, List<Object> objects) throws SQLException;

	void deleteValueFromTable(String tableName, String objectName, Object object, Status status) throws SQLException;

	void deleteValuesFromTable(String tableName, List<String> objectNames, List<Object> objects, Status status)
			throws SQLException;

	List<List<Object>> selectColumnsByValue(String tableName, String objectName, Object object, String columnsToSelect)
			throws SQLException;

	List<List<Object>> selectColumnsByPartialValue(String tableName, String objectName, Object object,
			String columnsToSelect) throws SQLException;

	List<List<Object>> selectColumnsByValues(String tableName, List<String> objectNames, List<Object> objectsValues,
			String columnsToSelect) throws SQLException;

	/**
	 * this funtion update 1 value in 1 column in the data base
	 * 
	 * Query = Update tableName set columnToUpdate = valueToUpdate Where
	 * columnCondition = conditonValue; note: inside the function i get only int
	 * object value!
	 * 
	 * @param tableName
	 * @param columnToUpdate
	 * @param valueToUpdate
	 * @param columnCondition
	 * @param conditonValue
	 * @throws SQLException
	 */
	void updateTableColumn(String tableName, String columnToUpdate, Object valueToUpdate, String columnCondition,
			Object conditonValue) throws SQLException;

	List<List<Object>> betweenDates(String tableName, String columnToSelect, Object Date1, String columnCondition,
			Object Date2) throws SQLException;

	List<List<Object>> betweenDatesAndConditions(String tableName, String columnToSelect, Object Date1,
			String columnDateCondition, Object Date2, String columnCondition1, String columnCondition2,
			Object conditon1, Object condition2) throws SQLException;

	List<List<Object>> selectColumnsByValue(String tableName, String objectName, Object object, String columnsToSelect,
			Status status) throws SQLException;

	List<List<Object>> selectColumnsByPartialValue(String tableName, String objectName, Object object,
			String columnsToSelect, Status status) throws SQLException;

	List<List<Object>> selectColumnsByValues(String tableName, List<String> objectNames, List<Object> objectsValues,
			String columnsToSelect, Status status) throws SQLException;

	List<List<Object>> selectAllColumns(String tableName, String columnsToSelect) throws SQLException;

}
