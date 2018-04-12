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

import entity.AutoIdEntity;
import entity.board.Posting;
import entity.club.TravelClub;
import step4_1.da.file.io.AutoIdFile;
import step4_1.da.file.io.AutoIdSequence;
import step4_1.da.file.io.PostingFile;
import step4_1.store.PostingStore;
import step4_1.util.MemberDuplicationException;

public class PostingFileStore implements PostingStore {
	//
	private PostingFile postingFile; 
	private AutoIdFile autoIdFile;
	
	public PostingFileStore() {
		//  
		this.postingFile = new PostingFile();
		this.autoIdFile = new AutoIdFile();
	}
	
	@Override
	public String create(Posting posting) {
		// 
		if (postingFile.exists(posting.getId())) {
			throw new MemberDuplicationException("Already exists: " + posting.getId()); 
		}
		if (posting instanceof AutoIdEntity) {
			String className = Posting.class.getSimpleName(); 
			
			if(autoIdFile.read(className) == null) {
				autoIdFile.write(new AutoIdSequence(className));  
			}
			AutoIdSequence autoIdSequence = autoIdFile.read(className); 
			posting.setAutoId(String.format("%05d", autoIdSequence.nextSequence()));  
			
			autoIdFile.update(autoIdSequence);
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