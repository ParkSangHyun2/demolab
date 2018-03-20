package step4_2.mapper;

import java.util.List;

import step1.share.domain.entity.board.Posting;

public interface PostingMapper {
	//
	int exist(int usid);
	
	void write(Posting posting);
	
	Posting read(int usid);
	
	List<Posting> readByBoardId(int boardId);
	
	List<Posting> readByTitle(String title);
	
	void update(Posting posting);
	
	void delete(int usid);
}
