package step4_2.mapper;

import java.util.List;

import step1.share.domain.entity.board.Posting;

public interface PostingMapper {
	//
	int exists(String usid);
	
	void write(Posting posting);
	
	Posting read(String usid);
	
	List<Posting> readByBoardId(String boardId);
	
	List<Posting> readByTitle(String title);
	
	void update(Posting posting);
	
	void delete(String usid);
}
