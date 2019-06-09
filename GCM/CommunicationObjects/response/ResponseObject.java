package response;

import java.io.Serializable;
import java.util.List;

import queries.RequestState;

public class ResponseObject implements Serializable {
	private static final long serialVersionUID = 1L;
	List<Object> objects;
	RequestState requestState;

	public ResponseObject() {
		objects = null;
	}

	public ResponseObject(RequestState state, List<Object> objects) {
		this.objects = objects;
		this.requestState = state;
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
