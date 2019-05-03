package server_side;

import java.io.IOException;
import java.io.OutputStream;
import database.IDBExecute;

public class RequestHandler {

	private static final String error_msg = "Error" + '\n';
	IDBExecute DBexecutor;
	RequestParser ReqParser;
	OutputStream outputStream;

	public RequestHandler(IDBExecute executor, RequestParser requestParser, OutputStream outsteam) {
		DBexecutor = executor;
		outputStream = outsteam;
		ReqParser = requestParser;
	}

	public void handleRequest() throws Exception {
		try {
			String message;
			Object action = ReqParser.get("action"), username = ReqParser.get("username"), password = ReqParser.get("password");
			switch (action.toString()) {
			case "register":
				message = register(username.toString(), password.toString());
				break;
			case "verifyUser":
				message = verifyUser();
				break;
			case "addPurchase":
				message = addPurchase();
				break;
			case "getNumPurchases":
				message = getNumPurchases();
				break;
			default:
				message = error_msg + "unknown action";
			}

			System.out.println("Client's request parsed.");
			outputStream.write(message.getBytes());
		} catch (IOException e) {
			System.err.println("Error in writing data to client");
			System.err.println(e.getMessage());
		}
	}

	private String addPurchase() {
		if (!DBexecutor.userExists())
			return error_msg + "username or password are wrong.";
		DBexecutor.addPurchase();
		return "Purchase added.";
	}

	private String getNumPurchases() {
		if (!DBexecutor.detailsExist())
			return error_msg + "username or password are wrong.";
		int numPurchases = DBexecutor.getNumPurchases();
		return String.valueOf(numPurchases);
	}

	private String verifyUser() {
		if (DBexecutor.detailsExist())
			return "Verification succeeded.";
		else
			return error_msg + "username or password are wrong.";
	}

	private String register(String username, String password) {
		StringBuilder message = new StringBuilder();
		if (!verifyDetailsConstrains(username, password, message))
			return error_msg + message;
		if (DBexecutor.userExists()) {
			return error_msg + "username already exists.";
		}
		DBexecutor.addUser();
		return "registaration completed";
	}

	// verify password/username length etc.
	private static boolean verifyDetailsConstrains(String username, String password, StringBuilder message) {
		if (username.length() < 6) {
			message.append("username too short");
			return false;
		} // length 6 = 4 characters
		if (password.length() < 6) {
			message.append("password too short");
			return false;
		}
		if (username.length() > 20) {
			message.append("username too long");
			return false;
		}
		if (password.length() > 20) {
			message.append("username too long");
			return false;
		}
		return true;
	}
//	private static String getPurchases(String un, String ps) {
//	// TODO
//	if (!DBexecutor.userExists(un, ps))
//		return error_msg + "username or password are wrong.";
//	ResultSet purchasesRs = DBexecutor.getPurchases(un, ps);
//	return rsetToString(purchasesRs, "purchase");
//}
//
//private static String rsetToString(ResultSet rs, String columnName) {
//	String s = "";
//	try {
//		while (rs.next()) {
//			s += rs.getString(columnName);
//		}
//	} catch (SQLException e) {
//		System.err.println("Error converting result set to string");
//		System.err.println(e.getMessage());
//	}
//	return s;
//}
//	private static boolean verifyFormat(String input) {
//		// String rgx = "action=.*(&username=.*&password=.*(&basket=.*)?)?"; // general
//		// query pattern
//		for (String query : acceptedQueries)
//			if (input.matches(query))
//				return true;
//		// map[query](m);
//		return false;
//	}

//	private static final String[] acceptedQueries = { "action=register&username=.*&password=.*",
//			"action=verifyUser&username=.*&password=.*", "action=addPurchase&username=.*&password=.*",
//			"action=getNumPurchases&username=.*&password=.*" };
}
