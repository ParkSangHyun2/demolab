package step4_1.dao;

import step1.share.domain.entity.club.TravelClub;

public class TravelClubDao {
	//
	private String usid;
	private String name;
	private String intro;
	private String foundationDay;
	private String boardId;
	
	private String[] fields;
	
	public TravelClubDao() {
		//
		String[] fields = {usid, name, intro, foundationDay, boardId};
		this.fields = fields;
	}
	
	public TravelClubDao(String...strings) {
		//
		this();
		this.usid = strings[0];
		this.name = strings[1];
		this.intro = strings[2];
		this.foundationDay = strings[3];
		this.boardId = strings[4];
	}
	
	public TravelClubDao(TravelClub travelClub) {
		//
		this();
		this.usid = travelClub.getUsid();
		this.name = travelClub.getName();
		this.intro = travelClub.getIntro();
		this.foundationDay = travelClub.getFoundationDay();
		this.boardId = travelClub.getBoardId();
	}
	
	public TravelClub toTravelClub() {
		//
		TravelClub travelClub = new TravelClub(name,intro);
		travelClub.setUsid(usid);
		travelClub.setFoundationDay(foundationDay);
		travelClub.setBoardId(boardId);
		return travelClub;
	}

	public String getUsid() {
		return usid;
	}

	public void setUsid(String usid) {
		this.usid = usid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getFoundationDay() {
		return foundationDay;
	}

	public void setFoundationDay(String foundationDay) {
		this.foundationDay = foundationDay;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	
	
}
