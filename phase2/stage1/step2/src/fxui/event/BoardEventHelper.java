package fxui.event;

import java.util.ArrayList;
import java.util.Collection;

import entity.dto.PostingDto;
import entity.dto.TravelClubDto;
import fxui.Session;
import fxui.util.AlertBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
		for(PostingDto posting : postingList) {
		}
		}catch(Exception e) {
			e.getMessage();
		}finally {
			if(postingList != null) {
				postingTable.setItems(this.setPostingTable(postingList));
			}
		}
		
	}

	public void modifyPosting(PostingDto posting, TextField titleField, TextArea articleField) {
		//
		if(this.checkMemberPermission(posting)) {
			posting.setTitle(titleField.getText());
			posting.setContents(articleField.getText());
			postingService.modify(posting);
		}else {
			AlertBox.alert("Info", "Permission Denied");
		}
	}

	public void deletePosting(PostingDto posting) {
		//
		if(this.checkMemberPermission(posting)) {
			postingService.remove(posting.getUsid());
		}else {
			AlertBox.alert("info", "Permission Denied");
		}
		
	}

	public void createPosting(String title, String article, TravelClubDto travelClubDto) {
		//
		postingService.register(travelClubDto.getUsid(), new PostingDto(title,Session.loggedInMemberEmail,article));
	}
	
	private ObservableList<PostingDto> setPostingTable(Collection<PostingDto> postings) {
		//
		ObservableList<PostingDto> allPostingList = FXCollections.observableArrayList();
		allPostingList.addAll(postings);
		return allPostingList;
	}
	
	private boolean checkMemberPermission(PostingDto posting) {
		//
		boolean isPermitted = false;
		if(posting.getWriterEmail().equals(Session.loggedInMemberEmail)) {
			isPermitted = true;
		}
		return isPermitted;
	}
}
