package server_side;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.api.client.http.UrlEncodedParser;
import com.google.api.client.util.Charsets;

import database.DBExecutor;

public class RequestParser {

	private static final String error_msg = "Error" + '\n';

	static void parse(BufferedReader input, OutputStream out) throws IOException {
		System.out.println("Parsing Client's request..");
		String query = input.readLine();
		Map<String, String> m = new HashMap<String, String>();
//		if (!verifyFormat(query)) { // query format is valid
//			out.write((error_msg + "unknown query").getBytes());
//			System.err.println("unknown client query: " + query);
//			return;
//		}
		try {
			UrlEncodedParser.parse(query, m);
		} catch (Exception e) {
			System.err.println("Error parsing request. -fromat not valid");
			System.err.println(e.getMessage());
		}

		try {
			byte[] msg;
			Object action = m.get("action"), un = m.get("username"), ps = m.get("password");
			switch (action.toString()) {
			case "[register]":
				msg = register(un.toString(), ps.toString()).getBytes();
				break;
			case "[verifyUser]":
				msg = verifyUser(un.toString(), ps.toString()).getBytes();
				break;
			case "[addPurchase]":
				msg = addPurchase(un.toString(), ps.toString()).getBytes();
				break;
			case "[getNumPurchases]":
				msg = getNumPurchases(un.toString(), ps.toString()).getBytes();
				break;
//			case "[getPurchases]":
//				msg = getPurchases(un.toString(), ps.toString()).getBytes();
//				break;
			default:
				msg = (error_msg + "unknown action").getBytes();
			}

			System.out.println("Client's request parsed.");
			out.write(msg);
		} catch (IOException e) {
			System.err.println("Error in writing data to client");
			System.err.println(e.getMessage());
		}
	}

	private static String addPurchase(String un, String ps) {
		if (!DBExecutor.userExists(un, ps))
			return error_msg + "username or password are wrong.";
		DBExecutor.addPurchase(un, ps);
		return "Purchase added.";
	}

	private static String getNumPurchases(String un, String ps) {
		if (!DBExecutor.userExists(un, ps))
			return error_msg + "username or password are wrong.";
		int numPurchases = DBExecutor.getNumPurchases(un, ps);
		return String.valueOf(numPurchases);
	}

	private static String verifyUser(String un, String ps) {
		if (DBExecutor.userExists(un, ps))
			return "Verification succeeded.";
		else
			return error_msg + "username or password are wrong.";
	}

	private static String register(String username, String password) {
		StringBuilder msg = new StringBuilder();
		if (!verifyDetailsConstrains(username, password, msg))
			return error_msg + "username or password are not valid. " + msg;
		if (DBExecutor.userExists(username)) {
			return error_msg + "username already exists.";
		}

		DBExecutor.addUser(username, password);
		return "registaration completed";
	}

	// verify password/username length etc.
	private static boolean verifyDetailsConstrains(String username, String password, StringBuilder msg) {
		if (username.length() < 6) {
			msg.append(error_msg + "username too short");
			return false;
		} // length 6 = 4 characters
		if (password.length() < 6) {
			msg.append(error_msg + "password too short");
			return false;
		}
		if (username.length() > 20) {
			msg.append(error_msg + "username too long");
			return false;
		}
		if (password.length() > 20) {
			msg.append(error_msg + "username too long");
			return false;
		}
		return true;
	}
//	private static String getPurchases(String un, String ps) {
//	// TODO
//	if (!DBExecutor.userExists(un, ps))
//		return error_msg + "username or password are wrong.";
//	ResultSet purchasesRs = DBExecutor.getPurchases(un, ps);
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
