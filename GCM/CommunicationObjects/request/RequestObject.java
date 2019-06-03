package request;

import java.io.Serializable;
import java.util.List;

import queries.GcmQuery;

@SuppressWarnings("serial")
public class RequestObject implements Serializable {
	GcmQuery query;
	List<Object> objectsToSend;
	String username, password;

	public RequestObject(GcmQuery query, List<Object> objects, String username, String password) {
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
