package fxui.event;

import java.util.ArrayList;
import java.util.Map;

public class LoginEventHelper {
	//
	//MemberService memberService = new MemberServiceLogic();

	public boolean login(Map<String, String> values) {
		//
//		String email = values.get("email");
//		String name = values.get("name");
//		for(MemeberDto member : memberService.findByName(name)) {
//			if(member.getEmail().equals(name)) {
//				return true;
//			}
//		}
//		return false;
		return true;
	}

	public void signupMember(Map<String, String> values) {
		//
		
		String email = values.get("email");
		String name = values.get("name");
		String phoneNumber = values.get("phone");
		//memberService.register(new MemberDto(email, name, phoneNumber));
	}

}
