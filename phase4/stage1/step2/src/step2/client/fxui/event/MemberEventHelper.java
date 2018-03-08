package step2.client.fxui.event;

import step1.share.domain.entity.dto.MemberDto;
import step1.share.service.logic.MemberService;
import step2.client.transfer.stub.service.ServiceLogicLycler;

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
		memberService.modify(member);//memberDto
	}
	
	public MemberDto getMemberDto(String memberName) {
		//
		return memberService.findByName(memberName).iterator().next();		
	}

}
