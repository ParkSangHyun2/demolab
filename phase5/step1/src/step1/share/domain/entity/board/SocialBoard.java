/*
 * COPYRIGHT (c) NEXTREE Consulting 2014
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 6. 10.
 */

package step1.share.domain.entity.board;

import step1.share.domain.entity.Entity;
import step1.share.domain.entity.club.CommunityMember;
import step1.share.domain.entity.club.TravelClub;
import step1.share.util.DateUtil;

public class SocialBoard implements Entity {
    //
    private String clubId;		
    private int sequence; 
    private String name;
    private String adminEmail;		
    private String createDate;
    
    public SocialBoard() {
    	//
    	this.sequence = 0; 
    }

    public SocialBoard(String clubId, String name, String adminEmail) {
        //  
        this();
        this.clubId = clubId; 
        this.name = name;
        this.adminEmail = adminEmail;
        this.createDate = DateUtil.today(); 
    }
    
    @Override
    public String toString() {
        //
        StringBuilder builder = new StringBuilder();
        
        builder.append("SocialBoard id: ").append(clubId);
        builder.append(", name: ").append(name); 
        builder.append(", admin email: ").append(adminEmail);
        builder.append(", creation date: ").append(createDate);
        
        return builder.toString();
    }

    public static SocialBoard getSample(TravelClub club) {
    	// 
    	CommunityMember member = CommunityMember.getSample(); 
    	
    	SocialBoard board = new SocialBoard(
    			club.getUsid(), 
    			"Board for " + club.getName(), 
    			member.getEmail()
    			); 
    	
    	board.setCreateDate("2016.07.02");
    	
    	return board; 
    }
    
    @Override
    public String getId() {
    	return clubId; 
    }

	public String nextPostingId() {
		return String.format("%s:%05d", clubId, sequence++);
	}
	
    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }
}