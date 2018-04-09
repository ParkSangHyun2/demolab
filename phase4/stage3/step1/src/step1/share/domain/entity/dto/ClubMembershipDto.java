/*
 * COPYRIGHT (c) NEXTREE Consulting 2016
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:eykim@nextree.co.kr">Kim, Eunyoung</a>
 * @since 2016. 7. 12.
 */
package step1.share.domain.entity.dto;

import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.RoleInClub;
import step1.share.domain.entity.util.DateUtil;

public class ClubMembershipDto {
	//
	private String clubId; 
	private String memberEmail; 
	private String memberName; 
	private RoleInClub role; 
	private String joinDate; 

	public ClubMembershipDto(String clubId, String memberEmail) {
		//
		this.clubId = clubId;
		this.memberEmail = memberEmail;
		
		this.role = RoleInClub.Member;
		this.joinDate = DateUtil.today();
	}
	
	public ClubMembershipDto(ClubMembership membership) {
		//
		this.clubId = membership.getClubId();
		this.memberEmail = membership.getMemberEmail();
		this.memberName = membership.getMemberName();
		this.role = membership.getRole();
		this.joinDate = membership.getJoinDate();
	}

	public ClubMembership toMembership() {
		//
		ClubMembership membership = new ClubMembership(clubId, memberEmail);
		membership.setMemberName(memberName);
		membership.setRole(role);
		membership.setJoinDate(joinDate);
		return membership;
	}
	
	@Override
	public String toString() {
		// 
		StringBuilder builder = new StringBuilder(); 
		
		builder.append("club Id:").append(clubId); 
		builder.append(", member email:").append(memberEmail); 
		builder.append(", name:").append(memberName); 
		builder.append(", role:").append(role.name()); 
		builder.append(", join date:").append(joinDate); 
		
		return builder.toString(); 
	}

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public RoleInClub getRole() {
		return role;
	}

	public void setRole(RoleInClub role) {
		this.role = role;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
}
