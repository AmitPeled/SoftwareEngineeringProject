package communicationObjects.ResponseObject;

import java.io.Serializable;

public class ResponseObject implements Serializable {
	static final long serialVersionUID = 0;
	Object object;
	boolean isError = false;
	String errorMsg;

	public ResponseObject() {
		object = null;
	}

	public ResponseObject(Object o) {
		object = o;
	}

	public Object getResponse() {
		return object;
	}

	public void setResponse(Object o) {
		object = o;
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
