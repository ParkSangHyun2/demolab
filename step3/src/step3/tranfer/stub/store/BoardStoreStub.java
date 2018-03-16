package step3.tranfer.stub.store;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import step1.share.domain.entity.board.SocialBoard;
import step1.share.service.store.BoardStore;
import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;
import step3.tranfer.SocketDispatcher;

public class BoardStoreStub implements BoardStore {
	//
	private SocketDispatcher dispatcher;
	private String storeName;

	
	
	public BoardStoreStub() {
		//
		this.storeName = (this.getClass().getInterfaces())[0].getSimpleName();
	}
	
	@Override
	public String create(SocialBoard board) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("create", (new Gson()).toJson(board), "SocialBoard");
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
	public SocialBoard retrieve(String boardId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("retrieve", (new Gson()).toJson(boardId), "String");
		ResponseMessage response = null;
		
		SocialBoard board = new SocialBoard();
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		
		if(response.getValue() != null) {
			board = (new Gson()).fromJson(response.getValue(), SocialBoard.class);
		}
		
		return board;
	}

	@Override
	public List<SocialBoard> retrieveByName(String boardId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("retrieveByName", (new Gson()).toJson(boardId), "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		
		return (new Gson()).fromJson(response.getValue(),new TypeToken<List<SocialBoard>>() {}.getType());
	}

	@Override
	public void update(SocialBoard board) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("update", (new Gson()).toJson(board), "SocialBoard");
		
		try {
			dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			System.out.println("Message --->" + e.getMessage());
		}
	}

	@Override
	public void delete(String boardId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("delete", (new Gson()).toJson(boardId), "String");
		
		try {
			dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			System.out.println("Message --->" + e.getMessage());
		}
	}

	@Override
	public boolean exists(String boardId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("exists", (new Gson()).toJson(boardId), "String");
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
