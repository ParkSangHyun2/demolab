package step4_3.test;

import junit.framework.TestCase;
import step1.share.domain.entity.club.ClubMembership;
import step4_3.hibernate.store.MembershipStoreService;

public class MembershipTest extends TestCase {
	//
	private MembershipStoreService membershipStore;
	
	public void testMembershipStoreService() {
		fail("Not yet implemented");
	}

	public void testReadByClubId() {
		fail("Not yet implemented");
	}

	public void testReadByMemberName() {
		fail("Not yet implemented");
	}

	public void testReadByMemberEmail() {
		fail("Not yet implemented");
	}

	public void testCreateMembership() {
		membershipStore = new MembershipStoreService();
		membershipStore.createMembership(new ClubMembership("00021","pus5684@naver.com"));
	}

	public void testCheckDeletedMembership() {
		fail("Not yet implemented");
	}

	public void testCheckCreatedMembership() {
		fail("Not yet implemented");
	}
}
