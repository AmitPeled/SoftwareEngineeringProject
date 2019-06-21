package requestHandle;

import java.util.List;

import queries.GcmQuery;
import request.RequestObject;
import response.ResponseObject;

public interface IHandleRequest {
	ResponseObject handleRequest(String username, String password, GcmQuery queryFromClient,
			List<Object> listObjectReceived);
}
