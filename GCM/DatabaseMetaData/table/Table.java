package table;

import java.util.HashMap;

public class Table {
	protected String name;
	protected HashMap<Integer, String> columns;
	
	public String getName() {
		return name;
	}
	public String getColumnName(int i) {
		return columns.get(i);
	}
}
