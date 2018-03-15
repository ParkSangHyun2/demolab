package step4_1.store.io;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import step1.share.domain.entity.board.Posting;
import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.CommunityMember;
import step1.share.domain.entity.club.RoleInClub;
import step4_1.dao.MemberDao;
import step4_1.dao.PostingDao;

public class PostingQuery {
	//
	private PreparedStatement state;
	
	public boolean exists(String id) {
		//
		boolean isExist = false;
		state = MariaDB.runQuery("SELECT * FROM POSTING WHERE USID = ?", id);
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

	public void write(Posting posting) {
		//
		try {
			state = MariaDB.getConnection().prepareStatement("INSERT INTO POSTING VALUES(?,?,?,?,?,?,?)");
			state.setString(1, posting.getUsid());
			System.out.println(posting.getUsid());
			state.setString(2, posting.getTitle());
			state.setString(3, posting.getWriterEmail());
			state.setString(4, posting.getContents());
			state.setString(5, posting.getWrittenDate());
			state.setInt(6, posting.getReadCount());
			state.setString(7, posting.getBoardId());
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

		state = MariaDB.runQuery("SELECT * FROM POSTING WHERE USID = ?", postingId);
		try {
			ResultSet result = state.executeQuery();
			if (result.next()) {
				posting = new Posting();
				posting.setUsid(result.getString("usid"));
				posting.setTitle(result.getString("title"));
				posting.setWriterEmail(result.getString("writerEmail"));
				posting.setContents(result.getString("contents"));
				posting.setWrittenDate(result.getString("writtenDate"));
				posting.setReadCount(result.getInt("readCount"));
				posting.setBoardId(result.getString("boardId"));
			} else {
				return posting;
			}

		} catch (SQLException e) {
			//
			System.out.println(e.getMessage());
		}
		MariaDB.closeQuery();
		return posting;
	}

	public List<Posting> readByBoardId(String boardId) {
		//
		List<Posting> postings = new ArrayList<>();
		Posting posting = null;

		state = MariaDB.runQuery("SELECT * FROM POSTING WHERE BOARDID = ?", boardId);
		try {
			ResultSet result = state.executeQuery();
			state.close();
			while (result.next()) {
				posting = new Posting();
				posting.setUsid(result.getString("usid"));
				posting.setTitle(result.getString("title"));
				posting.setWriterEmail(result.getString("writerEmail"));
				posting.setContents(result.getString("contents"));
				posting.setWrittenDate(result.getString("writtenDate"));
				posting.setReadCount(result.getInt("readCount"));
				posting.setBoardId(result.getString("boardId"));
				postings.add(posting);
			}

		} catch (SQLException e) {
			//
			System.out.println("readByName Exception :"+e.getMessage());
		}
		MariaDB.closeQuery();
		return postings;
	}

	public List<Posting> readByTitle(String title) {
		//
		List<Posting> postings = new ArrayList<>();
		Posting posting = null;

		state = MariaDB.runQuery("SELECT * FROM POSTING WHERE TITLE = ?", title);
		try {
			ResultSet result = state.executeQuery();
			state.close();
			while (result.next()) {
				posting = new Posting();
				posting.setUsid(result.getString("usid"));
				posting.setTitle(result.getString("title"));
				posting.setWriterEmail(result.getString("writerEmail"));
				posting.setContents(result.getString("contents"));
				posting.setWrittenDate(result.getString("writtenDate"));
				posting.setReadCount(result.getInt("readCount"));
				posting.setBoardId(result.getString("boardId"));
				postings.add(posting);
			}

		} catch (SQLException e) {
			//
			System.out.println("readByName Exception :"+e.getMessage());
		}
		MariaDB.closeQuery();
		return postings;
	}

	public void update(Posting posting) {
		//
		try {
			state = MariaDB.getConnection().prepareStatement(
					"UPDATE POSTING SET TITLE = ? AND CONTENTS = ? AND READCOUNT = ? WHERE USID = ?");
			state.setString(1, posting.getTitle());
			state.setString(2, posting.getContents());
			state.setInt(3, posting.getReadCount());
			state.setString(4, posting.getUsid());
			
			state.executeUpdate();
		} catch (SQLException e) {
			//
			System.out.println("POSTING UPDATE QUERY EXCEPTION -->" + e.getErrorCode()+e.getMessage());
		}

	}

	public void delete(String postingId) {
		//
		try {
			state = MariaDB.runQuery(
					"DELETE FROM POSTING WHERE USID = ?",
					postingId
					);
			state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
