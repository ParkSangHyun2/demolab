package step4_3.react.handler;

import com.google.gson.Gson;

import step1.share.domain.entity.board.Posting;
import step1.share.service.store.PostingStore;
import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;
import step4_3.hibernate.store.PostingStoreService;

public class PostingStoreHandler<V> implements StoreHandler {
	//
	PostingStore postingStore = new PostingStoreService();
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseMessage handle(RequestMessage request) {
		//
		String operation = request.getOperation();
		String value = request.getValue();
		String result = null;
		V returnValue;
		
		switch(operation) {
		case "create":
			returnValue = (V) postingStore.create((new Gson()).fromJson(
					value, Posting.class)
					);
			result = (new Gson().toJson(returnValue));
			break;
		case "retrieve":
			returnValue = (V) postingStore.retrieve((new Gson()).fromJson(
					value, String.class)
					);
			result = (new Gson().toJson(returnValue));
			break;
		case "retrieveByBoardId":
			returnValue = (V) postingStore.retrieveByBoardId((new Gson()).fromJson(
					value, String.class)
					);
			result = (new Gson().toJson(returnValue));
			break;
		case "retrieveByTitle":
			returnValue = (V) postingStore.retrieveByTitle((new Gson()).fromJson(
					value, String.class)
					);
			result = (new Gson().toJson(returnValue));
			break;
		case "update":
			postingStore.update((new Gson()).fromJson(
					value, Posting.class)
					);
			break;
		case "delete":
			postingStore.delete((new Gson()).fromJson(
					value, String.class)
					);
			break;
		case "exists":
			boolean isExist = postingStore.exists((new Gson()).fromJson(
					value, String.class)
					);
			result = (new Gson()).toJson(isExist);
			break;
			
		default:
			System.out.println("no such serviceName..");
		}
		ResponseMessage responseMessage=
				new ResponseMessage("BoardStore", result);
		return responseMessage;
	}

}
