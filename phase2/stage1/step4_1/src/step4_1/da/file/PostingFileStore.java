/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package step4_1.da.file;

import java.util.List;
import java.util.NoSuchElementException;

import entity.board.Posting;
import step4_1.da.file.io.PostingFile;
import step4_1.store.PostingStore;
import step4_1.util.MemberDuplicationException;

public class PostingFileStore implements PostingStore {
	//
	private PostingFile postingFile; 
	
	public PostingFileStore() {
		//  
		this.postingFile = new PostingFile(); 
	}
	
	@Override
	public String create(Posting posting) {
		// 
		if (postingFile.exists(posting.getId())) {
			throw new MemberDuplicationException("Already exists: " + posting.getId()); 
		}
		
		postingFile.write(posting); 
		return posting.getId();
	}

	@Override
	public Posting retrieve(String postingId) {
		// 
		return postingFile.read(postingId); 
	}
	
	@Override
	public List<Posting> retrieveByBoardId(String boardId){
		// 
		return postingFile.readByBoardId(boardId); 
	}
	
	@Override
	public List<Posting> retrieveByTitle(String name) {
		//
		return postingFile.readByTitle(name); 
	}

	@Override
	public void update(Posting posting) {
		// 
		if (!postingFile.exists(posting.getId())) {
			throw new NoSuchElementException("No such a element: " + posting.getId()); 
		}
		
		postingFile.update(posting); 
	}

	@Override
	public void delete(String postingId) {
		// 
		postingFile.delete(postingId);
	}

	@Override
	public boolean exists(String postingId) {
		//
		return postingFile.exists(postingId);
	}
}