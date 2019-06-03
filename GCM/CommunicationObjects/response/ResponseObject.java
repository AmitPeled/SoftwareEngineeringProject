package response;

import java.io.Serializable;
import java.util.List;

import queries.RequestState;

@SuppressWarnings("serial")
public class ResponseObject implements Serializable {
	List<Object> objects;
	RequestState requestState;

	public ResponseObject() {
		objects = null;
	}

	public ResponseObject(List<Object> objects) {
		this.objects = objects;
	}

	public List<Object> getResponse() {
		return objects;
	}

	public void setResponse(List<Object> objects) {
		this.objects = objects;
	}

	public void setRequestState(RequestState state) {
		this.requestState = state;
	}

	public RequestState getRequestState() {
		return requestState;
	}
}
