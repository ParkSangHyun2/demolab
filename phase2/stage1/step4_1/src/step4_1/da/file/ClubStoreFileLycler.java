package step4_1.da.file;

import step4_1.store.BoardStore;
import step4_1.store.ClubStore;
import step4_1.store.ClubStoreLycler;
import step4_1.store.MemberStore;
import step4_1.store.PostingStore;

public class ClubStoreFileLycler implements ClubStoreLycler {
	//
	private static ClubStoreLycler instance;
	
	private ClubStoreFileLycler() {
		//
	}
	
	public synchronized static ClubStoreLycler shareInstance() {
		//
		if (instance == null) {
			instance = new ClubStoreFileLycler();
		}
		return instance;
	} 
	
	@Override
	public MemberStore requestMemberStore() {
		//
		return new MemberFileStore();
	}

	@Override
	public ClubStore requestClubStore() {
		//
		return new ClubFileStore();
	}

	@Override
	public BoardStore requestBoardStore() {
		//
		return new BoardFileStore();
	}

	@Override
	public PostingStore requestPostingStore() {
		//
		return new PostingFileStore();
	}
}
