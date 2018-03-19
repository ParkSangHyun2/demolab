package step4_1.store.io;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.RoleInClub;
import step1.share.domain.entity.club.TravelClub;

public class ClubQuery {
	//
	private PreparedStatement state;
	private ResultSet resultSet;

	public boolean exists(String id) {
		//
		boolean isExist = false;
		try {
			state = MariaDB.runQuery("SELECT USID FROM TRAVELCLUB WHERE USID = ?");
			state.setInt(1, Integer.parseInt(id));
			resultSet = state.executeQuery();
			state.close();
			
			if(resultSet.next()) {
				isExist = true;
			}
		} catch (SQLException e) {
			//
			System.out.println(e.getMessage());
		}
		
		MariaDB.closeQuery();
		return isExist;
	}

	public void write(TravelClub club) {
		//
		state = MariaDB.runQuery("INSERT INTO TRAVELCLUB(NAME,INTRO,FOUNDATIONDAY,BOARDID) VALUES(?,?,?,?)");
		try {
			state.setString(1, club.getName());
			state.setString(2, club.getIntro());
			state.setString(3, club.getFoundationDay());
			state.setString(4, club.getBoardId());
			state.executeUpdate();
			state.close();
		} catch (SQLException e) {
			//
			System.out.println("ClubQuery(WRITE) Exception -->" + e.getMessage());
		}
		MariaDB.closeQuery();
	}

	public TravelClub read(String clubId) {
		//
		TravelClub club = null;
		
		try {
			state = MariaDB.runQuery("SELECT NAME, INTRO, FOUNDATIONDAY, BOARDID, USID FROM TRAVELCLUB WHERE USID = ?");
			state.setInt(1, Integer.parseInt(clubId));
			resultSet = state.executeQuery();
			state.close();
			
			if(resultSet.next()) {
			club =  new TravelClub(resultSet.getString("NAME"), resultSet.getString("INTRO"));
			club.setFoundationDay(resultSet.getString("FOUNDATIONDAY"));
			club.setBoardId(resultSet.getString("BOARDID"));
			club.setUsid(Integer.toString(resultSet.getInt("USID")));
			} else {
				return null;
			}

			state = MariaDB.runQuery("SELECT CLUBID, MEMBEREMAIL, MEMBERNAME, ROLE, JOINDATE FROM CLUBMEMBERSHIP WHERE CLUBID = ?");
			state.setInt(1, Integer.parseInt(clubId));
			resultSet = state.executeQuery();
			state.close();

			while (resultSet.next()) {
				ClubMembership membership = new ClubMembership(resultSet.getString("CLUBID"),resultSet.getString("MEMBEREMAIL"));
				membership.setMemberName(resultSet.getString("MEMBERNAME"));
				membership.setRole(valueOfRole(resultSet.getString("ROLE")));
				membership.setJoinDate(resultSet.getString("JOINDATE"));
				club.getMembershipList().add(membership);
			}

		} catch (SQLException e) {
			//
			System.out.println("ClubQuery Exception -->" + e.getMessage());
		}
		MariaDB.closeQuery();
		return club;
	}

	public TravelClub readByName(String name) {
		//
		TravelClub club = null;
		
		try {
			state = MariaDB.runQuery("SELECT NAME, INTRO, FOUNDATIONDAY, BOARDID, USID FROM TRAVELCLUB WHERE NAME = ?");
			state.setString(1, name);
			resultSet = state.executeQuery();
			state.close();
			
			if(resultSet.next()) {
			club =  new TravelClub(resultSet.getString("NAME"), resultSet.getString("INTRO"));
			club.setFoundationDay(resultSet.getString("FOUNDATIONDAY"));
			club.setBoardId(resultSet.getString("BOARDID"));
			club.setUsid(Integer.toString(resultSet.getInt("USID")));
			} else {
				return null;
			}

			state = MariaDB.runQuery("SELECT CLUBID, MEMBEREMAIL, MEMBERNAME, ROLE, JOINDATE FROM CLUBMEMBERSHIP WHERE CLUBID = ?");
			state.setInt(1, Integer.parseInt(club.getUsid()));
			resultSet = state.executeQuery();
			state.close();

			while (resultSet.next()) {
				ClubMembership membership = new ClubMembership(resultSet.getString("CLUBID"),resultSet.getString("MEMBEREMAIL"));
				membership.setMemberName(resultSet.getString("MEMBERNAME"));
				membership.setRole(valueOfRole(resultSet.getString("ROLE")));
				membership.setJoinDate(resultSet.getString("JOINDATE"));
				club.getMembershipList().add(membership);
			}

		} catch (SQLException e) {
			//
			System.out.println("ClubQuery Exception -->" + e.getMessage());
		}
		MariaDB.closeQuery();
		return club;
	}

	public void update(TravelClub club) {
		//
		ArrayList<ClubMembership> membershipList = new ArrayList<>();

		String memberEmail;

		try {
			state = MariaDB.runQuery("SELECT CLUBID, MEMBEREMAIL, MEMBERNAME, ROLE, JOINDATE FROM CLUBMEMBERSHIP WHERE CLUBID = ?");
			state.setInt(1, Integer.parseInt(club.getId()));
			resultSet = state.executeQuery();
			state.close();
			
			while (resultSet.next()) {
				memberEmail = resultSet.getString("memberEmail");
				membershipList.add(new ClubMembership(club.getUsid(), memberEmail));
			}
			
			if(membershipList.isEmpty()) {
				for(ClubMembership membership : club.getMembershipList()) {
					state = MariaDB.runQuery("INSERT INTO CLUBMEMBERSHIP(CLUBID, MEMBERMAIL, MEMBERNAME, ROLE, JOINDATE) VALUES(?,?,?,?,?)");
					state.setInt(1, Integer.parseInt(membership.getClubId()));
					state.setString(2, membership.getMemberEmail());
					state.setString(3, membership.getMemberName());
					state.setString(4, membership.getRole().toString());
					state.setString(5, membership.getJoinDate());
					state.executeUpdate();
					state.close();
				}
			} else {
				return;
			}

			for (ClubMembership membership : club.getMembershipList()) {
				for (ClubMembership membershipInDb : membershipList) {
					if(!club.getMembershipList().contains(membershipInDb) && membership.getRole().toString().equals("President")) {
						state = MariaDB.runQuery("DELETE FROM CLUBMEMBERSHIP WHERE CLUBID = ? AND MEMBEREMAIL=?");
						state.setInt(1, Integer.parseInt(membershipInDb.getClubId()));
						state.setString(2, membershipInDb.getMemberEmail());
						state.executeUpdate();
						state.close();
						break;
					}
					
					if (!(membershipInDb.getMemberEmail().equals(membership.getMemberEmail()))) {
						state = MariaDB.runQuery("INSERT INTO CLUBMEMBERSHIP(CLUBID, MEMBERMAIL, MEMBERNAME, ROLE, JOINDATE) VALUES(?,?,?,?,?)");
						state.setInt(1, Integer.parseInt(membership.getClubId()));
						state.setString(2, membership.getMemberEmail());
						state.setString(3, membership.getMemberName());
						state.setString(4, membership.getRole().toString());
						state.setString(5, membership.getJoinDate());
						state.executeUpdate();
						state.close();
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

	public void delete(String clubId) {
		//
		try {
			state = MariaDB.runQuery("DELETE FROM TRAVELCLUB WHERE USID = ?");
			state.setInt(1, Integer.parseInt(clubId));
			state.executeUpdate();
			state.close();
		} catch (SQLException e) {
			//
			System.out.println("TravelClub DELETE Exception -->" + e.getMessage());
		}
		MariaDB.closeQuery();
	}

	public List<TravelClub> readAll() {
		//
		List<TravelClub> travelClubs = new ArrayList<>();
		List<TravelClub> resultClubs = new ArrayList<>();
		
		try {
			state = MariaDB.runQuery("SELECT NAME, INTRO, FOUNDATIONDAY, BOARDID, USID FROM TRAVELCLUB ");
			resultSet = state.executeQuery();
			state.close();
			
			while (resultSet.next()) {
				TravelClub travelClub =  new TravelClub(resultSet.getString("NAME"), resultSet.getString("INTRO"));
				travelClub.setFoundationDay(resultSet.getString("FOUNDATIONDAY"));
				travelClub.setBoardId(resultSet.getString("BOARDID"));
				travelClub.setUsid(Integer.toString(resultSet.getInt("USID")));
				travelClubs.add(travelClub);
			}

			for (TravelClub travelClub : travelClubs) {
				state = MariaDB.runQuery("SELECT CLUBID, MEMBEREMAIL, MEMBERNAME, ROLE, JOINDATE FROM CLUBMEMBERSHIP WHERE CLUBID = ?");
				state.setInt(1, Integer.parseInt(travelClub.getUsid()));
				resultSet = state.executeQuery();
				state.close();
				
				while (resultSet.next()) {
					ClubMembership membership = new ClubMembership(Integer.toString(resultSet.getInt("CLUBID")),resultSet.getString("MEMBEREMAIL"));
					membership.setMemberName(resultSet.getString("MEMBERNAME"));
					membership.setRole(valueOfRole(resultSet.getString("ROLE")));
					membership.setJoinDate(resultSet.getString("JOINDATE"));
					travelClub.getMembershipList().add(membership);
				}
				resultSet.close();
				resultClubs.add(travelClub);
			}

		} catch (SQLException e) {
			//
			System.out.println("TravelClub READALL Exception -->" + e.getMessage());
		}
		MariaDB.closeQuery();
		return resultClubs;
	}
	
	private RoleInClub valueOfRole(String role) {
		//
		RoleInClub roleInClub = null;
		switch(role) {
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
