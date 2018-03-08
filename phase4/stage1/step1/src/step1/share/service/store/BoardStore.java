/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package step1.share.service.store;

import java.util.List;

import step1.share.domain.entity.board.SocialBoard;

public interface BoardStore {
	//
	public String create(SocialBoard board); 
	public SocialBoard retrieve(String boardId); 
	public List<SocialBoard> retrieveByName(String boardId); 
	public void update(SocialBoard board); 
	public void delete(String boardId); 
	
	public boolean exists(String boardId); 
}