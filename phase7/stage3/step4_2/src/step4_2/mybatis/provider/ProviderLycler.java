package step4_2.mybatis.provider;

public interface ProviderLycler {
	//
	public BoardProvider requestBoardProvider();
	
	public ClubProvider requestClubProvider();
	
	public MemberProvider requestMemberProvider();
	
	public PostingProvider requestPostingProvider();
}
