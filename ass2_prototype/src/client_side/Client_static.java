package client_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Client {
	static final String serverHostname = "127.0.0.1"; // local host for now
	static final int port = 8080;
	static String clientPass = null;
	static String clientUsername = null;
	static final String error_msg = "Error";

	public static void main(String args[]) {
		String un = "user1", ps = "pass1";
		StringBuilder msgReceived = new StringBuilder();
		System.out.println(send("bla", msgReceived));
		System.out.println("data from server: " + msgReceived);
		System.out.println();

//		System.out.println("query-register succeeded:" + register(un, ps, msgReceived));
//		System.out.println("data from server: " + msgReceived);
//		System.out.println();

		System.out.println("query-register succeeded:" + verifyUser(un, ps, msgReceived));
		System.out.println("data from server: " + msgReceived);
		System.out.println();

//		System.out.println("\nquery-addPurchase succeeded:" + addPurchase(un, msgReceived));
//		System.out.println("data from server: " + msgReceived);
//		System.out.println();

		System.out.println("query-getSavedBaskets succeeded:" + getNumPurchases(msgReceived));
		System.out.println("data from server: " + msgReceived);
		System.out.println();

	}

	public static boolean isGuest() {
		return clientUsername == null || clientPass == null;
	}

	private static void setDetails(String un, String ps) {
		clientUsername = un;
		clientPass = ps;
	}

	/**
	 * ----Explanation---- the functions returns true on success, false on failure.
	 * message sent by server written in {@msgReceived}
	 */
	public static boolean register(String un, String ps, StringBuilder msgReceived) {
		setDetails(un, ps);
		String dataToSend = "action=register&username=" + un + "&password=" + ps;
		return send(dataToSend, msgReceived);
	}

	public static boolean verifyUser(String un, String ps, StringBuilder msgReceived) {
		setDetails(un, ps);
		String dataToSend = "action=verifyUser&username=" + un + "&password=" + ps;
		return send(dataToSend, msgReceived);
	}

	public static boolean getUserDetails(String un, StringBuilder msgReceived) {
		String dataToSend = "action=getUserDetails&username=" + un;
		return send(dataToSend, msgReceived);
	}

	public static boolean addPurchase(StringBuilder msgReceived) {
		String dataToSend = "action=addPurchase&username=" + clientUsername + "&password=" + clientPass;
		return send(dataToSend, msgReceived);
	}

	public static boolean getNumPurchases(StringBuilder msgReceived) {
		String dataToSend = "action=getNumPurchases&username=" + clientUsername + "&password=" + clientPass;
		return send(dataToSend, msgReceived);
	}

	private static boolean send(String dataToSend, StringBuilder msgReceived) { // false for error, true otherwise
		System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");
//		SSLSocketFactory factory =
//                (SSLSocketFactory)SSLSocketFactory.getDefault();
//            SSLSocket serverSocket = null;
		Socket serverSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		if (msgReceived == null) {
			System.err.println("Error! StringBuilder sent to function has to be created (sent null).");
			return false;
		} else
			msgReceived.setLength(0); // cleaning msg initial data
		boolean state = true;
		try {
			serverSocket = new Socket(serverHostname, port);
//			serverSocket = (SSLSocket)factory.createSocket(serverHostname, port);
//			serverSocket.startHandshake();
			out = new PrintWriter(serverSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream(), "UTF-8"));

		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + serverHostname);
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Unable to get streams from server");
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
}
