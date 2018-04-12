package step4_1.clubfile;

import org.junit.jupiter.api.Test;

import entity.board.Posting;
import entity.board.SocialBoard;
import entity.club.TravelClub;
import step4_1.da.file.PostingFileStore;

class TestPotingFile {
	//
	PostingFileStore postingStore = new PostingFileStore();
	
	@Test
	void testCreate() {
		//
		Posting posting = new Posting(new SocialBoard().getSample(new TravelClub().getSample(true)),"title","pus5684@naver.com","contents");
		
		postingStore.create(posting);
	}

}
