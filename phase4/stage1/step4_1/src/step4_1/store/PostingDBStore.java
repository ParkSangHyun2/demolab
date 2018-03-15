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

import step1.share.domain.entity.board.Posting;
import step1.share.service.store.PostingStore;
import step1.share.util.MemberDuplicationException;
import step4_1.store.io.PostingQuery;

public class PostingDBStore implements PostingStore {
	//
	private PostingQuery postingQuery; 
	
	public PostingDBStore() {
		//  
		this.postingQuery = new PostingQuery(); 
	}
	
	@Override
	public String create(Posting posting) {
		// 
		if (postingQuery.exists(posting.getId())) {
			throw new MemberDuplicationException("Already exists: " + posting.getId()); 
		}
		
		postingQuery.write(posting); 
		return posting.getId();
	}

	@Override
	public Posting retrieve(String postingId) {
		// 
		return postingQuery.read(postingId); 
	}
	
	@Override
	public List<Posting> retrieveByBoardId(String boardId){
		// 
		return postingQuery.readByBoardId(boardId); 
	}
	
	@Override
	public List<Posting> retrieveByTitle(String name) {
		//
		return postingQuery.readByTitle(name); 
	}

	@Override
	public void update(Posting posting) {
		// 
		if (!postingQuery.exists(posting.getId())) {
			throw new NoSuchElementException("No such a element: " + posting.getId()); 
		}
		
		postingQuery.update(posting);
	}

	@Override
	public void delete(String postingId) {
		// 
		postingQuery.delete(postingId);
	}

	@Override
	public boolean exists(String postingId) {
		//
		return postingQuery.exists(postingId);
	}
}