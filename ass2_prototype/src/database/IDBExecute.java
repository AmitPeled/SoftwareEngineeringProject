package database;

import java.sql.ResultSet;
import java.util.concurrent.locks.ReentrantLock;

public interface IDBExecute {
	final String usersdetailsTable = "usersDetails";
	final String userspurchasesTable = "usersPurchases";
	ReentrantLock dbAccess = new ReentrantLock();

	public boolean detailsExist();

	public boolean userExists();

	public boolean addUser();

	public void addPurchase();

	public int getNumPurchases();

	public ResultSet getPurchases();

}
