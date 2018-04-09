package com.namoosori.step2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import logic.ServiceLogicLycler;
import step1.share.domain.entity.dto.PostingDto;
import step1.share.domain.entity.dto.TravelClubDto;
import step1.share.service.logic.PostingService;

@Controller
@RequestMapping("/")
public class PostingController {
	//
	private PostingService postingService;

	@RequestMapping("postings")
	public void setPostingList(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		postingService = ServiceLogicLycler.shareInstance().createPostingService();

		TravelClubDto travelClubDto = (TravelClubDto) session.getAttribute("travelClubDto");
		List<PostingDto> postingList = new ArrayList<>();
		try {
			postingList = (ArrayList<PostingDto>) postingService.findByBoardId(travelClubDto.getUsid());
			req.setAttribute("postingList", postingList);
			req.getRequestDispatcher("/WEB-INF/views/posting.jsp").forward(req, resp);
		} catch (Exception e) {
			e.getMessage();

		}
	}

	@RequestMapping("postings/new")
	public void newPosting(HttpServletRequest req, HttpServletResponse resp) {
		//
		try {
			req.getRequestDispatcher("/WEB-INF/views/newposting.jsp").forward(req, resp);
		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("posting/create")
	public void createPosting(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		String title = req.getParameter("postingTitle");
		String contents = req.getParameter("postingContents");
		String boardId = (String) session.getAttribute("boardId");
		String writerEmail = (String) session.getAttribute("memberEmail");

		PostingDto posting = new PostingDto(title, writerEmail, contents);

		postingService.register(boardId, posting);
		try {
			req.getRequestDispatcher("//postings").forward(req, resp);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "postings/load", method = RequestMethod.GET)
	public void viewPosting(HttpServletRequest req, HttpServletResponse resp) {
		//
		String usid = req.getParameter("usid");
		PostingDto posting = postingService.find(usid);

		posting.setReadCount(posting.getReadCount() + 1);
		postingService.modify(posting);

		req.setAttribute("selectedPosting", posting);
		try {
			req.getRequestDispatcher("/WEB-INF/views/postingDetail.jsp").forward(req, resp);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "postings/delete", method = RequestMethod.GET)
	public void deltePosting(HttpServletRequest req, HttpServletResponse resp) {
		//
		String usid = req.getParameter("usid");
		PostingDto posting = postingService.find(usid);

		postingService.remove(posting.getUsid());
		req.setAttribute("usid", usid);

		try {
			req.getRequestDispatcher("//postings").forward(req, resp);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "postings/modify", method = RequestMethod.POST)
	public void modifyPosting(HttpServletRequest req, HttpServletResponse resp) {
		//
		String title = req.getParameter("title");
		String contents = req.getParameter("contents");
		String email = req.getParameter("email");

		PostingDto posting = new PostingDto(title, email, contents);
		postingService.modify(posting);

		try {
			req.getRequestDispatcher("//postings").forward(req, resp);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
