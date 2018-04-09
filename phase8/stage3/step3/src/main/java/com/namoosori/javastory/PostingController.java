package com.namoosori.javastory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import logic.ServiceLogicLycler;
import step1.share.domain.entity.dto.PostingDto;
import step1.share.service.logic.PostingService;
import step1.share.util.NoSuchBoardException;
import step1.share.util.NoSuchPostingException;

@CrossOrigin
@RestController
@RequestMapping("/")
public class PostingController {
	//
	private PostingService postingService = null;

	@GetMapping("posting/usid/{usid}")
	public PostingDto searchPosting(@PathVariable String usid) {
		//
		postingService = ServiceLogicLycler.shareInstance().createPostingService();
		PostingDto posting = postingService.find(usid);

		posting.setReadCount(posting.getReadCount() + 1);
		try {
		postingService.modify(posting);
		} catch(NoSuchPostingException e) {
			System.out.println("Posting Search Exception --> " + e.getMessage());
		}

		return posting;
	}

	@GetMapping("posting/list")
	public List<PostingDto> getPostingList(@RequestParam(value = "clubId") String clubId) {
		//
		postingService = ServiceLogicLycler.shareInstance().createPostingService();

		List<PostingDto> postingList = new ArrayList<>();
		try {
		postingList = (ArrayList<PostingDto>) postingService.findByBoardId(clubId);
		} catch(NoSuchBoardException e) {
			System.out.println("Posting List Exception --> " + e.getMessage());
		}
		return postingList;
	}
	
	@PostMapping("posting/create")
	public void createPosting(@RequestBody String postingInfo) {
		//
		postingService = ServiceLogicLycler.shareInstance().createPostingService();
		
		 Map<String, String> values =
				 new Gson().fromJson(postingInfo, new TypeToken<Map<String,String>>(){}.getType());
		 
		String title = values.get("title");
		String contents = values.get("contents");
		String boardId = values.get("boardId");
		String writerEmail = values.get("writerEmail");
		PostingDto posting = new PostingDto(title, writerEmail, contents);	
		try {
			postingService.register(boardId, posting);
		} catch(NoSuchBoardException e) {
			e.getMessage();
		}
	}
	
	@DeleteMapping("posting/delete/{usid}")
	public void deletePosting(@PathVariable String usid) {
		//
		postingService = ServiceLogicLycler.shareInstance().createPostingService();
		
		PostingDto posting = postingService.find(usid);
		try {
		postingService.remove(posting.getUsid());
		} catch(NoSuchPostingException e){
			System.out.println("Posting Delete Exception --> " + e.getMessage());
		}
	}
	
	@PutMapping("posting/modify")
	public void modifyPosting(@RequestBody String postingInfo) {
		//
		postingService = ServiceLogicLycler.shareInstance().createPostingService();
		
		Map<String, String> values = 
				new Gson().fromJson(postingInfo, new TypeToken<Map<String, String>>(){}.getType());
		
		String title = values.get("title");
		String contents = values.get("contents");
		String email = values.get("email");
		
		PostingDto posting = new PostingDto(title, email, contents);
		try {
		postingService.modify(posting);
		} catch(NoSuchPostingException e) {
			System.out.println("Posting Exception -->" + e.getMessage());
		}
	}
}
