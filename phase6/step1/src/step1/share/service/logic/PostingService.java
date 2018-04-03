/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package step1.share.service.logic;
import java.util.List;

import step1.share.domain.entity.dto.PostingDto;

public interface PostingService {
	//
	public String register(String boardId, PostingDto posting); 
	public PostingDto find(String postingId); 
	public List<PostingDto> findByBoardId(String boardId);
	public void modify(PostingDto newPosting);
	public void remove(String postingId);
	public void removeAllIn(String boardId);
}