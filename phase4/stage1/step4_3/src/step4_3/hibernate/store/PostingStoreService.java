package step4_3.hibernate.store;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import step1.share.domain.entity.board.Posting;
import step1.share.service.store.PostingStore;
import step4_3.hibernate.entityManager.ClubEntityManager;

public class PostingStoreService implements PostingStore{
	//
	ClubEntityManager entityManager = new ClubEntityManager();
	EntityManagerFactory factory;
	
	public PostingStoreService() {
		try {
			factory = entityManager.setUp();
		} catch (Exception e) {
			//
			e.printStackTrace();
		}
	}

	@Override
	public String create(Posting posting) {
		//
		posting.setUsid(Long.toString(System.currentTimeMillis()));
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
		manager.persist(posting);
		manager.getTransaction().commit();
		manager.close();
		return posting.getTitle();
	}

	@Override
	public Posting retrieve(String postingId) {
		//
		Posting posting = null;
		
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		posting = (Posting) manager.createQuery("from Posting posting where posting.usid = " + postingId).getSingleResult();
		manager.getTransaction().commit();
		manager.close();
		return posting;
	}

	@Override
	public List<Posting> retrieveByBoardId(String boardId) {
		//
		List<Posting> postings = new ArrayList<>();
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		postings = manager.createQuery("from Posting posting where posting.boardId = " + boardId).getResultList();
		manager.getTransaction().commit();
		manager.close();
		return postings;
	}

	@Override
	public List<Posting> retrieveByTitle(String title) {
		//
		List<Posting> postings = new ArrayList<>();
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		postings = manager.createQuery("from Posting posting where posting.title = " + title).getResultList();
		manager.getTransaction().commit();
		manager.close();
		return postings;
	}

	@Override
	public void update(Posting posting) {
		//
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.merge(posting);
		manager.getTransaction().commit();
		manager.close();
	}

	@Override
	public void delete(String postingId) {
		//
		Posting posting = null;
		
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		posting = this.retrieve(postingId);
		manager.remove(posting);
		manager.getTransaction().commit();
		manager.close();
	}

	@Override
	public boolean exists(String postingId) {
		//
		if(this.retrieve(postingId) != null) {
			return true;
		}
		
		return false;
	}
}
