package step4_2.mybatis.impl;

import step4_2.mybatis.provider.BoardProvider;
import step4_2.mybatis.provider.ClubProvider;
import step4_2.mybatis.provider.MemberProvider;
import step4_2.mybatis.provider.PostingProvider;
import step4_2.mybatis.provider.ProviderLycler;

public class ProviderImplLycler implements ProviderLycler {
	//
	private static ProviderLycler lycler;

	private BoardProvider boardProvider;
	private ClubProvider clubProvider;
	private MemberProvider memberProvider;
	private PostingProvider postingProvider;

	public synchronized static ProviderLycler shareInstance() {
		//
		if(lycler == null) {
			lycler = new ProviderImplLycler();
		}
		return lycler;
	}

	@Override
	public BoardProvider requestBoardProvider() {
		//
		if(boardProvider == null) {
			boardProvider = new BoardProviderImpl();
		}
		return boardProvider;
	}

	@Override
	public ClubProvider requestClubProvider() {
		// 
		if(clubProvider == null) {
			clubProvider = new ClubProviderImpl();
		}
		return clubProvider;
	}

	@Override
	public MemberProvider requestMemberProvider() {
		//
		if(memberProvider == null) {
			memberProvider = new MemberProviderImpl();
		}
		return memberProvider;
	}

	@Override
	public PostingProvider requestPostingProvider() {
		//
		if(postingProvider == null) {
			postingProvider = new PostingProviderImpl();
		}
		return postingProvider;
	}
}
