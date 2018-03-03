package fxui.event;

import entity.dto.MemberDto;
import step3.logic.MemberServiceLogic;
import step3.logic.ServiceLogicLycler;
import step3.service.MemberService;
import step3.service.ServiceLycler;

public class LoginEventHelper {
	//
	private MemberService memberService = 
			ServiceLogicLycler.shareInstance().createMemberService();

	public boolean login(String email, String name) {
		//
		if (memberService.findByName(name) != null) {
			for (MemberDto member : memberService.findByName(name)) {
				if (member.getEmail().equals(email)) {
					return true;
				}
			}
		}
		return false;
		// return true;
	}

	public void signupMember(String email, String name, String phone) {
		//
		memberService.register(new MemberDto(email, name, phone));
	}

}
