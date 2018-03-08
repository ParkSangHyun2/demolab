package fxui.event;

import java.util.List;

import entity.dto.MemberDto;
import fxui.Session;
import step4_1.logic.ServiceLogicLycler;
import step4_1.service.MemberService;

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
