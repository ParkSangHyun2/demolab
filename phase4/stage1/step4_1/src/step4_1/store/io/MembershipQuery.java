package step4_1.store.io;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.CommunityMember;
import step1.share.domain.entity.club.RoleInClub;
import step1.share.domain.entity.club.TravelClub;
import step4_1.store.io.util.SqlCloseUtil;

public class MembershipQuery {
	//
	private PreparedStatement state;
	private ResultSet resultSet;

	public List<ClubMembership> readByClubId(String clubId) {
		//
		ArrayList<ClubMembership> memberships = new ArrayList<>();

		try {
			state = MariaDB.runQuery(
					"SELECT CLUBID, MEMBEREMAIL, MEMBERNAME, ROLE, JOINDATE FROM CLUBMEMBERSHIP WHERE CLUBID = ?");
			state.setInt(1, Integer.parseInt(clubId));
			resultSet = state.executeQuery();
			while (resultSet.next()) {
				ClubMembership membership = new ClubMembership(resultSet.getString("CLUBID"),
						resultSet.getString("MEMBEREMAIL"));
				membership.setMemberName(resultSet.getString("MEMBERNAME"));
				membership.setRole(valueOfRole(resultSet.getString("ROLE")));
				membership.setJoinDate(resultSet.getString("JOINDATE"));
				memberships.add(membership);
			}
		} catch (SQLException e) {
			//
			System.out.println("Membership query(readByClubId) Exception --> " + e.getMessage());
		}
		SqlCloseUtil.closeSql(state, resultSet, MariaDB.getConnection());
		return memberships;
	}

	public List<ClubMembership> readByMemberName(String memberName) {
		//
		ClubMembership membership;
		ArrayList<ClubMembership> membershipList = new ArrayList<>();

		try {
			state = MariaDB.runQuery(
					"SELECT CLUBID, MEMBEREMAIL, MEMBERNAME, ROLE, JOINDATE FROM CLUBMEMBERSHIP WHERE MEMBERNAME = ?");
			state.setString(1, memberName);
			resultSet = state.executeQuery();
			while (resultSet.next()) {
				membership = new ClubMembership(resultSet.getString("CLUBID"), resultSet.getString("MEMBEREMAIL"));
				membership.setMemberName(resultSet.getString("MEMBERNAME"));
				membership.setRole(valueOfRole(resultSet.getString("ROLE")));
				membership.setJoinDate(resultSet.getString("JOINDATE"));
				membershipList.add(membership);
			}
		} catch (SQLException e) {
			System.out.println("Membership Query(readByMemberName) Exception -->" + e.getMessage());
		}
		SqlCloseUtil.closeSql(state, resultSet, MariaDB.getConnection());
		return membershipList;
	}

	public List<ClubMembership> readByMemberEmail(String memberEmail) {
		//
		ClubMembership membership;
		ArrayList<ClubMembership> memberships = new ArrayList<>();

		try {
			state = MariaDB.runQuery(
					"SELECT CLUBID, MEMBEREMAIL, MEMBERNAME, ROLE, JOINDATE FROM CLUBMEMBERSHIP WHERE MEMBEREMAIL = ?");
			state.setString(1, memberEmail);
			resultSet = state.executeQuery();
			while (resultSet.next()) {
				membership = new ClubMembership(resultSet.getString("CLUBID"), resultSet.getString("MEMBEREMAIL"));
				membership.setMemberName(resultSet.getString("MEMBERNAME"));
				membership.setRole(valueOfRole(resultSet.getString("ROLE")));
				membership.setJoinDate(resultSet.getString("JOINDATE"));
				memberships.add(membership);
			}
		} catch (SQLException e) {
			//
			System.out.println("Membership query(readByMemberEmail) Exception --> " + e.getMessage());
		}
		SqlCloseUtil.closeSql(state, resultSet, MariaDB.getConnection());
		return memberships;
	}

	public boolean createMembershipForClub(TravelClub club) {
		//
		List<ClubMembership> membershipList = this.readByClubId(club.getId());
		try {
			if (membershipList.isEmpty()) {
				for (ClubMembership membership : club.getMembershipList()) {
					System.out.println("LUG : " + membership);
					state = MariaDB.runQuery(
							"INSERT INTO CLUBMEMBERSHIP(CLUBID, MEMBEREMAIL, MEMBERNAME, ROLE, JOINDATE) VALUES(?,?,?,?,?)");
					state.setInt(1, Integer.parseInt(membership.getClubId()));
					state.setString(2, membership.getMemberEmail());
					state.setString(3, membership.getMemberName());
					state.setString(4, membership.getRole().toString());
					state.setString(5, membership.getJoinDate());
					state.executeUpdate();
				}
			} else {
				return false;
			}
		} catch (SQLException e) {
			//
			System.out.println("Membership Query(createMembershipForCLub) Exception -->" + e.getMessage());
		}
		SqlCloseUtil.closeSql(state, MariaDB.getConnection());
		return true;
	}

	public boolean createMembershipForMember(CommunityMember member) {
		//
		List<ClubMembership> membershipList = this.readByMemberEmail(member.getEmail());

		try {
			if (membershipList.isEmpty()) {
				for (ClubMembership membership : member.getMembershipList()) {
					state = MariaDB.runQuery(
							"INSERT INTO CLUBMEMBERSHIP(CLUBID, MEMBEREMAIL, MEMBERNAME, ROLE, JOINDATE) VALUES(?,?,?,?,?)");
					state.setInt(1, Integer.parseInt(membership.getClubId()));
					state.setString(2, membership.getMemberEmail());
					state.setString(3, membership.getMemberName());
					state.setString(4, membership.getRole().toString());
					state.setString(5, membership.getJoinDate());
					state.executeUpdate();
				}
			} else {
				return false;
			}
		} catch (SQLException e) {
			//
			System.out.println("Membership Query(createMembership) Exception -->" + e.getMessage());
		}
		SqlCloseUtil.closeSql(state, MariaDB.getConnection());
		return true;
	}

	public void checkDeletedMembershipForClub(TravelClub club) {
		//
		List<ClubMembership> membershipList = this.readByClubId(club.getId());

		boolean isExist = false;
		try {
			for (ClubMembership membershipInDb : membershipList) {
				isExist = false;
				for (ClubMembership membership : club.getMembershipList()) {
					if ((membership.getMemberEmail().equals(membershipInDb.getMemberEmail())
							&& membership.getClubId().equals(membership.getClubId())))
						isExist = true;
				}
				if (!isExist) {
					state = MariaDB.runQuery("DELETE FROM CLUBMEMBERSHIP WHERE CLUBID = ? AND MEMBEREMAIL=?");
					state.setInt(1, Integer.parseInt(membershipInDb.getClubId()));
					state.setString(2, membershipInDb.getMemberEmail());
					state.executeUpdate();
				}
			}
		} catch (SQLException e) {
			System.out.println("Membership Query(checkDeletedMembership) Exception -->" + e.getMessage());
		}
		SqlCloseUtil.closeSql(state, MariaDB.getConnection());
	}

	public void checkDeletedMembershipForMember(CommunityMember member) {
		//
		List<ClubMembership> membershipList = this.readByMemberEmail(member.getEmail());
		boolean isExist = false;

		try {
			for (ClubMembership membershipInDb : membershipList) {
				isExist = false;
				for (ClubMembership membership : member.getMembershipList()) {
					if ((membership.getMemberEmail().equals(membershipInDb.getMemberEmail())
							&& membership.getClubId().equals(membership.getClubId())))
						isExist = true;
				}
				if (!isExist) {
					state = MariaDB.runQuery("DELETE FROM CLUBMEMBERSHIP WHERE CLUBID = ? AND MEMBEREMAIL=?");
					state.setInt(1, Integer.parseInt(membershipInDb.getClubId()));
					state.setString(2, membershipInDb.getMemberEmail());
					state.executeUpdate();
				}
			}
		} catch (SQLException e) {
			System.out.println("Membership Query(checkDeletedMembership) Exception -->" + e.getMessage());
		}
		SqlCloseUtil.closeSql(state, MariaDB.getConnection());
	}

	public void checkCreatedMembershipForClub(TravelClub club) {
		//
		List<ClubMembership> membershipList = this.readByClubId(club.getId());
		boolean isExist = false;

		try {
			for (ClubMembership membership : club.getMembershipList()) {
				isExist = false;
				for (ClubMembership membershipInDb : membershipList) {
					if ((membership.getMemberEmail().equals(membershipInDb.getMemberEmail())
							&& membership.getClubId().equals(membership.getClubId())))
						isExist = true;
				}
				if (!isExist) {
					state = MariaDB.runQuery(
							"INSERT INTO CLUBMEMBERSHIP(CLUBID, MEMBERMAIL, MEMBERNAME, ROLE, JOINDATE) VALUES(?,?,?,?,?)");
					state.setInt(1, Integer.parseInt(membership.getClubId()));
					state.setString(2, membership.getMemberEmail());
					state.setString(3, membership.getMemberName());
					state.setString(4, membership.getRole().toString());
					state.setString(5, membership.getJoinDate());
					state.executeUpdate();
				}
			}
		} catch (SQLException e) {
			//
			System.out.println("Membership Query(checkCreatedMembership) Exception --->" + e.getMessage());
		}
		MariaDB.closeQuery();
	}

	public void checkCreatedMembershipForMember(CommunityMember member) {
		//
		List<ClubMembership> membershipList = this.readByMemberEmail(member.getEmail());
		boolean isExist = false;

		try {
			for (ClubMembership membership : member.getMembershipList()) {
				isExist = false;
				for (ClubMembership membershipInDb : membershipList) {
					if ((membership.getMemberEmail().equals(membershipInDb.getMemberEmail())
							&& membership.getClubId().equals(membership.getClubId())))
						isExist = true;
				}
				if (!isExist) {
					state = MariaDB.runQuery(
							"INSERT INTO CLUBMEMBERSHIP(CLUBID, MEMBERMAIL, MEMBERNAME, ROLE, JOINDATE) VALUES(?,?,?,?,?)");
					state.setInt(1, Integer.parseInt(membership.getClubId()));
					state.setString(2, membership.getMemberEmail());
					state.setString(3, membership.getMemberName());
					state.setString(4, membership.getRole().toString());
					state.setString(5, membership.getJoinDate());
					state.executeUpdate();
				}
			}

		} catch (SQLException e) {
			//
			System.out.println("Membership Query(checkCreatedMembership) Exception --->" + e.getMessage());
		}
		SqlCloseUtil.closeSql(state, MariaDB.getConnection());
	}

	private RoleInClub valueOfRole(String role) {
		//
		RoleInClub roleInClub = null;
		switch (role) {
		case "Member":
			roleInClub = RoleInClub.Member;
			break;
		case "President":
			roleInClub = RoleInClub.President;
			break;
		}
		return roleInClub;
	}
}
