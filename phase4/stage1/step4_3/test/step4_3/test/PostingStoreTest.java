package step4_3.test;

import junit.framework.TestCase;
import step1.share.domain.entity.board.Posting;
import step4_3.hibernate.store.PostingStoreService;

public class PostingStoreTest extends TestCase {
	//
	PostingStoreService postingStore = null;
	
	public void testPostingStoreService() {
		postingStore = new PostingStoreService();
	}

	public void testCreate() {
		//
		this.testPostingStoreService();
		Posting posting = new Posting();
		posting.setUsid(Long.toString(System.currentTimeMillis() %1000));
		posting.setBoardId("00021");
		
		postingStore.create(posting);
	}

	public void testRetrieve() {
		//
		
	}

	public void testRetrieveByBoardId() {
		fail("Not yet implemented");
	}

	public void testRetrieveByTitle() {
		fail("Not yet implemented");
	}

	public void testUpdate() {
		fail("Not yet implemented");
	}

	public void testDelete() {
		fail("Not yet implemented");
	}

	public void testExists() {
		fail("Not yet implemented");
	}

}
