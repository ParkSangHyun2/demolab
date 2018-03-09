/*
 * COPYRIGHT (c) NEXTREE Consulting 2014
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 6. 10.
 */

package step4.util;

public class ClubDuplicationException extends RuntimeException {
	//
	private static final long serialVersionUID = -4246979292397269539L;

	public ClubDuplicationException(String message) {
		super(message); 
	}
}