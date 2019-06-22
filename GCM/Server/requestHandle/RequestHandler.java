package requestHandle;

import request.RequestObject;
import response.ResponseObject;
import users.User;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import approvalReports.cityApprovalReports.CitySubmission;
import approvalReports.mapApprovalReports.MapSubmission;
import approvalReports.sitesApprovalReports.SiteSubmission;
import approvalReports.tourApprovalReports.TourSubmission;
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
				case deleteMap:
					gcmDataExecutor.deleteMapEdit((int) listObjectReceived.get(0));
					break;
				case addCity:
					listToSend.add(gcmDataExecutor.addCity((City) listObjectReceived.get(0)));
					break;
				case getSiteSubmissions:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getSiteSubmissions();
				case getMapSubmissions:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getMapSubmissions();
					break;
				case getTourSubmissions:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getTourSubmissions();
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
				case getActiveCitiesPurchases:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getPurchasedMaps(username);
					break;
				case purchaseCity:
					listToSend.add(gcmDataExecutor.purchaseCity((int) listObjectReceived.get(0),
							(int) listObjectReceived.get(1), (PurchaseDetails) listObjectReceived.get(2), username));
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
				case notifyMapView:
					listToSend.add(gcmDataExecutor.notifyMapView(username, (int) listObjectReceived.get(0)));
					break;
				case purchaseMembershipToCity:
					listToSend.add(gcmDataExecutor.purchaseCity((int) listObjectReceived.get(0),
							(int) listObjectReceived.get(1), (PurchaseDetails) listObjectReceived.get(2), username));
					break;
				case deleteSiteFromMap:
					gcmDataExecutor.deleteSiteFromMapEdit((int) listObjectReceived.get(0), (int) listObjectReceived.get(1));
					break;
				case deleteCity:
					gcmDataExecutor.deleteCityEdit((int) listObjectReceived.get(0));
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
				case actionTourEdit:
					gcmDataExecutor.actionTourEdit((TourSubmission) listObjectReceived.get(0),
							(boolean) listObjectReceived.get(1));
					break;
				case actionSiteEdit:
					gcmDataExecutor.actionSiteEdit((SiteSubmission) listObjectReceived.get(0),
							(boolean) listObjectReceived.get(1));
					break;
				case actionMapEdit:
					listToSend = (List<Object>) (Object) gcmDataExecutor.actionMapEdit(
							(MapSubmission) listObjectReceived.get(0), (boolean) listObjectReceived.get(1));
					break;
				case actionCityEdit:
					gcmDataExecutor.actionCityEdit((CitySubmission) listObjectReceived.get(0),
							(boolean) listObjectReceived.get(1));
					break;
				case deleteCityEdit:
					gcmDataExecutor.deleteCityEdit((int) listObjectReceived.get(0));
					break;
				case deleteSiteFromCity:
					gcmDataExecutor.deleteSiteFromCity((int) listObjectReceived.get(0));
					break;
				case deleteTourFromCity:
					gcmDataExecutor.deleteTourFromCity((int) listObjectReceived.get(0));
					break;
				case deleteTourFromMap:
					gcmDataExecutor.deleteTourFromMapEdit((int) listObjectReceived.get(0), (int) listObjectReceived.get(1));
					break;
				case getCityTours:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getCityTours((int) listObjectReceived.get(0));
					break;
				case updateCity:
					gcmDataExecutor.updateCity((int) listObjectReceived.get(0), (City) listObjectReceived.get(1));
					break;
				case updateMap:
					gcmDataExecutor.updateMap((int) listObjectReceived.get(0), (Map) listObjectReceived.get(1));
					break;
				case UpdateSite:
					gcmDataExecutor.UpdateSite((int) listObjectReceived.get(0), (Site) listObjectReceived.get(1));
					break;
				case updateTour:
					gcmDataExecutor.updateTour((int) listObjectReceived.get(0), (Tour) listObjectReceived.get(1));
					break;
				case approveCityPrice:
					gcmDataExecutor.approveCityPrice((int) listObjectReceived.get(0),
							(List<Double>) listObjectReceived.get(1), (boolean) listObjectReceived.get(2));
					break;
				case changeCityPrices:
					gcmDataExecutor.changeCityPrices((int) listObjectReceived.get(0),
							(List<Double>) listObjectReceived.get(1));
					break;
				case getPriceSubmissions:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getPriceSubmissions();
					break;
				case getPurchaseHistory:
					listToSend = (List<Object>) (Object) gcmDataExecutor.getPurchaseHistory(username);
					break;
				case getCity:
					listToSend.add(gcmDataExecutor.getCityById((int) listObjectReceived.get(0)));
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
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("server exception. client query=" + query);
			System.err.println(e.getMessage());
			e.printStackTrace();

		}

		return new ResponseObject(requestState, listToSend);
	}

	private boolean verifyPrivilege(RequestState userType, GcmQuery query) {
    //everyone is privileged for now
		int a = 0;
		if (a == 0) // to eliminate "unreachable code" warning
			return true;
//		List<GcmQuery> everyone = Arrays.asList( GcmQuery.addCustomer, GcmQuery.verifyUser, GcmQuery.getMapDetails,
//				GcmQuery.getMapsByCityName, GcmQuery.getMapsBySiteName, GcmQuery.getMapsByDescription );
//		List<GcmQuery> customer = Arrays.asList( GcmQuery.getUserDetails, GcmQuery.verifyUser, GcmQuery.downloadMap,
//				GcmQuery.getPurchasedMaps, GcmQuery.getMapsBySiteName, GcmQuery.getMapsByDescription );

		switch (query) {
		case verifyUser:
			return true;
		case notifyMapView:
			return userType == RequestState.customer;
		case getMapFile:
			return userType == RequestState.editor || userType == RequestState.contentManager
					|| userType == RequestState.generalManager;
		case getUserDetails:
			return userType == RequestState.customer || userType == RequestState.editor
					|| userType == RequestState.contentManager || userType == RequestState.generalManager;
		case addCity:
			return userType == RequestState.editor || userType == RequestState.contentManager
					|| userType == RequestState.generalManager;
		case addExistingSiteToMap:
			return userType == RequestState.editor || userType == RequestState.contentManager
					|| userType == RequestState.generalManager;
		case addMap:
			return userType == RequestState.editor || userType == RequestState.contentManager
					|| userType == RequestState.generalManager;
		case addNewSiteToCity:
			return userType == RequestState.editor || userType == RequestState.contentManager
					|| userType == RequestState.generalManager;
		case deleteCity:
			return userType == RequestState.editor || userType == RequestState.contentManager
					|| userType == RequestState.generalManager;
		case deleteMap:
			return userType == RequestState.editor || userType == RequestState.contentManager
					|| userType == RequestState.generalManager;
		case deleteSiteFromMap:
			return userType == RequestState.editor || userType == RequestState.contentManager
					|| userType == RequestState.generalManager;
		case downloadMap:
			return userType == RequestState.customer;
		case getCityByMapId:
			return userType == RequestState.editor || userType == RequestState.contentManager
					|| userType == RequestState.generalManager;
		case getActiveCitiesPurchases:
			return userType == RequestState.customer;
		case purchaseCity:
			return userType == RequestState.customer;
		case addNewTourToCity:
			return userType == RequestState.editor || userType == RequestState.contentManager
					|| userType == RequestState.generalManager;
		case addExistingSiteToTour:
			return userType == RequestState.editor || userType == RequestState.contentManager
					|| userType == RequestState.generalManager;
		case addExistingTourToMap:
			return userType == RequestState.editor || userType == RequestState.contentManager
					|| userType == RequestState.generalManager;
		case actionCityAddEdit:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case actionCityDeleteEdit:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case actionCityUpdateEdit:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case actionMapAddEdit:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case actionMapDeleteEdit:
			return true;
		case actionMapUpdateEdit:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case actionSiteAddEdit:
			return true;
		case actionSiteDeleteEdit:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case actionSiteUpdateEdit:
			return true;
		case addCustomer:
			return true;
		case editCityPrice:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case getCitiesAddEdits:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case getCitiesDeleteEdits:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case getCitiesObjectAddedTo:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case getCitiesUpdateEdits:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case getCitySites:
			return userType == RequestState.editor || userType == RequestState.contentManager
					|| userType == RequestState.generalManager;
		case getMapDetails:
			return userType == RequestState.editor || userType == RequestState.contentManager
					|| userType == RequestState.generalManager;
		case getMapsAddEdits:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case getMapsByCityName:
			return userType == RequestState.editor || userType == RequestState.contentManager
					|| userType == RequestState.generalManager;
		case getMapsByDescription:
			return true;
		case getMapsBySiteName:
			return true;
		case getMapsDeleteEdits:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case getMapsObjectAddedTo:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case getMapsUpdateEdits:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case getSavedCreditCard:
			return userType == RequestState.customer;
		case getSitesAddEdits:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case getSitesDeleteEdits:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case getSitesUpdateEdits:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case getToursAddEdits:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case getToursDeleteEdits:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case getToursObjectAddedTo:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case getToursUpdateEdits:
			return userType == RequestState.contentManager || userType == RequestState.generalManager;
		case purchaseMembershipToCity:
			return userType == RequestState.customer;

		default:
			return false;
		}

	}

	private RequestState verifyDetailsConstrains(String username, String password) {
		// TODO Auto-generated method stub
		return RequestState.customer;
	}

}
