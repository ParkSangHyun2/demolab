/*
 * COPYRIGHT (c) Nextree Consulting 2015
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang Hyoungkoo</a>
 * @since 2015. 2. 24.
 */
package step1.share.service.logic;

public interface ServiceLycler {
	//
	public BoardService createBoardService(); 
	public ClubService createClubService(); 
	public MemberService createMemberService(); 
	public PostingService createPostingService(); 
}