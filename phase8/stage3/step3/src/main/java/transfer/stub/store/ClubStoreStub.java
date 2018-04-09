package transfer.stub.store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import step1.share.domain.entity.club.TravelClub;
import step1.share.service.store.ClubStore;
import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;
import transfer.SocketDispatcher;

public class ClubStoreStub implements ClubStore {
	//
	private SocketDispatcher dispatcher;
	private String storeName;

	public ClubStoreStub() {
		//
		this.storeName = (this.getClass().getInterfaces())[0].getSimpleName();
	}
	
	@Override
	public String create(TravelClub club) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("create", (new Gson()).toJson(club), "TravelClub");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		
		return (new Gson()).fromJson(response.getValue(), String.class);
	}

	@Override
	public TravelClub retrieve(String clubId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("retrieve", (new Gson()).toJson(clubId), "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		
		return (new Gson()).fromJson(response.getValue(), TravelClub.class);
	}

	@Override
	public TravelClub retrieveByName(String name) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("retrieveByName", (new Gson()).toJson(name), "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		
		return (new Gson()).fromJson(response.getValue(), TravelClub.class);
	}

	@Override
	public void update(TravelClub club) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("update", (new Gson()).toJson(club), "String");
		
		try {
			dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			System.out.println("Message --->" + e.getMessage());
		}
	}

	@Override
	public void delete(String clubId) {
		// 
		RequestMessage requestMessage = 
				createRequestMessage("delete", (new Gson()).toJson(clubId), "String");
		
		try {
			dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			System.out.println("Message --->" + e.getMessage());
		}
	}

	@Override
	public boolean exists(String clubId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("exists", (new Gson()).toJson(clubId), "String");
		ResponseMessage responseMessage = null;
		
		try {
			responseMessage = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			System.out.println("Message --->" + e.getMessage());
		}
		return (new Gson()).fromJson(responseMessage.getValue(), Boolean.class);
	}

	@Override
	public List<TravelClub> retrieveAll() {
		//
		RequestMessage requestMessage = 
				createRequestMessage("retrieveAll", null, null);
		ResponseMessage response = null;
		List<TravelClub> list = new ArrayList<>();
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
			System.out.println("Reference: "+response);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		
		if(response.getValue() !=null) {
			list = (new Gson()).fromJson(response.getValue(),new TypeToken<List<TravelClub>>() {}.getType());
		}
		
		return list;
	}

	private RequestMessage createRequestMessage(String operation, String parameter, String remark) {
		//
		this.dispatcher = getDispatcher();
		RequestMessage requestMessage = new RequestMessage(operation, parameter);
		requestMessage.setServiceName(storeName);
		requestMessage.setRemark(remark);
		return requestMessage;
	}
	
	private SocketDispatcher getDispatcher() {
		//
		return SocketDispatcher.getInstance("127.0.0.1", 8888);
	}
}
