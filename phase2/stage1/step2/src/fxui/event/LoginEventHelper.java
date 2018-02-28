package fxui.event;

import fxui.util.AlertBox;

public class LoginEventHelper {
	//
	//MemberService memberService = new MemberServiceLogic();

	public boolean login(String email, String name) {
		//
//		for(MemeberDto member : memberService.findByName(name)) {
//			if(member.getEmail().equals(name)) {
//				return true;
//			}
//		}
//		return false;
		return true;
	}

	public void signupMember(String email, String name, String phone) {
		//
		AlertBox.alert("Info", "Press SignupBtn");
		//memberService.register(new MemberDto(email, name, phoneNumber));
	}

}
