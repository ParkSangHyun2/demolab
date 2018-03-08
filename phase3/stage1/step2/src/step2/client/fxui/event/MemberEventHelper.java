package step2.client.fxui.event;

import java.util.List;

import step1.share.domain.entity.dto.MemberDto;
import step1.share.service.logic.MemberService;
import step2.client.fxui.Session;
import step2.client.transfer.stub.service.ServiceLogicLycler;

public class MemberEventHelper {
	//
	private MemberService memberService = 
			ServiceLogicLycler.shareInstance().createMemberService();
	
	public void modifyMember(String memberName, String phoneNumber, String nickName, String birthDay) {
		List<MemberDto> members = memberService.findByName(memberName);
		for(MemberDto member : members) {
			if(member.getEmail().equals(Session.loggedInMemberEmail)) {
				member.setPhoneNumber(phoneNumber);
				member.setNickName(nickName);
				member.setBirthDay(birthDay);
				memberService.modify(member);
			}
		}
	}
	
	public MemberDto getMemberDto(String memberName) {
		//
		List<MemberDto> members = memberService.findByName(memberName);
		for(MemberDto member : members) {
			if(member.getEmail().equals(Session.loggedInMemberEmail)) {
				return member;
			}
		}
		
		return null;
	}

}