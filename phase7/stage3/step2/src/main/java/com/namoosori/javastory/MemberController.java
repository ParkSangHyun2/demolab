package com.namoosori.javastory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import step1.share.domain.entity.dto.MemberDto;

@Controller
@RequestMapping("/")
public class MemberController {
	//
	@RequestMapping(value = "member/profile")
	public void getMemberMenu(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		MemberDto member = null;
		String memberEmail = (String) session.getAttribute("memberEmail");
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(RestApi.MEMBER_PROFILE_API)
				.queryParam("memberEmail", memberEmail);
		
		RestTemplate restTemplate = new RestTemplate();
		String value = restTemplate.getForObject(builder.toUriString(), String.class);
		
		member = new Gson().fromJson(value, MemberDto.class);
		
		req.setAttribute("memberEmail", member.getEmail());
		req.setAttribute("memberName", member.getName());
		req.setAttribute("phoneNumber", member.getPhoneNumber());
		req.setAttribute("nickName", member.getNickName());
		req.setAttribute("birthDay", member.getBirthDay());
		
		try {
			req.getRequestDispatcher("/WEB-INF/views/memberAccount.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			//
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value ="member/profile/modify" ,method=RequestMethod.POST)
	public void modifyMember(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		String memberInfo = req.getParameter("memberInfo");
		String value = req.getParameter("value");
		String memberEmail = (String)session.getAttribute("memberEmail");
		
		Map<String,String> values = new HashMap<>();
		values.put("memberInfo", memberInfo);
		values.put("value", value);
		values.put("memberEmail", memberEmail);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> request = new HttpEntity<Map<String, String>>(values);
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(RestApi.MEMBER_PROFILE_MODIFY_API, request);
		try {
			req.getRequestDispatcher("/member/profile").forward(req, resp);
		} catch (ServletException | IOException e) {
			//
			e.getMessage();
		}
	}
	
	@RequestMapping(value = "member/logout")
	public void logout(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		if(session != null) {
			session.invalidate();
		}
		try {
			req.getRequestDispatcher("/").forward(req, resp);
		} catch (ServletException | IOException e) {
			//
			e.printStackTrace();
		}
	}
}
