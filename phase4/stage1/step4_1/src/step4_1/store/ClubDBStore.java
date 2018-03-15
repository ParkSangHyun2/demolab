/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package step4_1.store;

import java.util.List;
import java.util.NoSuchElementException;

import step1.share.domain.entity.club.TravelClub;
import step1.share.service.store.ClubStore;
import step1.share.util.MemberDuplicationException;
import step4_1.store.io.ClubQuery;

public class ClubDBStore implements ClubStore {
	//
	private ClubQuery clubQuery; 
	
	public ClubDBStore() {
		//  
		this.clubQuery = new ClubQuery();
	}
	
	@Override
	public String create(TravelClub club) {
		// 
		if (clubQuery.exists(club.getId())) {
			throw new MemberDuplicationException("Member already exists with email: " + club.getId()); 
		}

		clubQuery.write(club); 
		return club.getId();
	}

	@Override
	public TravelClub retrieve(String clubId) {
		// 
		return clubQuery.read(clubId); 
	}
	
	@Override
	public TravelClub retrieveByName(String name) {
		//
		return clubQuery.readByName(name); 
	}

	@Override
	public void update(TravelClub club) {
		// 
		if (!clubQuery.exists(club.getId())) {
			throw new NoSuchElementException("No such a element: " + club.getId()); 
		}
		
		clubQuery.update(club); 
	}

	@Override
	public void delete(String clubId) {
		// 
		clubQuery.delete(clubId);
	}

	@Override
	public boolean exists(String clubId) {
		//
		return clubQuery.exists(clubId);
	}
	
	public static void main(String[] args) {
		// 
		ClubDBStore store = new ClubDBStore(); 
		TravelClub club = TravelClub.getSample(false); 
		String clubId = store.create(club); 
		
		TravelClub readClub = store.retrieve(clubId);
		System.out.println("read club: " + readClub.toString()); 
	}

	@Override
	public List<TravelClub> retrieveAll() {
		//
		return clubQuery.readAll();
	}
}