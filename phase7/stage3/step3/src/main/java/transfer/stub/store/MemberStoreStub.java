package transfer.stub.store;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import step1.share.domain.entity.club.CommunityMember;
import step1.share.service.store.MemberStore;
import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;
import transfer.SocketDispatcher;

public class MemberStoreStub implements MemberStore {
	//
	private SocketDispatcher dispatcher;
	private String storeName;

	
	
	public MemberStoreStub() {
		//
		this.storeName = (this.getClass().getInterfaces())[0].getSimpleName();
	}

	@Override
	public String create(CommunityMember member) {
		//
		RequestMessage requestMessage =
				createRequestMessage("create", (new Gson()).toJson(member), "CommunityMember");
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
	public CommunityMember retrieve(String email) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("retrieve", (new Gson()).toJson(email), "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		
		return (new Gson()).fromJson(response.getValue(), CommunityMember.class);
	}

	@Override
	public List<CommunityMember> retrieveByName(String name) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("retrieveByName",(new Gson()).toJson(name), "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		
		return (new Gson()).fromJson(response.getValue(), new TypeToken<List<CommunityMember>>() {}.getType());
	}

	@Override
	public void update(CommunityMember member) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("update", (new Gson()).toJson(member), "CommunityMember");
		
		try {
			dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			System.out.println("Message --->" + e.getMessage());
		}

	}

	@Override
	public void delete(String email) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("delete", (new Gson()).toJson(email), "String");
		
		try {
			dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			System.out.println("Message --->" + e.getMessage());
		}
	}

	@Override
	public boolean exists(String email) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("exist", (new Gson()).toJson(email), "String");
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
	public List<CommunityMember> retrieveAll() {
		//
		RequestMessage requestMessage = 
				createRequestMessage("retrieveAll",null, null);
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		
		return (new Gson()).fromJson(response.getValue(), new TypeToken<List<CommunityMember>>() {}.getType());
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
