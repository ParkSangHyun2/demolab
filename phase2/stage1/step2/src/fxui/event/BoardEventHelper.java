package fxui.event;

import java.util.ArrayList;
import java.util.Collection;

import entity.dto.PostingDto;
import entity.dto.TravelClubDto;
import fxui.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import step3.logic.ServiceLogicLycler;
import step3.service.PostingService;

public class BoardEventHelper {
	//
	private TravelClubDto travelClub;
	private PostingService postingService = ServiceLogicLycler.shareInstance().createPostingService();
	
	public BoardEventHelper(TravelClubDto travelClub) {
		//
		this.travelClub = travelClub;
	}

	public void setPostList(TableView<PostingDto> postingTable, TravelClubDto travelClubDto) {
		//
		ArrayList<PostingDto> postingList = null;
		try {
		postingList = 
				(ArrayList<PostingDto>) postingService.findByBoardId(travelClubDto.getUsid());
		}catch(Exception e) {
			e.getMessage();
		}finally {
			if(postingList != null) {
				postingTable.setItems(this.setPostingTable(postingList));
			}
		}
		
	}

	public void modifyPosting(PostingDto posting) {
		//
		postingService.modify(posting);
	}

	public void deletePosting(PostingDto posting) {
		//
		postingService.remove(posting.getUsid());
	}

	public void createPosting(String title, String article, TravelClubDto travelClubDto) {
		// TODO Auto-generated method stub
		postingService.register(travelClubDto.getUsid(), new PostingDto(title,Session.loggedInMemberEmail,article));
	}
	
	private ObservableList<PostingDto> setPostingTable(Collection<PostingDto> postings) {
		//
		ObservableList<PostingDto> allPostingList = FXCollections.observableArrayList();
		allPostingList.addAll(postings);
		return allPostingList;
	}
}
