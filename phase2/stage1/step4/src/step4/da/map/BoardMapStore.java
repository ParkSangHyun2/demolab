/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package step4.da.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import entity.board.SocialBoard;
import step4.da.map.io.MemoryMap;
import step4.store.BoardStore;
import step4.util.MemberDuplicationException;

public class BoardMapStore implements BoardStore {
	//
	private Map<String,SocialBoard> boardMap; 
	
	public BoardMapStore() {
		//  
		this.boardMap = MemoryMap.getInstance().getBoardMap(); 
	}
	
	@Override
	public String create(SocialBoard board) {
		// 
		if (boardMap.get(board.getId()) != null) {
			throw new MemberDuplicationException("Member already exists with email: " + board.getId()); 
		}
		
		boardMap.put(board.getId(), board); 
		return board.getId();
	}

	@Override
	public SocialBoard retrieve(String boardId) {
		// 
		return boardMap.get(boardId); 
	}
	
	@Override
	public List<SocialBoard> retrieveByName(String name) {
		//
		List<SocialBoard> boards = new ArrayList<>(); 
		Iterator<SocialBoard> boardIter = boardMap.values().iterator(); 
		while(boardIter.hasNext()) {
			SocialBoard board = boardIter.next(); 
			if (board.getName().equals(name)) {
				boards.add(board); 
			}
		}
		
		return boards; 
	}

	@Override
	public void update(SocialBoard board) {
		// 
		if (boardMap.get(board.getId()) == null) {
			throw new NoSuchElementException("No such a element: " + board.getId()); 
		}
		
		boardMap.put(board.getId(), board); 
	}

	@Override
	public void delete(String boardId) {
		// 
		boardMap.remove(boardId);
	}

	@Override
	public boolean exists(String baordId) {
		//
		if(boardMap.get(baordId) != null) {
			return true; 
		};
		
		return false; 
	}
}