package step4_2.mybatis.provider;

import java.util.List;

import step1.share.domain.entity.board.Posting;

public interface PostingProvider {
	//
	boolean exists(String usid);
	
	String create(Posting posting);
	
	Posting retrieve(String usid);
	
	List<Posting> retrieveByBoardId(String boardId);
	
	List<Posting> retrieveByTitle(String title);
	
	void update(Posting posting);
	
	void delete(String usid);
}
