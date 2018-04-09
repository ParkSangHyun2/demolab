package step4_3.hibernate.store;

import java.util.List;

import step1.share.domain.entity.club.ClubMembership;

public interface MembershipStore {
	List<ClubMembership> readByClubId(String clubId);
	
	List<ClubMembership> readByMemberName(String memberName);
	
	List<ClubMembership> readByMemberEmail(String memberEmail);
	
	void createMembership(ClubMembership membership);
	
	void checkDeletedMembership(String clubId, String memberEmail);
	
	void checkCreatedMembership(ClubMembership membership);
}
