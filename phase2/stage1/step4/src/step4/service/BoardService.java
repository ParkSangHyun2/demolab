/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package step4.service;

import java.util.List;

import entity.dto.BoardDto;

public interface BoardService {
	//
	public String register(BoardDto board); 
	public BoardDto find(String boardId);
	public List<BoardDto> findByName(String boardName);
	public BoardDto findByClubName(String clubName);
	public void modify(BoardDto board); 
	public void remove(String boardId);
}
