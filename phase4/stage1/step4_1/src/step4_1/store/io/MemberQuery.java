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
			state.setString(1, id);
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

		try {
			state = MariaDB.runQuery(
					"SELECT EMAIL, NAME, NICKNAME, PHONENUMBER, BIRTHDAY FROM COMMUNITYMEMBER WHERE EMAIL = ?");
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
		List<CommunityMember> members = new ArrayList<>();

		try {
			state = MariaDB.runQuery(
					"SELECT EMAIL, NAME, NICKNAME, PHONENUMBER, BIRTHDAY FROM COMMUNITYMEMBER WHERE NAME = ?");
			state.setString(1, name);
			resultSet = state.executeQuery();
			state.close();
			while (resultSet.next()) {
				communityMember = new CommunityMember(resultSet.getString("EMAIL"), resultSet.getString("NAME"),
						resultSet.getString("PHONENUMBER"));
				communityMember.setNickName(resultSet.getString("NICKNAME"));
				communityMember.setBirthDay(resultSet.getString("BIRTHDAY"));
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
		try {
			// Member자신의 정보가변할때 업데이트
			state = MariaDB.runQuery(
					"UPDATE COMMUNITYMEMBER SET NICKNAME = ?, PHONENUMBER = ? , BIRTHDAY = ? WHERE EMAIL = ?");
			state.setString(1, member.getNickName());
			state.setString(2, member.getPhoneNumber());
			state.setString(3, member.getBirthDay());
			state.setString(4, member.getEmail());
			state.executeUpdate();
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
		CommunityMember communityMember;

		try {
			state = MariaDB.runQuery("SELECT EMAIL, NAME, NICKNAME, PHONENUMBER, BIRTHDAY FROM COMMUNITYMEMBER ");
			resultSet = state.executeQuery();

			while (resultSet.next()) {
				communityMember = new CommunityMember(resultSet.getString("EMAIL"), resultSet.getString("NAME"),
						resultSet.getString("PHONENUMBER"));
				communityMember.setNickName(resultSet.getString("NICKNAME"));
				communityMember.setBirthDay(resultSet.getString("BIRTHDAY"));
				members.add(communityMember);
			}
			resultSet.close();

		} catch (SQLException e) {
			//
			System.out.println("Member Query Exception --> " + e.getMessage());
		}
		MariaDB.closeQuery();
		return members;
	}
}
