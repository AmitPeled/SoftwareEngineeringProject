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
//		System.out.println("New client connected from " + socket.getInetAddress().getHostAddress());
		this.requestHandler = requestHandler;
		start();
	}

	public void run() {

//		System.out.println("I'm in thread: " + this.getName());
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		RequestObject requestObject = null;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			requestObject = (RequestObject) in.readObject();
//			System.out.println("Connection is set");
		} catch (IOException ex) {
			System.err.println("Unable to get streams from client");
			System.err.println(ex.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println("Object sent by client is not of the type RequestObject");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		try {
			ResponseObject responseObject = requestHandler.handleRequest(requestObject);
			out.writeObject(responseObject);
		} catch (IOException e) {
			System.err.println("Error sending server response");
			System.err.println(e.getMessage());
			e.printStackTrace();
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
