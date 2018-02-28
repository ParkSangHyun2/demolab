package fxui.event;

import java.util.Collection;

import entity.dto.TravelClubDto;
import fxui.util.AlertBox;

public class ClubEventHelper {

	public void createClub(String clubName, String clubIntroduce) {
		//
		AlertBox.alert("Info", clubName + ", " +clubIntroduce+ "Press CreateClubBtn");
		//boardEvents.createBoard();
	}

	public void withdrawalClub(TravelClubDto next) {
		// TODO Auto-generated method stub
		
	}

	public void joinClub(TravelClubDto next) {
		// TODO Auto-generated method stub
		
	}

	public Collection<TravelClubDto> searchClub(String clubName) {
		return null;
		// TODO Auto-generated method stub
		
	}

	public Collection<TravelClubDto> searchAllClub() {
		return null;
		// TODO Auto-generated method stub
		
	}

}
