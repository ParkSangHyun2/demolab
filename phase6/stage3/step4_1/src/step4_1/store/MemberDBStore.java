package step4_1.store;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.CommunityMember;
import step1.share.service.store.MemberStore;
import step1.share.util.MemberDuplicationException;
import step4_1.store.io.MemberQuery;
import step4_1.store.io.MembershipQuery;

public class MemberDBStore implements MemberStore {
	//
	private MemberQuery memberQuery; 
	private MembershipQuery membershipQuery;
	
	public MemberDBStore() {
		//  
		this.memberQuery = new MemberQuery(); 
		this.membershipQuery = new MembershipQuery();
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
	public CommunityMember retrieve(String memberEmail) {
		//
		CommunityMember member = memberQuery.read(memberEmail);
		List<ClubMembership> membershipList = new ArrayList<>(); 
		membershipList = membershipQuery.readByMemberEmail(memberEmail);
		
		if(member != null) {
			member.getMembershipList().addAll(membershipList);
		}
		return member; 
	}
	
	@Override
	public List<CommunityMember> retrieveByName(String name) {
		//
		List<CommunityMember> members = new ArrayList<>(); 
		members = memberQuery.readByName(name);
		
		List<ClubMembership> membershipList = new ArrayList<>();
		membershipList = membershipQuery.readByMemberName(name);
		
		for(CommunityMember member : members) {
			member.getMembershipList().addAll(membershipList);
		}
		
		return members;
	}

	@Override
	public void update(CommunityMember member) {
		// 
		if (!memberQuery.exists(member.getId())) {
			throw new NoSuchElementException("No such a member with email: " + member.getId()); 
		}
		
		if(membershipQuery.createMembershipForMember(member)) {
			return;
		}
		memberQuery.update(member);
		membershipQuery.checkDeletedMembershipForMember(member);
		membershipQuery.checkCreatedMembershipForMember(member);
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
		List<CommunityMember> members = new ArrayList<CommunityMember>();
		List<ClubMembership> memberships = new ArrayList<ClubMembership>();
		
		members = memberQuery.readAll();
		for(CommunityMember member : members) {
			String memberEmail = member.getEmail();
			memberships = membershipQuery.readByMemberEmail(memberEmail);
			member.getMembershipList().addAll(memberships);
		}
		return members; 
	}
}