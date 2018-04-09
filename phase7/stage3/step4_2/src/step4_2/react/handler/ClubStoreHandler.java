package step4_2.react.handler;

import com.google.gson.Gson;

import step1.share.domain.entity.club.TravelClub;
import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;
import step4_2.mybatis.impl.ProviderImplLycler;
import step4_2.mybatis.provider.ClubProvider;

public class ClubStoreHandler<V> implements StoreHandler {
	//
	ClubProvider clubStore = ProviderImplLycler.shareInstance().requestClubProvider();
	
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
			returnValue = (V) clubStore.create((new Gson()).fromJson(
					value, TravelClub.class)
					);
			result = (new Gson().toJson(returnValue));
			break;
			
		case "retrieve":
			returnValue = (V) clubStore.retrieve((new Gson()).fromJson(
					value, String.class)
					);
			result = (new Gson().toJson(returnValue));
			break;
			
		case "retrieveByName":
			returnValue = (V) clubStore.retrieveByName((new Gson()).fromJson(
					value, String.class)
					);
			result = (new Gson().toJson(returnValue));
			break;
			
		case "update":
			clubStore.update((new Gson()).fromJson(
					value, TravelClub.class)
					);
			break;
		case "delete":
			clubStore.delete((new Gson()).fromJson(
					value, String.class)
					);
			break;
		case "exists":
			boolean isExist = clubStore.exists((new Gson()).fromJson(
					value, String.class)
					);
			result = (new Gson()).toJson(isExist);
			break;
		case "retrieveAll":
			returnValue = (V) clubStore.retrieveAll();
			System.out.println("Return Value : " + returnValue);
			result = (new Gson().toJson(returnValue));
			System.out.println("Result" + result);
			break;
		}
		ResponseMessage responseMessage = 
				new ResponseMessage("ClubStore", result);		
		
		return responseMessage;
	}

}
