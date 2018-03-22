package step4_2.mybatis.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import step1.share.domain.entity.board.Posting;
import step4_2.mapper.PostingMapper;
import step4_2.mybatis.Mybatis;
import step4_2.mybatis.provider.PostingProvider;

public class PostingProviderImpl implements PostingProvider {
	//
	private SqlSession session;

	public boolean exists(String usid) {
		//
		int postingUsid = -1;

		try {
			session = Mybatis.openSession();
			postingUsid = session.getMapper(PostingMapper.class).exists(usid);
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
		if (postingUsid != -1) {
			return true;
		}
		return false;
	}

	public String create(Posting posting) {
		//
		try {
			session = Mybatis.openSession();
			session.getMapper(PostingMapper.class).write(posting);
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
		return posting.getId();
	}

	public Posting retrieve(String usid) {
		//
		Posting posting;

		try {
			session = Mybatis.openSession();
			posting = session.getMapper(PostingMapper.class).read(usid);
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
		return posting;
	}

	public List<Posting> retrieveByBoardId(String boardId) {
		//
		List<Posting> postings = new ArrayList<>();

		try {
			session = Mybatis.openSession();
			postings = session.getMapper(PostingMapper.class).readByBoardId(boardId);
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
		return postings;
	}

	public List<Posting> retrieveByTitle(String title) {
		//
		List<Posting> postings = new ArrayList<>();

		try {
			session = Mybatis.openSession();
			postings = session.getMapper(PostingMapper.class).readByTitle(title);
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
		return postings;
	}

	public void update(Posting posting) {
		//
		try {
			session = Mybatis.openSession();
			session.getMapper(PostingMapper.class).update(posting);
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
	}

	public void delete(String usid) {
		//
		try {
			session = Mybatis.openSession();
			session.getMapper(PostingMapper.class).delete(usid);
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
	}

	public static void main(String[] args) {
		//
		PostingProviderImpl pro = new PostingProviderImpl();
		if (pro.exists("5")) {
			System.out.println("RUN");
		} else {
			System.out.println("Fail");
		}

		System.out.println(pro.retrieve("5"));
		System.out.println(pro.retrieveByBoardId("30"));
		System.out.println(pro.retrieveByTitle("11"));
		Posting posting = new Posting();
		posting = pro.retrieve("5");
		posting.setTitle("TItle");
		posting.setContents("is Changed");
		posting.setReadCount(1133);
		pro.update(posting);

	}
}
