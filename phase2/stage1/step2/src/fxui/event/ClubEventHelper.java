package fxui.event;

import java.util.ArrayList;
import java.util.Collection;

import entity.club.RoleInClub;
import entity.club.TravelClub;
import entity.dto.ClubMembershipDto;
import entity.dto.TravelClubDto;
import fxui.Session;
import fxui.util.AlertBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import step3.logic.ServiceLogicLycler;
import step3.service.ClubService;
import step3.service.MemberService;

public class ClubEventHelper {
	private ClubService clubService = ServiceLogicLycler.shareInstance().createClubService();
	private MemberService memberService = ServiceLogicLycler.shareInstance().createMemberService();

	public void createClub(String clubName, String clubIntroduce) {
		//
		clubService.registerClub(new TravelClubDto(clubName, clubIntroduce));
		TravelClubDto travelClub = clubService.findClubByName(clubName);
		ClubMembershipDto clubMembershipDto = new ClubMembershipDto(travelClub.getUsid(),Session.loggedInMemberEmail);
		clubMembershipDto.setRole(RoleInClub.President);
		clubService.addMembership(clubMembershipDto);
		
		AlertBox.alert("Info", clubName + " Registed!");
	}

	public void withdrawalClub(TravelClubDto next) {
		//
		String clubId = next.getUsid();
		String memberId = Session.loggedInMemberEmail;
		clubService.removeMembership(clubId, memberId);
		AlertBox.alert("Info", "Withdrawal");
	}

	public void joinClub(TravelClubDto travelClubDto) {
		//
		ClubMembershipDto membershipDto = new ClubMembershipDto(travelClubDto.getUsid(),Session.loggedInMemberEmail);
		membershipDto.setRole(RoleInClub.Member);
		clubService.addMembership(membershipDto);
		AlertBox.alert("Info", "Join!");
	}

	public void searchClub(String clubName, TableView<TravelClubDto> clubTable) {
		//
		TravelClubDto club = clubService.findClubByName(clubName);
		ArrayList<TravelClubDto> clubList = new ArrayList<>();
		clubList.add(club);
		clubTable.setItems(setClubTable(clubList));
	}

	public void searchAllClub(TableView<TravelClubDto> clubTable) {
		ArrayList<TravelClubDto> travelClubs = new ArrayList<>();
		for (TravelClub club : clubService.findAllClub()) {
			travelClubs.add(new TravelClubDto(club));
		}
		
		clubTable.setItems(setClubTable(travelClubs));
	}
	
	private ObservableList<TravelClubDto> setClubTable(Collection<TravelClubDto> travelClubs) {
		//
		ObservableList<TravelClubDto> allClubList = FXCollections.observableArrayList();
		allClubList.addAll(travelClubs);
		return allClubList;
	}

}
