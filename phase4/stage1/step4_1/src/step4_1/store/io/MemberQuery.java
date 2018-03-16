package step4_1.store.io;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.CommunityMember;
import step1.share.domain.entity.club.RoleInClub;
import step4_1.dao.MemberDao;

public class MemberQuery {
	//
	private static PreparedStatement state;

	public boolean exists(String id) {
		//
		boolean isExist = false;
		state = MariaDB.runQuery("SELECT * FROM COMMUNITYMEMBER WHERE EMAIL = ?", id);
		try {

			ResultSet result = state.executeQuery();
			state.close();
			while (result.next()) {
					isExist = true;
					System.out.println("true");
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
		state = MariaDB.runQuery(
				"INSERT INTO COMMUNITYMEMBER VALUES(?,?,?,?,?)",
				member.getEmail(),member.getName(), member.getNickName(), member.getPhoneNumber(), member.getBirthDay());
		try {
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
		String[] values = new String[5];
		CommunityMember member = new CommunityMember();

		state = MariaDB.runQuery("SELECT * FROM COMMUNITYMEMBER WHERE EMAIL = ?", memberId);
		try {
			ResultSet result = state.executeQuery();
			state.close();
			while (result.next()) {
				values[0] = result.getString("EMAIL");
				values[1] = result.getString("NAME");
				values[2] = result.getString("NICKNAME");
				values[3] = result.getString("PHONENUMBER");
				values[4] = result.getString("BIRTHDAY");
			}
			if(!result.first()) {
				return null;
			}
			member = new MemberDao(values).toCommunityMember();
			
			System.out.println("member -->" + member.getEmail() + member.getName());

			state = MariaDB.runQuery("SELECT * FROM CLUBMEMBERSHIP WHERE MEMBEREMAIL = ?", memberId);
			result = state.executeQuery();
			state.close();

			while (result.next()) {
				ClubMembership membership = new ClubMembership(result.getString("clubId"),
						result.getString("memberEmail"));
				membership.setMemberName(result.getString("memberName"));
				switch(result.getObject("role").toString()) {
				case "PRESIDENT":
					membership.setRole(RoleInClub.President);
					break;
				case "MEMBER":
					membership.setRole(RoleInClub.Member);
					break;
				}
				membership.setJoinDate(result.getString("joinDate"));
				
				System.out.println("hsip -->" + result.getShort("memberEmail"));
				System.out.println("hsip -->" + result.getShort("memberName"));
				System.out.println("hsip -->" + result.getShort("role"));
				System.out.println("hsip -->" + result.getShort("joinDate"));
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
		String[] values = new String[5];
		CommunityMember member = new CommunityMember();
		List<CommunityMember> members = new ArrayList<>();

		state = MariaDB.runQuery("SELECT * FROM COMMUNITYMEMBER WHERE NAME = ?", name);
		try {
			ResultSet result = state.executeQuery();
			state.close();
			while (result.next()) {
				for(int i=0; i<5; i++) {
					values[i] = result.getString(i+1);
				}
				member = new MemberDao(values).toCommunityMember();

				PreparedStatement membershipState = MariaDB
						.runQuery("SELECT * FROM CLUBMEMBERSHIP WHERE MEMBERNAME = ?", name);
				ResultSet membershipResult = membershipState.executeQuery();

				while (membershipResult.next()) {
					ClubMembership membership = new ClubMembership(membershipResult.getString("clubId"),
							membershipResult.getString("memberEmail"));
					membership.setMemberName(membershipResult.getString("memberName"));
					switch(membershipResult.getObject("role").toString()) {
					case "PRESIDENT":
						membership.setRole(RoleInClub.President);
						break;
					case "MEMBER":
						membership.setRole(RoleInClub.Member);
						break;
					}
					membership.setJoinDate(membershipResult.getString("joinDate"));

					member.getMembershipList().add(membership);
				}
				members.add(member);
			}

		} catch (SQLException e) {
			//
			System.out.println("readByName Exception :"+e.getMessage());
		}
		MariaDB.closeQuery();
		return members;
	}

	public void update(CommunityMember member) {
		//
		ArrayList<ClubMembership> membershipList = new ArrayList<>();

		String clubId;

		try {
			// Member자신의 정보가변할때 업데이트 
			state = MariaDB.runQuery("SELECT * FROM COMMUNITYMEMBER WHERE EMAIL = ?", member.getEmail());
			String nickName = member.getNickName();
			String phoneNumber = member.getPhoneNumber();
			String birthDay = member.getBirthDay();
			
			ResultSet memberResult = state.executeQuery();
			if(memberResult.next()) {
				state = MariaDB.runQuery("UPDATE COMMUNITYMEMBER SET NICKNAME = ?, PHONENUMBER = ? , BIRTHDAY = ? WHERE EMAIL = ?",
						nickName, phoneNumber, birthDay, member.getEmail());
				state.executeUpdate();
				state.close();
			}
			
			
			//클럽맴버쉽이 사라지거나 새로생긴것을 알아내고 업데이트
			state = MariaDB.runQuery("SELECT * FROM CLUBMEMBERSHIP WHERE MEMBEREMAIL= ?", member.getEmail());
			ResultSet membershipResult = state.executeQuery();
			state.close();
			while (membershipResult.next()) {
				clubId = membershipResult.getString("CLUBID");
				membershipList.add(new ClubMembership(clubId, member.getEmail()));
			}
			// 새로 만들어야하는 맴버쉽
			if(membershipList.isEmpty()) {
				for(ClubMembership membership : member.getMembershipList()) {
					state = MariaDB.runQuery("INSERT INTO CLUBMEMBERSHIP VALUES(?,?,?,?,?)", membership.getClubId(),
							membership.getMemberEmail(), membership.getMemberName(),
							membership.getRole().toString(), membership.getJoinDate());
					state.executeUpdate();
					state.close();
				}
			}
			//없어지거나 새로생긴것을 업데이트
			for (ClubMembership membership : member.getMembershipList()) {
				for (ClubMembership dbMembership : membershipList) {
					if(!member.getMembershipList().contains(dbMembership) && membership.getRole().toString().equals("Presidnet")) {
						state = MariaDB.runQuery(
								"DELETE FROM CLUBMEMBERSHIP WHERE CLUBID = ? AND MEMBEREMAIL=?",
								dbMembership.getClubId(), dbMembership.getMemberEmail());
						state.executeUpdate();
						state.close();
						break;
					}
					
					if (!(dbMembership.getClubId().equals(membership.getClubId()))) {
						state = MariaDB.runQuery("INSERT INTO CLUBMEMBERSHIP VALUES(?,?,?,?,?)", membership.getClubId(),
								membership.getMemberEmail(), membership.getMemberName(),
								membership.getRole().toString(), membership.getJoinDate());
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
		state = MariaDB.runQuery("DELETE FROM COMMUNITYMEMBER" + "WHERE EMAIL = ?", memberId);
		try {
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
		CommunityMember member = new CommunityMember();

		state = MariaDB.runQuery("SELECT * FROM COMMUNITYMEMBER ");
		try {
			ResultSet result = state.executeQuery();
			while (result.next()) {
				for(int i=0; i<5; i++) {
					values[i] = result.getString(i+1);
				}

				member = new MemberDao(values).toCommunityMember();
				result.close();
				
				state = MariaDB.runQuery("SELECT * FROM CLUBMEMBERSHIP WHERE MEMBERNAME = ?",member.getEmail());
				result = state.executeQuery();
				state.close();

				while (result.next()) {
					ClubMembership membership = new ClubMembership(result.getString("clubId"),
							result.getString("memberEmail"));
					membership.setMemberName(result.getString("memberName"));
					membership.setRole((RoleInClub) result.getObject("role"));
					membership.setJoinDate(result.getString("joinDate"));

					member.getMembershipList().add(membership);
					
				}
				result.close();
				members.add(member);
			}
		} catch (SQLException e) {
			//
			System.out.println(e.getMessage());
		}
		MariaDB.closeQuery();
		return members;
	}

}
