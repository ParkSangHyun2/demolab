/*
 * COPYRIGHT (c) NEXTREE Consulting 2014
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 6. 10.
 */

package util;

public enum TalkingAt {
	//
	Left(0), 
	Middle(3), 
	Right(6); 
	
	private int tabCount; 
	
	private TalkingAt(int tabCount) {
		this.tabCount = tabCount; 
	}
	
	public int tabCount() {
		return tabCount; 
	}
}