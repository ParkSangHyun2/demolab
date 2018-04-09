package com.namoosori.javastory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.RoleInClub;
import step1.share.domain.entity.club.TravelClub;
import step1.share.domain.entity.dto.BoardDto;
import step1.share.domain.entity.dto.ClubMembershipDto;
import step1.share.domain.entity.dto.TravelClubDto;
import step1.share.service.logic.BoardService;
import step1.share.service.logic.ClubService;
import step1.share.util.ClubDuplicationException;

@CrossOrigin
@RestController
@RequestMapping("/")
public class ClubController {
	//
	private ClubService clubService = null;
	private BoardService boardService = null;

	@GetMapping("club/search/all")
	public List<TravelClub> searchAll() {
		//
		clubService = ServiceLogicLycler.shareInstance().createClubService();
		List<TravelClub> clubList = new ArrayList<>();

		clubList = clubService.findAllClub();

		return clubList;
	}

	@PutMapping("club/join")
	public void joinClub(@RequestBody String value) {
		//
		Map<String, String> values = new HashMap<String, String>();
		values = new Gson().fromJson(value, new TypeToken<Map<String, String>>(){}.getType());
		String index = values.get("index");
		String memberEmail = values.get("memberEmail");
		
		List<TravelClub> clubList = new ArrayList<>();
		TravelClub joinClub;

		clubList = clubService.findAllClub();
		clubService = ServiceLogicLycler.shareInstance().createClubService();

		joinClub = clubList.get(Integer.parseInt(index));

		ClubMembershipDto membershipDto = new ClubMembershipDto(joinClub.getUsid(), memberEmail);
		membershipDto.setRole(RoleInClub.Member);
		clubService.addMembership(membershipDto);
	}

	@GetMapping("club/search/myclub")
	public List<TravelClubDto> searchClub(@RequestParam(value="memberEmail") String memberEmail) {
		//
		clubService = ServiceLogicLycler.shareInstance().createClubService();

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
		return myClubList;
	}

	@DeleteMapping("club/withdrawal")
	public void deleteClub(@RequestParam("index")String index,
			@RequestParam("memberEmail")String memberEmail) {
		//
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
		withDrawalClub = myClubList.get(Integer.parseInt(index));
		clubId = withDrawalClub.getUsid();

		for (ClubMembershipDto membership : withDrawalClub.getMembershipList()) {
			if (membership.getMemberEmail().equals(memberEmail)
					&& membership.getRole().toString().equals("President")) {
				clubService.remove(clubId);
				return;
			}
		}
		clubService.removeMembership(clubId, memberEmail);
	}

	@PostMapping("club/create")
	public String createClub(@RequestBody String values) {
		//
		clubService = ServiceLogicLycler.shareInstance().createClubService();
		boardService = ServiceLogicLycler.shareInstance().createBoardService();

		Map<String, String> valueMap = new HashMap<>();
		valueMap = new Gson().fromJson(values, new TypeToken<Map<String, String>>(){}.getType());
		
		String title = valueMap.get("title");
		String intro = valueMap.get("intro");
		String memberEmail = valueMap.get("loggedInEmail");
		
		TravelClubDto club = new TravelClubDto(title, intro);
		try {
			clubService.registerClub(club);
		} catch (ClubDuplicationException e) {
			System.out.println("Exception -->" + e.getMessage());
		}
		TravelClubDto foundClub = clubService.findClubByName(club.getName());
		ClubMembershipDto membershipDto = new ClubMembershipDto(foundClub.getUsid(), memberEmail);
		membershipDto.setRole(RoleInClub.President);

		clubService.addMembership(membershipDto);
		boardService.register(new BoardDto(foundClub.getUsid(), foundClub.getName(), memberEmail));
		
		return "SUCCESS";
	}
}
