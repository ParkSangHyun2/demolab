/*
 * COPYRIGHT (c) NEXTREE Consulting 2014
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 6. 10.
 */

package step3.logic.storage;

import java.util.LinkedHashMap;
import java.util.Map;

import entity.board.Posting;
import entity.board.SocialBoard;
import entity.club.CommunityMember;
import entity.club.TravelClub;

public class MapStorage {
	//
	private static MapStorage singletonMap; 
	
	private Map<String,CommunityMember>  memberMap; 
	private Map<String,TravelClub>  clubMap; 
	private Map<String,SocialBoard> boardMap; 
	private Map<String,Posting> postingMap; 
	private Map<String,Integer> autoIdMap; 
	
	private MapStorage() {
		// 
		this.memberMap = new LinkedHashMap<>(); 
		this.clubMap = new LinkedHashMap<>(); 
		this.boardMap = new LinkedHashMap<>(); 
		this.postingMap = new LinkedHashMap<>();
		this.autoIdMap = new LinkedHashMap<>(); 
	}
	
	public static MapStorage getInstance() {
		// 
		if (singletonMap == null) {
			singletonMap = new MapStorage(); 
		}
		
		return singletonMap; 
	}
	
	public Map<String,Integer> getAutoIdMap() {
		// 
		return this.autoIdMap; 
	}
	
	public Map<String,CommunityMember> getMemberMap() {
		return memberMap; 
	}
	
	public Map<String,TravelClub> getClubMap() {
		return clubMap; 
	}
	
	public Map<String,SocialBoard> getBoardMap() {
		return boardMap; 
	}
	
	public Map<String,Posting> getPostingMap() {
		return postingMap; 
	}
}