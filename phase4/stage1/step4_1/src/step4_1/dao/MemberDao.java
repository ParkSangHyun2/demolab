package step4_1.dao;

import step1.share.domain.entity.club.CommunityMember;

public class MemberDao {
	//
	private String email;
	private String name;
	private String nickName;
	private String phoneNumber;
	private String birthDay;
	
	private String[] fields;

	public MemberDao() {
		//
		String[] fields = { email, name, nickName, phoneNumber, birthDay };
		this.fields = fields;
	}

	public MemberDao(String email, String name) {
		//
		this();
		this.email = email;
		this.name = name;
	}
	
	public MemberDao(String...strings) {
		this();
		this.email = strings[0];
		this.name = strings[1];
		this.nickName = strings[2];
		this.phoneNumber = strings[3];
		this.birthDay = strings[4];
	}

	public MemberDao(CommunityMember member) {
		//
		this();
		this.email = member.getEmail();
		this.name = member.getName();
		this.nickName = member.getNickName();
		this.phoneNumber = member.getPhoneNumber();
		this.birthDay = member.getBirthDay();
	}
	
	public CommunityMember toCommunityMember() {
		//
		CommunityMember member = new CommunityMember(email, name,phoneNumber);
		member.setNickName(nickName);
		member.setBirthDay(birthDay);
		return member;
	}

	public String[] getValueList() {

		return fields;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

}
