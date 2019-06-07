package queries;

import java.io.Serializable;

public enum GcmQuery implements Serializable {
	addCustomer, verifyUser, addMap, deleteMap, getMapDetails, getMapFile, addCity, deleteCity, addNewSiteToCity,
	addExistingSiteToMap, deleteSiteFromMap, getMapsByCityName, getMapsBySiteName, getMapsByDescription
}
