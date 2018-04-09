package step2.client.transfer.stub.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import step1.share.domain.entity.dto.BoardDto;
import step1.share.service.logic.BoardService;
import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;
import step2.client.transfer.SocketDispatcher;

public class BoardServiceStub implements BoardService{
	//
	private SocketDispatcher dispatcher;
	private String serviceName;
	
	public BoardServiceStub() {
		//
		serviceName = this.getClass().getInterfaces()[0].getSimpleName();
	}
	
	@Override
	public String register(BoardDto board) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("register", (new Gson()).toJson(board), "BoardDto");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		
		return response.getValue();
	}

	@Override
	public BoardDto find(String boardId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("find", boardId, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		
		return (new Gson()).fromJson(response.getValue(), BoardDto.class);
	}

	@Override
	public List<BoardDto> findByName(String boardName) {
		//
		RequestMessage reqeustMessage = 
				createRequestMessage("findByName", boardName, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(reqeustMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		
		List<BoardDto> foundBoardList = (new Gson()).fromJson(response.getValue(), new TypeToken<List<BoardDto>>() {}.getType());
		return (ArrayList<BoardDto>)foundBoardList;
	}

	@Override
	public BoardDto findByClubName(String clubName) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("findByClubName", clubName, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		return (new Gson()).fromJson(response.getValue(), BoardDto.class);
	}

	@Override
	public void modify(BoardDto board) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("modify", (new Gson()).toJson(board), "BoardDto");
		
		try {
			dispatcher.dispatchVoid(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
	}

	@Override
	public void remove(String boardId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("remove", boardId, "String");

		try {
			dispatcher.dispatchVoid(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
	}
	
	private RequestMessage createRequestMessage(String operation, String parameter, String remark) {
		//
		this.dispatcher = getDispatcher();
		RequestMessage requestMessage = new RequestMessage(operation, parameter);
		requestMessage.setServiceName(serviceName);
		requestMessage.setRemark(remark);
		return requestMessage;
	}
	
	private SocketDispatcher getDispatcher() {
		//
		return SocketDispatcher.getInstance("127.0.0.1", 9999);
	}

}
