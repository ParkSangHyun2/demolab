package step4_2.mybatis.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.CommunityMember;
import step4_2.mapper.MemberMapper;
import step4_2.mapper.MembershipMapper;
import step4_2.mybatis.Mybatis;
import step4_2.mybatis.provider.MemberProvider;

public class MemberProviderImpl implements MemberProvider {
	//
	private SqlSession session;

	@Override
	public String create(CommunityMember member) {
		//
		String email;

		try {
			session = Mybatis.openSession();
			session.getMapper(MemberMapper.class).write(member);
			email = session.getMapper(MemberMapper.class).exists(member.getEmail());
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
		return email;
	}

	@Override
	public CommunityMember retrieve(String memberEmail) {
		//
		CommunityMember member = null;
		List<ClubMembership> memberships = new ArrayList<>();

		try {
			session = Mybatis.openSession();
			member = session.getMapper(MemberMapper.class).read(memberEmail);
			memberships = session.getMapper(MembershipMapper.class).readByMemberEmail(memberEmail);
			member.getMembershipList().addAll(memberships);
			session.commit();
		} catch(Exception e){
			return member;
		}finally {
			Mybatis.closeSession(session);
		}
		return member;
	}

	@Override
	public List<CommunityMember> retrieveByName(String name) {
		//
		List<CommunityMember> members = new ArrayList<>();
		List<ClubMembership> memberships = new ArrayList<>();

		try {
			session = Mybatis.openSession();
			members = session.getMapper(MemberMapper.class).readByName(name);
			memberships = session.getMapper(MembershipMapper.class).readByMemberName(name);
			session.commit();
			if (!memberships.isEmpty()) {
				for (CommunityMember member : members) {
					member.getMembershipList().addAll(memberships);
				}
			}
		} finally {
			Mybatis.closeSession(session);
		}
		return members;
	}

	@Override
	public void update(CommunityMember member) {
		//
		List<ClubMembership> memberships = new ArrayList<>();
		boolean isExist = false;

		try {
			session = Mybatis.openSession();
			memberships = session.getMapper(MembershipMapper.class).readByMemberEmail(member.getEmail());
			if (memberships.isEmpty() && !member.getMembershipList().isEmpty()) {
				for (ClubMembership membership : member.getMembershipList()) {
					session.getMapper(MembershipMapper.class).createMembership(membership);
					session.commit();
				}
				return;
			}
			session.getMapper(MemberMapper.class).update(member);

			for (ClubMembership membershipInDb : memberships) {
				isExist = false;
				for (ClubMembership membership : member.getMembershipList()) {
					if ((membership.getMemberEmail().equals(membershipInDb.getMemberEmail())
							&& membership.getClubId().equals(membership.getClubId())))
						isExist = true;
				}
				if (!isExist) {
					session.getMapper(MembershipMapper.class).checkDeletedMembership(membershipInDb.getClubId(),
							membershipInDb.getMemberEmail());
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
					session.getMapper(MembershipMapper.class).checkCreatedMembership(membership);
				}
			}
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
	}

	@Override
	public void delete(String memberId) {
		//
		try {
			session = Mybatis.openSession();
			session.getMapper(MemberMapper.class).delete(memberId);
			session.commit();
		} finally {
			Mybatis.closeSession(session);
		}
	}

	@Override
	public boolean exists(String memberId) {
		//
		String email;

		try {
			session = Mybatis.openSession();
			email = session.getMapper(MemberMapper.class).exists(memberId);
			session.commit();
			if (email.equals(memberId))
				return true;
		} finally {
			Mybatis.closeSession(session);
		}
		return false;
	}

	@Override
	public List<CommunityMember> retrieveAll() {
		//
		List<CommunityMember> members = new ArrayList<>();
		List<ClubMembership> memberships = new ArrayList<>();

		try {
			session = Mybatis.openSession();
			members = session.getMapper(MemberMapper.class).readAll();
			for (CommunityMember member : members) {
				memberships = session.getMapper(MembershipMapper.class).readByMemberEmail(member.getEmail());
				if(!memberships.isEmpty())
					member.getMembershipList().addAll(memberships);
				session.commit();
			}
		} finally {
			Mybatis.closeSession(session);
		}
		return members;
	}
}
