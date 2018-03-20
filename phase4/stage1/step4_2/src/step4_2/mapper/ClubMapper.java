package step4_2.mapper;

import java.util.List;

import step1.share.domain.entity.club.TravelClub;

public interface ClubMapper {
	//
	int exist(int usid);

	void write(TravelClub club);

	TravelClub read(int usid);

	TravelClub readByName(String clubName);

	void update(TravelClub club);
	
	void delete(int usid);
	
	List<TravelClub> readAll();
}
