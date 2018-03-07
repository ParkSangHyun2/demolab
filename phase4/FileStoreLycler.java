package step3.server.transfer.stub.store;

import step1.share.service.store.BoardStore;
import step1.share.service.store.ClubStore;
import step1.share.service.store.ClubStoreLycler;
import step1.share.service.store.MemberStore;
import step1.share.service.store.PostingStore;

public class FileStoreLycler implements ClubStoreLycler{
	//
	private static ClubStoreLycler lycler;
	
	private BoardStore boardStore;
	private ClubStore clubStore;
	private MemberStore memberStore;
	private PostingStore postingStore;
	
	public synchronized static ClubStoreLycler shareInstance() {
		//
		if(lycler == null) {
			lycler = new FileStoreLycler();
		}
		
		return lycler;
	}

	@Override
	public MemberStore requestMemberStore() {
		//
		if(memberStore == null) {
			memberStore = new MemberStoreStub();
		}
		return memberStore;
	}

	@Override
	public ClubStore requestClubStore() {
		//
		if(clubStore == null) {
			clubStore = new ClubStoreStub();
		}
		return clubStore;
	}

	@Override
	public BoardStore requestBoardStore() {
		//
		if(boardStore == null) {
			boardStore = new BoardStoreStub();
		}
		return boardStore;
	}

	@Override
	public PostingStore requestPostingStore() {
		//
		if(postingStore == null) {
			postingStore = new PostingStoreStub();
		}
		return postingStore;
	}

}
