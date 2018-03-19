package step4_1.store.io;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import step1.share.domain.entity.board.Posting;

public class PostingQuery {
	//
	private PreparedStatement state;
	private ResultSet resultSet;
	
	public boolean exists(String id) {
		//
		boolean isExist = false;
		try {
			state = MariaDB.runQuery("SELECT USID FROM POSTING WHERE USID = ?");
			state.setInt(1, Integer.parseInt(id));
			resultSet = state.executeQuery();
			state.close();
			
			if(resultSet.next()) {
				isExist = true;
			}
		} catch (SQLException e) {
			//
			System.out.println("Posting Query(EXIST) Exception --> " + e.getMessage());
		}
		
		MariaDB.closeQuery();
		return isExist;
	}

	public void write(Posting posting) {
		//
		try {
			state = MariaDB.getConnection().prepareStatement("INSERT INTO POSTING(TITLE, WRITEREMAIL, CONTENTS, WRITTENDATE, READCOUNT, BOARDID) VALUES(?,?,?,?,?,?)");
			state.setString(1, posting.getTitle());
			state.setString(2, posting.getWriterEmail());
			state.setString(3, posting.getContents());
			state.setString(4, posting.getWrittenDate());
			state.setInt(5, posting.getReadCount());
			state.setInt(6, Integer.parseInt(posting.getBoardId()));
			state.executeUpdate();
			state.close();
		} catch (SQLException e) {
			//
			System.out.println("POSTING QUERY EXCEPTION --> " + e.getErrorCode() + e.getMessage());
		}
		MariaDB.closeQuery();
	}

	public Posting read(String postingId) {
		//
		Posting posting = null;
		try {
			state = MariaDB.runQuery("SELECT TITLE, WRITEREMAIL, CONTENTS, WRITTENDATE, READCOUNT, BOARDID, USID FROM POSTING WHERE USID = ?");
			state.setInt(1, Integer.parseInt(postingId));
			resultSet = state.executeQuery();
			state.close();
			
			if (resultSet.next()) {
				posting = new Posting();
				posting.setUsid(resultSet.getString("USID"));
				posting.setTitle(resultSet.getString("TITLE"));
				posting.setWriterEmail(resultSet.getString("WRITEREMAIL"));
				posting.setContents(resultSet.getString("CONTENTS"));
				posting.setWrittenDate(resultSet.getString("WRITTENDATE"));
				posting.setReadCount(resultSet.getInt("READCOUNT"));
				posting.setBoardId(resultSet.getString("BOARDID"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			//
			System.out.println("Posting Query(READ) Exception --> " + e.getMessage());
		}
		MariaDB.closeQuery();
		return posting;
	}

	public List<Posting> readByBoardId(String boardId) {
		//
		List<Posting> postings = new ArrayList<>();
		Posting posting = null;

		try {
			state = MariaDB.runQuery("SELECT TITLE, WRITEREMAIL, CONTENTS, WRITTENDATE, READCOUNT, BOARDID, USID FROM POSTING WHERE BOARDID = ?");
			state.setInt(1, Integer.parseInt(boardId));
			resultSet = state.executeQuery();
			state.close();
			
			while (resultSet.next()) {
				posting = new Posting();
				posting.setUsid(Integer.toString(resultSet.getInt("usid")));
				posting.setTitle(resultSet.getString("title"));
				posting.setWriterEmail(resultSet.getString("writerEmail"));
				posting.setContents(resultSet.getString("contents"));
				posting.setWrittenDate(resultSet.getString("writtenDate"));
				posting.setReadCount(resultSet.getInt("readCount"));
				posting.setBoardId(resultSet.getString("boardId"));
				postings.add(posting);
			}
		} catch (SQLException e) {
			//
			System.out.println("Posting Query(readByBoardId) Exception :" + e.getMessage());
		}
		MariaDB.closeQuery();
		return postings;
	}

	public List<Posting> readByTitle(String title) {
		//
		List<Posting> postings = new ArrayList<>();
		Posting posting = null;
		
		try {
			state = MariaDB.runQuery("SELECT TITLE, WRITEREMAIL, CONTENTS, WRITTENDATE, READCOUNT, BOARDID FROM POSTING WHERE TITLE = ?");
			state.setString(1, title);
			resultSet = state.executeQuery();
			state.close();
			
			while (resultSet.next()) {
				posting = new Posting();
				posting.setUsid(resultSet.getString("usid"));
				posting.setTitle(resultSet.getString("title"));
				posting.setWriterEmail(resultSet.getString("writerEmail"));
				posting.setContents(resultSet.getString("contents"));
				posting.setWrittenDate(resultSet.getString("writtenDate"));
				posting.setReadCount(resultSet.getInt("readCount"));
				posting.setBoardId(resultSet.getString("boardId"));
				postings.add(posting);
			}
		} catch (SQLException e) {
			//
			System.out.println("Posting Query(readByName) Exception :"+e.getMessage());
		}
		MariaDB.closeQuery();
		return postings;
	}

	public void update(Posting posting) {
		//
		try {
			state = MariaDB.getConnection().prepareStatement(
					"UPDATE POSTING SET TITLE = ?, CONTENTS = ?, READCOUNT = ? WHERE USID = ?");
			state.setString(1, posting.getTitle());
			state.setString(2, posting.getContents());
			state.setInt(3, posting.getReadCount());
			state.setInt(4, Integer.parseInt(posting.getUsid()));
			
			state.executeUpdate();
		} catch (SQLException e) {
			//
			System.out.println("POSTING UPDATE QUERY EXCEPTION -->" + e.getErrorCode()+e.getMessage());
		}
	}

	public void delete(String postingId) {
		//
		try {
			state = MariaDB.runQuery("DELETE FROM POSTING WHERE USID = ?");
			state.setString(1, postingId);
			state.executeUpdate();
			state.close();
		} catch (SQLException e) {
			System.out.println("POSTING DELETE QUERY EXCEPTION -->" + e.getErrorCode()+e.getMessage());
		}
	}

}
