package step4_3.react.handler;

import com.google.gson.Gson;

import step1.share.domain.entity.club.CommunityMember;
import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;
import step4_3.hibernate.store.MemberStoreService;

public class MemberStoreHandler<V> implements StoreHandler {
	//
	MemberStoreService memberStore = new MemberStoreService();
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseMessage handle(RequestMessage request) {
		//
		String operation = request.getOperation();
		String value = request.getValue();
		String result = null;
		V returnValue;
		
		switch(operation) {
		case "create" :
			returnValue = (V) memberStore.create((new Gson()).fromJson(
					value, CommunityMember.class)
					);
			result = (new Gson().toJson(returnValue));
			break;
			
		case "retrieve":
			returnValue = (V) memberStore.retrieve(
					(new Gson()).fromJson(value, String.class)
					);
			result = (new Gson().toJson(returnValue));
			break;
			
		case "retrieveByName" :
			returnValue = (V) memberStore.retrieveByName(
					(new Gson()).fromJson(value, String.class)
					);
			result = (new Gson().toJson(returnValue));
			break;
			
		case "update":
			memberStore.update(
					(new Gson()).fromJson(value, CommunityMember.class)
					);
			break;
			
		case "delete" :
			memberStore.delete(
					(new Gson()).fromJson(value, String.class)
					);
			break;
			
		case "exists" :
			boolean isExist = memberStore.exists(
					(new Gson()).fromJson(value, String.class)
					);
			result = (new Gson().toJson(isExist));
			break;
			
		case "retrieveAll":
			returnValue = (V) memberStore.retrieveAll();
			result = (new Gson().toJson(returnValue));
			break;
		}
		
		ResponseMessage responseMessage = 
				new ResponseMessage("MemberStore", result);
				
		
		return responseMessage;
	}

}
