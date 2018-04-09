package step4_3.react.handler;

import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;

public interface StoreHandler {
	public ResponseMessage handle(RequestMessage request); 
}
