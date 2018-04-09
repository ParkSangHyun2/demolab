package step4_3.test;

import junit.framework.TestCase;
import step1.share.domain.entity.board.SocialBoard;
import step1.share.service.store.BoardStore;
import step4_3.hibernate.store.BoardStoreService;

public class BoardTest extends TestCase {
	//
	BoardStore boardStore;
	
	public BoardTest() {
		
	}

	public void testCreate() {
		boardStore = new BoardStoreService();
		TravelClubStoreTest test  = new TravelClubStoreTest();
		SocialBoard board = new SocialBoard("1522132887587","name","Email");
		boardStore.create(board);
	}

	public void testRetrieve() {
		boardStore = new BoardStoreService();
		System.out.println(boardStore.retrieve("00021"));
	}

	public void testRetrieveByName() {
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
