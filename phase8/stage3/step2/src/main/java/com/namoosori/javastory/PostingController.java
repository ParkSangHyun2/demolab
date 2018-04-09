package com.namoosori.javastory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import step1.share.domain.entity.dto.PostingDto;

@Controller
@RequestMapping("/")
public class PostingController {
	//
	@RequestMapping("postings/list")
	public void setPostingList(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		String clubId = (String) session.getAttribute("boardId");
		List<PostingDto> postingList = new ArrayList<>();
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(RestApi.POSTING_LIST_API)
			.queryParam("clubId", clubId);	
		
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(builder.toUriString(), String.class);
		postingList = new Gson().fromJson(result, new TypeToken<List<PostingDto>>() {}.getType());
		
		try {
			req.setAttribute("postingList", postingList);
			req.getRequestDispatcher("/WEB-INF/views/posting.jsp").forward(req, resp);
		} catch (Exception e) {
			e.getMessage();

		}
	}

	@RequestMapping("postings/new")
	public ModelAndView newPosting(HttpServletRequest req, HttpServletResponse resp) {
		//
		ModelAndView view = new ModelAndView("newposting");
		
		return view;
	}

	@RequestMapping("posting/create")
	public void createPosting(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		String title = req.getParameter("postingTitle");
		String contents = req.getParameter("postingContents");
		String boardId = (String) session.getAttribute("boardId");
		String writerEmail = (String) session.getAttribute("memberEmail");
		
		Map<String, String> values = new HashMap<>();
		values.put("title", title);
		values.put("contents", contents);
		values.put("boardId", boardId);
		values.put("writerEmail", writerEmail);
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> request = new HttpEntity<Map<String, String>>(values, header);
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForLocation(RestApi.POSTING_CREATE_API, request);

		try {
			resp.sendRedirect("/postings/list");
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "postings/load", method = RequestMethod.GET)
	public void viewPosting(HttpServletRequest req, HttpServletResponse resp) {
		//
		String usid = req.getParameter("usid");
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(RestApi.POSTING_LOAD_API)
				.path(usid);
		
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(builder.toUriString(), String.class);
		
		PostingDto posting = new Gson().fromJson(result, PostingDto.class);

		req.setAttribute("selectedPosting", posting);
		try {
			req.getRequestDispatcher("/WEB-INF/views/postingDetail.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			//
			e.printStackTrace();
		}
	}//Done

	@RequestMapping(value = "postings/delete", method = RequestMethod.GET)
	public void deltePosting(HttpServletRequest req, HttpServletResponse resp) {
		//
		String usid = req.getParameter("usid");
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(RestApi.POSTING_DELETE_API)
				.path(usid);
		
		RestTemplate template = new RestTemplate();
		template.delete(builder.toUriString());
		
		req.setAttribute("usid", usid);

		try {
			req.getRequestDispatcher("/postings/list").forward(req, resp);
		} catch (ServletException | IOException e) {
			//
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "postings/modify", method = RequestMethod.POST)
	public void modifyPosting(HttpServletRequest req, HttpServletResponse resp) {
		//
		String title = req.getParameter("title");
		String contents = req.getParameter("contents");
		String email = req.getParameter("email");
		
		Map<String, String> values = new HashMap<>();
		values.put("title", title);
		values.put("contents", contents);
		values.put("email", email);
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> request = new HttpEntity<Map<String, String>>(values, header);
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(RestApi.POSTING_MODIFY_API, request);
		try {
			req.getRequestDispatcher("/postings/list").forward(req, resp);
		} catch (ServletException | IOException e) {
			//
			e.printStackTrace();
		}
	}
}
