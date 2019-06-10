package server;

import java.io.IOException;
import java.net.ServerSocket;

import java.sql.Connection;

import database.connection.DBConnector;
import database.execution.DatabaseExecutor;
import database.execution.GcmDataExecutor;
import database.objectParse.DatabaseParser;
import requestHandle.RequestHandler;
import serverThread.ServerThread;

public class GcmServer {

	public static final int PORT_NUMBER = 8080;

	public static void main(String[] args) {
		System.out.println("Starting server");
//			SSLServerSocket server = null;
		ServerSocket server = null;
		try {
//				ServerSocketFactory SSLfactory = SSLServerSocketFactory.getDefault();
//				server = (SSLServerSocket)SSLfactory.createServerSocket(PORT_NUMBER);
			server = new ServerSocket(PORT_NUMBER);
			Connection connection = DBConnector.connect();
			RequestHandler requestHandler = new RequestHandler(new GcmDataExecutor(
					new DatabaseExecutor(connection/* , DatabaseMetaData.getDbName() */), new DatabaseParser()));

			while (true) {
				new ServerThread(server.accept(), requestHandler);
			}

		} catch (IOException ex) {
			System.out.println("Unable to start server.");
			System.err.println(ex.getMessage());
		} finally {
			try {
				DBConnector.closeConnection();
				if (server != null)
					server.close();
			} catch (IOException ex) {
				System.err.println("unable to close server socket");
				System.err.println(ex.getMessage());
			}
		}
	}
}