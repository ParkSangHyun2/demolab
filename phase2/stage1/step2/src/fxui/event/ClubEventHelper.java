package fxui.event;

import java.util.List;

import entity.dto.TravelClubDto;
import fxui.util.AlertBox;

public class ClubEventHelper {

	public void createClub(String clubName, String clubIntroduce) {
		//
		AlertBox.alert("Info", clubName + ", " +clubIntroduce+ "Press CreateClubBtn");
	}

	public void withdrawalClub(TravelClubDto next) {
		// TODO Auto-generated method stub
		
	}

	public void joinClub(TravelClubDto next) {
		// TODO Auto-generated method stub
		
	}

	public List<TravelClubDto> searchClub(String clubName) {
		return null;
		// TODO Auto-generated method stub
		
	}

	public List<TravelClubDto> searchAllClub() {
		return null;
		// TODO Auto-generated method stub
		
	}

}
