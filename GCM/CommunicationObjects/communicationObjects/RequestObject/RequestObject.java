package communicationObjects.RequestObject;
import java.io.Serializable;

import hash.sha1;

public class RequestObject implements Serializable{
	static final long serialVersionUID = 0;
	AcceptedQuery query;
	Object objectToSend;
	String username, password;

	public RequestObject(AcceptedQuery q, Object o, String un, String ps) {
		query = q;
		objectToSend = o;
		username = un;
		password = ps;

	}

	public RequestObject(AcceptedQuery q, String un, String ps) {
		query = q;
		objectToSend = null;
		username = un;
		password = ps;
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
