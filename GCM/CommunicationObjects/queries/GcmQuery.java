package queries;

import java.io.Serializable;

public enum GcmQuery implements Serializable {
	addCustomer, verifyUser, addMap, deleteContent, getMapDetails, getMapFile, addCity, deleteCity, addNewSiteToCity,
	addExistingSiteToMap, deleteSiteFromMap, getMapsByCityName, getMapsBySiteName, getMapsByDescription, getUserDetails, getCityByMapId, updateContent, purchaseMap, getPurchasedMaps, downloadMap
}
