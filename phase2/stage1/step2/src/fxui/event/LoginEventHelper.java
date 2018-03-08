package fxui.event;

import entity.dto.MemberDto;
import fxui.util.AlertBox;
import step4_1.logic.ServiceLogicLycler;
import step4_1.service.MemberService;

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
		try {
			memberService.find(email);
			AlertBox.alert("Info", "email : "+ email + ", Already registed Email in Club");

		} catch(Exception e) {
			e.getMessage();
			memberService.register(new MemberDto(email, name, phone));
			AlertBox.alert("Success", "Registed!");
		}
	}

}
