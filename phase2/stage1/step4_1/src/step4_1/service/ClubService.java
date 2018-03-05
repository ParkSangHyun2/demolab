/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package step4_1.service;

import java.util.List;

import entity.club.TravelClub;
import entity.dto.ClubMembershipDto;
import entity.dto.TravelClubDto;

public interface ClubService {
	//
	public void registerClub(TravelClubDto club); 
	public TravelClubDto findClub(String clubId); 
	public TravelClubDto findClubByName(String name);
	public void modify(TravelClubDto club); 
	public void remove(String clubId); 
	
	void addMembership(ClubMembershipDto membershipDto); 
	public ClubMembershipDto findMembershipIn(String clubId, String memberId);
	public List<ClubMembershipDto> findAllMembershipsIn(String clubId);
	public void modifyMembership(String clubId, ClubMembershipDto member);
	public void removeMembership(String clubId, String memberId);
	public List<TravelClub> findAllClub();
}