package databaseTables;

import java.util.HashMap;

import table.Table;

public class UsersTable extends Table {
	public final String name = "usersDetails";
	@SuppressWarnings("serial")
	public final HashMap<Integer, String> columns = new HashMap<Integer, String>() {
		{
			put(0, "username");
			put(1, "password");
			put(2, "userDetails");
		}
	};

	public UsersTable() {
		super.name = this.name;
		super.columns = this.columns;
	}
}
