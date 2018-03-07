/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package step3.server.store;

import java.util.List;
import java.util.NoSuchElementException;

import step1.share.domain.entity.board.SocialBoard;
import step1.share.service.store.BoardStore;
import step1.share.util.MemberDuplicationException;
import step3.server.store.io.BoardFile;

public class BoardFileStore implements BoardStore {
	//
	private BoardFile boardFile; 
	
	public BoardFileStore() {
		//  
		this.boardFile = new BoardFile(); 
	}
	
	@Override
	public String create(SocialBoard board) {
		// 
		if (boardFile.exists(board.getId())) {
			throw new MemberDuplicationException("Member already exists with email: " + board.getId()); 
		}
		
		boardFile.write(board); 
		return board.getId();
	}

	@Override
	public SocialBoard retrieve(String boardId) {
		// 
		return boardFile.read(boardId); 
	}
	
	@Override
	public List<SocialBoard> retrieveByName(String name) {
		//
		return boardFile.readByName(name); 
	}

	@Override
	public void update(SocialBoard board) {
		// 
		if (!boardFile.exists(board.getId())) {
			throw new NoSuchElementException("No such a element: " + board.getId()); 
		}
		
		boardFile.update(board); 
	}

	@Override
	public void delete(String boardId) {
		// 
		boardFile.delete(boardId);
	}

	@Override
	public boolean exists(String baordId) {
		//
		return boardFile.exists(baordId);
	}
}