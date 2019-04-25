package server_side;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class serverThread extends Thread {
	protected Socket socket;

	public serverThread(Socket socket) {
		this.socket = socket;
		System.out.println("New client connected from " + socket.getInetAddress().getHostAddress());
		start();
	}

	public void run() {
		System.out.println("I'm in thread: " + this.getName());
		InputStream in = null;
		OutputStream out = null;
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
			System.out.println("Connection is set");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			RequestParser.parse(br, out);
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
