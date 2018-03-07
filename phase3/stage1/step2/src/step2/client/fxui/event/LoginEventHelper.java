package step2.client.fxui.event;

import step1.share.domain.entity.dto.MemberDto;
import step1.share.service.logic.MemberService;
import step2.client.transfer.stub.service.ServiceLogicLycler;

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
