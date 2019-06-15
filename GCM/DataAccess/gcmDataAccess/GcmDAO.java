package gcmDataAccess;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import dataAccess.contentManager.ContentManagerDAO;
import dataAccess.customer.CustomerDAO;
import dataAccess.editor.EditorDAO;
import dataAccess.search.SearchDAO;
import dataAccess.users.PurchaseDetails;
import dataAccess.users.UserDAO;
import maps.City;
import maps.Map;
import maps.Site;
import maps.Tour;
import queries.GcmQuery;
import queries.RequestState;
import request.RequestObject;
import response.ResponseObject;
import users.User;

//import javax.net.ssl.SSLSocket;
//import javax.net.ssl.SSLSocketFactory;

@SuppressWarnings("serial")
public class GcmDAO implements UserDAO, CustomerDAO, EditorDAO, ContentManagerDAO, SearchDAO, Serializable {
	String serverHostname;
	int serverPortNumber;
	String password = null;
	String username = null;

	public GcmDAO(String host, int port) {
		serverHostname = host;
		serverPortNumber = port;
	}

	public GcmDAO() {
		serverHostname = "localhost";
		serverPortNumber = 8080;
	}

	@Override
	public Map getMapDetails(int mapID) {
		ResponseObject responseObject = send(new RequestObject(GcmQuery.getMapDetails, new ArrayList<Object>() {
			{
				add(mapID);
			}
		}, username, password));
		return (Map) responseObject.getResponse().get(0);
	}

	@Override
	public File getMapFile(int mapID) {
		ResponseObject responseObject = send(new RequestObject(GcmQuery.getMapFile, new ArrayList<Object>() {
			{

				add(mapID);
			}
		}, username, password));
		return (File) responseObject.getResponse().get(0);
	}

	@Override
	public RequestState register(String username, String password, User user) {
		setDetails(username, password);
		ResponseObject responseObject = send(new RequestObject(GcmQuery.addCustomer, new ArrayList<Object>() {
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
		setDetails(username, password);
		ResponseObject responseObject = send(new RequestObject(GcmQuery.verifyUser, new ArrayList<Object>() {
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

//		System.out.println("Sending data to server..");
		try {
			out.writeObject(req);
		} catch (IOException e1) {
			System.err.println("error in sending data to server");
			System.err.println(e1.getMessage());
		}
//		System.out.println("data sent. receiving data:");
		Object res = null;
		try {
			res = in.readObject();
		} catch (ClassNotFoundException | IOException e1) {
			System.err.println("error in reading data from server");
			System.err.println(e1.getMessage());
		}
		if (!(res instanceof ResponseObject)) {
			System.err.println("Error! unknown response from server." /* + res.toString() */);
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
		return (int) send(new RequestObject(GcmQuery.addMap, new ArrayList<Object>() {
			{
				add(cityId);
				add(mapDetails);
				add(mapFile);
			}
		}, username, password)).getResponse().get(0);
	}

	@Override
	public int addCity(City city) {
		return (int) send(new RequestObject(GcmQuery.addCity, new ArrayList<Object>() {
			{
				add(city);
			}
		}, username, password)).getResponse().get(0);
	}

	private void setDetails(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> getMapsByCityName(String cityName) {
		return (List<Map>) (Object) send(new RequestObject(GcmQuery.getMapsByCityName, new ArrayList<Object>() {
			{
				add(cityName);
			}
		}, username, password)).getResponse();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> getMapsBySiteName(String siteName) {
		return (List<Map>) (Object) send(new RequestObject(GcmQuery.getMapsBySiteName, new ArrayList<Object>() {
			{
				add(siteName);
			}
		}, username, password)).getResponse();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> getMapsByDescription(String description) {
		return (List<Map>) (Object) send(new RequestObject(GcmQuery.getMapsByDescription, new ArrayList<Object>() {
			{
				add(description);
			}
		}, username, password)).getResponse();
	}

	@Override
	public int addNewSiteToCity(int cityId, Site site) {
		return (int) send(new RequestObject(GcmQuery.addNewSiteToCity, new ArrayList<Object>() {
			{
				add(cityId);
				add(site);
			}
		}, username, password)).getResponse().get(0);
	}

	@Override
	public int addExistingSiteToMap(int mapId, int siteId) {
		send(new RequestObject(GcmQuery.addExistingSiteToMap, new ArrayList<Object>() {
			{
				add(mapId);
				add(siteId);
			}
		}, username, password));
		return 0;
	}

	public int deleteContent(int contentId) {
		// by now delete map only
		send(new RequestObject(GcmQuery.deleteContent, new ArrayList<Object>() {
			{
				add(contentId);
			}
		}, username, password));
		return 0;
	}

	@Override
	public int deleteSiteFromMap(int mapId, int siteId) {
		send(new RequestObject(GcmQuery.deleteSiteFromMap, new ArrayList<Object>() {
			{
				add(mapId);
				add(siteId);
			}
		}, username, password));
		return 0;
	}

	@Override
	public int updateContent(int contentId, Object newContent) {
		send(new RequestObject(GcmQuery.updateContent, new ArrayList<Object>() {
			{
				add(contentId);
				add(newContent);
			}
		}, username, password));
		return 0;
	}

	@Override
	public boolean purchaseCityOneTime(int mapId, PurchaseDetails purchaseDetails) {
		return (boolean) send(new RequestObject(GcmQuery.purchaseCity, new ArrayList<Object>() {
			{
				add(mapId);
				add(purchaseDetails);
			}
		}, username, password)).getResponse().get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> getPurchasedMaps() {
		return (List<Map>) (Object) send(new RequestObject(GcmQuery.getPurchasedMaps, null, username, password))
				.getResponse();
	}

	@Override
	public User getUserDetails() {
		return (User) send(new RequestObject(GcmQuery.getUserDetails, null, username, password)).getResponse().get(0);
	}

	@Override
	public City getCityByMapId(int mapId) {
		return (City) send(new RequestObject(GcmQuery.getCityByMapId, new ArrayList<Object>() {
			{
				add(mapId);
			}
		}, username, password)).getResponse().get(0);
	}

	@Override
	public int addNewTourToCity(int cityId, Tour tour) {
		return (int) send(new RequestObject(GcmQuery.addNewTourToCity, new ArrayList<Object>() {
			{
				add(cityId);
				add(tour);
			}
		}, username, password)).getResponse().get(0);
	}

	@Override
	public int addExistingTourToMap(int mapId, int tourId) {
		send(new RequestObject(GcmQuery.addExistingTourToMap, new ArrayList<Object>() {
			{
				add(mapId);
				add(tourId);
			}
		}, username, password));
		return 0;
	}

	@Override
	public int addExistingSiteToTour(int tourId, int siteId, int siteDurance) {
		return (int) send(new RequestObject(GcmQuery.addExistingSiteToTour, new ArrayList<Object>() {
			{
				add(tourId);
				add(siteId);
				add(siteDurance);
			}
		}, username, password)).getResponse().get(0);
	}

	@Override
	public int deleteSiteFromTour(int mapId, int siteId) {
		send(new RequestObject(GcmQuery.addExistingSiteToTour, new ArrayList<Object>() {
			{
				add(mapId);
				add(siteId);
			}
		}, username, password)).getResponse().get(0);
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Site> getCitySites(int cityId) {
		return (List<Site>) (Object) send(new RequestObject(GcmQuery.getCitySites, new ArrayList<Object>() {
			{
				add(cityId);
			}
		}, username, password)).getResponse();
	}

	@Override
	public double getMembershipPrice(int cityId, int timeInterval) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean repurchaseMembership(PurchaseDetails purchaseDetails) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean repurchaseMembershipBySavedDetails() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public File downloadMap(int mapId) {
//		return (File) send(new RequestObject(GcmQuery.downloadMap, new ArrayList<Object>() {
//			{
//				add(mapId);
//			}
//		}, username, password)).getResponse().get(0);
		return null; // TODO
	}

	@Override
	public boolean purchaseMembershipToCity(int cityId, int timeInterval, PurchaseDetails purchaseDetails) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void notifyMapView(int mapId) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSavedCreditCard() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void changeMapPrice(int mapId, double newPrice) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionMapAddEdit(Map map, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionMapUpdateEdit(Map map, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionMapDeleteEdit(Map map, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionCityAddEdit(City city, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionCityUpdateEdit(City city, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionCityDeleteEdit(City city, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionSiteAddEdit(Site site, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionSiteUpdateEdit(Site site, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionSiteDeleteEdit(Site site, boolean action) {
		// TODO Auto-generated method stub

	}

	@Override
	public int tourManager(int cityId, Tour tour) {
		int tourId = tour.getId();
		// check if tour existing if so add new tour
		if(tour.getId() == -1) {
			tourId = addNewTourToCity(cityId, tour);
		}
		
		// add places to tour
		List<Site> sitesList = tour.getSites();
		List<Integer> sitesTimeEstimationList = tour.getSitesTimeToVisit();
		int numberOfLastAddedPlaces = tour.getNumberOfLastAddedPlaces();
		int startingIndex = 0;
		if(numberOfLastAddedPlaces != 0) {
			startingIndex = sitesList.size() - numberOfLastAddedPlaces -1;
		}
		
		for (int i = startingIndex; i < sitesList.size(); i++) {
			addExistingSiteToTour(tourId, sitesList.get(i).getId(), sitesTimeEstimationList.get(i));
		}

		
		
		return 0;
	}

	@Override
	public List<Map> getMapsAddEdits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map> getMapsUpdateEdits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map> getMapsDeleteEdits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Site> getSitesAddEdits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Site> getSitesUpdateEdits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Site> getSitesDeleteEdits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<City> getCitiesAddEdits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<City> getCitiesUpdateEdits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<City> getCitiesDeleteEdits() {
		// TODO Auto-generated method stub
		return null;
	}
}
