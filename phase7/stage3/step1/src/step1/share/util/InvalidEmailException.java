/*
 * COPYRIGHT (c) NEXTREE Consulting 2014
 * This software is the proprietary of NEXTREE Consulting CO.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 6. 10.
 */

package step1.share.util;
public class InvalidEmailException extends RuntimeException {
	//
	private static final long serialVersionUID = -8812955226330753784L;

	public InvalidEmailException(String message) {
		super(message);
	}
}