package queries;

import java.io.Serializable;

public enum GcmQuery implements Serializable {

	 addCustomer, verifyUser, addMap, deleteMap, getMapDetails, getMapFile, addCity, deleteCity, addNewSiteToCity,
	 addExistingSiteToMap, deleteSiteFromMap, getMapsByCityName, getMapsBySiteName, getMapsByDescription,
	 getUserDetails, getCityByMapId, purchaseCity, getActiveCitiesPurchases, downloadMap, addNewTourToCity,
	 addExistingTourToMap, addExistingSiteToTour, getCitySites, purchaseMembershipToCity, getToursDeleteEdits,
	 getToursUpdateEdits, getToursAddEdits, getToursObjectAddedTo, getCitiesObjectAddedTo, getMapsObjectAddedTo,
	 getSitesAddEdits, getCitiesDeleteEdits, getCitiesUpdateEdits, getCitiesAddEdits, getSitesDeleteEdits,
	 getSitesUpdateEdits, getMapsDeleteEdits, getMapsUpdateEdits, getMapsAddEdits, actionSiteDeleteEdit,
	 actionSiteUpdateEdit, actionSiteAddEdit, actionCityDeleteEdit, actionCityUpdateEdit, actionCityAddEdit,
	 actionMapAddEdit, actionMapUpdateEdit, actionMapDeleteEdit, getSavedCreditCard, notifyMapView, editCityPrice,
	 getSiteSubmissions, getTourSubmissions, getMapSubmissions, actionSiteEdit, actionMapEdit, actionTourEdit,
	 actionCityEdit, updateMap, deleteSiteFromCity, updateTour, getCityTours, deleteTourFromCity, deleteTourFromMap,
	 UpdateSite, deleteCityEdit, updateCity, changeCityPrices, getPriceSubmissions, approveCityPrice,
	 getPurchaseHistory, getCity, logout, getTour, getCitySubmissions, editUsersWithNewPassword,
	 editUsersWithoutNewPassword, getSystemReport, getCityReport, getUserReports
}
