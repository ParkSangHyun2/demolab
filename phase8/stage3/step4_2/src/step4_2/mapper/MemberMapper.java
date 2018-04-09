package step4_2.mapper;

import java.util.List;

import step1.share.domain.entity.club.CommunityMember;

public interface MemberMapper {
	//
	String exists(String usid);
	
	void write(CommunityMember member);
	
	CommunityMember read(String memberEmail);
	
	List<CommunityMember> readByName(String memberName);
	
	void update(CommunityMember member);
	
	void delete(String memberEmail);
	
	List<CommunityMember> readAll();
}
