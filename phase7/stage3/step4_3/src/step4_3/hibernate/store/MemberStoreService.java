package step4_3.hibernate.store;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.CommunityMember;
import step1.share.service.store.MemberStore;
import step4_3.hibernate.entityManager.ClubEntityManager;

public class MemberStoreService implements MemberStore {
	//
	private ClubEntityManager entityManager;
	private MembershipStore membershipStore;
	private EntityManagerFactory factory;

	public MemberStoreService() {
		//
		try {
			entityManager = new ClubEntityManager();
			membershipStore = new MembershipStoreService();
			factory = entityManager.setUp();
		} catch (Exception e) {
			//
			e.printStackTrace();
		}
	}

	@Override
	public String create(CommunityMember member) {
		//
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.persist(member);
		manager.getTransaction().commit();
		manager.close();
		return member.getEmail();
	}

	@Override
	public CommunityMember retrieve(String email) {
		//
		CommunityMember member = null;

		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		Query query = manager.createQuery("from CommunityMember where memberEmail = :email");
		query.setParameter("email", email);
		try {
			member = (CommunityMember) query.getSingleResult();
			member.getMembershipList().addAll(membershipStore.readByMemberEmail(email));
		} catch (NoResultException e) {
			e.getMessage();
		} finally {
			manager.getTransaction().commit();
			manager.close();
		}
		return member;
	}

	@Override
	public List<CommunityMember> retrieveByName(String name) {
		//
		List<CommunityMember> members = new ArrayList<>();

		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		Query query = manager.createQuery("from CommunityMember where memberName = :name");
		query.setParameter("name", name);
		members = query.getResultList();
		for(CommunityMember member : members) {
			member.getMembershipList().addAll(membershipStore.readByMemberEmail(member.getEmail()));
		}
		manager.getTransaction().commit();
		manager.close();
		return members;
	}

	@Override
	public void update(CommunityMember member) {
		//
		boolean isExist = false;
		List<ClubMembership> memberships = new ArrayList<>();

		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();

		try {
			memberships = membershipStore.readByMemberEmail(member.getEmail());
			if (memberships.isEmpty() && !member.getMembershipList().isEmpty()) {
				for (ClubMembership membership : member.getMembershipList()) {
					membershipStore.createMembership(membership);
				}
				return;
			}
			
			manager.merge(member);

			for (ClubMembership membershipInDb : memberships) {
				isExist = false;
				for (ClubMembership membership : member.getMembershipList()) {
					if ((membership.getMemberEmail().equals(membershipInDb.getMemberEmail())
							&& membership.getClubId().equals(membership.getClubId())))
						isExist = true;
				}
				if (!isExist) {
					membershipStore.checkDeletedMembership(membershipInDb.getClubId(), membershipInDb.getMemberEmail());
				}
			}

			for (ClubMembership membership : member.getMembershipList()) {
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
	public void delete(String email) {
		//
		CommunityMember member = null;
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		Query query = manager.createQuery("from CommunityMember where memberEmail = :email");
		query.setParameter("email", email);
		member = (CommunityMember) query.getSingleResult();
		manager.remove(member);
		manager.getTransaction().commit();
		manager.close();
	}

	@Override
	public boolean exists(String email) {
		//
		if (this.retrieve(email) != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<CommunityMember> retrieveAll() {
		//
		List<CommunityMember> members = new ArrayList<>();

		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		members = manager.createQuery("from CommunityMember").getResultList();
		for(CommunityMember member : members) {
			member.getMembershipList().addAll(membershipStore.readByMemberEmail(member.getEmail()));
		}

		return members;
	}
}
