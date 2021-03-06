package step2.client.fxui.event;

import java.util.ArrayList;
import java.util.Collection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.RoleInClub;
import step1.share.domain.entity.club.TravelClub;
import step1.share.domain.entity.dto.BoardDto;
import step1.share.domain.entity.dto.ClubMembershipDto;
import step1.share.domain.entity.dto.TravelClubDto;
import step1.share.service.logic.BoardService;
import step1.share.service.logic.ClubService;
import step1.share.service.logic.MemberService;
import step2.client.fxui.Session;
import step2.client.fxui.util.AlertBox;
import step2.client.transfer.stub.service.ServiceLogicLycler;

public class ClubEventHelper {
	private ClubService clubService = ServiceLogicLycler.shareInstance().createClubService();
	private BoardService boardService = ServiceLogicLycler.shareInstance().createBoardService();

	public void createClub(String clubName, String clubIntroduce) {
		//
		TravelClubDto clubDto = new TravelClubDto(clubName, clubIntroduce);
		clubDto.setUsid("0");

		clubService.registerClub(clubDto);
		TravelClubDto travelClub = clubService.findClubByName(clubName);
		ClubMembershipDto clubMembershipDto = new ClubMembershipDto(travelClub.getUsid(), Session.loggedInMemberEmail);
		clubMembershipDto.setRole(RoleInClub.President);

		clubService.addMembership(clubMembershipDto);
		boardService.register(new BoardDto(travelClub.getUsid(),travelClub.getName(), Session.loggedInMemberEmail));
		
		AlertBox.alert("Info", clubName + " Registed!");
	}

	public void withdrawalClub(TravelClubDto next) {
		//
		String clubId = next.getUsid();
		String memberId = Session.loggedInMemberEmail;
		for(ClubMembershipDto membership : next.getMembershipList() ) {
			if (membership.getMemberEmail().equals(memberId) && membership.getRole().toString().equals("President")) {
				clubService.remove(clubId);
				return;
			}
		}
		clubService.removeMembership(clubId, memberId);
		AlertBox.alert("Info", "Withdrawal");
	}

	public void joinClub(TravelClubDto travelClubDto) {
		//
		ClubMembershipDto membershipDto = new ClubMembershipDto(travelClubDto.getUsid(), Session.loggedInMemberEmail);
		membershipDto.setRole(RoleInClub.Member);
		clubService.addMembership(membershipDto);
		AlertBox.alert("Info", "Join!");
	}

	public void searchClub(String clubName, TableView<TravelClubDto> clubTable) {
		//
		ArrayList<TravelClubDto> clubList = new ArrayList<>();
		for (TravelClub club : clubService.findAllClub()) {
			if (club.getName().contains(clubName)) {
				clubList.add(new TravelClubDto(club));
			}
		}

		clubTable.setItems(setClubTable(clubList));
	}

	public void searchAllClub(TableView<TravelClubDto> clubTable) {
		ArrayList<TravelClubDto> travelClubs = new ArrayList<>();
		for (TravelClub club : clubService.findAllClub()) {
			travelClubs.add(new TravelClubDto(club));
		}

		clubTable.setItems(setClubTable(travelClubs));
	}

	public void searchMyClubs(TableView<TravelClubDto> myClubTable, String memberEmail) {
		//
		ArrayList<TravelClubDto> clubList = new ArrayList<>();
		try {
			for (TravelClub club : clubService.findAllClub()) {
				for (ClubMembership membership : club.getMembershipList()) {
					if (membership.getMemberEmail().equals(memberEmail)) {
						clubList.add(new TravelClubDto(club));
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("myClubs is null..");
		}finally {
			if (clubList != null) {
				myClubTable.setItems(this.setClubTable(clubList));
			}
		}
	}

	private ObservableList<TravelClubDto> setClubTable(Collection<TravelClubDto> travelClubs) {
		//
		ObservableList<TravelClubDto> allClubList = FXCollections.observableArrayList();
		allClubList.addAll(travelClubs);
		return allClubList;
	}

}
