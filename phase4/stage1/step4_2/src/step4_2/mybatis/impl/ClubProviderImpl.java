package step4_2.mybatis.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.TravelClub;
import step4_2.mapper.ClubMapper;
import step4_2.mapper.MembershipMapper;
import step4_2.mybatis.Mybatis;
import step4_2.mybatis.provider.ClubProvider;

public class ClubProviderImpl implements ClubProvider {
	//
	private SqlSession session;

	@Override
	public String create(TravelClub club) {
		//
		try {
			session = Mybatis.openSession();
			session.getMapper(ClubMapper.class).write(club);
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
		return club.getName();
	}

	@Override
	public TravelClub retrieve(String clubId) {
		//
		TravelClub club;
		List<ClubMembership> memberships = new ArrayList<>();

		try {
			session = Mybatis.openSession();
			club = session.getMapper(ClubMapper.class).read(clubId);
			memberships = session.getMapper(MembershipMapper.class).readByClubId(clubId);
			session.commit();
			club.getMembershipList().addAll(memberships);
		} finally {
			Mybatis.closeSession(session);
		}
		return club;
	}

	@Override
	public TravelClub retrieveByName(String name) {
		//
		TravelClub club;
		List<ClubMembership> memberships = new ArrayList<>();

		try {
			session = Mybatis.openSession();
			club = session.getMapper(ClubMapper.class).readByName(name);
			memberships = session.getMapper(MembershipMapper.class).readByMemberName(name);
			session.commit();
			if (!memberships.isEmpty()) {
				club.getMembershipList().addAll(memberships);
			}
		} finally {
			Mybatis.closeSession(session);
		}
		return club;
	}

	@Override
	public void update(TravelClub club) {
		//
		List<ClubMembership> memberships = new ArrayList<>();
		boolean isExist = false;

		try {
			session = Mybatis.openSession();
			memberships = session.getMapper(MembershipMapper.class).readByClubId(club.getId());
			if (memberships.isEmpty()) {
				for (ClubMembership membership : club.getMembershipList()) {
					session.getMapper(MembershipMapper.class).createMembership(membership);
					session.commit();
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
					session.getMapper(MembershipMapper.class).checkDeletedMembership(membershipInDb.getClubId(),
							membershipInDb.getMemberEmail());
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
					session.getMapper(MembershipMapper.class).checkCreatedMembership(membership);
				}
			}
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
	}

	@Override
	public void delete(String clubId) {
		//
		try {
			session = Mybatis.openSession();
			session.getMapper(ClubMapper.class).delete(clubId);
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
	}

	@Override
	public boolean exists(String clubId) {
		//
		int usid = -1;

		try {
			session = Mybatis.openSession();
			usid = session.getMapper(ClubMapper.class).exists(clubId);
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
		if (usid != -1) {
			return true;
		}
		return false;
	}

	@Override
	public List<TravelClub> retrieveAll() {
		//
		List<TravelClub> clubs = new ArrayList<>();
		List<ClubMembership> memberships = new ArrayList<>();

		try {
			session = Mybatis.openSession();
			clubs = session.getMapper(ClubMapper.class).readAll();

			for (TravelClub club : clubs) {
				//
				memberships = session.getMapper(MembershipMapper.class).readByClubId(club.getId());
				if (!memberships.isEmpty()) {
					club.getMembershipList().addAll(memberships);
				}
			}
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}

		return clubs;
	}
}
