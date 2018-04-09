package step4_2.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import step1.share.domain.entity.club.CommunityMember;
import step4_2.mybatis.impl.MemberProviderImpl;

class MemberProviderTest {

	@Test
	void testCreate() {
		MemberProviderImpl memberProvider = new MemberProviderImpl();
		CommunityMember member = CommunityMember.getSample();
		memberProvider.create(member);
	}

	@Test
	void testRetrieve() {
		MemberProviderImpl memberProvider = new MemberProviderImpl();
		CommunityMember member = CommunityMember.getSample();
		memberProvider.retrieve(member.getEmail());		
	}

	@Test
	void testRetrieveByName() {
		MemberProviderImpl memberProvider = new MemberProviderImpl();
		CommunityMember member = CommunityMember.getSample();
		memberProvider.retrieveByName(member.getName());
	}

	@Test
	void testUpdate() {
		MemberProviderImpl memberProvider = new MemberProviderImpl();
		CommunityMember member = CommunityMember.getSample();
		member.setNickName("NickName");
		memberProvider.update(member);
	}

	@Test
	void testDelete() {
		MemberProviderImpl memberProvider = new MemberProviderImpl();
		CommunityMember member = CommunityMember.getSample();
		memberProvider.delete(member.getEmail());
	}

	@Test
	void testExists() {
		MemberProviderImpl memberProvider = new MemberProviderImpl();
		CommunityMember member = CommunityMember.getSample();
		assertTrue(memberProvider.exists(member.getEmail()));
	}

	@Test
	void testRetrieveAll() {
		MemberProviderImpl memberProvider = new MemberProviderImpl();
		System.out.print("values : "+ memberProvider.retrieveAll().size()+ ", "+ memberProvider.retrieveAll());
	}

}
