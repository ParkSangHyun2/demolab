package step4_1.react.handler;

import com.google.gson.Gson;

import step1.share.domain.entity.board.SocialBoard;
import step1.share.service.store.BoardStore;
import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;
import step4_1.store.DBStoreLycler;

public class BoardStoreHandler<V> implements StoreHandler {
	//
	BoardStore boardStore = DBStoreLycler.shareInstance().requestBoardStore();
	
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
			returnValue = (V) boardStore.create((new Gson()).fromJson(
					value, SocialBoard.class)
					);
			result = (new Gson().toJson(returnValue));
			break;
		case "retrieve":
			returnValue = (V) boardStore.retrieve((new Gson()).fromJson(
					value, String.class)
					);
			result = (new Gson().toJson(returnValue));
			break;
		case "retrieveByName":
			returnValue = (V) boardStore.retrieveByName((new Gson()).fromJson(
					value, String.class)
					);
			result = (new Gson().toJson(returnValue));
			break;
		case "update":
			boardStore.update((new Gson()).fromJson(
					value, SocialBoard.class)
					);
			break;
		case "delete":
			boardStore.delete((new Gson()).fromJson(
					value, String.class)
					);
			break;
		case "exists":
			boolean isExist = boardStore.exists((new Gson()).fromJson(
					value, String.class)
					);
			result = (new Gson()).toJson(isExist);
			break;
		}
		ResponseMessage responseMessage = 
				new ResponseMessage("BoardStore", result);
		
		
		return responseMessage;
	}

}
