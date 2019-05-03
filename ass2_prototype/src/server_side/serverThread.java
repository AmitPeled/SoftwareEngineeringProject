package server_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import database.DBExecutor;
import database.IDBExecute;

public class ServerThread extends Thread {
	protected Socket socket;

	public ServerThread(Socket socket) {
		this.socket = socket;
		System.out.println("New client connected from " + socket.getInetAddress().getHostAddress());
		start();
	}

	public void run() {
		System.out.println("I'm in thread: " + this.getName());
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			System.out.println("Connection is set");
			BufferedReader requestReader = new BufferedReader(new InputStreamReader(inputStream));
			System.out.println("debug 0");
			RequestParser requestParser = new RequestParser(requestReader);
			System.out.println("debug 1");
			IDBExecute databaseExecutor = new DBExecutor(requestParser.get("username"), requestParser.get("password"));
			System.out.println("debug 2");
			new RequestHandler(databaseExecutor, requestParser, outputStream).handleRequest();
			System.out.println("debug 3");

			// messageToReply = new RequestHandler(new DBExecutor(..,..),in,out
		} catch (IOException ex) {
			System.err.println("Unable to get streams from client");
			System.err.println(ex.getMessage());
		} catch (Exception e) {
			System.err.println(
					"Request Handling for client address " + socket.getInetAddress().getHostAddress() + " failed.");
			System.err.println(e.getMessage());
		} finally {
			try {
				inputStream.close();
				outputStream.close();
				socket.close();
			} catch (IOException ex) {
				System.err.println("Error closing resources");
				System.err.println(ex.getMessage());
			}
		}
	}

}
