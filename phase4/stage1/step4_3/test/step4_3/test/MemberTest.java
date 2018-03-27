package step4_3.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import step1.share.domain.entity.club.CommunityMember;
import step4_3.hibernate.store.MemberStoreService;

public class MemberTest extends TestCase {
	//
	MemberStoreService memberStore;

	public void testMemberStoreService() {
		memberStore = new MemberStoreService();
	}

	public void testCreate() {
		CommunityMember member = new CommunityMember("pus5684@naver.com", "park", "0109944");
		memberStore = new MemberStoreService();
		System.out.println(member);
		memberStore.create(member);
	}

	public void testRetrieve() {
		memberStore = new MemberStoreService();
		CommunityMember member = new CommunityMember();

		member = memberStore.retrieve("pus5684@naver.com");
		System.out.println(member);
	}

	public void testRetrieveByName() {
		memberStore = new MemberStoreService();
		List<CommunityMember> members = new ArrayList<>();
		members = memberStore.retrieveByName("park");
		System.out.println(members);
	}

	public void testUpdate() {

		CommunityMember member = memberStore.retrieve("pus5684@naver.com");
		member.setNickName("Nick");
		memberStore.update(member);
	}

	public void testDelete() {
		memberStore = new MemberStoreService();
		memberStore.delete("pus5684@naver.com");
	}

	public void testExists() {
		memberStore = new MemberStoreService();
		if (memberStore.exists("pus5684@naver.com")) {
			System.out.println("EXIST");
		} else {
			System.out.println("NOP");
		}
	}

	public void testRetrieveAll() {
		//
		memberStore = new MemberStoreService();
		System.out.println(memberStore.retrieveAll());
	}

}
