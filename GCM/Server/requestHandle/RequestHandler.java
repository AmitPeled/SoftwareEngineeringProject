package requestHandle;

import request.RequestObject;
import response.ResponseObject;
import users.User;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataAccess.users.PurchaseDetails;
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
					gcmDataExecutor.deleteMapEdit((int) listObjectReceived.get(0));
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
				// case purchaseCity:
				// listToSend.add(gcmDataExecutor.purchaseCityOneTime(cityId, purchaseDetails,
				// username));
				// break;
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
				case notifyMapView:
					gcmDataExecutor.notifyMapView((int) listObjectReceived.get(0));
					break;
				case purchaseMembershipToCity:
					listToSend.add(gcmDataExecutor.purchaseMembershipToCity((int) listObjectReceived.get(0),
							(int) listObjectReceived.get(1), (PurchaseDetails) listObjectReceived.get(2), username));
					break;
				case deleteSiteFromMap:
					gcmDataExecutor.deleteSiteFromMap((int) listObjectReceived.get(0), (int) listObjectReceived.get(1));
					break;
				case deleteCity:
					gcmDataExecutor.deleteCity((int) listObjectReceived.get(0));
					break;
				case actionCityAddEdit:
					gcmDataExecutor.actionCityAddEdit((City) listObjectReceived.get(0),
							(boolean) listObjectReceived.get(1));
					break;
				case actionCityDeleteEdit:
					gcmDataExecutor.actionCityDeleteEdit((City) listObjectReceived.get(0),
							(boolean) listObjectReceived.get(1));
					break;
				case actionCityUpdateEdit:
					gcmDataExecutor.actionCityUpdateEdit((City) listObjectReceived.get(0),
							(boolean) listObjectReceived.get(1));
					break;
				case actionMapAddEdit:
					gcmDataExecutor.actionMapAddEdit((Map) listObjectReceived.get(0),
							(boolean) listObjectReceived.get(1));
					break;
				case actionMapDeleteEdit:
					gcmDataExecutor.actionMapDeleteEdit((Map) listObjectReceived.get(0),
							(boolean) listObjectReceived.get(1));
					break;
				case actionMapUpdateEdit:
					gcmDataExecutor.actionMapUpdateEdit((Map) listObjectReceived.get(0),
							(boolean) listObjectReceived.get(1));
					break;
				case actionSiteAddEdit:
					gcmDataExecutor.actionSiteAddEdit((Site) listObjectReceived.get(0),
							(boolean) listObjectReceived.get(1));
					break;
				case actionSiteDeleteEdit:
					gcmDataExecutor.actionSiteDeleteEdit((Site) listObjectReceived.get(0),
							(boolean) listObjectReceived.get(1));
					break;
				case actionSiteUpdateEdit:
					gcmDataExecutor.actionSiteUpdateEdit((Site) listObjectReceived.get(0),
							(boolean) listObjectReceived.get(1));
					break;
				case editCityPrice:
					gcmDataExecutor.editCityPrice((int) listObjectReceived.get(0), (double) listObjectReceived.get(1));
					break;
				case getCitiesAddEdits:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getCitiesAddEdits();
					break;
				case getCitiesDeleteEdits:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getCitiesDeleteEdits();
					break;
				case getCitiesObjectAddedTo:
					listToSend = (List<Object>) (Object) gcmDataExecutor
							.getCitiesObjectAddedTo((int) listObjectReceived.get(0));
					break;
				case getCitiesUpdateEdits:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getCitiesUpdateEdits();
					break;
				case getMapsAddEdits:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getMapsAddEdits();
					break;
				case getMapsDeleteEdits:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getMapsDeleteEdits();
					break;
				case getMapsObjectAddedTo:
					listToSend = (List<Object>) (Object) gcmDataExecutor
							.getMapsObjectAddedTo((int) listObjectReceived.get(0));
					break;
				case getMapsUpdateEdits:
					listToSend = (List<Object>) (Object) gcmDataExecutor
							.getMapsObjectAddedTo((int) listObjectReceived.get(0));
					break;
				case getSavedCreditCard:
					listToSend.add(gcmDataExecutor.getSavedCreditCard(username));
					break;
				case getSitesAddEdits:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getSitesAddEdits();
					break;
				case getSitesDeleteEdits:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getSitesDeleteEdits();

					break;
				case getSitesUpdateEdits:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getSitesUpdateEdits();
					break;
				case getToursAddEdits:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getToursAddEdits();
					break;
				case getToursDeleteEdits:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getToursDeleteEdits();
					break;
				case getToursObjectAddedTo:
					listToSend = (List<Object>) (Object) gcmDataExecutor
							.getToursObjectAddedTo((int) listObjectReceived.get(0));
					break;
				case getToursUpdateEdits:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getToursUpdateEdits();
					break;

				default:
					break;
				}
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
