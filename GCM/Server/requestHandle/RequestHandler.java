package requestHandle;

import request.RequestObject;
import response.ResponseObject;
import users.User;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.execution.IGcmDataExecute;
import maps.City;
import maps.Map;
import maps.Site;
import maps.Tour;
import queries.GcmQuery;
import queries.RequestState;

public class RequestHandler implements IHandleRequest {
	IGcmDataExecute gcmDataExecutor;

	public RequestHandler(IGcmDataExecute gcmDataExecutor) {
		this.gcmDataExecutor = gcmDataExecutor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseObject handleRequest(RequestObject requestObject) {
		List<Object> listToSend = new ArrayList<Object>();
		GcmQuery query = requestObject.getQuery();
		String username = requestObject.getUname(), password = requestObject.getPass();
		RequestState requestState = null;
		try {
			RequestState userType = gcmDataExecutor.verifyUser(username, password);
			List<Object> listObjectReceived = requestObject.getObjects();
			if (verifyPrivilege(userType, query)) {

//				if (userType == UserType.notLogged || gcmDataExecutor.verifyUser(/* userType, */username, password)) {
				switch (requestObject.getQuery()) {
				case addCustomer:
					requestState = verifyDetailsConstrains(username, password);
					if (requestState == RequestState.customer
							&& !gcmDataExecutor.addUser((String) listObjectReceived.get(0),
									(String) listObjectReceived.get(1), (User) listObjectReceived.get(2))) {
						requestState = RequestState.usernameAlreadyExists;
					}
					break;
				case verifyUser:
					requestState = gcmDataExecutor.verifyUser(username, password);
					break;
				case addMap:
					listToSend.add(gcmDataExecutor.addMapToCity((int) listObjectReceived.get(0),
							(Map) listObjectReceived.get(1), (File) listObjectReceived.get(2)));
					break;

				case addNewSiteToCity:
					listToSend.add(gcmDataExecutor.addNewSiteToCity((int) listObjectReceived.get(0),
							(Site) listObjectReceived.get(1)));
					break;

				case addExistingSiteToMap:
					gcmDataExecutor.addExistingSiteToMap((int) listObjectReceived.get(0),
							(int) listObjectReceived.get(1));
					break;
				case getUserDetails:
					listToSend.add(gcmDataExecutor.getUserDetails(username));
					break;
				case getMapDetails:
					listToSend.add(gcmDataExecutor.getMapDetails((int) listObjectReceived.get(0)));
					break;
				case getMapFile:
					listToSend.add(gcmDataExecutor.getMapFile((int) listObjectReceived.get(0)));
					break;
				case deleteContent:
					gcmDataExecutor.deleteMap((int) listObjectReceived.get(0));
					break;
				case addCity:
					listToSend.add(gcmDataExecutor.addCity((City) listObjectReceived.get(0)));
					break;
//				case addCityWithInitialMap:
//					listToSend.add(gcmDataExecutor.addCityWithInitialMap((City) listObjectReceived.get(0),
//							(Map) listObjectReceived.get(1), (File) listObjectReceived.get(2)));
//					break;
				case getMapsByCityName:
					listToSend = (List<Object>) (Object) gcmDataExecutor
							.getMapsByCityName((String) listObjectReceived.get(0));
					break;
				case getMapsBySiteName:
					listToSend = (List<Object>) (Object) gcmDataExecutor
							.getMapsBySiteName((String) listObjectReceived.get(0));
					break;
				case getMapsByDescription:
					listToSend = (List<Object>) (Object) gcmDataExecutor
							.getMapsByDescription((String) listObjectReceived.get(0));
					break;
				case downloadMap:
					listToSend.add(gcmDataExecutor.downloadMap((int) listObjectReceived.get(0), username));
					break;
				case getCityByMapId:
					listToSend.add(gcmDataExecutor.getCityByMapId((int) listObjectReceived.get(0)));
					break;
				case getPurchasedMaps:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getPurchasedMaps(username);
					break;
				case purchaseCity:
					listToSend.add(gcmDataExecutor.purchaseMap(username));
					break;
				case addExistingSiteToTour:
					gcmDataExecutor.addExistingSiteToTour((int) listObjectReceived.get(0),
							(int) listObjectReceived.get(1), (int) listObjectReceived.get(2));
					break;
				case addExistingTourToMap:
					gcmDataExecutor.addExistingTourToMap((int) listObjectReceived.get(0),
							(int) listObjectReceived.get(1));
					break;
				case addNewTourToCity:
					listToSend.add(gcmDataExecutor.addNewTourToCity((int) listObjectReceived.get(0),
							(Tour) listObjectReceived.get(1)));
					break;
				case getCitySites:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getCitySites((int) listObjectReceived.get(0));
					break;
				case deleteSiteFromMap:
					break;
				case deleteCity:
					break;
				default:
					break;
				}
//				} else {
//					requestState = RequestState.wrongDetails;
//				}
			} else
				requestState = RequestState.wrongDetails;

		} catch (SQLException e) {
			requestState = RequestState.somethingWrongHappend;
			System.err.println("db exception");
			System.err.println(e.getMessage());

		} catch (Exception e) {
			System.err.println("server exception");
			System.err.println(e.getMessage());
		}

		return new ResponseObject(requestState, listToSend);
	}

	private boolean verifyPrivilege(RequestState userType, GcmQuery query) {
		switch (query) {
		case addCustomer:
			return true;
		case verifyUser:
			return true;
		case getMapDetails:
			return true;
		case getMapsByCityName:
			return true;
		case getMapsBySiteName:
			return true;
		case getMapsByDescription:
			return true;
		case getMapFile:
			return userType == RequestState.editor || userType == RequestState.contentManager;
		case getUserDetails:
			return userType == RequestState.customer || userType == RequestState.editor
					|| userType == RequestState.contentManager;
		case addCity:
			return userType == RequestState.editor || userType == RequestState.contentManager;
		case addExistingSiteToMap:
			return userType == RequestState.editor || userType == RequestState.contentManager;
		case addMap:
			return userType == RequestState.editor || userType == RequestState.contentManager;
		case addNewSiteToCity:
			return userType == RequestState.editor || userType == RequestState.contentManager;
		case deleteCity:
			return userType == RequestState.editor || userType == RequestState.contentManager;
		case deleteContent:
			return userType == RequestState.editor || userType == RequestState.contentManager;
		case deleteSiteFromMap:
			return userType == RequestState.editor || userType == RequestState.contentManager;
		case downloadMap:
			return userType == RequestState.customer;
		case getCityByMapId:
			return userType == RequestState.editor || userType == RequestState.contentManager;
		case getPurchasedMaps:
			return userType == RequestState.customer;
		case purchaseCity:
			return userType == RequestState.customer;
		case updateContent:
			return userType == RequestState.editor || userType == RequestState.contentManager;
		case addNewTourToCity:
			return userType == RequestState.editor || userType == RequestState.contentManager;
		case addExistingSiteToTour:
			return userType == RequestState.editor || userType == RequestState.contentManager;
		case addExistingTourToMap:
			return userType == RequestState.editor || userType == RequestState.contentManager;
		default:
			return false;
		}
	}

	private RequestState verifyDetailsConstrains(String username, String password) {
		// TODO Auto-generated method stub
		return RequestState.customer;
	}

}
