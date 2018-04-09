package step4_2.mybatis.provider;

import java.util.List;

import step1.share.domain.entity.club.TravelClub;

public interface ClubProvider {
	//
	String create(TravelClub club);
	
	TravelClub retrieve(String clubId);
	
	TravelClub retrieveByName(String name);
	
	void update(TravelClub club);
	
	void delete(String clubId);
	
	boolean exists(String clubId);
	
	List<TravelClub> retrieveAll();
}
