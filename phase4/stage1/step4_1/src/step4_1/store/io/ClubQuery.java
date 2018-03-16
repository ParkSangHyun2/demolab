package step4_1.store.io;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
		Connection connection = MariaDB.getConnection();

		try {
			state = connection.prepareStatement("SELECT * FROM TRAVELCLUB WHERE USID = ?");
			System.out.println("ID .." + id);
			state.setInt(1, Integer.parseInt(id));
			
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

	public void write(TravelClub club) {
		//
		state = MariaDB.runQuery("INSERT INTO TRAVELCLUB(Name,Intro,FoundationDay,BoardId) VALUES(?,?,?,?)",
				club.getName(), club.getIntro(), club.getFoundationDay(), club.getBoardId());
		try {
			state.executeUpdate();
			state.close();
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

		Connection connection = MariaDB.getConnection();	
		String usid = null;
		try {
			state = connection.prepareStatement("SELECT * FROM TRAVELCLUB WHERE USID = ?");
			state.setInt(1, Integer.parseInt(clubId));
			ResultSet result = state.executeQuery();
			state.close();
			if (result.next()) {
				for (int i = 0; i < 4; i++) {
					values[i] = result.getString(i + 1);
				}
				usid = Integer.toString(result.getInt("USID"));
			}
			if(!result.first()) {
				return null;
			}
			club = new TravelClubDao(values).toTravelClub();
			club.setUsid(usid);

			state = MariaDB.runQuery("SELECT * FROM CLUBMEMBERSHIP WHERE CLUBID = ?", clubId);
			result = state.executeQuery();
			state.close();

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
		String usid;
		TravelClub club = null;
		List<TravelClub> clubs = new ArrayList<>();

		state = MariaDB.runQuery("SELECT * FROM TRAVELCLUB WHERE NAME = ?", name);
		try {
			ResultSet result = state.executeQuery();
			state.close();
			if (result.next()) {
				for (int i = 0; i < 4; i++) {
					values[i] = result.getString(i + 1);
				}
				usid = Integer.toString(result.getInt("USID"));
			}else {
				return null;
			}
			

			TravelClubDao travelClubDao = new TravelClubDao(values);
			club = travelClubDao.toTravelClub();
			club.setUsid(usid);

			PreparedStatement travelClubState = MariaDB.runQuery("SELECT * FROM CLUBMEMBERSHIP WHERE MEMBERNAME = ?",
					name);
			ResultSet clubResult = travelClubState.executeQuery();
			travelClubState.close();

			while (clubResult.next()) {
				ClubMembership membership = new ClubMembership(Integer.toString(clubResult.getInt("clubId")),
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

		Connection connection = MariaDB.getConnection();
		try {
			state = connection.prepareStatement("SELECT * FROM CLUBMEMBERSHIP WHERE CLUBID= ?");
			state.setInt(1, Integer.parseInt(club.getId()));
			
			ResultSet membershipResult = state.executeQuery();
			state.close();
			while (membershipResult.next()) {
				memberEmail = membershipResult.getString("memberEmail");
				membershipList.add(new ClubMembership(club.getUsid(), memberEmail));
			}
			if(membershipList.isEmpty()) {
				for(ClubMembership membership : club.getMembershipList()) {
					state = connection.prepareStatement("INSERT INTO CLUBMEMBERSHIP VALUES(?,?,?,?,?)");
					state.setInt(1, Integer.parseInt(membership.getClubId()));
					state.setString(2, membership.getMemberEmail());
					state.setString(3, membership.getMemberName());
					state.setString(4, membership.getRole().toString());
					state.setString(5, membership.getJoinDate());

					state.executeUpdate();
					state.close();
				}
			}

			for (ClubMembership membership : club.getMembershipList()) {
				for (ClubMembership dbMembership : membershipList) {
					if(!club.getMembershipList().contains(dbMembership) && membership.getRole().toString().equals("President")) {
						state = connection.prepareStatement("DELETE FROM CLUBMEMBERSHIP WHERE CLUBID = ? AND MEMBEREMAIL=?");
						state.setInt(1, Integer.parseInt(dbMembership.getClubId()));
						state.setString(2, dbMembership.getMemberEmail());
						
						state.executeUpdate();
						System.out.println("INFO : " + dbMembership.getClubId() + ", " + dbMembership.getMemberEmail());
						state.close();
						break;
					}
					
					if (!(dbMembership.getMemberEmail().equals(membership.getMemberEmail()))) {
						state = connection.prepareStatement("INSERT INTO CLUBMEMBERSHIP VALUES(?,?,?,?,?)");
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
		Connection connection = MariaDB.getConnection();
		
		try {
			state = connection.prepareStatement("DELETE FROM TRAVELCLUB WHERE USID = ?");
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
		List<TravelClub> clubs = new ArrayList<>();
		List<TravelClub> resultClubs = new ArrayList<>();

		String[] values = new String[5];

		state = MariaDB.runQuery("SELECT * FROM TRAVELCLUB ");
		try {
			ResultSet result = state.executeQuery();
			state.close();
			while (result.next()) {
				for (int i = 0; i < 4; i++) {
					values[i] = result.getString(i + 1);
				}
				String usid = Integer.toString(result.getInt("Usid"));
				TravelClub travelClub = new TravelClubDao(values).toTravelClub();
				travelClub.setUsid(usid);
				clubs.add(travelClub);
			}
			result.close();

			for (TravelClub club : clubs) {
				Connection connection = MariaDB.getConnection();
				state = connection.prepareStatement("SELECT * FROM CLUBMEMBERSHIP WHERE clubId = ?");
				state.setInt(1, Integer.parseInt(club.getUsid()));
				result = state.executeQuery();
				state.close();
				while (result.next()) {
					ClubMembership membership = new ClubMembership(Integer.toString(result.getInt("clubId")),
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
