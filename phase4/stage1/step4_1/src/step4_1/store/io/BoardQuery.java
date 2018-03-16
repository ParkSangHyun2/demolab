package step4_1.store.io;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import step1.share.domain.entity.board.SocialBoard;
import step4_1.dao.BoardDao;

public class BoardQuery {
	//
	private PreparedStatement state;

	public boolean exists(String id) {
		//
		boolean isExist = false;
		state = MariaDB.runQuery("SELECT * FROM SOCIALBOARD WHERE CLUBID = ?", id);
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

	public void write(SocialBoard board) {
		//
		try {
			state = MariaDB.getConnection().prepareStatement(
					"INSERT INTO SOCIALBOARD(Name, AdminEmail, CreateDate, Sequence, clubId) VALUES(?,?,?,?,?)"
					);
			state.setString(1, "board");
			state.setString(2, board.getAdminEmail());
			state.setString(3, board.getCreateDate());
			state.setInt(4, 0);
			state.setString(5, board.getId());

			state.executeUpdate();
		} catch (SQLException e) {
			//
			System.out.println(e.getMessage());
		}
		MariaDB.closeQuery();
	}

	public SocialBoard read(String boardId) {
		//
		String[] values = new String[5];
		int clubId;
		SocialBoard board = null;

		state = MariaDB.runQuery("SELECT * FROM SOCIALBOARD WHERE CLUBID = ?", boardId);
		try {
			ResultSet result = state.executeQuery();
			if (result.next()) {
				for (int i = 0; i < 3; i++) {
					values[i] = result.getString(i + 1);
				}
				clubId = result.getInt("ClubId");
			} else {
				return board;
			}
			board = new BoardDao(values).toSocialBoard();
			board.setClubId(Integer.toString(clubId));

		} catch (SQLException e) {
			//
			System.out.println(e.getMessage());
		}
		MariaDB.closeQuery();
		return board;
	}

	public List<SocialBoard> readByName(String name) {
		//
		String[] values = new String[5];
		SocialBoard board = null;
		List<SocialBoard> boards = new ArrayList<>();

		state = MariaDB.runQuery("SELECT * FROM SOCIALBOARD WHERE NAME = ?", name);
		try {
			ResultSet result = state.executeQuery();
			if (result.next()) {
				for (int i = 0; i < 4; i++) {
					values[i] = result.getString(i + 1);
				}
			}

			board = new BoardDao(values).toSocialBoard();
			boards.add(board);

		} catch (SQLException e) {
			//
			System.out.println("readByName Exception :" + e.getMessage());
		}
		MariaDB.closeQuery();
		if (boards.isEmpty()) {
			return null;
		}
		return boards;
	}

	public void delete(String boardId) {
		//
		state = MariaDB.runQuery("DELETE FROM SOCIALBOARD WHERE = ?", boardId);
		try {
			state.executeUpdate();
		} catch (SQLException e) {
			//
			System.out.println("BOARD QUERY EXCEPTION -->" + e.getMessage());
		}
		MariaDB.closeQuery();
	}

}
