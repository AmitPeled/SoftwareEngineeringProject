package requestHandle;

import request.RequestObject;
import response.ResponseObject;

public interface IHandleRequest {
	ResponseObject handleRequest(RequestObject requestObject);
}
