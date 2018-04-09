package step4_2.mapper;

import java.util.List;

import step1.share.domain.entity.club.TravelClub;

public interface ClubMapper {
	//
	int exists(String usid);

	void write(TravelClub club);

	TravelClub read(String usid);

	TravelClub readByName(String clubName);

	void update(TravelClub club);
	
	void delete(String usid);
	
	List<TravelClub> readAll();
}
