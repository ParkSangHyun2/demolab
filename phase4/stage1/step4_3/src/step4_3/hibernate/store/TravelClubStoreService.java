package step4_3.hibernate.store;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.TravelClub;
import step1.share.service.store.ClubStore;
import step4_3.hibernate.entityManager.ClubEntityManager;

public class TravelClubStoreService implements ClubStore {
	//
	ClubEntityManager entityManager;
	MembershipStore membershipStore;
	EntityManagerFactory factory;

	public TravelClubStoreService() {
		try {
			membershipStore = new MembershipStoreService();
			entityManager = new ClubEntityManager();
			factory = entityManager.setUp();
		} catch (Exception e) {
			//
			e.printStackTrace();
		}
	}

	@Override
	public String create(TravelClub club) {
		//
		
		TravelClub createdClub = club;
		createdClub.setUsid(Long.toString(System.currentTimeMillis()));
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.persist(club);
		manager.getTransaction().commit();
		manager.close();
		return club.getName();
	}

	@Override
	public TravelClub retrieve(String clubId) {
		//
		TravelClub club = null;

		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		club = (TravelClub) manager.createQuery("from TravelClub club where club.usid = " + clubId).getSingleResult();
		club.getMembershipList().addAll(membershipStore.readByClubId(clubId));
		manager.getTransaction().commit();
		manager.close();
		return club;
	}

	@Override
	public TravelClub retrieveByName(String name) {
		//
		TravelClub club = null;

		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		Query query = manager.createQuery("from TravelClub where name = :name");
		query.setParameter("name", name);
		try {
			club = (TravelClub) query.getSingleResult();
			club.getMembershipList().addAll(membershipStore.readByClubId(club.getUsid()));
		} catch (NoResultException e) {
			e.getMessage();
		} finally {
			manager.getTransaction().commit();
			manager.close();
		}
		return club;
	}

	@Override
	public void update(TravelClub club) {
		//
		boolean isExist = false;
		List<ClubMembership> memberships = new ArrayList<>();

		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();

		try {
			memberships = membershipStore.readByClubId(club.getId());
			if (memberships.isEmpty()) {
				for (ClubMembership membership : club.getMembershipList()) {
					membershipStore.createMembership(membership);
				}
				return;
			}

			for (ClubMembership membershipInDb : memberships) {
				isExist = false;
				for (ClubMembership membership : club.getMembershipList()) {
					if ((membership.getMemberEmail().equals(membershipInDb.getMemberEmail())
							&& membership.getClubId().equals(membership.getClubId())))
						isExist = true;
				}
				if (!isExist) {
					membershipStore.checkDeletedMembership(membershipInDb.getClubId(), membershipInDb.getMemberEmail());
				}
			}

			for (ClubMembership membership : club.getMembershipList()) {
				isExist = false;
				for (ClubMembership membershipInDb : memberships) {
					if ((membership.getMemberEmail().equals(membershipInDb.getMemberEmail())
							&& membership.getClubId().equals(membership.getClubId())))
						isExist = true;
				}
				if (!isExist) {
					membershipStore.checkCreatedMembership(membership);
				}
			}
		} finally {
			manager.getTransaction().commit();
			manager.close();
		}
	}

	@Override
	public void delete(String clubId) {
		//
		TravelClub club = null;
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		club = (TravelClub) manager.createQuery("from TravelClub club where club.usid = " + clubId).getSingleResult();
		manager.remove(club);
		manager.getTransaction().commit();
		manager.close();
	}

	@Override
	public boolean exists(String clubId) {
		//
		if (this.retrieve(clubId) != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<TravelClub> retrieveAll() {
		//
		List<TravelClub> clubs = new ArrayList<>();

		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		clubs = manager.createQuery("from TravelClub club").getResultList();
		for(TravelClub club : clubs) {
			club.getMembershipList().addAll(membershipStore.readByClubId(club.getUsid()));
		}
		manager.getTransaction().commit();
		manager.close();

		return clubs;
	}

}
