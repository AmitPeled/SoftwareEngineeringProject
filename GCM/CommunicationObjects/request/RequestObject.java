package request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import queries.GcmQuery;

public class RequestObject implements Serializable {
	private static final long serialVersionUID = 2L;
	private UserType userType;
	private GcmQuery query;
	private List<Object> objectsToSend;
	private String username, password; // future session hash

	public RequestObject(UserType userType, GcmQuery query, List<Object> objects, String username, String password) {
		this.userType = userType;
		this.query = query;
		objectsToSend = objects;
		this.username = username;
		this.password = password;
	}

	public RequestObject(GcmQuery query, String username, String password) {
		this.query = query;
		objectsToSend = null;
		this.username = username;
		this.password = password;
	}

	public UserType getUserType() {
		return userType;
	}

	public GcmQuery getQuery() {
		return query;
	}

	public String getPass() {
		return password;
	}

	public String getUname() {
		return username;
	}

	public List<Object> getObjects() {
		return objectsToSend;
	}
}
