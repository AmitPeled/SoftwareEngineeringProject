package serverThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import request.RequestObject;
import requestHandle.IHandleRequest;
import response.ResponseObject;

public class ServerThread extends Thread {
	private Socket socket;
	private IHandleRequest requestHandler;

	public ServerThread(Socket socket, IHandleRequest requestHandler) {
		this.socket = socket;
		System.out.println("New client connected from " + socket.getInetAddress().getHostAddress());
		this.requestHandler = requestHandler;
		start();
	}

	public void run() {

		System.out.println("I'm in thread: " + this.getName());
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("Connection is set");
			try {
				RequestObject requestObject = (RequestObject) in.readObject();
				ResponseObject responseObject = requestHandler.handleRequest(requestObject);
				out.writeObject(responseObject);
			} catch (ClassNotFoundException e) {
				System.err.println("Object sent by client is not of the type RequestObject");
				System.err.println(e.getMessage());
			}
		} catch (IOException ex) {
			System.err.println("Unable to get streams from client");
			System.err.println(ex.getMessage());
		} finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException ex) {
				System.err.println("Error closing resources");
				System.err.println(ex.getMessage());
			}
		}
	}

}
