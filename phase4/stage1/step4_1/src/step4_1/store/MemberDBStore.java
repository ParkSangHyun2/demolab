package step4_1.store;

import java.util.List;
import java.util.NoSuchElementException;

import step1.share.domain.entity.club.CommunityMember;
import step1.share.service.store.MemberStore;
import step1.share.util.MemberDuplicationException;
import step4_1.store.io.MemberQuery;

public class MemberDBStore implements MemberStore {
	//
	private MemberQuery memberQuery; 
	
	public MemberDBStore() {
		//  
		this.memberQuery = new MemberQuery(); 
	}
	
	@Override
	public String create(CommunityMember member) {
		// 
		if (memberQuery.exists(member.getId())) {
			throw new MemberDuplicationException("Member already exists with email: " + member.getId()); 
		}

		memberQuery.write(member); 
		return member.getId();
	}

	@Override
	public CommunityMember retrieve(String memberId) {
		//
		CommunityMember member = memberQuery.read(memberId);
		if(member == null) {
			return null;
		}
		return member; 
	}
	
	@Override
	public List<CommunityMember> retrieveByName(String name) {
		//
		return memberQuery.readByName(name); 
	}

	@Override
	public void update(CommunityMember member) {
		// 
		if (!memberQuery.exists(member.getId())) {
			throw new NoSuchElementException("No such a member with email: " + member.getId()); 
		}
		
		memberQuery.update(member); 
	}

	@Override
	public void delete(String memberId) {
		// 
		memberQuery.delete(memberId);
	}

	@Override
	public boolean exists(String memberId) {
		//
		return memberQuery.exists(memberId);
	}

	@Override
	public List<CommunityMember> retrieveAll() {
		//
		return memberQuery.readAll();
	}
}