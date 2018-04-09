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

import step1.share.domain.entity.club.TravelClub;
import step1.share.domain.entity.dto.TravelClubDto;

@Controller
@RequestMapping("/")
public class clubController {
	//
	@RequestMapping(value="club/clublists")
	public ModelAndView showClubList(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		ModelAndView view = new ModelAndView("clubLists");

		String memberEmail = (String) session.getAttribute("memberEmail");

		List<TravelClub> clubList = new ArrayList<>();
		List<TravelClubDto> myClubList = new ArrayList<>();
		RestTemplate restTemplate = new RestTemplate();

		String clubResult = restTemplate.getForObject(RestApi.CLUB_SEARCH_ALL_API, String.class);
		clubList = new Gson().fromJson(clubResult, new TypeToken<ArrayList<TravelClub>>() {
		}.getType());

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(RestApi.CLUB_SEARCH_MY_API)
				.queryParam("memberEmail", memberEmail);
		
		String myClubResult = restTemplate.getForObject(builder.toUriString(), String.class);
		myClubList = new Gson().fromJson(myClubResult, new TypeToken<ArrayList<TravelClubDto>>() {
		}.getType());

		view.addObject("clubList", clubList);
		view.addObject("myClubList", myClubList);

		return view;
	}

	@RequestMapping(value = "club/new")
	public ModelAndView newClub(HttpServletRequest req, HttpServletResponse resp) {
		//
		ModelAndView view = new ModelAndView("newClub");
		return view;
	}

	/*
	 * jQuery로 옮길예정...
	 */
	@RequestMapping(value = "club/create", method = RequestMethod.POST)
	public void createClub(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		String name = req.getParameter("clubTitle");
		String intro = req.getParameter("clubIntro");
		String loggedInEmail = (String) session.getAttribute("memberEmail");
		
		Map<String, String> values = new HashMap<>();
		values.put("title", name);
		values.put("intro", intro);
		values.put("loggedInEmail", loggedInEmail);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> request = new HttpEntity<Map<String, String>>(values, header);

		RestTemplate restTemplate = new RestTemplate();	
		restTemplate.postForObject(RestApi.CLUB_CREATE_API, request, String.class);

		try {
			resp.sendRedirect("/clubMenu");
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "club/withdraw", method = RequestMethod.GET)
	public void withdrawClub(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		int index = Integer.parseInt(req.getParameter("index"));
		String memberEmail = (String) session.getAttribute("memberEmail");
		
		RestTemplate restTemplate = new RestTemplate();	
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(RestApi.CLUB_DELETE_API)
				.queryParam("index", index)
				.queryParam("memberEmail", memberEmail);
		
		restTemplate.delete(builder.toUriString());
		try {
			resp.sendRedirect("/clubMenu");
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "club/join", method = RequestMethod.GET)
	public void joinClub(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		String index = req.getParameter("index");
		String memberEmail = (String) session.getAttribute("memberEmail");
		Map<String,String> values = new HashMap<>();
		values.put("index", index);
		values.put("memberEmail", memberEmail);
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(new Gson().toJson(values), header);
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(RestApi.CLUB_JOIN_API, request);

		try {
			req.getRequestDispatcher("//clubMenu").forward(req, resp);
		} catch (ServletException | IOException e) {
			//
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "club/posting", method = RequestMethod.GET)
	public void showPosting(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		List<TravelClubDto> myClubList = new ArrayList<>();
		TravelClubDto postingOfClub = null;
		
		int index = Integer.parseInt(req.getParameter("index"));
		String usid = req.getParameter("boardId");
		String memberEmail = (String) session.getAttribute("memberEmail");
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(RestApi.CLUB_SEARCH_MY_API)
				.queryParam("memberEmail", memberEmail);
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(builder.toUriString(), String.class);
		myClubList = new Gson().fromJson(result, new TypeToken<List<TravelClubDto>>() {}.getType());
		
		postingOfClub = myClubList.get(index);
		session.setAttribute("travelClubDto", postingOfClub);
		session.setAttribute("boardId", usid);
		
		try {
			resp.sendRedirect("/postings/list");
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
	}
}
