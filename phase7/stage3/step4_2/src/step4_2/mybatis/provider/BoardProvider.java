package step4_2.mybatis.provider;

import java.util.List;

import step1.share.domain.entity.board.SocialBoard;

public interface BoardProvider {
	//
	String create(SocialBoard board);
	
	SocialBoard retrieve(String boardId);
	
	List<SocialBoard> retrieveByName(String name);
	
	void update(SocialBoard board);
	
	void delete(String boardId);
	
	boolean exists(String boardId);
	
}
