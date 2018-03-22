package step4_2.impl;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import step1.share.domain.entity.board.Posting;
import step1.share.domain.entity.board.SocialBoard;
import step1.share.domain.entity.club.TravelClub;
import step4_2.mybatis.impl.PostingProviderImpl;

class PostingProviderTest {

	@Test
	void testExists() {
		PostingProviderImpl postingProvider = new PostingProviderImpl();
	}

	@Test
	void testCreate() {
		PostingProviderImpl postingProvider = new PostingProviderImpl();
		Posting posting = new Posting();
		posting.setBoardId("30");
		posting.setContents("adad");
		posting.setReadCount(0);
		posting.setTitle("TITLE");
		posting.setWriterEmail("pus5684@naver.com");
		posting.setWrittenDate("2200818");
		postingProvider.create(posting);
	}

	@Test
	void testRetrieve() {
		fail("Not yet implemented");
	}

	@Test
	void testRetrieveByBoardId() {
		fail("Not yet implemented");
	}

	@Test
	void testRetrieveByTitle() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	void testMain() {
		fail("Not yet implemented");
	}

}
