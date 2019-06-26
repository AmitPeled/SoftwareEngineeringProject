package gcmDataAccess;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import com.sun.xml.internal.ws.api.Component;

import approvalReports.ActionTaken;
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
import dataAccess.search.CityMaps;
import dataAccess.search.SearchDAO;
import dataAccess.users.PurchaseDetails;
import dataAccess.users.UserDAO;
import dataAccess.imageDownload.ImageDownloader;
import database.serverObjects.MapSubmissionContent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mapFetchment.JFrameMapChooser;
import maps.City;
import maps.Coordinates;
import maps.Map;
import maps.Site;
import maps.Tour;
import queries.GcmQuery;
import queries.RequestState;
import request.RequestObject;
import response.ResponseObject;
import users.User;
import users.UserReport;

//import javax.net.ssl.SSLSocket;
//import javax.net.ssl.SSLSocketFactory;

@SuppressWarnings({ "serial", "unchecked" })
public class GcmDAO
          implements UserDAO, CustomerDAO, EditorDAO, ContentManagerDAO, GeneralManagerDAO, SearchDAO, Serializable {
	 private String serverHostname;
	 private int    serverPortNumber;
	 private String password = null;
	 private String username = null;

	 private static final String PATH_TO_SOURCE_FOLDER = "import\\resources\\system-files";

	 public GcmDAO(String host, int port) {
		  serverHostname = host;
		  serverPortNumber = port;
	 }

	 public static void main(String[] args) {
		  GcmDAO gcmDAO = new GcmDAO();
		  gcmDAO.login("editor", "editor");
		  City city = new City("Yokne'am", "Yokne'am is a small city");
		  Map map = new Map("Aroma café", "best coffee seller in Israel", 1, 1, new Coordinates());
		  int cityId = gcmDAO.addCity(city);
		  File mapFile = JFrameMapChooser.chooseFromDialog("import//resources");
		  if (mapFile != null) {
			   int mapId = gcmDAO.addMapToCity(cityId, map, mapFile);
			   map = new Map(mapId, "Aroma café", "best coffee seller in Israel", 1, 1, new Coordinates(), -1,
			             new ArrayList<>(), new ArrayList<>());
			   MapSubmission mapSubmission = new MapSubmission(cityId, map, mapFile, ActionTaken.ADD);
			   gcmDAO.actionMapEdit(mapSubmission, true);
			   gcmDAO.getMapFile(mapId);
		  }
	 }

	 static public class JavaFXFileDialogChooser extends Application {
		  public void launchChoose() {
			   launch(new String[0]);
		  }

		  File selectedFile = null;

		  @Override
		  public void start(Stage primaryStage) {
			   primaryStage.setTitle("JavaFX App");
			   FileChooser fileChooser = new FileChooser();
			   selectedFile = fileChooser.showOpenDialog(primaryStage);
			   Thread.currentThread().interrupt();
		  }

	 }

	 static File JavaFXDialogChoose() {
		  GcmDAO.JavaFXFileDialogChooser javaFXFileDialogChooser = new GcmDAO.JavaFXFileDialogChooser();
		  javaFXFileDialogChooser.launchChoose();
		  return javaFXFileDialogChooser.selectedFile;
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
	 public Site getSiteById(int siteId) {
		  try {
			   ResponseObject responseObject = send(new RequestObject(GcmQuery.getSiteById, new ArrayList<Object>() {
					{
						 add(siteId);
					}
			   }, username, password));
			   return (Site) responseObject.getResponse().get(0);
		  } catch (Exception e) {
			   return null;
		  }
	 }

	 @Override
	 public File getMapFile(int mapID) {
		  try {
			   byte[] fileBytes = (byte[]) send(new RequestObject(GcmQuery.getMapFile, new ArrayList<Object>() {
					{
						 add(mapID);
					}
			   }, username, password)).getResponse().get(0);
			   String filePath = PATH_TO_SOURCE_FOLDER + "\\mapImage_" + mapID;
			   return ImageDownloader.downloadImage(fileBytes, filePath);
		  } catch (Exception e) {
			   return null;
		  }
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

	 @Override
	 public RequestState updateUser(User user) {
		  ResponseObject responseObject = send(
		            new RequestObject(GcmQuery.editUsersWithoutNewPassword, new ArrayList<Object>() {
			             {
					          add(user);
					          add(password);
			             }
		            }, username, password));
		  RequestState requestState = responseObject.getRequestState();
		  if (isProperUser(requestState))
			   setDetails(user.getUsername(), password);
		  return requestState;
	 }

	 @Override
	 public RequestState updateUser(User user, String newPassword) {
		  ResponseObject responseObject = send(
		            new RequestObject(GcmQuery.editUsersWithNewPassword, new ArrayList<Object>() {
			             {
					          add(user);
					          add(newPassword);
			             }
		            }, username, password));
		  RequestState requestState = responseObject.getRequestState();
		  if (isProperUser(requestState))
			   setDetails(user.getUsername(), newPassword);
		  return requestState;
	 }

	 private boolean isProperUser(RequestState requestState) {
		  return requestState == RequestState.customer || requestState == RequestState.editor
		            || requestState == RequestState.contentManager || requestState == RequestState.generalManager;
	 }

	 private ResponseObject send(RequestObject req) { // false for error, true otherwise
		  System.out.println("Connecting to host " + serverHostname + " on port " + serverPortNumber + ".");
		  // SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		  // SSLSocket serverSocket = null;
		  Socket serverSocket = null;
		  ObjectInputStream in = null;
		  ObjectOutputStream out = null;
		  if (req == null) {
			   System.err.println("Error! no request sent.");
			   return null;
		  }
		  try {
			   // System.out.println("connecting to server: ");
			   serverSocket = new Socket(serverHostname, serverPortNumber);
			   // factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			   // serverSocket = (SSLSocket) factory.createSocket(serverHostname, port);
			   // serverSocket.startHandshake();
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

		  // System.out.println("Sending data to server..");
		  try {
			   out.writeObject(req);
		  } catch (IOException e1) {
			   System.err.println("error in sending data to server");
			   System.err.println(e1.getMessage());
		  }
		  // System.out.println("data sent. receiving data:");
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
	 public CityMaps getMapsByCityName(String cityName) {
		  try {
			   return (CityMaps) send(new RequestObject(GcmQuery.getMapsByCityName, new ArrayList<Object>() {
					{
						 add(cityName);
					}
			   }, username, password)).getResponse().get(0);
		  } catch (Exception e) {
			   return null;
		  }
	 }

	 @Override
	 public CityMaps getMapsBySiteName(String siteName) {
		  try {
			   return (CityMaps) send(new RequestObject(GcmQuery.getMapsBySiteName, new ArrayList<Object>() {
					{
						 add(siteName);
					}
			   }, username, password)).getResponse().get(0);
		  } catch (Exception e) {
			   return null;
		  }
	 }

	 @Override
	 public CityMaps getMapsByDescription(String description) {
		  try {
			   return (CityMaps) send(new RequestObject(GcmQuery.getMapsByDescription, new ArrayList<Object>() {
					{
						 add(description);
					}
			   }, username, password)).getResponse().get(0);
		  } catch (Exception e) {
			   return null;
		  }
	 }

	 @Override
	 public CityMaps getMapsBySiteAndCityNames(String cityName, String siteName) {
		  try {
			   return (CityMaps) send(new RequestObject(GcmQuery.getMapsBySiteAndCityNames, new ArrayList<Object>() {
					{
						 add(cityName);
						 add(siteName);
					}
			   }, username, password)).getResponse().get(0);
		  } catch (Exception e) {
			   return null;
		  }
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

	 public int deleteMap(int contentId) {
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

	 // @Override
	 // public boolean purchaseCityOneTime(int mapId, PurchaseDetails
	 // purchaseDetails) {
	 // try {
	 // return (boolean) send(new RequestObject(GcmQuery.purchaseCity, new
	 // ArrayList<Object>() {
	 // {
	 // add(mapId);
	 // add(purchaseDetails);
	 // }
	 // }, username, password)).getResponse().get(0);
	 // } catch (Exception e) {
	 // return false;
	 // }
	 // }

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
		  send(new RequestObject(GcmQuery.deleteSiteFromTour, new ArrayList<Object>() {
			   {
					add(mapId);
					add(siteId);
			   }
		  }, username, password)).getResponse();
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
	 public int tourManager(int cityId, Tour tour, List<Site> tempSitesList, List<Integer> tempSitesTimeEstimation) {
		  int tourId = tour.getId();

		  // check if tour existing if so add new tour
		  if (tour.getId() == -1) {
			   tourId = addNewTourToCity(cityId, tour);
		  } else {
			   updateTour(tour.getId(), tour);
		  }
		  System.out.println(tour.getSites());
		  // add places to tour
		  for (int i = 0; i < tempSitesList.size(); i++) {
			   addExistingSiteToTour(tourId, tempSitesList.get(i).getId(), tempSitesTimeEstimation.get(i));
		  }

		  return 0;
	 }

	 @Override
	 public File downloadMap(int mapId) {
		  try {
			   byte[] fileBytes = (byte[]) send(new RequestObject(GcmQuery.downloadMap, new ArrayList<Object>() {
					{
						 add(mapId);
					}
			   }, username, password)).getResponse().get(0);
			   String filePath = PATH_TO_SOURCE_FOLDER + "\\mapImage_" + mapId;
			   return ImageDownloader.downloadImage(fileBytes, filePath);
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
	 public List<CitySubmission> getCitySubmissions() {
		  return (List<CitySubmission>) (Object) send(
		            new RequestObject(GcmQuery.getCitySubmissions, new ArrayList<Object>(), username, password))
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
		  List<MapSubmissionContent> serverMapSubmissions = (List<MapSubmissionContent>) (Object) send(
		            new RequestObject(GcmQuery.getMapSubmissions, new ArrayList<Object>(), username, password))
		                      .getResponse();
		  List<MapSubmission> mapSubmissions = new ArrayList<>();
		  String basePath = PATH_TO_SOURCE_FOLDER + "\\mapImage_";
		  serverMapSubmissions.forEach((serverMapSubmission) -> {
			   try {
					String filePath = basePath + serverMapSubmission.getMap().getId();
					mapSubmissions.add(serverMapSubmission.getMapSubmission(filePath));
			   } catch (Exception e) {
					e.printStackTrace();
			   }
		  });
		  return mapSubmissions;
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
	 public void updateCity(int cityId, City city) {
		  send(new RequestObject(GcmQuery.updateCity, new ArrayList<Object>() {
			   {
					add(cityId);
					add(city);
			   }
		  }, username, password));

	 }

	 @Override
	 public void deleteCityEdit(int cityId) {
		  send(new RequestObject(GcmQuery.deleteCityEdit, new ArrayList<Object>() {
			   {
					add(cityId);
			   }
		  }, username, password));

	 }

	 @Override
	 public void UpdateSite(int siteId, Site newSite) {
		  send(new RequestObject(GcmQuery.UpdateSite, new ArrayList<Object>() {
			   {
					add(siteId);
					add(newSite);
			   }
		  }, username, password));
	 }

	 @Override
	 public void deleteTourFromMap(int mapId, int tourId) {
		  send(new RequestObject(GcmQuery.deleteTourFromMap, new ArrayList<Object>() {
			   {
					add(mapId);
					add(tourId);
			   }
		  }, username, password));
	 }

	 @Override
	 public void deleteTourFromCity(int tourId) {
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
	 public void updateTour(int tourId, Tour tour) {
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
	 public void updateMap(int mapId, Map newMap) {
		  send(new RequestObject(GcmQuery.updateMap, new ArrayList<Object>() {
			   {
					add(mapId);
					add(newMap);
			   }
		  }, username, password));
	 }

	 @Override
	 public void changeCityPrices(int cityId, List<Double> prices) {
		  send(new RequestObject(GcmQuery.changeCityPrices, new ArrayList<Object>() {
			   {
					add(cityId);
					add(prices);
			   }
		  }, username, password));

	 }

	 @Override
	 public List<PriceSubmission> getPriceSubmissions() {
		  return (List<PriceSubmission>) (Object) send(
		            new RequestObject(GcmQuery.getPriceSubmissions, new ArrayList<Object>(), username, password))
		                      .getResponse();

	 }

	 @Override
	 public void approveCityPrice(int cityId, List<Double> prices, boolean approve) {
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
	 public Report getCityReport(Date startDate, Date endDate, String cityName) {
		  try {
			   return (Report) send(new RequestObject(GcmQuery.getCityReport, new ArrayList<Object>() {
					{
						 add(startDate);
						 add(endDate);
						 add(cityName);
					}
			   }, username, password)).getResponse().get(0);
		  } catch (Exception e) {
			   return null;
		  }
	 }

	 @Override
	 public List<Report> getSystemReport(Date startDate, Date endDate) {
		  return (List<Report>) (Object) send(new RequestObject(GcmQuery.getSystemReport, new ArrayList<Object>() {
			   {
					add(startDate);
					add(endDate);
			   }
		  }, username, password)).getResponse();
	 }

	 @Override
	 public UserReport getReportOnUser(java.sql.Date startDate, java.sql.Date endDate, String username) {
		  try {
			   return (UserReport) send(new RequestObject(GcmQuery.getUserReport, new ArrayList<Object>() {
					{
						 add(startDate);
						 add(endDate);
						 add(username);
					}
			   }, username, password)).getResponse().get(0);
		  } catch (Exception e) {
			   return null;
		  }
	 }

	 @Override
	 public boolean repurchaseSubscriptionToCity(int cityId) {
		  try {
			   ResponseObject responseObject = send(
			             new RequestObject(GcmQuery.repurchaseSubsriptionToCity, new ArrayList<Object>() {
					          {
						           add(cityId);
					          }
			             }, username, password));
			   return (boolean) responseObject.getResponse().get(0);
		  } catch (Exception e) {
			   return false;
		  }
	 }

	 @Override
	 public boolean ownActiveSubscription(int cityId) {
		  try {
			   ResponseObject responseObject = send(
			             new RequestObject(GcmQuery.ownActiveSubsicription, new ArrayList<Object>() {
					          {
						           add(cityId);
					          }
			             }, username, password));
			   return (boolean) responseObject.getResponse().get(0);
		  } catch (Exception e) {
			   return false;
		  }
	 }

	 @Override
	 public Tour getTour(int tourId) {
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

	 @Override
	 public boolean hadPurchasedCityInPast(int cityId) {
		  try {
			   ResponseObject responseObject = send(
			             new RequestObject(GcmQuery.hadPurchasedCityInPast, new ArrayList<Object>() {
					          {
						           add(cityId);
					          }
			             }, username, password));
			   return (boolean) responseObject.getResponse().get(0);
		  } catch (Exception e) {
			   return false;
		  }
	 }

}
