package step4_1.dao;

import step1.share.domain.entity.board.Posting;
import step1.share.domain.entity.board.SocialBoard;

public class PostingDao {
	//
	private String usid;
	private String title;
	private String writerEmail;
	private String contents;
	private String writtenDate;
	private int readCount;
	private String boardId;
	
	private String[] fields;
	
	public PostingDao() {
		//
		String[] fields = {usid, title, writerEmail, contents, writtenDate, boardId};
		this.fields = fields;
	}
	
	public PostingDao(String...strings) {
		//
		this();
		this.usid = strings[0];
		this.title = strings[1];
		this.writerEmail = strings[2];
		this.contents = strings[3];
		this.writtenDate = strings[4];
		this.boardId = strings[5];
	}
	
	public PostingDao(Posting posting) {
		//
		this();
		this.usid = posting.getUsid();
		this.title = posting.getTitle();
		this.writerEmail = posting.getWriterEmail();
		this.contents = posting.getContents();
		this.writtenDate = posting.getWrittenDate();
		this.readCount = posting.getReadCount();
		this.boardId = posting.getBoardId();
	}
	
	public Posting toPosting() {
		//
		Posting posting = new Posting(new SocialBoard(boardId, "board", writerEmail), title, writerEmail, contents);
		posting.setUsid(usid);
		posting.setWrittenDate(writtenDate);
		posting.setBoardId(boardId);
		posting.setReadCount(readCount);
		return posting;
	}
	
	public String getUsid() {
		return usid;
	}

	public void setUsid(String usid) {
		this.usid = usid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriterEmail() {
		return writerEmail;
	}

	public void setWriterEmail(String writerEmail) {
		this.writerEmail = writerEmail;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getWrittenDate() {
		return writtenDate;
	}

	public void setWrittenDate(String writtenDate) {
		this.writtenDate = writtenDate;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}
}
