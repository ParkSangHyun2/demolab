package step4_2.mapper;

import java.util.List;

import step1.share.domain.entity.board.SocialBoard;

public interface BoardMapper {
	//
	int exists(int clubId);

	void write(SocialBoard board);

	SocialBoard read(String clubId);
	
	List<SocialBoard> readByName(String boardName);
	
	void delete(String clubId);
}
