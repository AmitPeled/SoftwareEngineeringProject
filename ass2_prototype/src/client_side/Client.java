package client_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Client {
	final String serverHostname = "127.0.0.1"; // local host for now
	final int port = 8080;
	String password = null;
	String username = null;
	final String error_msg = "Error";

	public Client(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * ----Explanation---- the fusernamections returns true on success, false on failure.
	 * message sent by server written in {@msgReceived}
	 */
	public boolean register(StringBuilder msgReceived) {
		String dataToSend = "action=register&username=" + username + "&password=" + password;
		return send(dataToSend, msgReceived);
	}

	public boolean verifyUser(StringBuilder msgReceived) {
		String dataToSend = "action=verifyUser&username=" + username + "&password=" + password;
		return send(dataToSend, msgReceived);
	}

	public boolean getUserDetails(StringBuilder msgReceived) {
		String dataToSend = "action=getUserDetails&username=" + username;
		return send(dataToSend, msgReceived);
	}

	public boolean addPurchase(StringBuilder msgReceived) {
		String dataToSend = "action=addPurchase&username=" + username + "&password=" + password;
		return send(dataToSend, msgReceived);
	}

	public boolean getNumPurchases(StringBuilder msgReceived) {
		String dataToSend = "action=getNumPurchases&username=" + username + "&password=" + password;
		return send(dataToSend, msgReceived);
	}

	private boolean send(String dataToSend, StringBuilder msgReceived) { // false for error, true otherwise
		System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");
//			SSLSocketFactory factory =
//	                (SSLSocketFactory)SSLSocketFactory.getDefault();
//	            SSLSocket serverSocket = null;
		Socket serverSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		if (msgReceived == null) {
			System.err.println("Error! StringBuilder sent to fusernamection has to be created (sent null).");
			return false;
		} else
			msgReceived.setLength(0); // cleaning msg initial data
		boolean state = true;
		try {
			serverSocket = new Socket(serverHostname, port);
//				serverSocket = (SSLSocket)factory.createSocket(serverHostname, port);
//				serverSocket.startHandshake();
			out = new PrintWriter(serverSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream(), "UTF-8"));

//		} catch (unknownHostException e) {
//			System.err.println("unknown host: " + serverHostname);
//			System.err.println(e.getMessage());
//			System.exit(1);
		} catch (IOException e) {
			System.err.println("unable to get streams from server");
			System.err.println(e.getMessage());
			System.exit(1);
		}

		System.out.println("Sending data to server..");
		out.println(dataToSend);
		System.out.println("data sent.");
		try {
			String serveroutput = in.readLine();
			if (serveroutput == null || serveroutput.equals(error_msg))
				state = false;
			while (serveroutput != null) {
				// System.out.println('\t'+serveroutput);
				msgReceived.append(serveroutput + '\n');
				serveroutput = in.readLine();
			}
		} catch (IOException e) {
			System.err.println("Error reading data from server");
			System.err.println(e.getMessage());
		}

		/** Closing all the resources */
		try {
			out.close();
			in.close();
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("Error closing resources");
			System.err.println(e.getMessage());
		}
		return state;
	}
	public String getUsername() {
		return username;
	}
}
