package fxui.event;

import entity.dto.MemberDto;
import step3.logic.ServiceLogicLycler;
import step3.service.MemberService;

public class MemberEventHelper {
	//
	private MemberService memberService = 
			ServiceLogicLycler.shareInstance().createMemberService();
	
	public void modifyMember(String memberName, String phoneNumber, String nickName, String birthDay) {
		//세션에있는 이메일과 이름으로도 같이 묶어서 수정.
		MemberDto member = memberService.findByName(memberName).iterator().next();
		member.setPhoneNumber(phoneNumber);
		member.setNickName(nickName);
		member.setBirthDay(birthDay);
		memberService.modify(member);
	}
	
	public MemberDto getMemberDto(String memberName) {
		//
		return memberService.findByName(memberName).iterator().next();		
	}

}
