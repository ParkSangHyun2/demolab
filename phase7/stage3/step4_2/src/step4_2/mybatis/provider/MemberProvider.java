package step4_2.mybatis.provider;

import java.util.List;

import step1.share.domain.entity.club.CommunityMember;

public interface MemberProvider {
	//
	String create(CommunityMember member);
	
	CommunityMember retrieve(String memberEmail);
	
	List<CommunityMember> retrieveByName(String name);
	
	void update(CommunityMember member);
	
	void delete(String memberId);
	
	boolean exists(String memberId);
	
	List<CommunityMember> retrieveAll();
	
	
}
