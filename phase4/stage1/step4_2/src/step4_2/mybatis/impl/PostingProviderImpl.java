package step4_2.mybatis.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import step1.share.domain.entity.board.Posting;

public class PostingProviderImpl {
	//
	private SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	private final String resource = "./mybatis/MybatisConfig.xml";
	private InputStream inputStream;
	private SqlSessionFactory factory;
	
	public PostingProviderImpl() {
		try {
			inputStream = Resources.getResourceAsStream(resource);
			factory = builder.build(inputStream);
		} catch (IOException e) {
			//
			System.out.println("Posting Provider Exception --> " + e.getMessage());
		}
	}
	
	public boolean exists(String usid) {
		//
		boolean isExist = false;
		
		SqlSession session = factory.openSession();
		int postingUsid = session.selectOne("PostingMapper.exist",Integer.parseInt(usid));
		if(postingUsid != -1) {
			System.out.println(postingUsid);
			isExist = true;
		}
		return isExist;
	}
	
	public void write(Posting posting) {
		//
		SqlSession session = factory.openSession();
		session.insert("PostingMapper.write", posting);
	}
	
	public Posting read(String usid) {
		//
		SqlSession session = factory.openSession();
		Posting posting = session.selectOne("PostingMapper.read", Integer.parseInt(usid));
		return posting;
	}
	
	public List<Posting> readByBoardId(String boardId){
		//
		SqlSession session = factory.openSession();
		List<Posting> postings = session.selectList("PostingMapper.readByBoardId", Integer.parseInt(boardId));
		return postings;
	}
	
	public List<Posting> readByTitle(String title){
		//
		SqlSession session = factory.openSession();
		List<Posting> postings = session.selectList("PostingMapper.readByTitle", title);
		return postings;
	}
	
	public void update(Posting posting) {
		//
		SqlSession session = factory.openSession();
		System.out.println("-->" + session.update("PostingMapper.update", posting));
	}
	
	public void delete(String usid) {
		//
		SqlSession session = factory.openSession();
		session.delete("PostingMapper.delete", Integer.parseInt(usid));
	}
	
	public static void main(String[] args) {
		//
		PostingProviderImpl pro = new PostingProviderImpl();
		if(pro.exists("5")) {
			System.out.println("RUN");
		}else {
			System.out.println("Fail");
		}
		
		System.out.println(pro.read("5"));
		System.out.println(pro.readByBoardId("30"));
		System.out.println(pro.readByTitle("11"));
		Posting posting = new Posting();
		posting = pro.read("5");
		posting.setTitle("TItle");
		posting.setContents("is Changed");
		posting.setReadCount(1133);
		pro.update(posting);
		
	}
}

