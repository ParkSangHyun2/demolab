package step2.client.transfer.stub.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import step1.share.domain.entity.dto.MemberDto;
import step1.share.service.logic.MemberService;
import step1.share.util.InvalidEmailException;
import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;
import step2.client.transfer.SocketDispatcher;

public class MemberServiceStub implements MemberService{
	//
	private SocketDispatcher dispatcher;
	private String serviceName;
	
	
	public MemberServiceStub() {
		this.serviceName = (this.getClass().getInterfaces())[0].getSimpleName();
	}

	@Override
	public void register(MemberDto member) throws InvalidEmailException {
		//
		RequestMessage requestMessage =
				createRequestMessage("register",(new Gson()).toJson(member),"MemberDto");
		
		try {
			dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		dispatcher.close();
	}

	@Override
	public MemberDto find(String memberId) {
		//
		RequestMessage requestMessage =
				createRequestMessage("find",memberId, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dispatcher.close();
		
		return (new Gson()).fromJson(response.getValue(), MemberDto.class);
	}

	@Override
	public List<MemberDto> findByName(String memberName) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("findByName", memberName, "String");
		ResponseMessage response = null;
		
		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
		List<MemberDto> foundMemberDtos = (new Gson()).fromJson(response.getValue(), new TypeToken<List<MemberDto>>() {}.getType());
		return (ArrayList<MemberDto>)foundMemberDtos;
	}

	@Override
	public void modify(MemberDto member) throws InvalidEmailException {
		//
		RequestMessage requestMessage = 
				createRequestMessage("modify", (new Gson()).toJson(member),"MemberDto");
		
		try {
			dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dispatcher.close();
	}

	@Override
	public void remove(String memberId) {
		// 		
		RequestMessage requestMessage = 
		createRequestMessage("remove", memberId,"MemberDto");
		
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
