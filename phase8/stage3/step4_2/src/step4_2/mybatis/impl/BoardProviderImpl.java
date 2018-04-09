package step4_2.mybatis.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import step1.share.domain.entity.board.SocialBoard;
import step4_2.mapper.BoardMapper;
import step4_2.mybatis.Mybatis;
import step4_2.mybatis.provider.BoardProvider;

public class BoardProviderImpl implements BoardProvider {
	//
	private SqlSession session;

	@Override
	public String create(SocialBoard board) {
		//
		SocialBoard newBoard;

		try {
			session = Mybatis.openSession();
			session.getMapper(BoardMapper.class).write(board);
			newBoard = session.getMapper(BoardMapper.class).read(board.getClubId());
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
		return newBoard.toString();
	}

	@Override
	public SocialBoard retrieve(String boardId) {
		//
		SocialBoard board;

		try {
			session = Mybatis.openSession();
			board = session.getMapper(BoardMapper.class).read(boardId);
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
		return board;
	}

	@Override
	public List<SocialBoard> retrieveByName(String name) {
		//
		List<SocialBoard> boards = new ArrayList<>();

		try {
			session = Mybatis.openSession();
			boards = session.getMapper(BoardMapper.class).readByName(name);
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
		return boards;
	}

	@Override
	public void update(SocialBoard board) {
		//
		try {
			session = Mybatis.openSession();
			session.getMapper(BoardMapper.class).update(board.getClubId());
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
	}

	@Override
	public void delete(String boardId) {
		//
		try {
			session = Mybatis.openSession();
			session.getMapper(BoardMapper.class).delete(boardId);
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
	}

	@Override
	public boolean exists(String boardId) {
		//
		int clubId = -1;

		try {
			session = Mybatis.openSession();
			session.getMapper(BoardMapper.class).exists(boardId);
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
		if (clubId != -1) {
			return false;
		}
		return true;
	}
}
