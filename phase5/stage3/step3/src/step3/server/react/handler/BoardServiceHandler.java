package step3.server.react.handler;

import java.util.List;

import com.google.gson.Gson;

import step1.share.domain.entity.dto.BoardDto;
import step1.share.service.logic.BoardService;
import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;
import step3.server.logic.ServiceLogicLycler;

public class BoardServiceHandler implements ServiceHandler {
	//
	private BoardService boardService;
	
	public BoardServiceHandler() {
		//
		boardService = ServiceLogicLycler.shareInstance().createBoardService();
	}

	@Override
	public ResponseMessage handle(RequestMessage request) {
		//
		String operation = request.getOperation();
		String responseValue = null;
		String boardId, boardName, clubName;
		BoardDto boardDto = null;
		
		switch(operation) {
		case "register":
			boardDto = (new Gson()).fromJson(request.getValue(), BoardDto.class);
			boardName = boardService.register(boardDto);
			responseValue = boardName;
			break;
			
		case "find":
			boardId = request.getValue();
			boardDto = boardService.find(boardId);
			responseValue = (new Gson()).toJson(boardDto);
			break;
			
		case "findByName":
			boardName = request.getValue();
			List<BoardDto> foundBoardList = boardService.findByName(boardName);
			responseValue = (new Gson()).toJson(foundBoardList);
			break;
			
		case "findByClubName":
			clubName = request.getValue();
			boardDto = boardService.findByClubName(clubName);
			responseValue = (new Gson()).toJson(boardDto);
			break;
			
		case "modify":
			boardDto = (new Gson()).fromJson(request.getValue(), BoardDto.class);
			boardService.modify(boardDto);
			responseValue = "Success";
			break;
			
		case "remove":
			boardId = request.getValue();
			boardService.remove(boardId);
			responseValue = "Success";
			break;
		}
		return new ResponseMessage(operation, responseValue);
	}

}
