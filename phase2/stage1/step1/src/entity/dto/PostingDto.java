/*
 * COPYRIGHT (c) NEXTREE Consulting 2016
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:eykim@nextree.co.kr">Kim, Eunyoung</a>
 * @since 2016. 7. 12.
 */
package entity.dto;

import java.util.UUID;

import entity.board.Posting;
import entity.board.SocialBoard;
import util.DateUtil;

public class PostingDto {
	//
	private String usid;
	private String title;
	private String writerEmail;
	private String contents;
	private String writtenDate;
	private int readCount;
	
	private String boardId; 
	
	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public PostingDto() {
		//
		this.readCount = 0; 
	}

	public PostingDto(String title, String writerEmail, String contents) {
		//
		this();
		this.title = title;
		this.writerEmail = writerEmail;
		this.contents = contents;
		this.writtenDate = DateUtil.today();
	}

	public PostingDto(Posting posting) {
		//
		this.usid = posting.getUsid();
		this.title = posting.getTitle();
		this.writerEmail = posting.getWriterEmail();
		this.contents = posting.getContents();
		this.writtenDate = posting.getWrittenDate();
		this.readCount = posting.getReadCount();
		this.boardId = posting.getBoardId();
	}

	@Override
	public String toString() {
		//
		StringBuilder builder = new StringBuilder();
		builder.append("Posting id: " + usid);
		builder.append(", title: " + title);
		builder.append(", writer email: " + writerEmail);
		builder.append(", read count: " + readCount);
		builder.append(", written date: " + writtenDate);
		builder.append(", contents: " + contents);

		return builder.toString();
	}

	public Posting toPostingIn(SocialBoard board) {
		//
		//자동으로 usid만들어주는 기능 없음 -> 파일로가야 있음. 
		//
		Posting posting = new Posting(board, title, writerEmail, contents);
		posting.setUsid(UUID.randomUUID().toString());
		posting.setWrittenDate(writtenDate);
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
		 // TODO check length
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
}
