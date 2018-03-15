/*
 * COPYRIGHT (c) NEXTREE Consulting 2014
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 6. 10.
 */

package step1.share.domain.entity.club;

import java.util.ArrayList;
import java.util.List;

import step1.share.domain.entity.Entity;
import step1.share.util.InvalidEmailException;

public class CommunityMember implements Entity {
	//
	private String email;		// key 
	private String name; 
	private String nickName; 
	private String phoneNumber; 
	private String birthDay; 

	private List<Address> addresses; 
	private List<ClubMembership> membershipList; 
	
	public CommunityMember() {
		// 
		this.membershipList = new ArrayList<>(); 
		this.addresses = new ArrayList<>(); 
	}
	
	public CommunityMember(String email, String name, String phoneNumber) throws InvalidEmailException {
		// 
		this(); 
		setEmail(email);
		this.name = name; 
		this.phoneNumber = phoneNumber; 
	}

	@Override
	public String toString() {
		// 
		StringBuilder builder = new StringBuilder(); 
		
		builder.append("Name:").append(name); 
		builder.append(", email:").append(email); 
		builder.append(", nickname:").append(nickName); 
		builder.append(", phone number:").append(phoneNumber); 
		builder.append(", birthDay:").append(birthDay); 

		if (addresses != null) {
			int i = 1; 
			for(Address address : addresses) {
				builder.append(", Address[" + i + "]").append(address.toString()); 
			}
		}
		
		return builder.toString(); 
	}
	
	public static CommunityMember getSample() {
		// 
		CommunityMember member = null;
		try {
			member = new CommunityMember("mymy@nextree.co.kr", "Minsoo Lee", "010-3321-1001");
			member.setBirthDay("2001.09.23");
			member.getAddresses().add(Address.getHomeAddressSample());
		} catch (InvalidEmailException e) {
			System.out.println(e.getMessage());
		}
		return member; 
	}

	@Override
	public String getId() {
		return email; 
	}

	public List<ClubMembership> getMembershipList() {
		return this.membershipList; 
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws InvalidEmailException {
		//
		if (!this.isValidEmailAddress(email)) {
			throw new InvalidEmailException("Email is not valid. --> " + email); 
		}
		
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
	
	public List<Address> getAddresses() {
		return addresses;
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

    private boolean isValidEmailAddress(String email) {
    	//
    	System.out.println("email CHECK!!!!->" +email);
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
  //  	return true;
    }
}
