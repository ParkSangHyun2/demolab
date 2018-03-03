/*
 * COPYRIGHT (c) NEXTREE Consulting 2016
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:eykim@nextree.co.kr">Kim, Eunyoung</a>
 * @since 2016. 7. 12.
 */
package step3.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import entity.board.SocialBoard;
import entity.club.TravelClub;
import entity.dto.BoardDto;
import step3.logic.storage.MapStorage;
import step3.service.BoardService;
import step3.util.BoardDuplicationException;
import step3.util.NoSuchBoardException;
import step3.util.NoSuchClubException;
import step3.util.NoSuchMemberException;

public class BoardServiceLogic implements BoardService {
	//
	private Map<String,SocialBoard> boardMap;
	private Map<String,TravelClub> clubMap;
	
	public BoardServiceLogic() {
		//
		this.boardMap = MapStorage.getInstance().getBoardMap();
		this.clubMap = MapStorage.getInstance().getClubMap();
	}
	
	@Override
	public String register(BoardDto boardDto) {
		//
		String boardId = boardDto.getId();
		SocialBoard boardFound = boardMap.get(boardId);
		if (boardFound != null) {
			throw new BoardDuplicationException("There is board already in the club -->" + boardFound.getName());
		}
		
		TravelClub clubFound = clubMap.get(boardId);
		if (clubFound == null) {
			throw new NoSuchClubException("No such a club with id: " + boardId);
		}
		if (clubFound.getMebershipBy(boardDto.getAdminEmail()) == null) {
			throw new NoSuchMemberException("In the club, No such member with admin's email -->" + boardDto.getAdminEmail());
		}
		
		SocialBoard board = boardDto.toBoard();
		boardMap.put(boardId, board);
		return boardId;
	}

	@Override
	public BoardDto find(String boardId) {
		//
		SocialBoard board = boardMap.get(boardId);
		if (board == null) {
			throw new NoSuchBoardException("No such board with id --> " + boardId);
		}
		
		return new BoardDto(board);
	}
	
	@Override
	public List<BoardDto> findByName(String boardName) {
		//
		Collection<SocialBoard> boards = boardMap.values();
		if (boards.isEmpty()) {
			throw new NoSuchBoardException("No boards in the stroage.");
		}
		
		List<BoardDto> boardDtos = new ArrayList<>();
		for (SocialBoard board : boards) {
			if (board.getName().equals(boardName)) {
				boardDtos.add(new BoardDto(board));
			}
		}
		
		if (boardDtos.isEmpty()) {
			throw new NoSuchBoardException("No such board with name --> " + boardName);
		}

		return boardDtos;
	}

	@Override
	public BoardDto findByClubName(String clubName) {
		//
		TravelClub foundClub = null;
		for (TravelClub club : clubMap.values()) {
			if (club.getName().equals(clubName)) {
				foundClub = club;
				break;
			}
		}
		
		if(foundClub == null) {
			throw new NoSuchClubException("No such a club with name: " + clubName);
		}
		
		SocialBoard foundBoard = boardMap.get(foundClub.getId());
		return new BoardDto(foundBoard);
	}

	@Override
	public void modify(BoardDto boardDto) {
		//
		String boardId = boardDto.getId();
		SocialBoard foundBoard = boardMap.get(boardId);
		if (foundBoard == null) {
			throw new NoSuchBoardException("No such board with id --> " + boardDto.getId());
		}
		
		boardMap.put(boardId, boardDto.toBoard());
	}

	@Override
	public void remove(String boardId) {
		//
		SocialBoard foundBoard = boardMap.get(boardId);
		if (foundBoard == null) {
			throw new NoSuchBoardException("No such board with id --> " + boardId);
		}
		
		boardMap.remove(boardId);
	}
}
