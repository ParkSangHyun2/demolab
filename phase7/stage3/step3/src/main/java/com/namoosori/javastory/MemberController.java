package com.namoosori.javastory;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import logic.ServiceLogicLycler;
import step1.share.domain.entity.dto.MemberDto;
import step1.share.service.logic.MemberService;
import step1.share.util.InvalidEmailException;

@CrossOrigin
@RestController
@RequestMapping("/")
public class MemberController {
	//
	private final String FAIL = "FAIL";
	private final String SUCCESS = "SUCCESS";

	private MemberService memberService = null;

	@PostMapping("member/signin")
	public String signin(@RequestBody String memberInfo, HttpSession session) {
		//
		MemberDto member = new Gson().fromJson(memberInfo, MemberDto.class);
		memberService = ServiceLogicLycler.shareInstance().createMemberService();

		String email = member.getEmail();
		String name = member.getName();
		member = memberService.find(email);
		
		System.out.println(email);
		System.out.println(name);
		System.out.println(member);

		if ((member != null) && (name.equals(member.getName()))) {
			session.setAttribute("memberEmail", email);
			session.setAttribute("memberName", name);
			return new Gson().toJson(SUCCESS);
		} else {
			return new Gson().toJson(FAIL);
		}
	}

	@PostMapping("member/signup")
	public String signup(@RequestBody String memberInfo) {
		//
		MemberDto member = new Gson().fromJson(memberInfo, MemberDto.class);
		memberService = ServiceLogicLycler.shareInstance().createMemberService();

		try {
			memberService.register(member);
			return new Gson().toJson(SUCCESS);
		} catch (InvalidEmailException e) {
			System.out.println("Message --> " + e.getMessage());
			return new Gson().toJson(FAIL);
		}
	}

	@PutMapping("/member/profile/modify")
	public void modifyProfile(@RequestBody String values) {
		//
		Map<String,String> modifyValue = new HashMap<>();
		modifyValue = new Gson().fromJson(values, new TypeToken<Map<String, String>>(){}.getType());
		
		String memberEmail = modifyValue.get("memberEmail");
		String value = modifyValue.get("value");
		String memberInfo = modifyValue.get("memberInfo");
		
		memberService = ServiceLogicLycler.shareInstance().createMemberService();
		MemberDto member = memberService.find(memberEmail);
		
		switch(memberInfo) {
		case "phone":
			member.setPhoneNumber(value);
			break;
		case "nickName":
			member.setNickName(value);
			break;
		case "birthDay":
			member.setBirthDay(value);
			break;
		}
		memberService.modify(member);
	}

	@GetMapping("/member/profile")
	public MemberDto showProfile(@RequestParam(value = "memberEmail") String memberEmail) {
		//
		MemberDto member = null;

		memberService = ServiceLogicLycler.shareInstance().createMemberService();
		member = memberService.find(memberEmail);
		
		MemberDto memberDto = new MemberDto(member.getEmail(),member.getName(),member.getPhoneNumber());
		memberDto.setBirthDay(member.getBirthDay());
		memberDto.setNickName(member.getNickName());
		
		return memberDto;
	}
}
