package transport_objects;

import java.io.Serializable;

public class ResponseObject implements Serializable {
	static final long serialVersionUID = 0;
	Object object;
	boolean isError = false;
	String errorMsg;

	public ResponseObject() {
		object = null;
	}

	public ResponseObject(Object object) {
		this.object = object;
	}

	public Object getResponse() {
		return object;
	}

	public void setResponse(Object object) {
		object = object;
	}

	public void setError(String error) {
		errorMsg = error;
		isError = true;
	}

	public boolean Error() {
		return isError;
	}

	public String getError() {
		if (isError)
			return errorMsg;
		else
			return null;
	}
}
