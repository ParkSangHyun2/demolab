/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
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
		return boardQuery.read(boardId); 
	}
	
	@Override
	public List<SocialBoard> retrieveByName(String name) {
		//
		return boardQuery.readByName(name); 
	}

	@Override
	public void update(SocialBoard board) {
		// 
		if (!boardQuery.exists(board.getId())) {
			throw new NoSuchElementException("No such a element: " + board.getId()); 
		}
		
		//boardQuery.update(board);
		//unused
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