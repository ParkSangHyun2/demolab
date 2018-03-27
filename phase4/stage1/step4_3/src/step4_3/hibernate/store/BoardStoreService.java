package step4_3.hibernate.store;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

import step1.share.domain.entity.board.SocialBoard;
import step1.share.service.store.BoardStore;
import step4_3.hibernate.entityManager.ClubEntityManager;

public class BoardStoreService implements BoardStore {
	//
	ClubEntityManager entityManager;
	EntityManagerFactory factory;

	public BoardStoreService() {
		try {
			entityManager = new ClubEntityManager();
			factory = entityManager.setUp();
		} catch (Exception e) {
			//
			e.printStackTrace();
		}
	}

	@Override
	public String create(SocialBoard board) {
		//
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.persist(board);
		manager.getTransaction().commit();
		manager.close();

		return board.getName();
	}

	@Override
	public SocialBoard retrieve(String boardId) {
		//
		SocialBoard board = null;

		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		try {
			board = (SocialBoard) manager.createQuery("from SocialBoard board where board.clubId =" + boardId)
					.getSingleResult();
		} catch (NoResultException e) {
			e.getMessage();
		} finally {
			manager.getTransaction().commit();
			manager.close();
		}
		return board;
	}

	@Override
	public List<SocialBoard> retrieveByName(String boardId) {
		//
		List<SocialBoard> boards = new ArrayList<>();

		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		boards = manager.createQuery("from SocialBoard board where board.clubId = " + boardId).getResultList();
		manager.getTransaction().commit();
		manager.close();

		return boards;
	}

	@Override
	public void update(SocialBoard board) {
		//
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.merge(board);
		manager.getTransaction().commit();
		manager.close();
	}

	@Override
	public void delete(String boardId) {
		//
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.remove(this.retrieve(boardId));
		manager.getTransaction().commit();
		manager.close();
	}

	@Override
	public boolean exists(String boardId) {
		//
		if (this.retrieve(boardId) != null) {
			return true;
		}
		return false;
	}

}
