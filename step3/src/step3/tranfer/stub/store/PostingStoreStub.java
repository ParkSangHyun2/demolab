package step3.tranfer.stub.store;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import step1.share.domain.entity.board.Posting;
import step1.share.service.store.PostingStore;
import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;
import step3.tranfer.SocketDispatcher;

public class PostingStoreStub implements PostingStore {
	//
	private SocketDispatcher dispatcher;
	private String storeName;

	
	
	public PostingStoreStub() {
		//
		this.storeName = (this.getClass().getInterfaces())[0].getSimpleName();
	}
	
	@Override
	public String create(Posting posting) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("create", (new Gson()).toJson(posting), "Posting");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			System.out.println("Message --->" + e.getMessage());
		}
		
		return (new Gson()).fromJson(response.getValue(), String.class);
	}

	@Override
	public Posting retrieve(String postingId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("retrieve", (new Gson()).toJson(postingId), "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			System.out.println("Message --->" + e.getMessage());
		}
		
		return (new Gson()).fromJson(response.getValue(), Posting.class);
	}

	@Override
	public List<Posting> retrieveByBoardId(String boardId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("retrieveByBoardId",(new Gson()).toJson(boardId), "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			System.out.println("Message --->" + e.getMessage());
		}
		
		return (new Gson()).fromJson(response.getValue(), new TypeToken<List<Posting>>() {}.getType());
	}

	@Override
	public List<Posting> retrieveByTitle(String title) {
		// 
		RequestMessage requestMessage = 
				createRequestMessage("retrieveByTitle",(new Gson()).toJson(title), "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			System.out.println("Message --->" + e.getMessage());
		}
		
		return (new Gson()).fromJson(response.getValue(), new TypeToken<List<Posting>>() {}.getType());
	}

	@Override
	public void update(Posting posting) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("update", (new Gson()).toJson(posting), "String");
		
		try {
			dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			System.out.println("Message --->" + e.getMessage());
		}
	}

	@Override
	public void delete(String posingId) {
		// 
		RequestMessage requestMessage = 
				createRequestMessage("delete", (new Gson()).toJson(posingId), "String");
		
		try {
			dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			System.out.println("Message --->" + e.getMessage());
		}
	}

	@Override
	public boolean exists(String postingId) {
		// 
		RequestMessage requestMessage = 
				createRequestMessage("exists", (new Gson()).toJson(postingId), "String");
		ResponseMessage responseMessage = null;
		
		try {
			responseMessage = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			System.out.println("Message --->" + e.getMessage());
		}
		return (new Gson()).fromJson(responseMessage.getValue(), Boolean.class);
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
