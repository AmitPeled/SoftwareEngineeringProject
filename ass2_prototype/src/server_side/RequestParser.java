package server_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.api.client.http.UrlEncodedParser;

public class RequestParser {
	Map<String, Object> parsedRequest = new HashMap<>();

	public RequestParser(BufferedReader urlEncodedReader) {
		try {
			String query = urlEncodedReader.readLine();
			UrlEncodedParser.parse(query, parsedRequest);
		} catch (IOException e) {
			System.err.println("Error parsing request. -fromat not valid");
			System.err.println(e.getMessage());
		}
	}

	public String get(String key) throws Exception {
		ArrayList<String> value = (ArrayList<String>) parsedRequest.get(key);
		if (value.isEmpty()) {
			System.err.println("the field "+ key + " not found.");
			throw new Exception("the field " + key + " not found.");
		}
		return value.get(0);
	}
}
