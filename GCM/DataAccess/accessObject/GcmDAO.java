package accessObject;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import dataAccess.maps.MapDAO;
import dataAccess.users.UserDAO;
import maps.City;
import maps.Map;
import queries.GcmQuery;
import queries.RequestState;
import request.RequestObject;
import request.UserType;
import response.ResponseObject;
import users.User;

//import javax.net.ssl.SSLSocket;
//import javax.net.ssl.SSLSocketFactory;

@SuppressWarnings("serial")
public class GcmDAO implements UserDAO, MapDAO, Serializable {
	String serverHostname;
	int serverPortNumber;
	String password = null;
	String username = null;

	public GcmDAO(String username, String password, String host, int port) {
		serverHostname = host;
		serverPortNumber = port;
		this.username = username;
		this.password = password;
	}

	public GcmDAO(String username, String password) {
		serverHostname = "localhost";
		serverPortNumber = 8080;
		this.username = username;
		this.password = password;
	}

	@Override
	public Map getMapDetails(int mapID) {
		ResponseObject responseObject = send(
				new RequestObject(UserType.notLogged, GcmQuery.getMapDetails, new ArrayList<Object>() {
					{
						add(mapID);
					}
				}, username, password));
		return (Map) responseObject.getResponse().get(0);
	}

	@Override
	public File getMapFile(int mapID) {
		ResponseObject responseObject = send(
				new RequestObject(UserType.editor, GcmQuery.getMapFile, new ArrayList<Object>() {
					{

						add(mapID);
					}
				}, username, password));
		return (File) responseObject.getResponse().get(0);
	}

	@Override
	public RequestState register(String username, String password, User user) {
		ResponseObject responseObject = send(
				new RequestObject(UserType.notLogged, GcmQuery.addCustomer, new ArrayList<Object>() {
					{
						add(username);
						add(password);
						add(user);
					}
				}, username, password));
		return responseObject.getRequestState();
	}

	@Override
	public RequestState login(String username, String password) {
		ResponseObject responseObject = send(
				new RequestObject(UserType.notLogged, GcmQuery.verifyCustomer, new ArrayList<Object>() {
					private static final long serialVersionUID = 1L;

					{
						add(username);
						add(password);
					}
				}, username, password));
		return responseObject.getRequestState();
	}

	private ResponseObject send(RequestObject req) { // false for error, true otherwise
		System.out.println("Connecting to host " + serverHostname + " on port " + serverPortNumber + ".");
//		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
//		SSLSocket serverSocket = null;
		Socket serverSocket = null;
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		if (req == null) {
			System.err.println("Error! no request sent.");
			return null;
		}
		try {
			System.out.println("connecting to server: ");
			serverSocket = new Socket(serverHostname, serverPortNumber);
//			factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
//			serverSocket = (SSLSocket) factory.createSocket(serverHostname, port);
//			serverSocket.startHandshake();
			out = new ObjectOutputStream(serverSocket.getOutputStream());
			in = new ObjectInputStream(serverSocket.getInputStream());

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
		try {
			out.writeObject(req);
		} catch (IOException e1) {
			System.err.println("error in sending data to server");
			System.err.println(e1.getMessage());
			System.err.println(req.getUname() + req.getPass() + req.getQuery() + req.getUserType());
			System.exit(1);

		}
		System.out.println("data sent. receiving data:");
		Object res = null;
		try {
			res = in.readObject();
		} catch (ClassNotFoundException | IOException e1) {
			System.err.println("error in reading data from server");
			System.err.println(e1.getMessage());
		}
		if (!(res instanceof ResponseObject)) {
			System.err.println("Error! unknown response from server.");
			return null;
		}
		ResponseObject resObject = (ResponseObject) res;

		/** Closing all the resources */
		try {
			out.close();
			in.close();
			serverSocket.close();
		} catch (IOException e) {
			System.err.println("Error closing resources");
			System.err.println(e.getMessage());
		}
		return resObject;
	}

	@Override
	public int addMapToCity(int cityId, Map mapDetails, File mapFile) {
		return (int) send(new RequestObject(UserType.editor, GcmQuery.addMap, new ArrayList<Object>() {
			{
				add(cityId);
				add(mapDetails);
				add(mapFile);
			}
		}, username, password)).getResponse().get(0);
	}

	@Override
	public void deleteMap(int mapId) {
		send(new RequestObject(UserType.editor, GcmQuery.deleteMap, new ArrayList<Object>() {
			{
				add(mapId);
			}
		}, username, password));

	}

	@Override
	public int addCity(City city) {
		return (int) send(new RequestObject(UserType.editor, GcmQuery.addCity, new ArrayList<Object>() {
			{
				add(city);
			}
		}, username, password)).getResponse().get(0);
	}
}
