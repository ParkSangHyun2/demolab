package step1.share.domain.entity.club;

import java.io.Serializable;

import step1.share.util.DateUtil;

public class ClubMembership implements Serializable{
	//
	private String clubId; 
	private String memberEmail; 
	private String memberName; 
	private RoleInClub role; 
	private String joinDate; 
	
	public ClubMembership() {}
	
	public ClubMembership(TravelClub club, CommunityMember member) {
		// 
		this.clubId = club.getUsid(); 
		this.memberEmail = member.getEmail(); 
		this.memberName = member.getName();
		this.role = RoleInClub.Member; 
		this.joinDate = DateUtil.today(); 
	}
	
	public ClubMembership(String clubId, String memberEmail) {
		//
		this.clubId = clubId; 
		this.memberEmail = memberEmail;
		this.role = RoleInClub.Member; 
		this.joinDate = DateUtil.today(); 
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
	
	public static ClubMembership getSample(TravelClub club, CommunityMember member) {
		// 
		ClubMembership membership = new ClubMembership(club, member); 
		membership.setRole(RoleInClub.Member);
		
		return membership; 
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

	public String getClubId() {
		return clubId;
	}

	public String getMemberEmail() {
		return memberEmail;
	}	
	
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	
	public void setClubId(String clubId) {
		this.clubId = clubId;
	}
	
	public static void main(String[] args) {
		// 
		System.out.println(ClubMembership.getSample(TravelClub.getSample(false), CommunityMember.getSample())); 
	}
}