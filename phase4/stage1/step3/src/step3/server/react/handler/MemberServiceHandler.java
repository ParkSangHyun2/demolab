package step3.server.react.handler;

import java.util.List;

import com.google.gson.Gson;

import step1.share.domain.entity.dto.MemberDto;
import step1.share.service.logic.MemberService;
import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;
import step3.server.logic.ServiceLogicLycler;

public class MemberServiceHandler implements ServiceHandler {
	//
	private MemberService memberService;
	
	public MemberServiceHandler() {
		//
		memberService = ServiceLogicLycler.shareInstance().createMemberService();
	}
	
	@Override
	public ResponseMessage handle(RequestMessage request) {
		//
		String operation = request.getOperation();
		String memberId, memberName;
		MemberDto memberDto;
		String responseValue = null;
		boolean success = true;
		
		switch(operation) {
		case "register":
			memberDto = (new Gson()).fromJson(request.getValue(), MemberDto.class);
			memberService.register(memberDto);
			break;
			
		case "find":
			memberId = request.getValue();
			memberDto = memberService.find(memberId);
			responseValue = (new Gson()).toJson(memberDto);
			break;
			
		case "findByName":
			memberName = request.getValue();
			List<MemberDto> foundMemberList = memberService.findByName(memberName);
			responseValue = (new Gson()).toJson(foundMemberList);
			break;
			
		case "modify":
			memberDto = (new Gson()).fromJson(request.getValue(), MemberDto.class);
			memberService.modify(memberDto);
			break;
			
		case "remove":
			memberId = request.getValue();
			memberService.remove(memberId);
			break;
		}
		ResponseMessage responseMessage = new ResponseMessage(operation, responseValue);
		responseMessage.setSuccess(success);
		return responseMessage;
	}

}
