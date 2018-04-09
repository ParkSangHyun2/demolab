package step4_2.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import step1.share.domain.entity.club.TravelClub;
import step4_2.mybatis.impl.ClubProviderImpl;

class ClubProviderImplTest {
	//
	@Test
	void testCreate() {
		ClubProviderImpl clubProvider = new ClubProviderImpl();
		TravelClub club = TravelClub.getSample(true);
		clubProvider.create(club);
	}

	@Test
	void testRetrieve() {
		ClubProviderImpl clubProvider = new ClubProviderImpl();
		TravelClub club = null;
		club = clubProvider.retrieve("30");
		System.out.print(club);
		assertNotNull(club);
	}

	@Test
	TravelClub testRetrieveByName() {
		ClubProviderImpl clubProvider = new ClubProviderImpl();
		TravelClub club = null;
		club = clubProvider.retrieveByName("JavaTravelClub");
		System.out.print(club);
		assertNotNull(club);
		return club;
	}

	@Test
	void testUpdate() {
		ClubProviderImpl clubProvider = new ClubProviderImpl();
		TravelClub club = this.testRetrieveByName();
		club.setIntro("Updated Intro");
		clubProvider.update(club);
		System.out.print(club);
	}

	@Test
	void testDelete() {
		ClubProviderImpl clubProvider = new ClubProviderImpl();
		this.testCreate();
		TravelClub club = this.testRetrieveByName();
		clubProvider.delete(club.getId());
	}

	@Test
	void testExists() {
		ClubProviderImpl clubProvider = new ClubProviderImpl();
		TravelClub club = this.testRetrieveByName();
		assertTrue(clubProvider.exists(club.getId()));
	}

	@Test
	void testRetrieveAll() {
		ClubProviderImpl clubProvider = new ClubProviderImpl();
		List<TravelClub> clubList = new ArrayList<>();
		clubList = clubProvider.retrieveAll();
		System.out.print("Clubs : " + clubList);
	}

}
