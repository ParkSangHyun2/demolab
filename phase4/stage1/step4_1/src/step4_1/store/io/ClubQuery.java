package step4_1.store.io;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.RoleInClub;
import step1.share.domain.entity.club.TravelClub;
import step4_1.dao.TravelClubDao;

public class ClubQuery {
	//
	private PreparedStatement state;

	public boolean exists(String id) {
		//
		boolean isExist = false;
		state = MariaDB.runQuery("SELECT * FROM TRAVELCLUB WHERE USID = ?", id);
		try {
			ResultSet result = state.executeQuery();
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

	public void write(TravelClub club) {
		//
		state = MariaDB.runQuery("INSERT INTO TRAVELCLUB VALUES(?,?,?,?,?)", UUID.randomUUID().toString(),
				club.getName(), club.getIntro(), club.getFoundationDay(), club.getBoardId());
		try {
			state.executeUpdate();
		} catch (SQLException e) {
			//
			System.out.println(e.getMessage());
		}
		MariaDB.closeQuery();

	}

	public TravelClub read(String clubId) {
		//
		String[] values = new String[5];
		TravelClub club = null;

		state = MariaDB.runQuery("SELECT * FROM TRAVELCLUB WHERE USID = ?", clubId);
		try {
			ResultSet result = state.executeQuery();
			if (result.next()) {
				for (int i = 0; i < 5; i++) {
					values[i] = result.getString(i + 1);
				}
			}
			if(!result.first()) {
				return null;
			}
			club = new TravelClubDao(values).toTravelClub();

			state = MariaDB.runQuery("SELECT * FROM CLUBMEMBERSHIP WHERE CLUBID = ?", clubId);
			result = state.executeQuery();

			while (result.next()) {
				ClubMembership membership = new ClubMembership(result.getString("clubId"),
						result.getString("memberEmail"));
				membership.setMemberName(result.getString("memberName"));
				switch(result.getObject("role").toString()) {
				case "President":
					membership.setRole(RoleInClub.President);
					break;
				case "Member":
					membership.setRole(RoleInClub.Member);
					break;
				}
				membership.setJoinDate(result.getString("joinDate"));

				club.getMembershipList().add(membership);
			}

		} catch (SQLException e) {
			//
			System.out.println(e.getMessage());
		}
		MariaDB.closeQuery();
		return club;
	}

	public TravelClub readByName(String name) {
		//
		String[] values = new String[5];
		TravelClub club = null;
		List<TravelClub> clubs = new ArrayList<>();

		state = MariaDB.runQuery("SELECT * FROM TRAVELCLUB WHERE NAME = ?", name);
		try {
			ResultSet result = state.executeQuery();
			if (result.next()) {
				for (int i = 0; i < 5; i++) {
					values[i] = result.getString(i + 1);
				}
			}else {
				return null;
			}
			

			TravelClubDao travelClubDao = new TravelClubDao(values);
			club = travelClubDao.toTravelClub();

			PreparedStatement travelClubState = MariaDB.runQuery("SELECT * FROM CLUBMEMBERSHIP WHERE MEMBERNAME = ?",
					name);
			ResultSet clubResult = travelClubState.executeQuery();

			while (clubResult.next()) {
				ClubMembership membership = new ClubMembership(clubResult.getString("clubId"),
						clubResult.getString("memberEmail"));
				membership.setMemberName(clubResult.getString("memberName"));
				switch(clubResult.getObject("role").toString()) {
				case "President":
					membership.setRole(RoleInClub.President);
					break;
				case "Member":
					membership.setRole(RoleInClub.Member);
					break;
				}
				membership.setJoinDate(clubResult.getString("joinDate"));

				club.getMembershipList().add(membership);
			}
			clubs.add(club);

		} catch (SQLException e) {
			//
			System.out.println("readByName Exception :" + e.getMessage());
		}
		MariaDB.closeQuery();
		if (clubs.isEmpty()) {
			return null;
		}

		return clubs.get(0);
	}

	public void update(TravelClub club) {
		//
		ArrayList<ClubMembership> membershipList = new ArrayList<>();

		String memberEmail;

		try {
			state = MariaDB.runQuery("SELECT * FROM CLUBMEMBERSHIP WHERE CLUBID= ?", club.getId());
			ResultSet membershipResult = state.executeQuery();
			while (membershipResult.next()) {
				memberEmail = membershipResult.getString("memberEmail");
				membershipList.add(new ClubMembership(club.getUsid(), memberEmail));
			}
			if(membershipList.isEmpty()) {
				for(ClubMembership membership : club.getMembershipList()) {
					state = MariaDB.runQuery("INSERT INTO CLUBMEMBERSHIP VALUES(?,?,?,?,?)", membership.getClubId(),
							membership.getMemberEmail(), membership.getMemberName(),
							membership.getRole().toString(), membership.getJoinDate());
					state.executeUpdate();
				}
			}

			for (ClubMembership membership : club.getMembershipList()) {
				for (ClubMembership dbMembership : membershipList) {
					if(!club.getMembershipList().contains(dbMembership) && membership.getRole().toString().equals("President")) {
						state = MariaDB.runQuery(
								"DELETE FROM CLUBMEMBERSHIP WHERE CLUBID = ? AND MEMBEREMAIL=?",
								dbMembership.getClubId(), dbMembership.getMemberEmail());
						state.executeUpdate();
						System.out.println("INFO : " + dbMembership.getClubId() + ", " + dbMembership.getMemberEmail());
						state.close();
						break;
					}
					
					if (!(dbMembership.getMemberEmail().equals(membership.getMemberEmail()))) {
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

	public void delete(String clubId) {
		//
		state = MariaDB.runQuery("DELETE FROM TRAVELCLUB WHERE USID = ?", clubId);
		try {
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
		List<TravelClub> clubs = new ArrayList<>();
		List<TravelClub> resultClubs = new ArrayList<>();

		String[] values = new String[5];

		state = MariaDB.runQuery("SELECT * FROM TRAVELCLUB ");
		try {
			ResultSet result = state.executeQuery();
			while (result.next()) {
				for (int i = 0; i < 5; i++) {
					values[i] = result.getString(i + 1);
				}
				clubs.add(new TravelClubDao(values).toTravelClub());
			}
			result.close();

			for (TravelClub club : clubs) {
				state = MariaDB.runQuery("SELECT * FROM CLUBMEMBERSHIP WHERE clubId = ?", club.getUsid());
				result = state.executeQuery();
				while (result.next()) {
					ClubMembership membership = new ClubMembership(result.getString("clubId"),
							result.getString("memberEmail"));
					membership.setMemberName(result.getString("memberName"));
					switch(result.getObject("role").toString()) {
					case "President":
						membership.setRole(RoleInClub.President);
						break;
					case "Member":
						membership.setRole(RoleInClub.Member);
						break;
					}
					membership.setJoinDate(result.getString("joinDate"));

					club.getMembershipList().add(membership);

				}
				result.close();
				resultClubs.add(club);
			}

		} catch (SQLException e) {
			//
			System.out.println(e.getMessage());
		}
		MariaDB.closeQuery();
		return clubs;
	}

}
