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
			return userType == RequestState.editor || userType == RequestState.manager;
		case addCity:
			return userType == RequestState.editor || userType == RequestState.manager;
		case addExistingSiteToMap:
			return userType == RequestState.editor || userType == RequestState.manager;
		case addMap:
			return userType == RequestState.editor || userType == RequestState.manager;
		case addNewSiteToCity:
			return userType == RequestState.editor || userType == RequestState.manager;
		case deleteCity:
			return userType == RequestState.editor || userType == RequestState.manager;
		case deleteContent:
			return userType == RequestState.editor || userType == RequestState.manager;
		case deleteSiteFromMap:
			return userType == RequestState.editor || userType == RequestState.manager;
		default:
			return false;
		}
	}

	private RequestState verifyDetailsConstrains(String username, String password) {
		// TODO Auto-generated method stub
		return RequestState.customer;
	}

}
