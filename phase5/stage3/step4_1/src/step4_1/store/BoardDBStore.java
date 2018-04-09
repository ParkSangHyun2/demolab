package step4_1.store;

import java.util.List;
import java.util.NoSuchElementException;

import step1.share.domain.entity.board.SocialBoard;
import step1.share.service.store.BoardStore;
import step1.share.util.MemberDuplicationException;
import step4_1.store.io.BoardQuery;

public class BoardDBStore implements BoardStore {
	//
	private BoardQuery boardQuery; 
	
	public BoardDBStore() {
		//  
		this.boardQuery = new BoardQuery(); 
	}
	
	@Override
	public String create(SocialBoard board) {
		// 
		if (boardQuery.exists(board.getId())) {
			throw new MemberDuplicationException("Member already exists with email: " + board.getId()); 
		}
		
		boardQuery.write(board); 
		return board.getId();
	}

	@Override
	public SocialBoard retrieve(String boardId) {
		// 
		SocialBoard socialBoard = boardQuery.read(boardId);
		if(socialBoard.getClubId() == null) {
			return null;
		}
		return socialBoard;
	}
	
	@Override
	public List<SocialBoard> retrieveByName(String name) {
		//
		List<SocialBoard> socialBoards = boardQuery.readByName(name);
		if(socialBoards.isEmpty()) {
			return null;
		}
		return socialBoards;
	}

	@Override
	public void update(SocialBoard board) {
		// 
		if (!boardQuery.exists(board.getId())) {
			throw new NoSuchElementException("No such a element: " + board.getId()); 
		}
	}

	@Override
	public void delete(String boardId) {
		// 
		boardQuery.delete(boardId);
	}

	@Override
	public boolean exists(String baordId) {
		//
		return boardQuery.exists(baordId);
	}
}