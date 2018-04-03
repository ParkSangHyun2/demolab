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
public class LoginController {
	//
	private MemberService memberService;

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public void main(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		String memberEmail = req.getParameter("memberEmail");
		String memberName = req.getParameter("memberName");

		memberService = ServiceLogicLycler.shareInstance().createMemberService();
		MemberDto member = memberService.find(memberEmail);
		try {
			if (member == null) {
				req.getRequestDispatcher("/").forward(req, resp);
				return;
			}
			if (memberName.equals(member.getName())) {
				session.setAttribute("memberEmail", memberEmail);
				session.setAttribute("memberName", memberName);
				req.getRequestDispatcher("club").forward(req, resp);
			} else {
				req.getRequestDispatcher("/").forward(req, resp);
			}
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "signupPage")
	public String showSignup() {
		//
		return "signup";
	}

	@RequestMapping(value = "signup")
	public void signup(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		MemberDto member;
		memberService = ServiceLogicLycler.shareInstance().createMemberService();

		String email = req.getParameter("memberEmail");
		String name = req.getParameter("memberName");
		String phoneNumber = req.getParameter("memberPhone");

		member = new MemberDto(email, name, phoneNumber);

		memberService.register(member);

		req.getRequestDispatcher("/step2/clubMenu");
	}
}
