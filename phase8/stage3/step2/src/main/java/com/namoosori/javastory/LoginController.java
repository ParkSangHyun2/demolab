package com.namoosori.javastory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import step1.share.domain.entity.dto.MemberDto;

@Controller
@RequestMapping("/")
public class LoginController {
	//

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "member/signin", method = RequestMethod.POST)
	public void signin(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException {
		//
		String memberEmail = req.getParameter("memberEmail");
		String memberName = req.getParameter("memberName");

		MemberDto memberDto = new MemberDto(memberEmail, memberName, null);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity request = new HttpEntity(new Gson().toJson(memberDto));

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.postForObject(RestApi.MEMBER_SIGNIN_API, request, String.class);
		

		switch (new Gson().fromJson(result, String.class)) {
		case "SUCCESS":
			session.setAttribute("memberEmail", memberEmail);
			session.setAttribute("memberName", memberName);
			try {
				resp.sendRedirect("/club/clublists");
			} catch (IOException e) {
				//
				System.out.println("Error --> " + e.getMessage());
			}
			break;
		case "FAIL":
			System.out.println("FAIL");
			session.setAttribute("loginStatus", "FAIL");
			break;
		default:
			System.out.println("DEFAULT");
			session.setAttribute("loginStatus", "FAIL");
		}
	}

	@RequestMapping(value = "member/signup-page")
	public ModelAndView showSignup() {
		//
		ModelAndView view = new ModelAndView("signup");
		return view;
	}

	@RequestMapping(value = "member/signup-page/signup")
	public ModelAndView signup(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		ModelAndView view = new ModelAndView("login");

		String email = req.getParameter("memberEmail");
		String name = req.getParameter("memberName");
		String phoneNumber = req.getParameter("memberPhone");

		MemberDto member = new MemberDto(email, name, phoneNumber);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		@SuppressWarnings({ "rawtypes", "unchecked" })
		HttpEntity request = new HttpEntity(new Gson().toJson(member), header);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForObject(RestApi.MEMBER_SIGNUP_API, request, String.class);

		return view;
	}
}
