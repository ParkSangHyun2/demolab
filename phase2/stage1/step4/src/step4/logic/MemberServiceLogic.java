/*
 * COPYRIGHT (c) NEXTREE Consulting 2016
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:eykim@nextree.co.kr">Kim, Eunyoung</a>
 * @since 2016. 7. 12.
 */
package step4.logic;

import java.util.ArrayList;
import java.util.List;

import entity.club.CommunityMember;
import entity.dto.MemberDto;
import step4.da.map.ClubStoreMapLycler;
import step4.service.MemberService;
import step4.store.MemberStore;
import step4.util.MemberDuplicationException;
import step4.util.NoSuchMemberException;
import util.InvalidEmailException;

public class MemberServiceLogic implements MemberService {
	//
	private MemberStore memberStore;

	public MemberServiceLogic() {
		//
		memberStore = ClubStoreMapLycler.getInstance().requestMemberStore();
	}

	@Override
	public void register(MemberDto newMemberDto) throws InvalidEmailException {
		//
		String email = newMemberDto.getEmail();
		CommunityMember member = memberStore.retrieve(email);
		if (member != null) {
			throw new MemberDuplicationException("It is already exist the member email: " + email);
		}
		
		memberStore.create(newMemberDto.toMember());
	}

	@Override
	public MemberDto find(String memberEmail) {
		//
		CommunityMember member = memberStore.retrieve(memberEmail);

		if (member == null) {
			throw new NoSuchMemberException("No such a member with email: " + memberEmail);
		}
		return new MemberDto(member);
	}

	@Override
	public List<MemberDto> findByName(String memberName) {
		//
		List<CommunityMember> members = memberStore.retrieveByName(memberName);

		List<MemberDto> memberDtos = new ArrayList<>();
		for (CommunityMember member : members) {
			memberDtos.add(new MemberDto(member));
		}
		return memberDtos;
	}

	@Override
	public void modify(MemberDto memberDto) throws InvalidEmailException {
		//
		CommunityMember targetMember = memberStore.retrieve(memberDto.getEmail());
		if (targetMember == null) {
			throw new NoSuchMemberException("No such a member with email: " + memberDto.getEmail());
		}
		
		// modify if only user inputs some value.
		if (memberDto.getName() != null && !memberDto.getName().isEmpty()) {
			targetMember.setName(memberDto.getName());
		}
		if (memberDto.getNickName() != null && !memberDto.getNickName().isEmpty()) {
			targetMember.setNickName(memberDto.getNickName());
		}
		if (memberDto.getPhoneNumber() != null && !memberDto.getPhoneNumber().isEmpty()) {
			targetMember.setPhoneNumber(memberDto.getPhoneNumber());
		}
		if (memberDto.getBirthDay() != null && !memberDto.getBirthDay().isEmpty()) {
			targetMember.setBirthDay(memberDto.getBirthDay());
		}
		
		memberStore.update(targetMember);
	}
	

	@Override
	public void remove(String memberId) {
		//
		if (!memberStore.exists(memberId)) {
			throw new NoSuchMemberException("No such a member with email: " + memberId);
		}

		memberStore.delete(memberId);
	}

}
