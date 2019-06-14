package queries;

import java.io.Serializable;

public enum GcmQuery implements Serializable {
	addCustomer, verifyUser, addMap, deleteContent, getMapDetails, getMapFile, addCity, deleteCity, addNewSiteToCity,
	addExistingSiteToMap, deleteSiteFromMap, getMapsByCityName, getMapsBySiteName, getMapsByDescription, getUserDetails,
	getCityByMapId, updateContent, purchaseCity, getPurchasedMaps, downloadMap, addCityWithInitialMap, addNewTourToCity,
	addExistingTourToMap, addExistingSiteToTour
}
