package step4_3.test;

import junit.framework.TestCase;
import step1.share.domain.entity.club.TravelClub;
import step4_3.hibernate.store.TravelClubStoreService;

public class TravelClubStoreTest extends TestCase {
	//
	TravelClubStoreService clubService;
	
	
	public TravelClubStoreTest() {
		this.clubService = new TravelClubStoreService();
	}

	public void testCreate() {
		clubService.create(TravelClub.getSample(true));
	}

	public void testRetrieve() {
		System.out.println(clubService.retrieve("001"));
	}

	public void testRetrieveByBoardId() {
		System.out.println(clubService.retrieveAll());
	}

	public void testRetrieveByTitle() {
		System.out.println(clubService.retrieveByName("FirstFirst"));
	}

	public void testUpdate() {
		TravelClub club = clubService.retrieve("001");
		club.setIntro("Change~~~~~~~~~~~~~");
		clubService.update(club);
	}

	public void testDelete() {
		clubService.delete("001");
	}

	public void testExists() {
		fail("Not yet implemented");
	}

}
