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
	private ResultSet resultSet;

	public boolean exists(String id) {
		//
		boolean isExist = false;
		try {
			state = MariaDB.runQuery("SELECT CLUBID FROM SOCIALBOARD WHERE CLUBID = ?");
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

	public void write(SocialBoard board) {
		//
		try {
			state = MariaDB.runQuery("INSERT INTO SOCIALBOARD(NAME, ADMINEMAIL, CREATEDATE, SEQUENCE, CLUBID) VALUES(?,?,?,?,?)");
			state.setString(1, board.getName());//안되면 임의지정.
			state.setString(2, board.getAdminEmail());
			state.setString(3, board.getCreateDate());
			state.setInt(4, 0);
			state.setString(5, board.getId());
			state.executeUpdate();
		} catch (SQLException e) {
			//
			System.out.println("BoardQuery Exception --> " + e.getMessage());
		}
		MariaDB.closeQuery();
	}

	public SocialBoard read(String boardId) {
		//
		SocialBoard board = null;
		
		try {
			state = MariaDB.runQuery("SELECT NAME, ADMINEMAIL, CREATEDATE, SEQUENCE, CLUBID FROM SOCIALBOARD WHERE CLUBID = ?");
			state.setInt(1, Integer.parseInt(boardId));
			resultSet = state.executeQuery();
			state.close();
			if (resultSet.next()) {
				board = new SocialBoard(Integer.toString(resultSet.getInt("CLUBID")), resultSet.getString("NAME"), resultSet.getString("ADMINEMAIL"));
				board.setCreateDate(resultSet.getString("CREATEDATE"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			//
			System.out.println(e.getMessage());
		}
		MariaDB.closeQuery();
		return board;
	}

	public List<SocialBoard> readByName(String name) {
		//
		SocialBoard board = null;
		List<SocialBoard> boards = new ArrayList<>();

		try {
			state = MariaDB.runQuery("SELECT NAME, ADMINEMAIL, CREATEDATE, SEQUENCE, CLUBID FROM SOCIALBOARD WHERE NAME = ?");
			state.setString(1, name);
			
			resultSet = state.executeQuery();
			while (resultSet.next()) {
				board = new SocialBoard(Integer.toString(resultSet.getInt("CLUBID")), resultSet.getString("NAME"), resultSet.getString("ADMINEMAIL"));
				board.setCreateDate(resultSet.getString("CREATEDATE"));
				boards.add(board);
			}
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
		try {
			state = MariaDB.runQuery("DELETE FROM SOCIALBOARD WHERE = ?");
			state.setInt(1, Integer.parseInt(boardId));
			state.executeUpdate();
			state.close();
		} catch (SQLException e) {
			//
			System.out.println("BOARD QUERY EXCEPTION -->" + e.getMessage());
		}
		MariaDB.closeQuery();
	}
}
