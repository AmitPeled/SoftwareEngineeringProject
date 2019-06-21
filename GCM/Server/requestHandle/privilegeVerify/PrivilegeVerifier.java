package requestHandle.privilegeVerify;

import java.util.Arrays;
import java.util.List;

import queries.GcmQuery;
import queries.RequestState;

public class PrivilegeVerifier {
//	static List<GcmQuery> guestQueries = Arrays.asList(GcmQuery.addCustomer, GcmQuery.verifyUser,
//			GcmQuery.getMapDetails, GcmQuery.getCity, GcmQuery.getCityByMapId, GcmQuery.getMapsByDescription,
//			GcmQuery.getMapsByCityName, GcmQuery.getMapsBySiteName);
//	static List<GcmQuery> customerQueries = Arrays.asList(GcmQuery.getUserDetails, GcmQuery.purchaseSubscriptionToCity,
//			GcmQuery.getActiveSubscriptions, GcmQuery.downloadMap, GcmQuery.getPurchaseHistory,
//			GcmQuery.getSavedCreditCard, GcmQuery.notifyMapView);
//	static List<GcmQuery> editorQueries = Arrays.asList(GcmQuery.addMap, GcmQuery.deleteMap, GcmQuery.getMapFile,
//			GcmQuery.addCity, GcmQuery.deleteCity, GcmQuery.addNewSiteToCity, GcmQuery.addExistingSiteToMap,
//			GcmQuery.deleteSiteFromMap, GcmQuery.addNewTourToCity, GcmQuery.addExistingTourToMap,
//			GcmQuery.addExistingSiteToTour, GcmQuery.getCitySites, GcmQuery.updateMap, GcmQuery.deleteSiteFromCity,
//			GcmQuery.updateTour, GcmQuery.getCityTours, GcmQuery.deleteTourFromCity, GcmQuery.deleteTourFromMap,
//			GcmQuery.UpdateSite, GcmQuery.deleteCityEdit, GcmQuery.updateCity);
//	static List<GcmQuery> contentManagerQueris = Arrays.asList(GcmQuery.getTourSubmissions, GcmQuery.getMapSubmissions,
//			GcmQuery.actionSiteEdit, GcmQuery.actionMapEdit, GcmQuery.actionTourEdit, GcmQuery.actionCityEdit,
//			GcmQuery.getToursDeleteEdits, GcmQuery.getToursUpdateEdits, GcmQuery.getToursAddEdits,
//			GcmQuery.getToursObjectAddedTo, GcmQuery.getCitiesObjectAddedTo, GcmQuery.getMapsObjectAddedTo,
//			GcmQuery.getSitesAddEdits, GcmQuery.getCitiesDeleteEdits, GcmQuery.getCitiesUpdateEdits,
//			GcmQuery.getCitiesAddEdits, GcmQuery.getSitesDeleteEdits, GcmQuery.getSitesUpdateEdits,
//			GcmQuery.getMapsDeleteEdits, GcmQuery.getMapsUpdateEdits, GcmQuery.getMapsAddEdits,
//			GcmQuery.actionSiteDeleteEdit, GcmQuery.actionSiteUpdateEdit, GcmQuery.actionSiteAddEdit,
//			GcmQuery.actionCityDeleteEdit, GcmQuery.actionCityUpdateEdit, GcmQuery.actionCityAddEdit,
//			GcmQuery.actionMapAddEdit, GcmQuery.actionMapUpdateEdit, GcmQuery.actionMapDeleteEdit,
//			GcmQuery.editCityPrice, GcmQuery.getSiteSubmissions, GcmQuery.changeCityPrices);
//
//	static List<GcmQuery> managerQueris = Arrays.asList(GcmQuery.getPriceSubmissions, GcmQuery.approveCityPrice);
	public static boolean verifyPrivilege(GcmQuery gcmQuery, RequestState requestState) {
		return true;
	}
}
