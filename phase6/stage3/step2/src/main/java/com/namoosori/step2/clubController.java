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
import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.RoleInClub;
import step1.share.domain.entity.club.TravelClub;
import step1.share.domain.entity.dto.BoardDto;
import step1.share.domain.entity.dto.ClubMembershipDto;
import step1.share.domain.entity.dto.TravelClubDto;
import step1.share.service.logic.BoardService;
import step1.share.service.logic.ClubService;

@Controller
@RequestMapping("/")
public class clubController {
	//
	private ClubService clubService;
	private BoardService boardService;

	@RequestMapping("club")
	public void showClubList(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		clubService = ServiceLogicLycler.shareInstance().createClubService();

		String memberEmail = (String) session.getAttribute("memberEmail");

		List<TravelClub> clubList = new ArrayList<>();
		List<TravelClubDto> myClubList = new ArrayList<>();

		clubList = clubService.findAllClub();

		for (TravelClub club : clubList) {
			for (ClubMembership membership : club.getMembershipList()) {
				if (membership.getMemberEmail().equals(memberEmail)) {
					myClubList.add(new TravelClubDto(club));
				}
			}
		}

		req.setAttribute("clubList", clubList);
		req.setAttribute("myClubList", myClubList);

		try {
			req.getRequestDispatcher("/WEB-INF/views/clubLists.jsp").forward(req, resp);
		} catch (ServletException e) {
			//
			e.printStackTrace();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "club/new")
	public void newClub(HttpServletRequest req, HttpServletResponse resp) {
		//
		try {
			req.getRequestDispatcher("/WEB-INF/views/newClub.jsp").forward(req, resp);
		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "club/create", method = RequestMethod.POST)
	public void createClub(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		clubService = ServiceLogicLycler.shareInstance().createClubService();
		boardService = ServiceLogicLycler.shareInstance().createBoardService();
		String name = req.getParameter("clubTitle");
		String intro = req.getParameter("clubIntro");
		String loggedInEmail = (String) session.getAttribute("memberEmail");
		TravelClubDto club = new TravelClubDto(name, intro);
		clubService.registerClub(club);

		TravelClubDto travelClub = clubService.findClubByName(name);
		ClubMembershipDto clubMembershipDto = new ClubMembershipDto(travelClub.getUsid(), loggedInEmail);
		clubMembershipDto.setRole(RoleInClub.President);

		clubService.addMembership(clubMembershipDto);
		boardService.register(new BoardDto(travelClub.getUsid(), travelClub.getName(), loggedInEmail));

		try {
			req.getRequestDispatcher("//clubMenu").forward(req, resp);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "club/withdraw", method = RequestMethod.GET)
	public void withdrawClub(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		int index = Integer.parseInt(req.getParameter("index"));
		String memberEmail = (String) session.getAttribute("memberEmail");
		String clubId = null;

		List<TravelClub> clubList = new ArrayList<>();
		List<TravelClubDto> myClubList = new ArrayList<>();
		TravelClubDto withDrawalClub;

		clubList = clubService.findAllClub();
		clubService = ServiceLogicLycler.shareInstance().createClubService();

		for (TravelClub club : clubList) {
			for (ClubMembership membership : club.getMembershipList()) {
				if (membership.getMemberEmail().equals(memberEmail)) {
					myClubList.add(new TravelClubDto(club));
				}
			}
		}
		withDrawalClub = myClubList.get(index);
		clubId = withDrawalClub.getUsid();

		try {
			for (ClubMembershipDto membership : withDrawalClub.getMembershipList()) {
				if (membership.getMemberEmail().equals(memberEmail)
						&& membership.getRole().toString().equals("President")) {
					clubService.remove(clubId);
					return;
				}
			}
			clubService.removeMembership(clubId, memberEmail);
		} finally {
			try {
				req.getRequestDispatcher("//clubMenu").forward(req, resp);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "club/join", method = RequestMethod.GET)
	public void joinClub(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		int index = Integer.parseInt(req.getParameter("index"));
		String memberEmail = (String) session.getAttribute("memberEmail");

		List<TravelClub> clubList = new ArrayList<>();
		TravelClub joinClub;

		clubList = clubService.findAllClub();
		clubService = ServiceLogicLycler.shareInstance().createClubService();

		joinClub = clubList.get(index);

		ClubMembershipDto membershipDto = new ClubMembershipDto(joinClub.getUsid(), memberEmail);
		membershipDto.setRole(RoleInClub.Member);
		clubService.addMembership(membershipDto);

		try {
			req.getRequestDispatcher("//clubMenu").forward(req, resp);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "club/posting", method = RequestMethod.GET)
	public void showPosting(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		//
		int index = Integer.parseInt(req.getParameter("index"));
		String usid = req.getParameter("boardId");

		String memberEmail = (String) session.getAttribute("memberEmail");
		clubService = ServiceLogicLycler.shareInstance().createClubService();

		List<TravelClub> clubList = new ArrayList<>();
		List<TravelClubDto> myClubList = new ArrayList<>();
		TravelClubDto postingOfClub;

		clubList = clubService.findAllClub();

		for (TravelClub club : clubList) {
			for (ClubMembership membership : club.getMembershipList()) {
				if (membership.getMemberEmail().equals(memberEmail)) {
					myClubList.add(new TravelClubDto(club));
				}
			}
		}

		postingOfClub = myClubList.get(index);

		session.setAttribute("travelClubDto", postingOfClub);
		session.setAttribute("boardId", usid);

		req.setAttribute("travelClubDto", postingOfClub);
		req.setAttribute("usid", usid);

		try {
			req.getRequestDispatcher("//postings").forward(req, resp);
		} catch (ServletException | IOException e) {
			//
			e.printStackTrace();
		}
	}
}
