package requestHandle;

import request.RequestObject;
import request.UserType;
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

	@Override
	public ResponseObject handleRequest(RequestObject requestObject) {
		List<Object> listToSend = new ArrayList<Object>();
		UserType userType = requestObject.getUserType();
		GcmQuery query = requestObject.getQuery();
		String username = requestObject.getUname(), password = requestObject.getPass();
		List<Object> listObjectReceived = requestObject.getObjects();
		RequestState requestState;
		if (verifyPrivilege(userType, query)) {
			try {
				if (userType == UserType.notLogged || gcmDataExecutor.verifyUser(/* userType, */username, password)) {
					requestState = RequestState.success;
					switch (requestObject.getQuery()) {
					case addCustomer:
						requestState = verifyDetailsConstrains(username, password);
						if (requestState == RequestState.success
								&& !gcmDataExecutor.addUser((String) listObjectReceived.get(0),
										(String) listObjectReceived.get(1), (User) listObjectReceived.get(2))) {
							requestState = RequestState.usernameAlreadyExists;
						}
						break;
					case verifyCustomer:
						if (!gcmDataExecutor.verifyUser(username, password))
							requestState = RequestState.wrongDetails;
						break;
					case addMap:
						listToSend.add(gcmDataExecutor.addMapToCity((int) listObjectReceived.get(0), (Map) listObjectReceived.get(1),
								(File) listObjectReceived.get(2)));
						break;
					case getMapDetails:
						listToSend.add(gcmDataExecutor.getMapDetails((int) listObjectReceived.get(0)));
						break;
					case getMapFile:
						listToSend.add(gcmDataExecutor.getMapFile((int) listObjectReceived.get(0)));
						break;
					case deleteMap:
						gcmDataExecutor.deleteMap((int) listObjectReceived.get(0));
						break;
					case addCity:
						listToSend.add(gcmDataExecutor.addCity((City) listObjectReceived.get(0)));
						break;

					default:
						break;
					}
				} else {
					requestState = RequestState.wrongDetails;
				}
			} catch (SQLException e) {
				requestState = RequestState.somethingWrongHappend;
			}
		} else
			requestState = RequestState.notPrivileged;

		return new ResponseObject(requestState, listToSend);
	}

	private boolean verifyPrivilege(UserType userType, GcmQuery query) {
		// TODO Auto-generated method stub
		return true;
	}

	private RequestState verifyDetailsConstrains(String username, String password) {
		// TODO Auto-generated method stub
		return RequestState.success;
	}

}
