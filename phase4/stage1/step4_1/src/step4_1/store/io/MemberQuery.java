package step4_1.store.io;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.CommunityMember;
import step1.share.domain.entity.club.RoleInClub;

public class MemberQuery {
	//
	private PreparedStatement state;
	private ResultSet resultSet;

	public boolean exists(String id) {
		//
		boolean isExist = false;
		try {
			state = MariaDB.runQuery("SELECT EMAIL FROM COMMUNITYMEMBER WHERE EMAIL = ?");
			state.setString(1,id);
			resultSet = state.executeQuery();
			state.close();

			if (resultSet.next()) {
				isExist = true;
			}
		} catch (SQLException e) {
			//
			System.out.println(e.getMessage());
		}

		MariaDB.closeQuery();
		return isExist;
	}

	public void write(CommunityMember member) {
		//
		try {
			state = MariaDB.runQuery("INSERT INTO COMMUNITYMEMBER(EMAIL, NAME, PHONENUMBER) VALUES(?,?,?)");
			state.setString(1, member.getEmail());
			state.setString(2, member.getName());
			state.setString(3, member.getPhoneNumber());
			state.executeUpdate();
			state.close();
		} catch (SQLException e) {
			//
			System.out.println(e.getMessage());
		}
		MariaDB.closeQuery();
	}

	public CommunityMember read(String memberId) {
		//
		CommunityMember member = null;
		ClubMembership membership;

		try {
			state = MariaDB.runQuery("SELECT EMAIL, NAME, NICKNAME, PHONENUMBER, BIRTHDAY FROM COMMUNITYMEMBER WHERE EMAIL = ?");
			state.setString(1, memberId);
			resultSet = state.executeQuery();
			state.close();
			if (resultSet.next()) {
				member = new CommunityMember(resultSet.getString("EMAIL"), resultSet.getString("NAME"),
						resultSet.getString("PHONENUMBER"));
				member.setNickName(resultSet.getString("NICKNAME"));
				member.setBirthDay(resultSet.getString("BIRTHDAY"));
			} else {
				return null;
			}

			state = MariaDB.runQuery("SELECT CLUBID, MEMBEREMAIL, MEMBERNAME, ROLE, JOINDATE FROM CLUBMEMBERSHIP WHERE MEMBEREMAIL = ?");
			state.setString(1, memberId);
			resultSet = state.executeQuery();
			state.close();

			while (resultSet.next()) {
				membership = new ClubMembership(Integer.toString(resultSet.getInt("CLUBID")), resultSet.getString("MEMBEREMAIL"));
				membership.setMemberName(resultSet.getString("MEMBERNAME"));
				membership.setRole(valueOfRole(resultSet.getString("ROLE")));
				membership.setJoinDate(resultSet.getString("JOINDATE"));
				member.getMembershipList().add(membership);
			}
		} catch (SQLException e) {
			//
			System.out.println(e.getMessage());
		}
		MariaDB.closeQuery();
		return member;
	}

	public List<CommunityMember> readByName(String name) {
		//
		CommunityMember communityMember = new CommunityMember();
		ClubMembership membership;

		PreparedStatement membershipState;
		ResultSet membershipResult;

		List<CommunityMember> members = new ArrayList<>();

		try {
			state = MariaDB.runQuery("SELECT EMAIL, NAME, NICKNAME, PHONENUMBER, BIRTHDAY FROM COMMUNITYMEMBER WHERE NAME = ?");
			state.setString(1, name);
			resultSet = state.executeQuery();
			state.close();
			while (resultSet.next()) {
				communityMember = new CommunityMember(resultSet.getString("EMAIL"), resultSet.getString("NAME"),
						resultSet.getString("PHONENUMBER"));
				communityMember.setNickName(resultSet.getString("NICKNAME"));
				communityMember.setBirthDay(resultSet.getString("BIRTHDAY"));

				membershipState = MariaDB.runQuery("SELECT CLUBID, MEMBEREMAIL, MEMBERNAME, ROLE, JOINDATE FROM CLUBMEMBERSHIP WHERE MEMBERNAME = ?");
				membershipState.setString(1, name);
				membershipResult = membershipState.executeQuery();

				while (membershipResult.next()) {
					membership = new ClubMembership(membershipResult.getString("CLUBID"),
							membershipResult.getString("MEMBEREMAIL"));
					membership.setMemberName(membershipResult.getString("MEMBERNAME"));
					membership.setRole(valueOfRole(membershipResult.getString("ROLE")));
					membership.setJoinDate(membershipResult.getString("JOINDATE"));
					communityMember.getMembershipList().add(membership);
				}
				members.add(communityMember);
			}
		} catch (SQLException e) {
			//
			System.out.println("MemberQuery Exception --> " + e.getMessage());
		}
		MariaDB.closeQuery();
		return members;
	}

	public void update(CommunityMember member) {
		//
		ArrayList<ClubMembership> comparedMembershipList = new ArrayList<>();
		ResultSet memberResult;
		ResultSet membershipResult;

		String clubId;

		try {
			// Member자신의 정보가변할때 업데이트
			state = MariaDB.runQuery("UPDATE COMMUNITYMEMBER SET NICKNAME = ?, PHONENUMBER = ? , BIRTHDAY = ? WHERE EMAIL = ?");
			state.setString(1, member.getNickName());
			state.setString(2, member.getPhoneNumber());
			state.setString(3, member.getBirthDay());
			state.setString(4, member.getEmail());
			state.executeUpdate();
			state.close();

			// 클럽맴버쉽이 사라지거나 새로생긴것을 알아내고 업데이트
			state = MariaDB.runQuery("SELECT CLUBID, MEMBEREMAIL, MEMBERNAME, ROLE, JOINDATE FROM CLUBMEMBERSHIP WHERE MEMBEREMAIL= ?");
			state.setString(1, member.getEmail());
			membershipResult = state.executeQuery();
			state.close();
			while (membershipResult.next()) {
				comparedMembershipList.add(new ClubMembership(membershipResult.getString("CLUBID"), member.getEmail()));
			}

			// 새로 만들어야하는 맴버쉽
			if (comparedMembershipList.isEmpty()) {
				for (ClubMembership membership : member.getMembershipList()) {
					state = MariaDB.runQuery("INSERT INTO CLUBMEMBERSHIP(CLUBID, MEMBEREMAIL, MEMBERNAME, ROLE, JOINDATE) VALUES(?,?,?,?,?)");
					state.setInt(1, Integer.parseInt(membership.getClubId()));
					state.setString(2, membership.getMemberEmail());
					state.setString(3, membership.getMemberName());
					state.setString(4, membership.getRole().toString());
					state.setString(5, membership.getJoinDate());
					state.executeUpdate();
					state.close();
				}
			}
			// 없어지거나 새로생긴것을 업데이트
			for (ClubMembership membership : member.getMembershipList()) {
				for (ClubMembership dbMembership : comparedMembershipList) {
					if (!member.getMembershipList().contains(dbMembership) && membership.getRole().toString().equals("PRESIDENT")) {
						state = MariaDB.runQuery("DELETE FROM CLUBMEMBERSHIP WHERE CLUBID = ? AND MEMBEREMAIL=?");
						state.setInt(1, Integer.parseInt(dbMembership.getClubId()));
						state.setString(2, dbMembership.getMemberEmail());
						state.executeUpdate();
						state.close();
						break;
					}

					if (!(dbMembership.getClubId().equals(membership.getClubId()))) {
						state = MariaDB.runQuery("INSERT INTO CLUBMEMBERSHIP(CLUBID, MEMBEREMAIL, MEMBERNAME, ROLE, JOINDATE) VALUES(?,?,?,?,?)");
						state.setInt(1, Integer.parseInt(membership.getClubId()));
						state.setString(2, membership.getMemberEmail());
						state.setString(3, membership.getMemberName());
						state.setString(4, membership.getRole().toString());
						state.setString(5, membership.getJoinDate());
						state.executeUpdate();
						break;
					}
				}
			}
			state.close();
		} catch (SQLException e) {
			//
			System.out.println("TravelClub UPDATE Exception --->" + e.getMessage());
		}
		MariaDB.closeQuery();
	}

	public void delete(String memberId) {
		//
		try {
			state = MariaDB.runQuery("DELETE FROM COMMUNITYMEMBER WHERE EMAIL = ?");
			state.setString(1, memberId);
			state.executeUpdate();
			state.close();
		} catch (SQLException e) {
			//
			System.out.println("Member Delete QueryException -->" + e.getMessage());
		}
		MariaDB.closeQuery();
	}

	public List<CommunityMember> readAll() {
		//
		List<CommunityMember> members = new ArrayList<>();

		String[] values = new String[5];
		CommunityMember communityMember;
		ClubMembership membership;

		
		try {
			state = MariaDB.runQuery("SELECT EMAIL, NAME, NICKNAME, PHONENUMBER, BIRTHDAY FROM COMMUNITYMEMBER ");
			resultSet = state.executeQuery();
			
			while (resultSet.next()) {
				communityMember = new CommunityMember(resultSet.getString("EMAIL"), resultSet.getString("NAME"), resultSet.getString("PHONENUMBER"));
				communityMember.setNickName(resultSet.getString("NICKNAME"));
				communityMember.setBirthDay(resultSet.getString("BIRTHDAY"));
				resultSet.close();

				state = MariaDB.runQuery("SELECT CLUBID, MEMBEREMAIL, MEMBERNAME, ROLE, JOINDATE FROM CLUBMEMBERSHIP WHERE MEMBERNAME = ?");
				state.setString(1, communityMember.getEmail());
				resultSet = state.executeQuery();
				state.close();

				while (resultSet.next()) {
					membership = new ClubMembership(resultSet.getString("CLUBID"), resultSet.getString("MEMBEREMAIL"));
					membership.setMemberName(resultSet.getString("MEMBERNAME"));
					membership.setRole(valueOfRole(resultSet.getString("ROLE")));
					membership.setJoinDate(resultSet.getString("JOINDATE"));
					communityMember.getMembershipList().add(membership);

				}
				resultSet.close();
				members.add(communityMember);
			}
		} catch (SQLException e) {
			//
			System.out.println("Member Query Exception --> " + e.getMessage());
		}
		MariaDB.closeQuery();
		return members;
	}

	private RoleInClub valueOfRole(String role) {
		//
		RoleInClub roleInClub = null;
		switch (role) {
		case "MEMBER":
			roleInClub = RoleInClub.Member;
			break;
		case "PRESIDENT":
			roleInClub = RoleInClub.President;
			break;
		}
		return roleInClub;
	}

}
