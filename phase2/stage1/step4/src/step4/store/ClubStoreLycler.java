package step4.store;

public interface ClubStoreLycler {
	//
	public MemberStore requestMemberStore(); 
	public ClubStore requestClubStore(); 
	public BoardStore requestBoardStore(); 
	public PostingStore requestPostingStore(); 
}