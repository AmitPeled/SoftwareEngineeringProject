package transport_objects;

import java.io.Serializable;

import hash.sha1;

public class RequestObject implements Serializable {
	static final long serialVersionUID = 0;
	AcceptedQuery query;
	Object objectToSend;
	String username, password;

	public RequestObject(AcceptedQuery query, Object object, String username, String password) {
		this.query = query;
		objectToSend = object;
		this.username = username;
		this.password = password;
	}

	public RequestObject(AcceptedQuery query, String username, String password) {
		this.query = query;
		objectToSend = null;
		this.username = username;
		this.password = password;
	}

	public AcceptedQuery getQuery() {
		return query;
	}

	public String getPass() {
		return password;
	}

	public String getUname() {
		return username;
	}

	public Object getObject() {
		return objectToSend;
	}
}
