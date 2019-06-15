package queries;

import java.io.Serializable;

public enum GcmQuery implements Serializable {
	addCustomer, verifyUser, addMap, deleteContent, getMapDetails, getMapFile, addCity, deleteCity, addNewSiteToCity,
	addExistingSiteToMap, deleteSiteFromMap, getMapsByCityName, getMapsBySiteName, getMapsByDescription, getUserDetails,
	getCityByMapId, updateContent, purchaseCity, getPurchasedMaps, downloadMap, addCityWithInitialMap, addNewTourToCity,
	addExistingTourToMap, addExistingSiteToTour, getCitySites, purchaseMembershipToCity, getToursDeleteEdits,
	getToursUpdateEdits, getToursAddEdits, getToursObjectAddedTo, getCitiesObjectAddedTo, getMapsObjectAddedTo,
	getSitesAddEdits, getCitiesDeleteEdits, getCitiesUpdateEdits, getCitiesAddEdits, getSitesDeleteEdits,
	getSitesUpdateEdits, getMapsDeleteEdits, getMapsUpdateEdits, getMapsAddEdits, actionSiteDeleteEdit,
	actionSiteUpdateEdit, actionSiteAddEdit,actionCityDeleteEdit,actionCityUpdateEdit,actionCityAddEdit,actionMapAddEdit, actionMapUpdateEdit, actionMapDeleteEdit, changeMapPrice, getSavedCreditCard, notifyMapView
}
