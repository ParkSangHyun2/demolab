package step4_2.mapper;

import java.util.List;

import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.CommunityMember;
import step1.share.domain.entity.club.TravelClub;

public interface MembershipMapper {
	//
	List<ClubMembership> readByClubId(String clubId);
	
	List<ClubMembership> readByMemberName(String memberName);
	
	List<ClubMembership> readByMemberEmail(String memberEmail);
	
	void createMembership(ClubMembership membership);
	
	void checkDeletedMembership(String clubId, String memberEmail);
	
	void checkCreatedMembership(ClubMembership membership);
}
