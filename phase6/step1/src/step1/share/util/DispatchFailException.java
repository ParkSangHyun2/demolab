package step1.share.util;

public class DispatchFailException extends RuntimeException {
	//
	private static final long serialVersionUID = 1457900373488196665L;
	
	public DispatchFailException(String message) {
		super(message); 
	}
}