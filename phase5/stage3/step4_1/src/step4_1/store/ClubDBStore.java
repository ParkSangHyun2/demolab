package step4_1.store;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import step1.share.domain.entity.club.ClubMembership;
import step1.share.domain.entity.club.TravelClub;
import step1.share.service.store.ClubStore;
import step1.share.util.MemberDuplicationException;
import step4_1.store.io.ClubQuery;
import step4_1.store.io.MembershipQuery;

public class ClubDBStore implements ClubStore {
	//
	private ClubQuery clubQuery;
	private MembershipQuery membershipQuery;

	public ClubDBStore() {
		//
		this.clubQuery = new ClubQuery();
		this.membershipQuery = new MembershipQuery();
	}

	@Override
	public String create(TravelClub club) {
		//
		if (clubQuery.exists(club.getId())) {
			throw new MemberDuplicationException("Member already exists with email: " + club.getId());
		}

		clubQuery.write(club);
		return club.getId();
	}

	@Override
	public TravelClub retrieve(String clubId) {
		//
		TravelClub club = clubQuery.read(clubId);
		List<ClubMembership> membershipList = membershipQuery.readByClubId(clubId);
		club.getMembershipList().addAll(membershipList);
		return club;
	}

	@Override
	public TravelClub retrieveByName(String name) {
		//
		TravelClub club = clubQuery.readByName(name);
		List<ClubMembership> membershipList = membershipQuery.readByMemberName(name);
		if(!membershipList.isEmpty()) {
			club.getMembershipList().addAll(membershipList);
		}
		return club;
	}

	@Override
	public void update(TravelClub club) {
		//
		if (!clubQuery.exists(club.getId())) {
			throw new NoSuchElementException("No such a element: " + club.getId());
		}
		
		if(membershipQuery.createMembershipForClub(club)) {
			return;
		}
		clubQuery.update(club);
		membershipQuery.checkDeletedMembershipForClub(club);
		membershipQuery.checkCreatedMembershipForClub(club);
	}

	@Override
	public void delete(String clubId) {
		//
		clubQuery.delete(clubId);
	}

	@Override
	public boolean exists(String clubId) {
		//
		return clubQuery.exists(clubId);
	}

	public static void main(String[] args) {
		//
		ClubDBStore store = new ClubDBStore();
		TravelClub club = TravelClub.getSample(false);
		String clubId = store.create(club);
		TravelClub readClub = store.retrieve(clubId);
		System.out.println("read club: " + readClub.toString());
	}

	@Override
	public List<TravelClub> retrieveAll() {
		//
		List<TravelClub> clubs = new ArrayList<>();
		List<TravelClub> resultClubs = new ArrayList<>();
		List<ClubMembership> memberships = new ArrayList<>();

		clubs = clubQuery.readAll();
		for (TravelClub club : clubs) {
			memberships = membershipQuery.readByClubId(club.getId());
			club.getMembershipList().addAll(memberships);
			resultClubs.add(club);
		}

		return clubs;
	}
}