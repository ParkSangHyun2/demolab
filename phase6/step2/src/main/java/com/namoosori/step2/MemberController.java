package com.namoosori.step2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import logic.ServiceLogicLycler;
import step1.share.domain.entity.dto.MemberDto;
import step1.share.service.logic.MemberService;

@Controller
@RequestMapping("/")
public class MemberController {
	//
	private MemberService memberService;
	
	@RequestMapping(value = "memberProfile")
	public void getMemberMenu(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		MemberDto member = null;
		
		memberService = ServiceLogicLycler.shareInstance().createMemberService();
		member = memberService.find((String)session.getAttribute("memberEmail"));
		
		req.setAttribute("memberEmail", member.getEmail());
		req.setAttribute("memberName", member.getName());
		req.setAttribute("phoneNumber", member.getPhoneNumber());
		req.setAttribute("nickName", member.getNickName());
		req.setAttribute("birthDay", member.getBirthDay());
		
		try {
			req.getRequestDispatcher("/WEB-INF/views/memberAccount.jsp").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value ="memberProfile/modify" ,method=RequestMethod.POST)
	public void modifyMember(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		String memberInfo = req.getParameter("memberInfo");
		String value = req.getParameter("value");
		
		memberService = ServiceLogicLycler.shareInstance().createMemberService();
		MemberDto member = memberService.find((String)session.getAttribute("memberEmail"));
		
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
		try {
			req.getRequestDispatcher("/memberProfile").forward(req, resp);
		} catch (ServletException e) {
			//
			e.printStackTrace();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "logout")
	public void logout(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		if(session != null) {
			session.invalidate();
		}
		try {
			req.getRequestDispatcher("/").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
