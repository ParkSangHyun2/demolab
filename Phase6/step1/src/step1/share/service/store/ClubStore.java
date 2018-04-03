package step1.share.service.store;

import java.util.List;

import step1.share.domain.entity.club.TravelClub;

public interface ClubStore {
	//
	public String create(TravelClub club); 
	public TravelClub retrieve(String clubId);
	public TravelClub retrieveByName(String name);
	public void update(TravelClub club); 
	public void delete(String clubId); 
	
	public boolean exists(String clubId);
	public List<TravelClub> retrieveAll(); 
}