package step4_1.store;

import step1.share.service.store.BoardStore;
import step1.share.service.store.ClubStore;
import step1.share.service.store.ClubStoreLycler;
import step1.share.service.store.MemberStore;
import step1.share.service.store.PostingStore;

public class DBStoreLycler implements ClubStoreLycler{
	//
	private static ClubStoreLycler lycler;
	
	private BoardStore boardStore;
	private ClubStore clubStore;
	private MemberStore memberStore;
	private PostingStore postingStore;
	
	public synchronized static ClubStoreLycler shareInstance() {
		//
		if(lycler == null) {
			lycler = new DBStoreLycler();
		}
		
		return lycler;
	}

	@Override
	public MemberStore requestMemberStore() {
		//
		if(memberStore == null) {
			memberStore = new MemberDBStore();
		}
		return memberStore;
	}

	@Override
	public ClubStore requestClubStore() {
		//
		if(clubStore == null) {
			clubStore = new ClubDBStore();
		}
		return clubStore;
	}

	@Override
	public BoardStore requestBoardStore() {
		//
		if(boardStore == null) {
			boardStore = new BoardDBStore();
		}
		return boardStore;
	}

	@Override
	public PostingStore requestPostingStore() {
		//
		if(postingStore == null) {
			postingStore = new PostingDBStore();
		}
		return postingStore;
	}

}
