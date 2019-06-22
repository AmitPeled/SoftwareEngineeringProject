package gcmDataAccess;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import approvalReports.cityApprovalReports.CitySubmission;
import approvalReports.mapApprovalReports.MapSubmission;
import approvalReports.priceApprovalReports.PriceSubmission;
import approvalReports.sitesApprovalReports.SiteSubmission;
import approvalReports.tourApprovalReports.TourSubmission;
import dataAccess.contentManager.ContentManagerDAO;
import dataAccess.customer.CustomerDAO;
import dataAccess.customer.PurchaseHistory;
import dataAccess.editor.EditorDAO;
import dataAccess.generalManager.GeneralManagerDAO;
import dataAccess.generalManager.Report;
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

@SuppressWarnings({ "serial", "unchecked" })
public class GcmDAO
        implements UserDAO, CustomerDAO, EditorDAO, ContentManagerDAO, GeneralManagerDAO, SearchDAO, Serializable {
	private static final String PATH_TO_SOURCE_FOLDER = "import\\resources";
	String                      serverHostname;
	int                         serverPortNumber;
	String                      password              = null;
	String                      username              = null;

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
		try {
			ResponseObject responseObject = send(new RequestObject(GcmQuery.getMapDetails, new ArrayList<Object>() {
				{
					add(mapID);
				}
			}, username, password));
			return (Map) responseObject.getResponse().get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public File getMapFile(int mapID) {
		try {
			ResponseObject responseObject = send(new RequestObject(GcmQuery.getMapFile, new ArrayList<Object>() {
				{
					add(mapID);
				}
			}, username, password));
			byte[] fileBytes = (byte[]) responseObject.getResponse().get(0);
			String fileName = "mapImage_" + mapID;
			return downloadImage(fileBytes, fileName);
		} catch (Exception e) {
			return null;
		}
	}

	private File downloadImage(byte[] imageBytes, String fileName) throws IOException {
		String imageType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(imageBytes));
		String imageExtension = imageType.substring(imageType.indexOf('/') + 1); // Content Type format is [file_type] / [file_extension]
		String filePath = PATH_TO_SOURCE_FOLDER + "\\" + fileName + "." + imageExtension;
		try (FileOutputStream stream = new FileOutputStream(filePath)) {
			stream.write(imageBytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new File(filePath);
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
//			System.out.println("connecting to server: ");
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
		try {
			return (int) send(new RequestObject(GcmQuery.addMap, new ArrayList<Object>() {
				{
					add(cityId);
					add(mapDetails);
					add(mapFile);
				}
			}, username, password)).getResponse().get(0);
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int addCity(City city) {
		try {
			return (int) send(new RequestObject(GcmQuery.addCity, new ArrayList<Object>() {
				{
					add(city);
				}
			}, username, password)).getResponse().get(0);
		} catch (Exception e) {
			return -1;
		}
	}

	private void setDetails(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public List<Map> getMapsByCityName(String cityName) {
		return (List<Map>) (Object) send(new RequestObject(GcmQuery.getMapsByCityName, new ArrayList<Object>() {
			{
				add(cityName);
			}
		}, username, password)).getResponse();
	}

	@Override
	public List<Map> getMapsBySiteName(String siteName) {
		return (List<Map>) (Object) send(new RequestObject(GcmQuery.getMapsBySiteName, new ArrayList<Object>() {
			{
				add(siteName);
			}
		}, username, password)).getResponse();
	}

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
		try {
			return (int) send(new RequestObject(GcmQuery.addNewSiteToCity, new ArrayList<Object>() {
				{
					add(cityId);
					add(site);
				}
			}, username, password)).getResponse().get(0);
		} catch (Exception e) {
			return -1;
		}
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
		send(new RequestObject(GcmQuery.deleteMap, new ArrayList<Object>() {
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

//	@Override
//	public boolean purchaseCityOneTime(int mapId, PurchaseDetails purchaseDetails) {
//		try {
//			return (boolean) send(new RequestObject(GcmQuery.purchaseCity, new ArrayList<Object>() {
//				{
//					add(mapId);
//					add(purchaseDetails);
//				}
//			}, username, password)).getResponse().get(0);
//		} catch (Exception e) {
//			return false;
//		}
//	}

	@Override
	public List<City> getActiveCitiesPurchases() {
		return (List<City>) (Object) send(
		        new RequestObject(GcmQuery.getActiveCitiesPurchases, null, username, password)).getResponse();
	}

	@Override
	public User getUserDetails() {
		try {
			return (User) send(new RequestObject(GcmQuery.getUserDetails, null, username, password)).getResponse()
			        .get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public City getCityByMapId(int mapId) {
		try {
			return (City) send(new RequestObject(GcmQuery.getCityByMapId, new ArrayList<Object>() {
				{
					add(mapId);
				}
			}, username, password)).getResponse().get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int addNewTourToCity(int cityId, Tour tour) {
		try {
			return (int) send(new RequestObject(GcmQuery.addNewTourToCity, new ArrayList<Object>() {
				{
					add(cityId);
					add(tour);
				}
			}, username, password)).getResponse().get(0);
		} catch (Exception e) {
			return -1;
		}
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
	public void addExistingSiteToTour(int tourId, int siteId, int siteDurance) {
		send(new RequestObject(GcmQuery.addExistingSiteToTour, new ArrayList<Object>() {
			{
				add(tourId);
				add(siteId);
				add(siteDurance);
			}
		}, username, password)).getResponse();
	}

	@Override
	public void deleteSiteFromTour(int mapId, int siteId) {
		send(new RequestObject(GcmQuery.addExistingSiteToTour, new ArrayList<Object>() {
			{
				add(mapId);
				add(siteId);
			}
		}, username, password)).getResponse().get(0);
	}

	@Override
	public List<Site> getCitySites(int cityId) {
		return (List<Site>) (Object) send(new RequestObject(GcmQuery.getCitySites, new ArrayList<Object>() {
			{
				add(cityId);
			}
		}, username, password)).getResponse();
	}

	@Override
	public int tourManager(int cityId, Tour tour) {
		int tourId = tour.getId();
		// check if tour existing if so add new tour
		if (tour.getId() == -1) {
			tourId = addNewTourToCity(cityId, tour);
		}

		// add places to tour
		List<Site> sitesList = tour.getSites();
		List<Integer> sitesTimeEstimationList = tour.getSitesTimeToVisit();
		int numberOfLastAddedPlaces = tour.getNumberOfLastAddedPlaces();
		int startingIndex = 0;
		if (numberOfLastAddedPlaces != 0) {
			startingIndex = sitesList.size() - numberOfLastAddedPlaces;
		}

		for (int i = startingIndex; i < sitesList.size(); i++) {
			addExistingSiteToTour(tourId, sitesList.get(i).getId(), sitesTimeEstimationList.get(i));
		}

		return 0;
	}

	@Override
	public File downloadMap(int mapId) {
		try {
			ResponseObject responseObject = send(new RequestObject(GcmQuery.downloadMap, new ArrayList<Object>() {
				{
					add(mapId);
				}
			}, username, password));
			byte[] fileBytes = (byte[]) responseObject.getResponse().get(0);
			String fileName = "mapImage_" + mapId;
			return downloadImage(fileBytes, fileName);

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean purchaseCity(int cityId, int timeInterval, PurchaseDetails purchaseDetails) {
		try {
			return (boolean) send(new RequestObject(GcmQuery.purchaseMembershipToCity, new ArrayList<Object>() {
				{
					add(cityId);
					add(timeInterval);
					add(purchaseDetails);
				}
			}, username, password)).getResponse().get(0);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean notifyMapView(int mapId) {
		try {
			return (boolean) send(new RequestObject(GcmQuery.notifyMapView, new ArrayList<Object>() {
				{
					add(mapId);
				}
			}, username, password)).getResponse().get(0);
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public String getSavedCreditCard() {
		try {
			return (String) send(
			        new RequestObject(GcmQuery.getSavedCreditCard, new ArrayList<Object>(), username, password))
			                .getResponse().get(0);
		} catch (Exception e) {
			return "No credit card saved.";
		}
	}

	@Override
	public void editCityPrice(int mapId, double newPrice) {
		send(new RequestObject(GcmQuery.editCityPrice, new ArrayList<Object>() {
			{
				add(mapId);
				add(newPrice);
			}
		}, username, password));
	}

	@Override
	public List<City> getCitiesAddEdits() {
		return (List<City>) (Object) send(
		        new RequestObject(GcmQuery.getCitiesAddEdits, new ArrayList<Object>(), username, password))
		                .getResponse();

	}

	@Override
	public List<City> getCitiesUpdateEdits() {
		return (List<City>) (Object) send(
		        new RequestObject(GcmQuery.getCitiesUpdateEdits, new ArrayList<Object>(), username, password))
		                .getResponse();

	}

	@Override
	public List<City> getCitiesDeleteEdits() {
		return (List<City>) (Object) send(
		        new RequestObject(GcmQuery.getCitiesDeleteEdits, new ArrayList<Object>(), username, password))
		                .getResponse();

	}

	@Override
	public List<SiteSubmission> getSiteSubmissions() {
		return (List<SiteSubmission>) (Object) send(
		        new RequestObject(GcmQuery.getSiteSubmissions, new ArrayList<Object>(), username, password))
		                .getResponse();
	}

	@Override
	public List<MapSubmission> getMapSubmissions() {
		return (List<MapSubmission>) (Object) send(
		        new RequestObject(GcmQuery.getMapSubmissions, new ArrayList<Object>(), username, password))
		                .getResponse();
	}

	@Override
	public List<TourSubmission> getTourSubmissions() {
		return (List<TourSubmission>) (Object) send(
		        new RequestObject(GcmQuery.getTourSubmissions, new ArrayList<Object>(), username, password))
		                .getResponse();
	}

	@Override
	public void actionCityEdit(CitySubmission citySubmission, boolean action) {
		send(new RequestObject(GcmQuery.actionCityEdit, new ArrayList<Object>() {
			{
				add(citySubmission);
				add(action);
			}
		}, username, password));

	}

	@Override
	public List<User> actionMapEdit(MapSubmission citySubmission, boolean action) {
		return (List<User>) (Object) send(new RequestObject(GcmQuery.actionMapEdit, new ArrayList<Object>() {
			{
				add(citySubmission);
				add(action);
			}
		}, username, password)).getResponse();

	}

	@Override
	public void actionTourEdit(TourSubmission citySubmission, boolean action) {
		send(new RequestObject(GcmQuery.actionTourEdit, new ArrayList<Object>() {
			{
				add(citySubmission);
				add(action);
			}
		}, username, password));

	}

	@Override
	public void actionSiteEdit(SiteSubmission citySubmission, boolean action) {
		send(new RequestObject(GcmQuery.actionSiteEdit, new ArrayList<Object>() {
			{
				add(citySubmission);
				add(action);
			}
		}, username, password));

	}

	@Override
	public void updateCity(int cityId, City city) throws SQLException {
		send(new RequestObject(GcmQuery.updateCity, new ArrayList<Object>() {
			{
				add(cityId);
				add(city);
			}
		}, username, password));

	}

	@Override
	public void deleteCityEdit(int cityId) throws SQLException {
		send(new RequestObject(GcmQuery.deleteCityEdit, new ArrayList<Object>() {
			{
				add(cityId);
			}
		}, username, password));

	}

	@Override
	public void UpdateSite(int siteId, Site newSite) throws SQLException {
		send(new RequestObject(GcmQuery.UpdateSite, new ArrayList<Object>() {
			{
				add(siteId);
				add(newSite);
			}
		}, username, password));
	}

	@Override
	public void deleteTourFromMap(int mapId, int tourId) throws SQLException {
		send(new RequestObject(GcmQuery.deleteTourFromMap, new ArrayList<Object>() {
			{
				add(mapId);
				add(tourId);
			}
		}, username, password));
	}

	@Override
	public void deleteTourFromCity(int tourId) throws SQLException {
		send(new RequestObject(GcmQuery.deleteTourFromCity, new ArrayList<Object>() {
			{
				add(tourId);
			}
		}, username, password));
	}

	@Override
	public List<Tour> getCityTours(int cityId) {
		return (List<Tour>) (Object) send(new RequestObject(GcmQuery.getCityTours, new ArrayList<Object>() {
			{
				add(cityId);
			}
		}, username, password)).getResponse();
	}

	@Override
	public void updateTour(int tourId, Tour tour) throws SQLException {
		send(new RequestObject(GcmQuery.updateTour, new ArrayList<Object>() {
			{
				add(tourId);
				add(tour);
			}
		}, username, password));
	}

	@Override
	public void deleteSiteFromCity(int siteId) {
		send(new RequestObject(GcmQuery.deleteSiteFromCity, new ArrayList<Object>() {
			{
				add(siteId);
			}
		}, username, password));
	}

	@Override
	public void updateMap(int mapId, Map newMap) throws SQLException {
		send(new RequestObject(GcmQuery.updateMap, new ArrayList<Object>() {
			{
				add(mapId);
				add(newMap);
			}
		}, username, password));
	}

	@Override
	public void changeCityPrices(int cityId, List<Double> prices) throws SQLException {
		send(new RequestObject(GcmQuery.changeCityPrices, new ArrayList<Object>() {
			{
				add(cityId);
				add(prices);
			}
		}, username, password));

	}

	@Override
	public List<PriceSubmission> getPriceSubmissions() throws SQLException {
		return (List<PriceSubmission>) (Object) send(
		        new RequestObject(GcmQuery.getPriceSubmissions, new ArrayList<Object>(), username, password))
		                .getResponse();

	}

	@Override
	public void approveCityPrice(int cityId, List<Double> prices, boolean approve) throws SQLException {
		send(new RequestObject(GcmQuery.approveCityPrice, new ArrayList<Object>() {
			{
				add(cityId);
				add(prices);
				add(approve);
			}
		}, username, password));
	}

	@Override
	public List<PurchaseHistory> getPurchaseHistory() {
		return (List<PurchaseHistory>) (Object) send(
		        new RequestObject(GcmQuery.getPurchaseHistory, new ArrayList<Object>(), username, password))
		                .getResponse();
	}

	@Override
	public City getCity(int cityId) {
		try {
			return (City) send(new RequestObject(GcmQuery.getCity, new ArrayList<Object>() {
				{
					add(cityId);
				}
			}, username, password)).getResponse().get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Report getCityReport(Date startDate, Date endDate, int cityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Report> getSystemReport(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
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
	public Tour getTour(int tourId) throws SQLException {
		try {
			ResponseObject responseObject = send(new RequestObject(GcmQuery.getTour, new ArrayList<Object>() {
				{
					add(tourId);
				}
			}, username, password));
			return (Tour) responseObject.getResponse().get(0);
		} catch (Exception e) {
			return null;
		}
	}
}
