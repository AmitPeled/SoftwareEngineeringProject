package serverMetaData;

import java.util.Scanner;

public class ServerMetaDataHandler {
	static final int DEFAULT_PORT = 8080;
	static final String DEFAULT_HOST = "localhost";
	static int serverPortNumber = DEFAULT_PORT;
	static String serverHostName = DEFAULT_HOST;

	public static void receiveFromConsole(String args[]) {
		Scanner in = null;
		try {
			serverHostName = args[0];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Enter server host name");
			in = new Scanner(System.in);
			serverHostName = in.nextLine();
		}
		try {
			serverPortNumber = Integer.parseInt(args[1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Enter server port number");
			in = new Scanner(System.in);
			serverPortNumber = Integer.parseInt(in.nextLine());
		}
		try {
			in.close();
		} catch (NullPointerException e) {
			System.out.println("Readed host and port succesfully");
		}
	}

	public static String getServerHostName() {
		return serverHostName;
	}

	public static int getServerPortNumer() {
		return serverPortNumber;
	}
}
