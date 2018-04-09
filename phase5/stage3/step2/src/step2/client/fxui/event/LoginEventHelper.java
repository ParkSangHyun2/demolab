package step2.client.fxui.event;

import step1.share.domain.entity.dto.MemberDto;
import step1.share.service.logic.MemberService;
import step2.client.fxui.util.AlertBox;
import step2.client.transfer.stub.service.ServiceLogicLycler;

public class LoginEventHelper {
	//
	private MemberService memberService = ServiceLogicLycler.shareInstance().createMemberService();

	public boolean login(String email, String name) {
		//
		boolean isCorrect = false;
		if (memberService.findByName(name) != null) {
			for (MemberDto member : memberService.findByName(name)) {
				if (member.getEmail().equals(email)) {
					isCorrect = true;
				}
			}
		}
		return isCorrect;
	}

	public void signupMember(String email, String name, String phone) {
		//
		if(memberService.find(email) == null) {
			memberService.register(new MemberDto(email, name, phone));
			AlertBox.alert("Success", "Registed!");
		}else {
			AlertBox.alert("Info", "email : "+ email + ", Already registed Email in Club");
		}
	}

}
