package serverThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import queries.GcmQuery;
import request.RequestObject;
import requestHandle.IHandleRequest;
import response.ResponseObject;
import userManagement.UserManager;

public class ServerThread extends Thread {
	private Socket socket;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private IHandleRequest requestHandler;
	private String clientUsername, clientPassword;

	public ServerThread(Socket socket, IHandleRequest requestHandler) {
		this.socket = socket;
//		System.out.println("New client connected from " + socket.getInetAddress().getHostAddress());
		this.requestHandler = requestHandler;
		start();
	}

	public boolean equals(ServerThread otherThread) {
		return clientUsername.equals(otherThread.getClientUsername())
				&& clientPassword.equals(otherThread.getClientPassword());
	}

	public void run() {
//		System.out.println("I'm in thread: " + this.getName());
		RequestObject requestObject = null;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			requestObject = (RequestObject) in.readObject();
		} catch (IOException ex) {
			System.err.println("Unable to read streams from client");
			System.err.println(ex.getMessage());
			ex.printStackTrace();
			return;
		} catch (ClassNotFoundException e) {
			System.err.println("Object sent by client is not of the type RequestObject");
			System.err.println(e.getMessage());
			e.printStackTrace();
			return;
		}
		manageAccess(requestObject);
		while (socket.isConnected()) {
			try {
				in = new ObjectInputStream(socket.getInputStream());
				out = new ObjectOutputStream(socket.getOutputStream());
				requestObject = (RequestObject) in.readObject();
			} catch (IOException ex) {
				System.err.println("Unable to read streams from client");
				System.err.println(ex.getMessage());
			} catch (ClassNotFoundException e) {
				System.err.println("Object sent by client is not of the type RequestObject");
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			try {
				/** parsing client request */
				clientUsername = requestObject.getUname();
				clientPassword = requestObject.getPass();
				GcmQuery query = requestObject.getQuery();
				List<Object> objectsFromClient = requestObject.getObjects();
				ResponseObject responseObject = requestHandler.handleRequest(clientUsername, clientPassword, query,
						objectsFromClient);
				out.writeObject(responseObject);
			} catch (IOException e) {
				System.err.println("Error sending server response");
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			try {
				in.close();
				out.close();
			} catch (IOException ex) {
				System.err.println("Error closing IO resources");
				System.err.println(ex.getMessage());
				ex.printStackTrace();
			}
		}
//		} finally {
		try {
			UserManager.removeUser(clientUsername, clientPassword);
			socket.close();
		} catch (IOException ex) {
			System.err.println("Error closing resources");
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}

	}

	private void manageAccess(RequestObject requestObject) {
		String username = requestObject.getUname();
		String password = requestObject.getPass();
		GcmQuery query = requestObject.getQuery();
		if (query == GcmQuery.verifyUser && !UserManager.addUser(username, password))
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException ex) {
				System.err.println("Error closing IO resources");
				System.err.println(ex.getMessage());
			}
		return;
	}

	public String getClientUsername() {
		return clientUsername;
	}

	public void setClientUsername(String clientUsername) {
		this.clientUsername = clientUsername;
	}

	public String getClientPassword() {
		return clientPassword;
	}

	public void setClientPassword(String clientPassword) {
		this.clientPassword = clientPassword;
	}

}
