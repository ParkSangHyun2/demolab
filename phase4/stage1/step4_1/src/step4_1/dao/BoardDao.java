package step4_1.dao;

import step1.share.domain.entity.board.SocialBoard;

public class BoardDao {
	//
	private String clubId;
	private String name;
	private String adminEmail;
	private String createDate;
	private int sequence;
	
	private String[] fields;
	
	public BoardDao() {
		//
		String[] fields = {clubId, name, adminEmail, createDate};
		this.fields = fields;
	}
	
	public BoardDao(String...strings) {
		//
		this();
		this.name = strings[0];
		this.adminEmail = strings[1];
		this.createDate = strings[2];
	}
	
	public BoardDao(SocialBoard board) {
		this();
		this.clubId = board.getClubId();
		this.name = board.getName();
		this.adminEmail = board.getAdminEmail();
		this.createDate = board.getCreateDate();
	}
	
	public SocialBoard toSocialBoard() {
		//
		SocialBoard board = new SocialBoard(clubId, name, adminEmail);
		board.setCreateDate(createDate);
		return board;
	}
	
	public String getClubId() {
		return clubId;
	}
	public void setClubId(String clubId) {
		this.clubId = clubId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
}
