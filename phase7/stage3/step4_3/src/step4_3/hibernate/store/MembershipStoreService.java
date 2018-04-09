package step4_3.hibernate.store;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import step1.share.domain.entity.club.ClubMembership;
import step4_3.hibernate.entityManager.ClubEntityManager;

public class MembershipStoreService implements MembershipStore{
	//
	private ClubEntityManager entityManager;
	private EntityManagerFactory factory;

	public MembershipStoreService() {
		try {
			entityManager = new ClubEntityManager();
			factory = entityManager.setUp();
		} catch (Exception e) {
			//
			e.printStackTrace();
		}
	}

	@Override
	public List<ClubMembership> readByClubId(String clubId) {
		//
		List<ClubMembership> memberships = new ArrayList<>();
		
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		Query query = manager.createQuery("from ClubMembership where clubId = :clubId");
		query.setParameter("clubId", clubId);
		memberships = query.getResultList();
		return memberships;
	}

	@Override
	public List<ClubMembership> readByMemberName(String memberName) {
		//
		List<ClubMembership> memberships = new ArrayList<>();
		
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		Query query = manager.createQuery("from ClubMembership where memberName = :memberName");
		query.setParameter("memberName", memberName);
		memberships = query.getResultList();
		return memberships;
	}

	@Override
	public List<ClubMembership> readByMemberEmail(String memberEmail) {
		//
		List<ClubMembership> memberships = new ArrayList<>();
		
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		Query query = manager.createQuery("from ClubMembership where memberEmail = :email");
		query.setParameter("email", memberEmail);
		memberships = query.getResultList();
		manager.getTransaction().commit();
		manager.close();
		return memberships;
	}

	@Override
	public void createMembership(ClubMembership membership) {
		//
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.persist(membership);
		manager.getTransaction().commit();
		manager.close();
	}

	@Override
	public void checkDeletedMembership(String clubId, String memberEmail) {
		//
		ClubMembership membership = new ClubMembership();
		
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		Query query = manager.createQuery("from ClubMembership where memberEmail = :email and clubId = :clubId");
		query.setParameter("email", memberEmail);
		query.setParameter("clubId", clubId);
		membership = (ClubMembership) query.getSingleResult();
		manager.getTransaction().commit();
		manager.remove(membership);
		manager.close();
	}

	@Override
	public void checkCreatedMembership(ClubMembership membership) {
		//
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.persist(membership);
		manager.getTransaction().commit();
		manager.close();
	}
	

}
