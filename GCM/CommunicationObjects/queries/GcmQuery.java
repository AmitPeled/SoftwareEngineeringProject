package queries;

import java.io.Serializable;

public enum GcmQuery implements Serializable{
	addCustomer, verifyCustomer, verifyEditor, verifyManager, addMap, deleteMap, getMapDetails, getMapFile, addCity, deleteCity, addNewSiteToCity, addExistingSiteToMap
}
