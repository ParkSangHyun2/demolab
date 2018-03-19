package step4_1.store.io;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

			if (resultSet.next()) {
				club = new TravelClub(resultSet.getString("NAME"), resultSet.getString("INTRO"));
				club.setFoundationDay(resultSet.getString("FOUNDATIONDAY"));
				club.setBoardId(resultSet.getString("BOARDID"));
				club.setUsid(Integer.toString(resultSet.getInt("USID")));
			} else {
				return null;
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

			if (resultSet.next()) {
				club = new TravelClub(resultSet.getString("NAME"), resultSet.getString("INTRO"));
				club.setFoundationDay(resultSet.getString("FOUNDATIONDAY"));
				club.setBoardId(resultSet.getString("BOARDID"));
				club.setUsid(Integer.toString(resultSet.getInt("USID")));
			} else {
				return null;
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
		try {
			state = MariaDB.runQuery("UPDATE TRAVELCLUB SET NAME = ?, INTRO = ?, BOARDID = ?");
			state.setString(1, club.getName());
			state.setString(2, club.getIntro());
			state.setString(3, club.getBoardId());
			state.executeUpdate();
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

		try {
			state = MariaDB.runQuery("SELECT NAME, INTRO, FOUNDATIONDAY, BOARDID, USID FROM TRAVELCLUB ");
			resultSet = state.executeQuery();
			state.close();

			while (resultSet.next()) {
				TravelClub travelClub = new TravelClub(resultSet.getString("NAME"), resultSet.getString("INTRO"));
				travelClub.setFoundationDay(resultSet.getString("FOUNDATIONDAY"));
				travelClub.setBoardId(resultSet.getString("BOARDID"));
				travelClub.setUsid(Integer.toString(resultSet.getInt("USID")));
				travelClubs.add(travelClub);
			}
		} catch (SQLException e) {
			//
			System.out.println("TravelClub READALL Exception -->" + e.getMessage());
		}
		MariaDB.closeQuery();
		return travelClubs;
	}
}
