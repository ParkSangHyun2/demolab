package step3.server.react.handler;

import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;

public interface ServiceHandler {
	//
	public ResponseMessage handle(RequestMessage request); 
}
