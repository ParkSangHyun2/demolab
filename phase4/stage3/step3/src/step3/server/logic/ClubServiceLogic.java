/*
 * COPYRIGHT (c) NEXTREE Consulting 2016
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:eykim@nextree.co.kr">Kim, Eunyoung</a>
 * @since 2016. 7. 12.
 */
package step3.server.logic;

import java.util.ArrayList;
import java.util.List;

import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.CommunityMember;
import step1.share.domain.entity.club.RoleInClub;
import step1.share.domain.entity.club.TravelClub;
import step1.share.domain.entity.dto.ClubMembershipDto;
import step1.share.domain.entity.dto.TravelClubDto;
import step1.share.service.logic.ClubService;
import step1.share.service.store.ClubStore;
import step1.share.service.store.MemberStore;
import step1.share.util.ClubDuplicationException;
import step1.share.util.MemberDuplicationException;
import step1.share.util.NoSuchClubException;
import step1.share.util.NoSuchMemberException;
import step3.server.transfer.stub.store.FileStoreLycler;

public class ClubServiceLogic implements ClubService {
	//
	private ClubStore clubStore;
	private MemberStore memberStore;
	
	public ClubServiceLogic() {
		//
		this.clubStore = FileStoreLycler.shareInstance().requestClubStore();
		this.memberStore = FileStoreLycler.shareInstance().requestMemberStore();
	}

	@Override
	public void registerClub(TravelClubDto clubDto) {
		//
		if (clubStore.retrieveByName(clubDto.getName()) != null) {
			throw new ClubDuplicationException("It is already exist the club name:" + clubDto.getName());
		}
		TravelClub club = clubDto.toTravelClub();
		String clubId = clubStore.create(club);
		
		clubDto.setUsid(clubId);
	}
	
	@Override
	public List<TravelClub> findAllClub() {
		//
		return clubStore.retrieveAll();
	}

	@Override
	public TravelClubDto findClub(String clubId) {
		//
		TravelClub club = clubStore.retrieve(clubId);
		
		if(club == null) {
			throw new NoSuchClubException("No such a club with id: " + clubId);
		}
		
		return new TravelClubDto(club);
	}

	@Override
	public TravelClubDto findClubByName(String name) {
		//
		TravelClub club = clubStore.retrieveByName(name);
		
		if (club == null) {
			//return null;
			throw new NoSuchClubException("No such a club with name: " + name);
		} 
		
		return new TravelClubDto(club);
	}

	@Override
	public void modify(TravelClubDto clubDto) {
		//
		if (!clubStore.exists(clubDto.getUsid())) {
			throw new NoSuchClubException("No such a club with id: " + clubDto.getUsid());
		}
		
		TravelClub club = clubDto.toTravelClub();
		clubStore.update(club);
	}

	@Override
	public void remove(String clubId) {
		//
		if (!clubStore.exists(clubId)) {
			throw new NoSuchClubException("No such a club with id: " + clubId);
		}
		
		clubStore.delete(clubId);
	}

	// Membership
	@Override
	public void addMembership(ClubMembershipDto membershipDto) {
		//
		System.out.println("addMembership");
		// check existing member
		String memberId = membershipDto.getMemberEmail();
		CommunityMember member = memberStore.retrieve(memberId);
		if (member == null) {
			throw new NoSuchClubException("No such a member with email: " + memberId);
		}
		
		// check existing membership in the club
		TravelClub club = clubStore.retrieve(membershipDto.getClubId());
		for (ClubMembership membership : club.getMembershipList()) {
			if(memberId.equals(membership.getMemberEmail())) {
				throw new MemberDuplicationException("There is member in club already -->" + memberId);
			}
		}
		
		// add membership
		ClubMembership clubMembership = membershipDto.toMembership();
		club.getMembershipList().add(clubMembership);
		System.out.println("LOG: " + club.getMembershipList());
		clubStore.update(club);
		member.getMembershipList().add(clubMembership);
		System.out.println("LOG: " + member.getMembershipList());
		memberStore.update(member);
	}

	@Override
	public ClubMembershipDto findMembershipIn(String clubId, String memberId) {
		//
		TravelClub club = clubStore.retrieve(clubId);
		
		ClubMembership membership = getMembershipIn(club, memberId);
		
		return new ClubMembershipDto(membership);
	}
	
	@Override
	public List<ClubMembershipDto> findAllMembershipsIn(String clubId) {
		//
		List<ClubMembershipDto> clubMembershipDtos = new ArrayList<>();
		
		TravelClub club = clubStore.retrieve(clubId);
		
		for (ClubMembership membership : club.getMembershipList()) {
			clubMembershipDtos.add(new ClubMembershipDto(membership));
		}
		
		return clubMembershipDtos;
	}

	@Override
	public void modifyMembership(String clubId, ClubMembershipDto newMembership) {
		//
		String targetEmail = newMembership.getMemberEmail();
		RoleInClub newRole = newMembership.getRole();
		
		// modify membership of the club.
		TravelClub targetClub = clubStore.retrieve(clubId);
		ClubMembership membershipOfClub = getMembershipIn(targetClub, targetEmail);
		membershipOfClub.setRole(newRole);
		clubStore.update(targetClub);
		
		// modify membership of the member.
		CommunityMember targetMember = memberStore.retrieve(targetEmail);
		for (ClubMembership membershipOfMember : targetMember.getMembershipList()) {
			if (membershipOfMember.getClubId().equals(clubId)) {
				membershipOfMember.setRole(newRole);
			}
		}
		memberStore.update(targetMember);
		
	}

	@Override
	public void removeMembership(String clubId, String memberId) {
		//
		TravelClub foundClub = clubStore.retrieve(clubId);
		CommunityMember foundMember = memberStore.retrieve(memberId);
		ClubMembership clubMembership = getMembershipIn(foundClub, memberId);
		// remove membership
		foundClub.getMembershipList().remove(clubMembership);
		clubStore.update(foundClub);
		
		foundMember = memberStore.retrieve(memberId);
		foundMember.getMembershipList().remove(clubMembership);
		memberStore.update(foundMember);
		
	}
	
	private ClubMembership getMembershipIn(TravelClub club, String memberEmail) {
		//
		for (ClubMembership membership : club.getMembershipList()) {
			if(memberEmail.equals(membership.getMemberEmail())) {
				return membership;
			}
		}
		
		String message = String.format("No such a member[email:%s] in club[name:%s]", memberEmail, club.getName());
		throw new NoSuchMemberException(message);
	}

	@Override
	public List<TravelClubDto> findAll() {
		//
		List<TravelClubDto> clubDtos = new ArrayList<TravelClubDto>();
		if(this.findAllClub() != null) {
			for(TravelClub club : this.findAllClub()) {
				clubDtos.add(new TravelClubDto(club));
			}
		}
		return clubDtos;
	}


}
